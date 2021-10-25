package com.ConsoleApp.CommandClasses;


import java.util.Scanner;

public class CreateAccount {
    private static final Scanner in = new Scanner(System.in);

    public void CreateAccountCLI(DBInterface db){
        System.out.println("Enter a username: ");
        String username = in.next();
        System.out.println("Enter a password: ");
        String password = in.next();
        
        
    }
    
}
