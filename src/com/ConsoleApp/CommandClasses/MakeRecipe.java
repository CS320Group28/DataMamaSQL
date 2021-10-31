package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.DBInterface;
import com.EntityClasses.Pantry;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

public class MakeRecipe {


    public static void makeRecipe(DBInterface db, User user, Recipe recipe, double scale) throws SQLException{
        //first
/**
         * First: get the required ingredients and their quantities.
         *        select ingredientname, quantity from requires where recipeid = recipeid
         *        save all of this is something like a hashmap
         *          *          (POTENTIALLY ALGORITHMICALLY DETERMINE PROPER SCALING, MAKING SURE THE SCALE ISN'T TOO LOW TO MAKE INGREDIENTS 0)
         *
         *        scale ingredients by scalefactor (consider finding a way to keep the proportions right..)
         *  DONE
         */
        ////////////////////////////////////////////////////////////////////////////

        
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
        ingredientToQuantity.keySet().forEach((String key) -> ingredientToQuantity.put(key, (int)(ingredientToQuantity.get(key) * scale)));;
        stmt.close();
        

         /**
         * Second: perform queries to make user user.username() has the right number of every ingredient.
         *  this might be faster with a join, not sure, too complicated.
         *       for each key in map<String, int>
         *  
         * 
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
         *          select current_quantity from pantry where  username = user, ingredientname = key, sort by expirationdate asc;
         *          
         *          for each current_quantity in resultset                                                                                                                                        (maybe select ingredientname, current quantity where username = user and (ingredientname1 = ingredient1 || .... N(generated procedurally)))
            *          if(current_quantity >= neededquantity)
            *                 new_current = current - needed;
            *                 update pantry relation with new current
            *          else
            *                 needed = needed - current
            *                 delete pantry relation 
         *          while (required > 0)
         */

       
    }
}
