package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CreateRecipe {

    private static Scanner scan = new Scanner(System.in);


    /**
     * Handles all logic and user input for creating a Recipe and setting up relevant relations
     * @param db database interface to use with a connection.
     * @param user user that is associated with creating the recipe.
     */
    public static Recipe CreateRecipeCLI(DBInterface db, User user) throws SQLException{

        System.out.print("Enter recipe name: ");
        String recipeName = scan.nextLine();
   
        
        System.out.print("Enter the cooktime in minutes: ");
        int cookTime = scan.nextInt();
        scan.nextLine();

        System.out.print("Enter the difficult on a scale of 1-5: ");
        int difficulty = scan.nextInt();
        scan.nextLine();
        while(difficulty < 1 || difficulty > 5){
            System.out.print("Difficulty must between 1 and 5, Try again: ");
            difficulty = scan.nextInt();
            scan.nextLine();
        }

        System.out.print("Enter the number of servings this recipe makes: ");
        int servings = scan.nextInt();
        scan.nextLine();

        System.out.print("Enter a brief one-line description of the recipe: ");
        String description = scan.nextLine();

        String steps = "";
        String clause = "";
        StringBuilder sb = new StringBuilder(steps);
        System.out.println("Enter list of steps below. Pressing enter adds a single step. Enter only \"quit\" once finished");
        while(true){
            clause = scan.nextLine();
            if(clause.toLowerCase().equals("quit")){
                break;
            }
            sb.append(clause + "\n");
        }
        steps = sb.toString();

        Recipe recipe = new Recipe(db);
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("recipename", recipeName);
        recipeMap.put("description", description);
        recipeMap.put("cooktime", cookTime);
        recipeMap.put("steps", steps);
        recipeMap.put("servings", servings);
        recipeMap.put("difficulty", difficulty);
        recipe.configEntity(recipeMap);
        recipe.InsertEntity();


        int rid = -1;

        // Get the recipe ID that was generated for the recipe just made.
        PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\" from \"Recipe\" where \"RecipeName\" = ?"
                                                    + " and \"CreationDate\" = ?");
        stmt.setString(1, recipeName);
        stmt.setObject(2, recipe.getCreationDate());
        ResultSet rs = db.execStatementQuery(stmt);

        if(rs.next()) {
            rid = rs.getInt(1);
            stmt.close();
        }

        if(rid < 0){
            System.err.println("rId was not able to be retrieved, try again.");
            return null; //broken as hell PDM wins
        }

        setupIngredients(db, rid);
        relateToAuthor(db, rid, user);
        relateCategories(db, rid);
        
        return recipe;
    }


    private static void relateCategories(DBInterface db, int rid) throws SQLException{
        String category = null;
        do{
            System.out.print("Enter a category (or press enter to skip): ");
            category = scan.nextLine().strip(); 

            if(category.length() <= 0){
                return;
            }
            ResultSet cats = null;
            Statement hasCat = null;

            hasCat = db.getStatement();
            String sql = String.format("select \"CategoryID\" from \"Category\" where \"CategoryName\" = '%s'", category);
            cats = hasCat.executeQuery(sql);
            if(!cats.next()){
                Category c = new Category(db);
                c.setCategoryName(category);
                c.InsertEntity();
                cats = hasCat.executeQuery(sql);
                cats.next();         
            }
            do{ //if cats.next() is true, do this (before calling rs.next() again)
                int categoryID = cats.getInt(1);
                PreparedStatement st = db.getPreparedStatement("insert into \"HasCategory\" values(?, ?)");
                st.setInt(1, rid);
                st.setInt(2, categoryID);
                db.execStatementUpdate(st);
            }while(cats.next());

        }while(category.length() > 0);

    }

    /**
     * Setup the ingredient requirements for a new recipe, handles user input.
     * @param db database instance
     * @param rid recipe id
     */
    public static void setupIngredients(DBInterface db, int rid){


        System.out.println("Enter list of ingredients and the required quantity below.");
        //String ingredients = "";

        String ingredient = "";
        Ingredient ing = new Ingredient(db);
        // Prompt for ingredients one at time
        while(true){
            System.out.print("<Name>, <Quantity> (type \"quit\" to quit): ");
            ingredient = scan.nextLine();
            if(ingredient.equals("quit")){
                break;
            }
            // [name, amount]
            String[] tokens = ingredient.split("\\s*,\\s*");
            tokens[0] = tokens[0].toLowerCase();
            Map<String, Object> ingMap = new HashMap<>();
            ingMap.put("ingredientname", tokens[0]);
            ing.configEntity(ingMap);
            // check if ingredient already exists, if not, create it
            try {
                PreparedStatement stmt = db.getPreparedStatement("select * from \"Ingredient\" where \"ingredientname\" = ?");
                stmt.setString(1,tokens[0]);
                ResultSet rs = stmt.executeQuery();
                PreparedStatement insStmt = db.getPreparedStatement("insert into \"Requires\" values( ?, ?, ?);");
                if(rs.next()){
                    // set up requires relation if ingredient is already in table
                    insStmt.setInt(1, rid);
                    insStmt.setString(2, ing.getIngredientName());
                    insStmt.setInt(3, Integer.parseInt(tokens[1]));
                    db.execStatementUpdate(insStmt);
                    stmt.close();
                }
                else{
                    ing.InsertEntity();
                    insStmt = db.getPreparedStatement("insert into \"Requires\" values( ?, ?, ?);");
                    insStmt.setInt(1, rid);
                    insStmt.setString(2, ing.getIngredientName());
                    insStmt.setInt(3, Integer.parseInt(tokens[1]));
                    db.execStatementUpdate(insStmt);
                    insStmt.close();
                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void relateToAuthor(DBInterface db, int rid, User user){
        Authors author = new Authors(db);
        Map<String, Object> authorsMap = new HashMap<>();
        authorsMap.put("recipeid", rid);
        authorsMap.put("username", user.getUserName());
        author.configEntity(authorsMap);
        author.InsertEntity();
    }
}
