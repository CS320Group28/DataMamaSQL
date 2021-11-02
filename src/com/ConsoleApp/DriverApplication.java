package com.ConsoleApp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.*;
import com.ConsoleApp.CommandClasses.*;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

public class DriverApplication {

    /**
     * We can create instance of the commandclass to inject the db into, which contains the logic for interacting with the database.
     * 
     * We can then pass the db to the entity that is created in the commandclass.
     */

    private static final DBInterface db = new DBInterface();
    private static final Scanner scan = new Scanner(System.in);


    public static void main (String[] args) {
        boolean close = false;
        int select;
        int rSort;
        boolean logged = false;
        boolean running = true;
        System.out.println("Welcome to the Recipe App!");

        while (running) {
            //this ArrayList should have a user value at index 0 and a boolean value at index 1, these will be used to
            //(0)set the current user and (1) indicate whether or not the program should continue running based on the
            //values obtained in the login.WelcomeCLI loop.
            ArrayList<Object> welcomeLoopResults = Login.WelcomeCLI(db);
            User user = (User)welcomeLoopResults.get(0);
            running = (boolean)welcomeLoopResults.get(1);

            //if the user has logged in the user value will not be null, in this case we want close to be false so the
            //menu loop will execute and logged to be true since the user is logged in and should have access to the
            //options in the menu loop
            if (user != null) {
                logged = true;
                close = false;
            }

            //if the user is logged in, proceed with execution
            if (logged) {
                //this represents the main menu loop and should only be visible if the user is logged in
                //it gives the user options to manipulate the recipe database in various ways
                while (!close) {
                    //these print statements represent the options a user is given to manipulate the database
                    System.out.println("\t1. Add Category");
                    System.out.println("\t2. Author Recipe");
                    System.out.println("\t3. Search Recipes");
                    System.out.println("\t4. Add ingedients to your pantry");
                    System.out.println("\t5. Make a recipe");
                    System.out.println("\t6. Edit or Delete Recipe");
                    System.out.println("\t99. Logout");
                    System.out.println("\t100. Exit");

                    //we user a try block here to ensure the user is entering valid selection values for the menu
                    //options, valid options are integers.
                    try {
                        System.out.print(">> ");
                        select = scan.nextInt();
                        //this switch statement acts on the cases for the select value which shall be an integer
                        //the integer represents the list items in the menu
                        switch (select) {
                            //new category case
                            case 1:
                                System.out.println("creating new category...");
                                CreateCategory.CreateCategoryCLI(db);
                                break;
                            //new recipe case
                            case 2:
                                System.out.println("creating a new recipe...");
                                CreateRecipe.CreateRecipeCLI(db, user);
                                break;
                            //sort recipe case
                            case 3:
                                System.out.println("How would you like to sort the recipes?");
                                System.out.println("\t1. Sort by name");
                                System.out.println("\t2. Sort by rating");
                                System.out.println("\t3. Sort by most recent");
                                System.out.println("\t4. Search for recipes by name");
                                System.out.println("\t5. Select a recipe by ID");
                                System.out.print(">> ");
                                rSort = scan.nextInt();
                                //this switch acts on the cases for the rSort value which will be an integer
                                //representing items in the list
                                switch (rSort) {
                                    //name sorting case
                                    case 1:
                                        System.out.println("sorting by name...");
                                        SearchRecipes.SortByNameCLI(db);
                                        break;
                                    //rating sorting case
                                    case 2:
                                        System.out.println("sorting by rating...");
                                        SearchRecipes.SortByRatingCLI(db);
                                        break;
                                    //chronological sorting case
                                    case 3:
                                        System.out.println("sorting by most recent");
                                        SearchRecipes.SortByRecentCLI(db);
                                        break;
                                    case 4:
                                        System.out.println("preparing search by name...");
                                        SearchRecipes.SearchByNameCLI(db);
                                        break;
                                    case 5:
                                        System.out.println("prepared search by id...");
                                        SearchRecipes.SelectByID(db);
                                        break;
                                }
                                break;
                            // adding ingredients to pantry case
                            case 4:
                                System.out.println("Adding ingredients to your pantry...");
                                AddIngredients.AddIngredientsCLI(db, user);
                                break;

                            //make recipe case
                            case 5:
                                System.out.println("Here is our menu for sorting and searching recipes; after you're done, select a recipe with the ID");
                                System.out.println("\t1. Sort by name");
                                System.out.println("\t2. Sort by rating");
                                System.out.println("\t3. Sort by most recent");
                                System.out.println("\t4. Search for recipes by name");
                                System.out.println("\t5. Search for recipes by ingredient");
                                System.out.println("\t6. Search for recipes by category");
                                System.out.println("\t7. Make a recipe by ID");
                                System.out.print(">> ");
                                rSort = scan.nextInt();
                                //this switch acts on the cases for the rSort value which will be an integer
                                //representing items in the list
                                switch (rSort) {
                                    //name sorting case
                                    case 1:
                                        System.out.println("sorting by name...");
                                        SearchRecipes.SortByNameCLI(db);
                                        break;
                                    //rating sorting case
                                    case 2:
                                        System.out.println("sorting by rating...");
                                        SearchRecipes.SortByRatingCLI(db);
                                        break;
                                    //chronological sorting case
                                    case 3:
                                        System.out.println("sorting by most recent");
                                        SearchRecipes.SortByRecentCLI(db);
                                        break;
                                    case 4:
                                        System.out.println("preparing search by name...");
                                        SearchRecipes.SearchByNameCLI(db);
                                        break;
                                    case 5:
                                        System.out.println("preparing search by ingredient...");
                                        SearchRecipes.SearchByIngredientCLI(db);
                                        break;
                                    case 6:
                                        System.out.println("preparing search by category...");
                                        SearchRecipes.SearchByCategoryCLI(db);
                                        break;
                                    case 7:
                                        System.out.println("prepared search by id...");
                                        Recipe recipe = SearchRecipes.SelectByID(db);
                                        System.out.println(recipe);
                                        System.out.print("How would you like to scale this recipe? <scaler>: ");
                                        double scale = scan.nextDouble(); scan.nextLine();
                                        MakeRecipe.makeRecipe(db, user, recipe, scale);
                                        break;
                                }
                                break;

                            case 6:
                                System.out.println("Your Recipes: ");

                                //list of recipes to either edit or delete
                                ArrayList<Recipe> recipes = RecipeListForDeleteOrEdit.getRecipes(db, user);

                                //print the recipes from the list
                                for(int i = 0; i < recipes.size(); i++){
                                    System.out.println(i+1 + ".) " + recipes.get(i));
                                }

                                //selection loop for recipes
                                int recipeSelect = recipes.size();
                                //if the selection is not within the acceptable range, allow the user to try again
                                while(recipeSelect >= recipes.size() || recipeSelect < 0) {
                                    try {
                                        System.out.print(">> ");
                                        recipeSelect = scan.nextInt() - 1;
                                        scan.nextLine();
                                        if (recipeSelect >= recipes.size() || recipeSelect < 0) {
                                            System.out.println("Index out of acceptable range.");
                                        }
                                    }catch(Exception e){
                                        System.out.println("Enter the number representing the list item.");
                                        recipeSelect = recipes.size();
                                    }
                                }

                                //set the chosen recipe
                                Recipe chosenRecipe = recipes.get(recipeSelect);
                                System.out.println("Selected Recipe: " + chosenRecipe);
                                boolean valid = false;
                                //allow the user to edit or delete the recipe
                                while(!valid){
                                    System.out.println("1. Edit Recipe");
                                    System.out.println("2. Delete Recipe");
                                    int functionSelect = scan.nextInt();
                                    scan.nextLine();
                                    switch(functionSelect){
                                        case 1:
                                            EditRecipe.EditRecipeCLI(user, chosenRecipe, db);
                                            valid = true;
                                            break;
                                        case 2:
                                            System.out.println("Are you sure? (y/Y or n/N)");
                                            if(scan.nextLine().toLowerCase().equals("y")){
                                                chosenRecipe.DeleteEntity();
                                                valid = true;
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }

                                break;
                            //logout case
                            case 99:
                                System.out.println("Logging out...");
                                user = null;
                                logged = false;
                                close = true;
                                break;
                            //exit case
                            case 100:
                                close = true;
                                running = false;
                                break;
                            //case for when input type is correct but input domain is not
                            default:
                                System.out.println(select + " is not an option.");
                                break;
                        }

                    }catch(SQLException e){
                        scan.nextLine();
                        e.printStackTrace();
                        System.err.println("Something went wrong with the database.");
                    } catch (Exception e) {
                        scan.nextLine();
                        System.err.println(e.getMessage());
                        System.out.println("Enter the number of the list item.");
                    }
                    
                }
            }
        }
        scan.close();
        try {
            db.endSSH();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if the program is ending execution, close the scanner and the connection to the database
    }
}