package com.ConsoleApp.CommandClasses;

import java.sql.PreparedStatement;

import com.DBInterface;
import com.EntityClasses.Pantry;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

public class MakeRecipe {


    public static void makeRecipe(DBInterface db, User user, Recipe recipe){
        /**
         * First: get the required ingredients and their quantities.
         *        select ingredientname, quantity from requires where recipeid = recipeid
         *        save all of this is something like a hashmap
         * 
         * Second: perform queries to make user user.username() has the right number of every ingredient.
         *  this might be faster with a join, not sure, too complicated.
         *       for each key in map<String, int>
         *  
         *          neededquantity =  map.get(key)
         *          query select currentquantity where username=user.getname and ingredientname = ingredient.getname()
         *          for each currentquantity
            *          neededquantity - currentquantity
            *          if needquatntiy <= 0, break
            *       if neededquantitiy > 0 //still need more
            *          cant make it
            *     can make it
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
