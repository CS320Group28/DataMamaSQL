package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SortRecipes {

    private static Scanner scan = new Scanner(System.in);
    private static int ad;

    public static void SortByNameCLI(DBInterface db){
        System.out.println("Sort by...");
        System.out.println("\t1. Ascending");
        System.out.println("\t2. Descending");
        System.out.print(">> ");
        ad = scan.nextInt();
        switch(ad){
            case 1:
                try {
                    System.out.println("sorting by ascending name...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    System.out.println("sorting by descending name...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
        System.out.println("\t1. Ascending");
        System.out.println("\t2. Descending");
        System.out.print(">> ");
        ad = scan.nextInt();

        switch(ad){
            case 1:
                try{
                    System.out.println("sorting by ascending rating...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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
                    System.out.println("sorting by descending rating...");
                    PreparedStatement stmt = db.getPreparedStatement("select \"RecipeName\", \"Rating\", \"CreationDate\" from \"Recipe\"" +
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


    private static void formatRS(ResultSet rs) throws SQLException{
        if(rs == null){
            System.err.println("error retrieving results");
            return;
        }
        while(rs.next()){
            String recipeName = rs.getString("RecipeName");
            String rating =  rs.getString("Rating");
            String creationDate = rs.getString("CreationDate");

            System.out.print("RecipeName: " + String.format("%1$-32s", recipeName));
            System.out.print("Rating: " + String.format("%1$-4s", rating));
            System.out.println("CreationDate: " + creationDate);
        }

    }
}
