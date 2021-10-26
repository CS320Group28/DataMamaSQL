package com.ConsoleApp;
import java.util.Scanner;

import com.*;
import com.ConsoleApp.CommandClasses.CreateAccount;
import com.ConsoleApp.CommandClasses.CreateCategory;

public class DriverApplication {

    /**
     * We can create instance of the commandclass to inject the db into, which contains the logic for interacting with the database.
     * 
     * We can then pass the db to the entity that is created in the commandclass.
     */

    private static final DBInterface db = new DBInterface();

    private static final Scanner scan = new Scanner(System.in);


    public static void main (String[] args){
        boolean close = false;
        int select;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Recipe App!");

        //TODO: call Login here (i think?) then wrap the program loop in a conditional based on whether login worked or not.

        while (!close) {
            System.out.println("\t1. Add User");
            System.out.println("\t2. Add Category");






            System.out.println("\t100. Exit");
            try {
                System.out.print(">> ");
                select = scan.nextInt();
                switch (select) {
                    case 1:
                        System.out.println("creating new user...");
                        CreateAccount.CreateAccountCLI(db);
                        break;
                    case 2:
                        System.out.println("creating new category...");
                        CreateCategory.CreateCategoryCLI(db);
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
}