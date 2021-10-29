package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.rowset.serial.SerialRef;

public class SortRecipes {

    private static final Scanner scan = new Scanner(System.in);
    private static int ad;

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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
            String sql = String.format("select \"RecipeID\", \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\" where lower(\"RecipeName\") like '%%%s%%'", name.toLowerCase());
            Statement stmt = db.getStatement();
            formatRS(stmt.executeQuery(sql));
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void SelectByID(DBInterface db){
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
                String steps = rs.getString("steps");
                System.out.println("You are viewing " + recipeName); // add "by ..."
                System.out.println("Description: " + description + "\n");
                System.out.printf("This will take %d minutes.\n\n", cookTime);
                System.out.println("STEPS");
                System.out.println(steps);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }



    /***
     * a simple helper function that prints the ResultSet obtained by sorting the recipes in the proper format.
     * @param rs
     * @throws SQLException
     */
    private static void formatRS(ResultSet rs) throws SQLException{
        if(rs == null){
            System.err.println("error retrieving results");
            return;
        }
        while(rs.next()){
            int recipeID = rs.getInt("RecipeID");
            String recipeName = rs.getString("RecipeName");
            String rating =  rs.getString("Rating");
            String creationDate = rs.getString("CreationDate");

            System.out.print("RecipeName: " + String.format("%1$-32s", recipeName)); // 1$ indicates the first argument of the string
                                                                                     // -32 indicates a right padded string of 32 characters
            System.out.print(" ID: " + String.format("%d", recipeID));
            System.out.print(" Rating: " + String.format("%1$-4s", rating));
            System.out.println(" CreationDate: " + creationDate);
        }

    }
}
