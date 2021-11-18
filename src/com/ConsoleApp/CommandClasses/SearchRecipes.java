package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.*;
import com.EntityClasses.Recipe.Difficulty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class containing static methods for CLI's pertaining to ordering and selecting recipes.
 */
public class SearchRecipes {

    private static final Scanner scan = new Scanner(System.in);
    private static int ad;

    /**
     * CLI for sorting recipes by name lexicographically
     * @param db DBInterface instance
     */
    public static void SortByNameCLI(DBInterface db){
        System.out.println("Sort by...");
        System.out.println("\t1. Alphabetical");
        System.out.println("\t2. Reverse Alphabetical");
        System.out.print(">> ");
        ad = scan.nextInt(); // check for ascending or descending
        scan.nextLine();

        switch(ad){
            case 1:
                try {
                    System.out.println("sorting by name alphabetically...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"RecipeName\" asc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    break;
                }
            case 2:
                try {
                    System.out.println("sorting by name reverse alphabetically...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"RecipeName\" desc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
        }
    }

    /**
     * CLI for showing all recipes sorted by rating.
     * @param db DBInterface instance
     */
    public static void SortByRatingCLI(DBInterface db){
        System.out.println("Sort by...");
        System.out.println("\t1. Lowest Rated");
        System.out.println("\t2. Highest Rated");
        System.out.print(">> ");
        ad = scan.nextInt(); // check for ascending or descending
        scan.nextLine();

        switch(ad){
            case 1:
                try{
                    System.out.println("sorting by lowest rated...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"Rating\" asc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
            case 2:
                try{
                    System.out.println("sorting by highest rated...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"Rating\" desc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
        }

    }

    /**
     * CLI for printing all recipes sorted by creationdate
     * @param db DBInterface instance
     */
    public static void SortByRecentCLI(DBInterface db){
        System.out.println("Sort by...");
        System.out.println("\t1. Oldest");
        System.out.println("\t2. Newest");
        System.out.print(">> ");
        ad = scan.nextInt(); // check for ascending or descending
        scan.nextLine();
        switch(ad){
            case 1:
                try{
                    System.out.println("sorting by oldest...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"CreationDate\" asc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;

                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
            case 2:
                try{
                    System.out.println("sorting by newest...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                            "order by \"CreationDate\" desc;");
                    ResultSet rs = null;
                    rs=db.execStatementQuery(stmt);
                    formatRS(rs);
                    stmt.close();
                    break;

                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    break;
                }
        }

    }

    /**
     * CLI for showing recipes of a specific category
     * @param db DBInterface instance
     */
    public static void SearchByCategoryCLI(DBInterface db){
        System.out.println("Enter a category to search: ");
        String category = scan.nextLine();

        try{
            System.out.printf("Searching database for %s...\n", category);
            String sql = String.format("select * from \"Recipe\" inner join \"Authors\" on \"RecipeID\" = \"recipeid\" inner join (\"HasCategory\" inner join \"Category\" on \"categoryid\" = \"CategoryID\")\n" +
                    "            on \"HasCategory\".\"recipeid\" = \"Recipe\".\"RecipeID\"\n" +
                    "                where lower(\"Category\".\"CategoryName\") like '%%%s%%' order by \"Recipe\".\"RecipeName\" asc",category.toLowerCase() );
            Statement stmt = db.getStatement();
            formatRS(stmt.executeQuery(sql));
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * CLI for searching for recipes by name.
     * @param db DBInterface instance
     */
    public static void SearchByNameCLI(DBInterface db){
        System.out.print("Enter a name to search: ");
        String name = scan.nextLine();
        
        try{
            System.out.printf("Searching database for %s...\n", name);
            String sql = String.format("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\" where lower(\"RecipeName\") like '%%%s%%' order by \"Recipe\".\"RecipeName\"", name.toLowerCase());
            Statement stmt = db.getStatement();
            formatRS(stmt.executeQuery(sql));
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Select a recipe by a RecipeID
     * @param db DBInterface instance
     * @returns Recipe object with required data about the recipe.
     */
    public static Recipe SelectByID(DBInterface db){
        Integer id = null;
        while(id == null){
            System.out.print("Enter the ID of the recipe: ");
            try{
                id = Integer.parseInt(scan.nextLine().strip()); // cleans the buffer better
            }catch(Exception e){
                System.out.print("\nEnter a valid integer ID: ");
                id = Integer.parseInt(scan.nextLine().strip());
            }
        }
        int rid = id.intValue();
        String sql = "select * from \"Recipe\" where \"RecipeID\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        ResultSet rs = null;
        try {

            stmt.setInt(1, rid);
            rs = db.execStatementQuery(stmt);

            if(rs == null){
                System.err.println("error retrieving results");
            }
            if(rs.next()){
                String recipeName = rs.getString("RecipeName");
                String description = rs.getString("Description");
                int cookTime = rs.getInt("CookTime");
                String steps = rs.getString("Steps");
                int rating = rs.getInt("Rating");
                int difficulty = rs.getInt("Difficulty");
                int servings = rs.getInt("Servings");
                LocalDateTime creationDate = rs.getTimestamp("CreationDate").toLocalDateTime();
                System.out.println("You are viewing " + recipeName); // add "by ..."
                System.out.println("Description: " + description + "\n");
                System.out.printf("This will take %d minutes.\n\n", cookTime);
                System.out.println("STEPS");
                System.out.println(steps);

                Recipe recipe = new Recipe(db);
                recipe.setRecipeID(rid);
                recipe.setCookTime(cookTime);
                recipe.setCreationDate(creationDate);
                recipe.setDescription(description);
                recipe.setDifficulty(Difficulty.values()[difficulty]);
                recipe.setRating(rating);
                recipe.setRecipeName(recipeName);
                recipe.setServings(servings);
                recipe.setSteps(steps);
                return recipe;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /***
     * a simple helper function that prints the ResultSet obtained by sorting the recipes in the proper format.
     * @param rs
     * @throws SQLException
     * @returns the ID of the recipe
     */
    private static void formatRS(ResultSet rs) throws SQLException{
        if(rs == null){
            System.err.println("error retrieving results");
            return;
        }
        int recipeID;
        while(rs.next()){
            recipeID = rs.getInt("RecipeID");
            String recipeName = rs.getString("RecipeName");
            String rating =  rs.getString("Rating");
            String creationDate = rs.getString("CreationDate");
            String author = rs.getString("username");

            if(author == null){
                author = "Unknown Author";
            }
            System.out.print("RecipeName: " + String.format("RecipeName: %1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                     // -32 indicates a right padded string of 32 characters
            System.out.print(" ID: " + String.format("%1$-6d", recipeID));
            System.out.print(" Rating: " + String.format("%1$-4s", rating));
            System.out.print(" Created By: " + String.format("%1$-32s", author));
            System.out.println(" CreationDate: " + creationDate);
        }
    }

    /**
     * CLI for selecting recipes containing a specific ingredient.
     * @param db DBInterface
     * @throws SQLException
     */
    public static void SearchByIngredientCLI(DBInterface db) throws SQLException{
        System.out.print("Enter a ingredient to search for: ");
        String ingredient = scan.nextLine().strip();
        String sql = "SELECT * FROM \"Recipe\" INNER JOIN \"Requires\" ON \"Recipe\".\"RecipeID\" = \"Requires\".\"recipeid\" INNER JOIN \"Authors\" ON \"Recipe\".\"RecipeID\" = \"Authors\".recipeid where \"Requires\".\"ingredientname\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        stmt.setString(1, ingredient);
        ResultSet rs = db.execStatementQuery(stmt);
        formatRS(rs);
        stmt.close();
    }

    /**
     * Print out the 50 highest rated recipes in a pretty format
     * @param db DBInterface instance
     * @throws SQLException
     */
    public static void topFiftyRecipes(DBInterface db) throws SQLException{
        System.out.println("Grabbing highest rated recipes...");
        String sql = "SELECT * FROM \"Recipe\" INNER JOIN \"Authors\" ON \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\" ORDER BY \"Recipe\".\"Rating\" DESC";
        Statement stmt = db.getStatement();
        ResultSet rs = stmt.executeQuery(sql);

        for (int i = 0; i < 50; i++){
            if(rs.next()){
                int rid = rs.getInt("RecipeID");
                String recipeName = rs.getString("RecipeName");
                String rating =  rs.getString("Rating");
                String creationDate = rs.getString("CreationDate");
                String author = rs.getString("username");
    
                if(author == null){
                    author = "Unknown Author";
                }
                System.out.print("RecipeName: " + String.format("RecipeName: %1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                         // -32 indicates a right padded string of 32 characters
                System.out.print(" ID: " + String.format("%1$-6d", rid));
                System.out.print(" Rating: " + String.format("%1$-4s", rating));
                System.out.print(" Created By: " + String.format("%1$-32s", author));
                System.out.println(" CreationDate: " + creationDate);
            }
        }
        stmt.close();
    }

    /**
     * Get the fifty newest recipes and print them out
     * @param db DBinterface instance
     * @throws SQLException
     */
    public static void topFiftyNewestRecipes(DBInterface db) throws SQLException{
        System.out.println("Grabbing the newest recipes...");
        String sql = "SELECT * FROM \"Recipe\" INNER JOIN \"Authors\" ON \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\" ORDER BY \"Recipe\".\"CreationDate\" DESC";
    
        Statement stmt = db.getStatement();
        ResultSet rs = stmt.executeQuery(sql);

        for (int i = 0; i < 50; i++){
            if(rs.next()){
                int rid = rs.getInt("RecipeID");
                String recipeName = rs.getString("RecipeName");
                String rating =  rs.getString("Rating");
                String creationDate = rs.getString("CreationDate");
                String author = rs.getString("username");
    
                if(author == null){
                    author = "Unknown Author";
                }
                System.out.print("RecipeName: " + String.format("RecipeName: %1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                         // -32 indicates a right padded string of 32 characters
                System.out.print(" ID: " + String.format("%1$-6d", rid));
                System.out.print(" Rating: " + String.format("%1$-4s", rating));
                System.out.print(" Created By: " + String.format("%1$-32s", author));
                System.out.println(" CreationDate: " + creationDate);
            }
        }
        stmt.close();    
    }

    /**
     * The recipes that the user can make
     * @param db DBInterface 
     * @param user The user querying
     */
    public static void recipesUserCanMake(DBInterface db, User user) throws SQLException{

        // get a map of all of the user's ingredients
        String sql ="SELECT \"ingredientname\", SUM(\"quantitybought\")" +
                    "FROM \"PutsIntoPantry\"" +
                    "WHERE expirationdate > NOW() AND \"username\" = ? " +
                    "GROUP BY \"ingredientname\"";

        PreparedStatement stmt = db.getPreparedStatement(sql);
        stmt.setString(1, user.getUserName());
        ResultSet ingredentRS = db.execStatementQuery(stmt);

        // Turn this set into a hashmap.
        Map<String, Integer> ingredients = new HashMap<>();
        while(ingredentRS.next()){
            String name = ingredentRS.getString(1);
            int count = ingredentRS.getInt(2);
            ingredients.put(name, count);
        }
        stmt.close();
        ingredentRS.close();

        // Next, I'll just make a set of every single recipe id
        String sql2 = "SELECT \"RecipeID\" FROM \"Recipe\"";
        PreparedStatement stmt2 = db.getPreparedStatement(sql2);
        ResultSet allRecipeID = db.execStatementQuery(stmt2);
        Set<Integer> recipeIDs = new HashSet<>();
        while(allRecipeID.next()){
            recipeIDs.add(allRecipeID.getInt(1));
        }
        // lets try to not literally run out of memory
        stmt2.close();
        allRecipeID.close();

        // next get all the recipes and their ingredients
        String sql3 = "SELECT * FROM \"Requires\" INNER JOIN \"Recipe\" ON \"Requires\".\"recipeid\" = \"Recipe\".\"RecipeID\"" +
                      "INNER JOIN \"Authors\" A on \"Recipe\".\"RecipeID\" = A.recipeid ORDER BY \"Recipe\".\"Rating\" DESC;";
        PreparedStatement stmt3 = db.getScrollablePreparedStatement(sql3);
        ResultSet allRecipes = db.execStatementQuery(stmt3);

        // remove the recipeID of the recipes that can not be made from the set.
        while(allRecipes.next()){
            int RID = allRecipes.getInt(1); //first should be a rid
            String ingredientname = allRecipes.getString(2); // second should be ingredient name
            int quantityNeeded = allRecipes.getInt(3);
            
            if(ingredients.containsKey(ingredientname) && ingredients.get(ingredientname) >= quantityNeeded){
                continue;
            }else{
                recipeIDs.remove(RID); // can't make this
            }
        }
        // move the curser for the recipes back to the start
        allRecipes.beforeFirst();
        recipeIDs.forEach(System.out::println);
        // print the recipes
        Set<Integer> alreadyPrinted = new HashSet<>();
        while(allRecipes.next()){
            int RID = allRecipes.getInt(1);
            // if the recipe can be made and hasn't been printed yet.
            if((alreadyPrinted.contains(RID) || (!recipeIDs.contains(RID)))){
                continue;
            }else{
                alreadyPrinted.add(RID);
                int recipeID = allRecipes.getInt("RecipeID");
                String recipeName = allRecipes.getString("RecipeName");
                String rating =  allRecipes.getString("Rating");
                String creationDate = allRecipes.getString("CreationDate");
                String author = allRecipes.getString("username");
    
                if(author == null){
                    author = "Unknown Author";
                }
                System.out.print("RecipeName: " + String.format("RecipeName: %1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                         // -32 indicates a right padded string of 32 characters
                System.out.print(" ID: " + String.format("%1$-6d", recipeID));
                System.out.print(" Rating: " + String.format("%1$-4s", rating));
                System.out.print(" Created By: " + String.format("%1$-32s", author));
                System.out.println(" CreationDate: " + creationDate);            }
        }
        allRecipes.close();
        stmt3.close();
    }

    /**
     * Get the recipes that have been made by people who made the same recipes as a given user.
     * This method does not and must not recurse otherwise all recipes will most certainly be printed.
     * @param db DBInterface containing and instance of the database in use
     * @param user User object of the user making the request.
     * @throws SQLException
     */
    public static void getSuggestions(DBInterface db, User user) throws SQLException{
        // First, get all of the recipes a 
    }
}

