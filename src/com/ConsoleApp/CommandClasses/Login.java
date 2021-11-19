package com.ConsoleApp.CommandClasses;
import com.EntityClasses.User;

import com.DBInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static com.ConsoleApp.CommandClasses.CreateAccount.CreateAccountCLI;

public class Login {
    private static final Scanner in = new Scanner(System.in);

    /**
     * CLI to be displayed as a welcome, first thing seen.
     * @param db
     * @return
     */
    public static ArrayList<Object> WelcomeCLI(DBInterface db){

        boolean exit = false;
        boolean running = true;
        User user = null;

        //welcome menu loop
        while(!exit){
            try {
                int selection = 0;
                //options to select from
                System.out.println("Menu:");
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print(">> ");
                //new scanner
                selection = in.nextInt();
                switch (selection) {
                    //login case
                    case 1:
                        //user from the LoginCLI method
                        user = LoginCLI(db);
                        //if user has logged in exit the program and update the last login time for the user
                        if (user != null) {
                            if(updateLastLogin(db, user)) {
                                exit = true;
                            }else{
                                System.err.println("Failed to update user last login time.");
                                continue;
                            }
                        }
                        else{
                            System.out.println("Failed to login.");
                        }
                        break;
                    //create account case
                    case 2:
                        CreateAccountCLI(db);
                        break;
                    //exit case
                    case 3:
                        exit = true;
                        running = false;
                        break;
                    //input is valid but input is out of appropriate domain
                    default:
                        System.out.println(selection + " is not a valid input.");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Enter the number of the list item.");
                in.nextLine();
            }
        }
        //the 0 index is the user and the 1 index is a boolean used to determine if the program should continue
        //execution in the loop that contains a call to this static method
        ArrayList<Object> results = new ArrayList<Object>();
        results.add(user);
        results.add(running);
        return results;
    }

    /**
     * CLI for a user to login to the database
     * @param db
     * @return
     */
    public static User LoginCLI(DBInterface db){
        //allow the user to enter the username and password
        System.out.println("RecipeMate Login");
        in.nextLine();
        System.out.println("Enter your username:");
        String username = in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        User user = null;

        //create sql statement to check if this combo exists
        ResultSet rs;
        PreparedStatement stmt = db.getPreparedStatement("SELECT * FROM \"User\" where Username = ? AND UserPassword = ?");
        try {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            //if the user exists make a new user object
            if(rs.next()) {
                System.out.println("Login successful.");
                user = new User((String) rs.getObject(1), (String) rs.getObject(3),
                        ((Timestamp)rs.getObject(2)).toLocalDateTime(), ((Timestamp)rs.getObject(4)).toLocalDateTime());
            }
            else{
                System.out.println("Login failed, invalid username or password.");
                return null;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("something went wrong");
            in.nextLine();
        }
        return user;
    }

    /**
     * Update the lastAccessDate of a user 
     * @param db 
     * @param user
     * @return
     */
    private static boolean updateLastLogin(DBInterface db, User user){
        User temp = new User(db);
        temp.setUserName(user.getUserName());
        temp.setPassword(user.getPassword());
        temp.setCreationDate(user.getCreationDate());
        temp.setLastAccessDate(LocalDateTime.now());
        return temp.UpdateEntity();
    }
}
