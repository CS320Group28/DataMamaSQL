package com.ConsoleApp;
import com.*;

public class DriverApplication {

    /**
     * We can create instance of the commandclass to inject the db into, which contains the logic for interacting with the database.
     * 
     * We can then pass the db to the entity that is created in the commandclass.
     */

    private static final DBInterface db = new DBInterface();




    public static void main (String[] args){
        System.out.println("commands and stuff will go here when done...");
    }
}