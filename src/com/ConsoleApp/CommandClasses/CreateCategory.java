package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.DBInterface;
import com.EntityClasses.Category;


public class CreateCategory {

    private static final Scanner in = new Scanner(System.in);

    public static void CreateCategoryCLI(DBInterface db){

        Category ct = new Category(db);
        System.out.println("Enter a Category");
        String categoryName = in.next();
        Map<String, Object> ctMap = new HashMap<>();
        ctMap.put("categoryName", categoryName);
        ct.configEntity(ctMap);


        PreparedStatement stmt = db.getPreparedStatement("SELECT * FROM \"Category\" where \"CategoryName\" = ?");
        ResultSet rs = null;

        try{
            stmt.setString(1, categoryName);
            rs = stmt.executeQuery();
            if(rs.next()){
                System.out.println("This category is already in the database.");
                return;
            }
        }
        catch(SQLException e){
            e.toString();
        }

        ct.InsertEntity();
        System.out.println("Category Successfully Created");
        return;



    }

}
