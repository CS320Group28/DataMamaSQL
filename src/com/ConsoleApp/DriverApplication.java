package com.ConsoleApp;
import java.sql.SQLException;
import java.util.Scanner;

import com.*;
import com.ConsoleApp.CommandClasses.CreateRecipe;
import com.EntityClasses.User;
import com.ConsoleApp.CommandClasses.CreateAccount;
import com.ConsoleApp.CommandClasses.CreateCategory;
import com.ConsoleApp.CommandClasses.Login;

public class DriverApplication {

    /**
     * We can create instance of the commandclass to inject the db into, which contains the logic for interacting with the database.
     * 
     * We can then pass the db to the entity that is created in the commandclass.
     */

    private static final DBInterface db = new DBInterface();

    private static final Scanner scan = new Scanner(System.in);


    public static void main (String[] args) {
        boolean close = false;
        int select;
        boolean logged = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Recipe App!");

        User user = null;
        user = Login.WelcomeCLI(db);
        if(user != null){
            logged = true;
        }

        if (logged) {
            while (!close) {
                System.out.println("\t1. Add Category");
                System.out.println("\t2. Author Recipe");

                System.out.println("\t100. Exit");
                try {
                    System.out.print(">> ");
                    select = scan.nextInt();
                    switch (select) {
                        case 1:
                            System.out.println("creating new category...");
                            CreateCategory.CreateCategoryCLI(db);
                            break;
                        case 2:
                            System.out.println("creating a new recipe...");
                            CreateRecipe.CreateRecipeCLI(db);
                            break;
                        case 100:
                            scan.close();
                            db.endSSH();
                            close = true;
                            break;
                        default:
                            System.out.println(select + " is not an option.");
                            break;
                    }
                } catch (Exception e) {
                    scan.nextLine();
                    System.out.println("Enter the number of the list item.");
                }
            }
        }
        else{
            try{
                db.endSSH();
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}