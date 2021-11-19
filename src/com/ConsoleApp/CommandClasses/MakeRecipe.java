package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import com.DBInterface;
import com.EntityClasses.Makes;
import com.EntityClasses.Pantry;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

public class MakeRecipe {


    /**
     * CLI for making a recipe
     * @param db
     * @param user User making the recipe
     * @param recipe Recipe object to try to make
     * @param scale Scale the ingredients
     * @throws SQLException
     */
    public static void makeRecipe(DBInterface db, User user, Recipe recipe, double scale) throws SQLException{
        /**
         * First: get the required ingredients and their quantities.
         *        scale ingredients by scalefactor 
         */
        Map<String, Integer> ingredientToQuantity = new HashMap<>();
        PreparedStatement stmt = db.getPreparedStatement("select * from \"Requires\" where \"recipeid\" = ?");
        ResultSet ingredientset;

        // may throw sqlexception
        stmt.setInt(1, recipe.getRecipeID());
        ingredientset = db.execStatementQuery(stmt);

        while(ingredientset.next()){
            ingredientToQuantity.put(ingredientset.getString("ingredientname"), ingredientset.getInt("quantity"));
        }

        // python-like line for scaling all of the ingredients in the map; problem, the proportions could be off now.
        ingredientToQuantity.keySet().forEach((String key) -> ingredientToQuantity.put(key, (int)(ingredientToQuantity.get(key) * scale)));
        stmt.close();

         /**
         * Second: perform queries to make user user.username() has the right number of every ingredient.
         *  this might be faster with a join, not sure, too complicated.
         *       for each key in map<String, int>
         *          query select currentquantity where username=user.getname and ingredientname = ingredient.getname()
         *          for each currentquantity
            *          neededquantity -= currentquantity
            *          if needquatntiy <= 0, break
            *       if neededquantitiy > 0 //still need more
            *          cant make it
            *     can make it
        */
        for(String key : ingredientToQuantity.keySet()){
            int quantityNeeded = ingredientToQuantity.get(key);
            // this theoretically could be more efficient if we used "or" logic with a loop and indexing for setString
            String sql = "select \"currentquantity\", \"expirationdate\" from \"PutsIntoPantry\" where \"username\" = ? and \"ingredientname\" = ?"; 
            PreparedStatement statement = db.getPreparedStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, key);
            ResultSet quants = db.execStatementQuery(statement);
            
            while(quants.next()){
                LocalDateTime expdate = quants.getTimestamp("expirationdate").toLocalDateTime();
                if(expdate.isAfter(LocalDateTime.now())){
                    int currentQuantity = quants.getInt("currentquantity");
                    quantityNeeded -= currentQuantity;
                }else{
                    // delete it from the table somehow.
                }
                if(quantityNeeded <= 0){
                    break;
                }
            }
            if(quantityNeeded > 0){
                System.out.printf("You don't have the required amount of %s to make this recipe\n", key);
                return;
            }
        }
        /** 
         * third: make recipe
         *       for each key in map<String, int>
         *          needed_quantity = map.get(key)
         *          select current_quantity from pantry where  username = user, ingredientname = key, sort by \"expirationdate\" asc;
         *          (maybe select ingredientname, current quantity where username = user and (ingredientname1 = ingredient1 || .... N(generated procedurally)))
         * 
         *          for each current_quantity in resultset                                                                                                                                      
            *          if(current_quantity >= neededquantity)
            *                 new_current = current - needed;
            *                 update pantry relation with new current
            *          else
            *                 needed = needed - current
            *                 delete pantry relation 
         *          while (required > 0)
         */
        for(String key: ingredientToQuantity.keySet()){
            int quantityNeeded = ingredientToQuantity.get(key);
            String sql = "select \"currentquantity\", \"expirationdate\", \"aisle\", \"quantitybought\" from \"PutsIntoPantry\" where \"username\" = ? and \"ingredientname\" = ? order by \"expirationdate\" asc;";
            PreparedStatement statement = db.getPreparedStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, key);
            ResultSet quants = db.execStatementQuery(statement);

            while(quants.next()){
                int currentQuantity = quants.getInt("currentquantity");
                int remainder = currentQuantity - quantityNeeded;
                Pantry pantry = new Pantry(db);
                pantry.setIngredientName(key);
                pantry.setExpirationDate(quants.getTimestamp("expirationdate").toLocalDateTime().toLocalDate());
                pantry.setUsername(user.getUserName());
                pantry.setCurrentQuantity(remainder);
                pantry.setAisle(quants.getString("aisle"));
                pantry.setQuantityBought(quants.getInt("quantitybought"));
                // updateentity
                if(remainder > 0){
                    pantry.UpdateEntity();
                }else{
                    pantry.DeleteEntity();
                }
            }
        }
        /**
         * Fourth: update Makes relation.
         */
        Makes makes = new Makes(db);
        Map<String, Object> makesMap = new HashMap<>();
        makesMap.put("recipeid", recipe.getRecipeID());
        makesMap.put("username", user.getUserName());
        makesMap.put("quantitymade", scale);
        makesMap.put("datemade", LocalDate.now());
        makes.configEntity(makesMap);
        PreparedStatement statement = db.getPreparedStatement("select \"quantitymade\" from \"Makes\" where \"recipeid\" = ? and \"username\" = ?");
        statement.setInt(1, recipe.getRecipeID());
        statement.setString(2, user.getUserName());
        ResultSet makeSet = db.execStatementQuery(statement);
        if(makeSet.next()){
            makes.UpdateEntity();
        }else{
            makes.InsertEntity();
        }
        System.out.printf("You have made %s\n", recipe.getRecipeName());
    }
}
