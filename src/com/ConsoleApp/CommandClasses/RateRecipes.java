package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.DBInterface;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

public class RateRecipes {

    /// scannner will be closed later by driver since it's on stdin
    private static final Scanner scan = new Scanner(System.in);
    
    /**
     * CLI for rating recipes
     * @param db DBInterface instance
     * @param chosen Recipe object that contains at least the RecipeID of the chosen recipe
     * @param user User who is rating the recipe
     * @throws SQLException
     * 
     * NOTE: If a user has already rated the recipe, they will be rejected
     */
    public static void rateRecipeCLI(DBInterface db, Recipe chosen, User user) throws SQLException{
        int rid = chosen.getRecipeID();
        int rating = 0;

        while(true){
            System.out.print("Give a rating between 1 and 5: ");
            try{
                rating = scan.nextInt();
            }catch(InputMismatchException e){
                scan.nextLine();
                System.err.println("What you entered was not an integer");
                continue;
            }
            if(rating < 1 || rating > 5){
                System.out.println("Out of range rating");
                continue;
            }
            break;
        }
        ResultSet rslookup = db.getStatement()
                               .executeQuery(String.format("SELECT \"username\" FROM \"Ratings\" WHERE \"username\" = '%s'", user.getUserName()));
        if(rslookup.next()){
            System.out.println("You have already rated this recipe");
            rslookup.close();
            return;
        }
        rslookup.close();

        String sql = "INSERT INTO \"Ratings\" VALUES(?, ?, ?)";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        stmt.setInt(1, rid);
        stmt.setString(2, user.getUserName());
        stmt.setInt(3, rating);
        db.execStatementUpdate(stmt);

    }
    
}
