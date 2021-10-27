package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.Recipe;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        return recipe;
    }
}
