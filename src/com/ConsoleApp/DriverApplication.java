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
        System.out.println("Enter command: ");
        String command = scan.nextLine();
        if(command.equals("register")){
            CreateAccount.CreateAccountCLI(db);
        }
    }
}