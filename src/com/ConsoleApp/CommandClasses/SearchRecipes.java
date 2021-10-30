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

import javax.sql.rowset.serial.SerialRef;

public class SearchRecipes {

    private static final Scanner scan = new Scanner(System.in);
    private static int ad;
    private DBInterface db;
    public SearchRecipes(DBInterface db){
        this.db = db;
    }
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

    public static void SearchByName(DBInterface db){
        System.out.print("Enter a name to search: ");
        String name = scan.nextLine();
        
        try{
            System.out.printf("Searching database for %s...\n", name);
            String sql = String.format("select \"Authors\".\"username\", \"Recipe\".\"RecipeID\", \"Recipe\".\"RecipeName\", \"Recipe\".\"Rating\", \"Recipe\".\"CreationDate\" from \"Recipe\" inner join \"Authors\" on \"Authors\".\"recipeid\" = \"Recipe\".\"RecipeID\" where lower(\"RecipeName\") like '%%%s%%'", name.toLowerCase());
            Statement stmt = db.getStatement();
            formatRS(stmt.executeQuery(sql));
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

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
                LocalDateTime creationDate = (LocalDateTime)rs.getObject("CreationDate");
                System.out.println("You are viewing " + recipeName); // add "by ..."
                System.out.println("Description: " + description + "\n");
                System.out.printf("This will take %d minutes.\n\n", cookTime);
                System.out.println("STEPS");
                System.out.println(steps);

                Recipe recipe = new Recipe(db);
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
            System.out.print("RecipeName: " + String.format("%1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                     // -32 indicates a right padded string of 32 characters
            System.out.print(" ID: " + String.format("%1$-6d", recipeID));
            System.out.print(" Rating: " + String.format("%1$-4s", rating));
            System.out.print(" Created By: " + String.format("%1$-32s", author));
            System.out.println(" CreationDate: " + creationDate);
        }
    }

}