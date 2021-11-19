package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.DBInterface;
import com.EntityClasses.Ingredient;
import com.EntityClasses.Pantry;
import com.EntityClasses.User;

public class AddIngredients {

    private static final Scanner scan = new Scanner(System.in);

    /// parse dates as mm/dd/yyyy
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

    /**
     * CLI for making a transaction into the pantry
     * @param db DBInterface with connection to the database
     * @param user user making the transaction
     */
    public static void AddIngredientsCLI(DBInterface db, User user){
        System.out.println("Enter Ingredients to add to your pantry below");
        String ingredient = "";
        String exp = "";
        boolean loop = true;
        // Prompt for ingredients one at time
        while(loop){
            // get ingredient
            System.out.print("<Name>, <Quantity> (type \"quit\" to quit): ");
            ingredient = scan.nextLine();

            // make sure it's not the quit keyword
            if(ingredient.equals("quit")){
                loop = false;
                break;
            }

            // parse the string.
            String[] tokens;
            PreparedStatement stmt = db.getPreparedStatement("select * from \"Ingredient\" where \"ingredientname\" = ?");
            tokens = ingredient.split("\\s*,\\s*");
            tokens[0] = tokens[0].toLowerCase(); 

            // check if it is in the database and add it if it is not.
            try{
                stmt.setString(1,tokens[0]);
                ResultSet rs = stmt.executeQuery();
                if(!rs.next()){
                    Ingredient ingr = new Ingredient(db);
                    Map<String, Object> ingMap = new HashMap<>();
                    ingMap.put("ingredientname", tokens[0]);
                    ingr.configEntity(ingMap);
                    ingr.InsertEntity();
                    stmt.close();
                }
            }catch(SQLException e){
                System.err.println("Failed to add ingredient to our database.");
                continue;
            }
            int quantityBought;
            try{
                quantityBought = Integer.parseInt(tokens[1]);
            }catch(Exception e){
                System.err.println("Quantity must be an integer number.");
                continue;
            }if(quantityBought <= 0){
                System.err.println("Quantity must be greater than 0.");
                continue;
            }

            // get the exp date
            System.out.print("Expiration date mm/dd/yyyy: ");
            exp = scan.nextLine();
            LocalDate expirationDate = LocalDate.parse(exp, formatter);
            System.out.println(expirationDate);
            // make sure it's valid
            if(!expirationDate.isAfter(LocalDate.now())){
                System.err.println("Expiration date must be in the future, try again.");
                continue;
            }
            System.out.print("Enter aisle name to place into pantry: ");
            String aisle = scan.nextLine();
            int currentQuantity = 0;
            boolean exists = false;

            // if the ingredient is already in the pantry, we will just have to update it
            try{
                String sql = "select \"currentquantity\" from \"PutsIntoPantry\" where \"username\" = ? and \"ingredientname\" = ? and \"expirationdate\" = ?";
                PreparedStatement stmt2 = db.getPreparedStatement(sql);
                stmt2.setString(1, user.getUserName());
                stmt2.setString(2, tokens[0]);
                stmt2.setObject(3, expirationDate);
                ResultSet results = db.execStatementQuery(stmt2);
                if(results.next()){
                    currentQuantity = results.getInt("currentquantity");
                    exists = true;
                }else{
                    exists = false;
                }
            }catch(SQLException e){
                e.printStackTrace();
                System.err.println("Failed internally with checking if you already have some of the ingredient.");
                continue;
            }

            Pantry pantry = new Pantry(db);
            Map<String, Object> pantryMap = new HashMap<>();
            pantryMap.put("username", user.getUserName());
            pantryMap.put("ingredientname", tokens[0]);
            pantryMap.put("expirationdate", expirationDate);
            pantryMap.put("purchasedate", LocalDateTime.now());
            pantryMap.put("aisle", aisle);
            pantryMap.put("quantitybought", quantityBought);
            pantryMap.put("currentquantity", quantityBought + currentQuantity);
            pantry.configEntity(pantryMap);
            System.out.println(currentQuantity);

            // updateEntity will prevent duplicate key violation if it exists.
            if(exists){
                pantry.UpdateEntity();
            }else{
                pantry.InsertEntity();
            }
        }
    }
}
