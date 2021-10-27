package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.Ingredient;
import com.EntityClasses.Recipe;
import com.EntityClasses.Requires;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CreateRecipe {

    private static Scanner scan = new Scanner(System.in);

    public static Recipe CreateRecipeCLI(DBInterface db){

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

        int id = -1;
        try {
            PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\" from \"Recipe\" where \"RecipeName\" = ?"
                                                        + " and \"CreationDate\" = ?");
            stmt.setString(1, recipeName);
            stmt.setObject(2, recipe.getCreationDate());
            ResultSet rs = db.execStatementQuery(stmt);

            if(rs.next()) {
                id = rs.getInt(1);
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setupIngredients(db, id);
        if(id < 0){
            return null; //broken as hell PDM wins
        }
        return recipe;
    }
    private static void setupIngredients(DBInterface db, int id){


        System.out.println("Enter list of ingredients and the required quantity below.");
        String ingredients = "";

        String ingredient = "";
        // Prompt for ingredients one at time
        while(true){
            System.out.print("<Name> <Quantity>: ");
            ingredient = scan.nextLine();
            if(ingredient.equals("quit")){
                scan.close();
                break;
            }
            // [name, amount]
            String[] tokens = ingredient.split("\\s+");
            tokens[0] = tokens[0].toLowerCase();
            // check if ingredient already exists, if not, create it
            try {
                PreparedStatement statement = db.getPreparedStatement("select count(1) from \"Ingredient\" where \"ingredientname\" = ? ");
                statement.setString(1, tokens[0]);
                ResultSet results = db.execStatementQuery(statement);
                results.next();
                if(results.getInt(1) > 0){
                    continue;
                }else{
                    Ingredient newIngredient = new Ingredient(db);
                    Map<String, Object> ingredientMap = new HashMap<>();
                    ingredientMap.put("ingredientname", tokens[0]);
                    newIngredient.configEntity(ingredientMap);
                    newIngredient.InsertEntity();
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Requires req = new Requires(db);
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("recipeid", id);
            reqMap.put("ingredientname", tokens[0]);
            reqMap.put("quantity", Integer.parseInt(tokens[1]));
            req.configEntity(reqMap);
            req.InsertEntity();
        }
    }
}
