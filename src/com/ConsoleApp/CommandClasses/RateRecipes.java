package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.Recipe;

public class RateRecipes {
    public static void rateRecipeCLI(DBInterface db, Recipe chosen){
        int rid = chosen.getRecipeID();
        System.out.println(chosen);
        return;
    }
    
}
