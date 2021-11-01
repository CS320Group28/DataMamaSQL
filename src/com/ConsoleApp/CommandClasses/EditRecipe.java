package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

import java.sql.SQLException;
import java.util.Scanner;

public class EditRecipe {
    public static void EditRecipeCLI(User user, Recipe recipe, DBInterface db) throws SQLException {
        Scanner scan = new Scanner(System.in);
        boolean valid = false;
        while(!valid) {
            System.out.println(recipe.recipeRepresentation());
            System.out.println("\t1. Recipe Name");
            System.out.println("\t2. Description");
            System.out.println("\t3. Steps");
            System.out.println("\t4. Cook Time");
            System.out.println("\t5. Servings");
            System.out.println("\t6. Difficulty");
            System.out.println("\t7. Submit Changes");
            System.out.print(">> ");
            try{
                int select = scan.nextInt();
                scan.nextLine();
                switch (select){
                    case 1:
                        System.out.println("New recipe name:");
                        recipe.setRecipeName(scan.nextLine());
                        break;
                    case 2:
                        System.out.println("New description:");
                        recipe.setDescription(scan.nextLine());
                        break;
                    case 3:
                        System.out.println("New steps:");
                        recipe.setSteps(stepsLoop(scan));
                        break;
                    case 4:
                        System.out.println("New cook time:");
                        recipe.setCookTime(Integer.parseInt(scan.nextLine().strip()));
                        break;
                    case 5:
                        System.out.println("New servings:");
                        recipe.setServings(Integer.parseInt(scan.nextLine().strip()));
                        break;
                    case 6:
                        System.out.println("New difficulty:");
                        System.out.println("\tenter number between 1 and 5");
                        System.out.print(">> ");
                        recipe.setDifficulty(Recipe.Difficulty.values()[Integer.parseInt(scan.nextLine().strip())]);
                        break;
                    case 7:
                        System.out.println("Saving changes...");
                        recipe.UpdateEntity();
                        valid = true;
                        break;
                    default:
                        System.out.println("Enter the line item number.");
                        break;
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("Index out of bounds");
            }
            catch(IllegalArgumentException e){
                System.out.println("Must be an integer.");
            }
        }
    }
    public static String stepsLoop(Scanner scan){
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
        return sb.toString();
    }
}
