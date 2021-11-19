package com.ConsoleApp.CommandClasses;

import com.DBInterface;
import com.EntityClasses.Recipe;
import com.EntityClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeListForDeleteOrEdit {

    /**
     * Gets the recipes that a user has made.
     * @param db DBInterface with a connection  to database
     * @param user User object
     * @return list of recipe objects.
     * @throws SQLException
     */
    public static ArrayList<Recipe> getRecipes(DBInterface db, User user) throws SQLException {
        //create the SQL statement and execute the query
        PreparedStatement stmt = db.getPreparedStatement("SELECT * FROM \"Recipe\" INNER JOIN \"Authors\" ON \"Recipe\".\"RecipeID\" = \"Authors\".\"recipeid\" WHERE \"Authors\".\"username\" = ? ORDER BY \"Recipe\".\"RecipeName\" ASC;");
        stmt.setString(1, user.getUserName());
        ResultSet rs = db.execStatementQuery(stmt);

        //fill an arraylist with the result set info
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        while(rs.next()){
            recipes.add(new Recipe(rs.getInt("RecipeID"), rs.getString("RecipeName"),
                    rs.getString("Steps"), rs.getString("Description"), rs.getInt("Servings"),
                    rs.getInt("CookTime"), Recipe.Difficulty.values()[rs.getInt("Difficulty")], db));
        }

        return recipes;
    }
}
