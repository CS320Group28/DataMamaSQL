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

    public static Recipe CreateRecipeCLI(DBInterface db, User user){

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
        try {
            PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\" from \"Recipe\" where \"RecipeName\" = ?"
                                                        + " and \"CreationDate\" = ?");
            stmt.setString(1, recipeName);
            stmt.setObject(2, recipe.getCreationDate());
            ResultSet rs = db.execStatementQuery(stmt);

            if(rs.next()) {
                rid = rs.getInt(1);
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rid < 0){
            System.err.println("rId was not able to be retrieved, try again.");
            return null; //broken as hell PDM wins
        }

        setupIngredients(db, rid);
        relateToAuthor(db, rid, user);
        //scan.close();

        return recipe;
    }
    private static void setupIngredients(DBInterface db, int rid){


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

    public static void relateToAuthor(DBInterface db, int rid, User user){
        Authors author = new Authors(db);
        Map<String, Object> authorsMap = new HashMap<>();
        authorsMap.put("recipeid", rid);
        authorsMap.put("username", user.getUserName());
        author.configEntity(authorsMap);
        author.InsertEntity();
    }
}
