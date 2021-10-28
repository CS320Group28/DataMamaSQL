package com.ConsoleApp.CommandClasses;
import com.EntityClasses.User;

import com.DBInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

import static com.ConsoleApp.CommandClasses.CreateAccount.CreateAccountCLI;

public class Login {
    private static final Scanner in = new Scanner(System.in);

    public static User WelcomeCLI(DBInterface db){

        boolean exit = false;
        boolean isLogged = false;
        User user = null;

        while(!exit){
            try {
                int selection = 0;
                System.out.println("Menu:");
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print(">> ");
                selection = in.nextInt();
                switch (selection) {
                    case 1:
                        user = LoginCLI(db);
                        if (user != null) {
                            if(updateLastLogin(db, user)) {
                                isLogged = true;
                                exit = true;
                            }else{
                                System.err.println("Failed to update user last login time.");
                                continue;
                            }
                        }
                        break;
                    case 2:
                        CreateAccountCLI(db);
                        isLogged = false;
                        break;
                    case 3:
                        isLogged = false;
                        exit = true;
                        break;
                    default:
                        isLogged = false;
                        System.out.println(selection + " is not a valid input.");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Enter the number of the list item.");
                in.nextLine();
            }
        }
        return user;
    }

    public static User LoginCLI(DBInterface db){
        System.out.println("RecipeMate Login");
        in.nextLine();
        System.out.println("Enter your username:");
        String username = in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        User user = null;

        ResultSet rs;
        PreparedStatement stmt = db.getPreparedStatement("SELECT * FROM \"User\" where Username = ? AND UserPassword = ?");
        try {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
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

    private static boolean updateLastLogin(DBInterface db, User user){
        User temp = new User(db);
        temp.setUserName(user.getUserName());
        temp.setPassword(user.getPassword());
        temp.setCreationDate(user.getCreationDate());
        temp.setLastAccessDate(LocalDateTime.now());
        return temp.UpdateEntity();
    }

    public static void main(String[] args){
        DBInterface db = new DBInterface();
        WelcomeCLI(db);
        try {
            db.endSSH();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
