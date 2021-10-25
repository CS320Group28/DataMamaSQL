package com.ConsoleApp;
import java.util.Scanner;

import com.*;
import com.ConsoleApp.CommandClasses.CreateAccount;

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
        while (!close) {
            System.out.println("    1. Add User");
            System.out.println("    2. Exit");
            try {
                select = scan.nextInt();
                switch (select) {
                    case 1:
                        System.out.println("creating new user...");
                        CreateAccount.CreateAccountCLI(db);
                        break;
                    case 2:
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