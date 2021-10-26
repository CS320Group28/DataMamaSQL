package com.ConsoleApp.CommandClasses;
import com.EntityClasses.User;

import com.DBInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Login {
    private static final Scanner in = new Scanner(System.in);

    public static void WelcomeCLI(DBInterface db){
        boolean exit = false;

        while(!exit){
            try {
                int selection = 0;
                System.out.println("Menu:");
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                selection = in.nextInt();
                switch (selection) {
                    case 1:
                        User user = LoginCli(db);
                        if(user == null){
                            break;
                        }
                        else{
                            continue;
                        }
                    case 2:
                        CreateUserCli(db);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println(selection + " is not a valid input.");
                }
            }
            catch(Exception e){
                System.out.println("Enter the number of the list item.");
                in.nextLine();
            }
        }
    }

    public static User LoginCli(DBInterface db){
        System.out.println("RecipeMate Login");
        System.out.println("Enter your username:");
        String username = in.nextLine();
        in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        User user = null;

        ResultSet rs;
        PreparedStatement stmt = db.getStatement("SELECT * FROM \"User\" where Username = ? AND UserPassword = ?");
        try {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs == null){
                System.out.println("Login failed, invalid username or password.");
                return null;
            }
            else{
                System.out.println("Login successful.");
                if(rs.next()) {
                    user = new User((String) rs.getObject(1), (String) rs.getObject(2),
                            (LocalDateTime) rs.getObject(3), (LocalDateTime) rs.getObject(4));
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("something went wrong");
            in.nextLine();
        }
        return user;
    }

    public static void CreateUserCli(DBInterface db){

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
