package com.ConsoleApp.CommandClasses;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.DBInterface;
import com.EntityClasses.User;

public class CreateAccount {
    private static final Scanner in = new Scanner(System.in);

    public static void CreateAccountCLI(DBInterface db){

        while(true){

            System.out.print("Enter a username: ");
            String username = in.next();
            System.out.print("Enter a password: ");
            String password = in.next();
            
            User user = new User(db);
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("Username", username);
            userMap.put("Password", password);
            user.configEntity(userMap);

            PreparedStatement stmt = db.getStatement("Select * From \"User\" where \"username\" = ?");
            ResultSet rs = null;
            try {
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                if(rs.next()){
                    System.out.println("Username already exists, try again.");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            user.InsertEntity();
            return;

        }
        
    }
    
}
