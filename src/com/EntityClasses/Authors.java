package com.EntityClasses;

import com.DBInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Authors implements EntityType{
    private String username;
    private int recipeID;
    private DBInterface db;

    /**
     * Constructor for instantiating an Author object for an author, where the author's username and recipe ID has not been determined by the table yet.
     */
    public Authors(DBInterface db){
        this.db = db;
    }

    public static String getAuthorName(int RecipeID, DBInterface db){
        PreparedStatement stmt = db.getPreparedStatement("select \"username\" from \"Authors\" where \"recipeid\" = ?");
        try{
            stmt.setInt(1, RecipeID);
            ResultSet rs = db.execStatementQuery(stmt);
            if(rs.next()){
                return rs.getString("username");
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return null;
    }

    // Set the attributes for the Authors object
    @Override
    public void configEntity(Map attributes) {
        this.username = (String) attributes.get("username");
        this.recipeID = (int) attributes.get("recipeid");
    }

    // Inserts an Author into the database
    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement("Insert into \"Authors\" values(?, ?)");
        try {
            stmt.setString(1, this.username);
            stmt.setInt(2, this.recipeID);
            stmt.executeUpdate();
            stmt.close();
            return true;
        }catch (SQLException se){
            return false;
        }
    }

    // Deletes an Author from the database
    @Override
    public boolean DeleteEntity() {
        return EntityType.super.DeleteEntity();
    }

    // Modifies the attributes of an Author in the database
    @Override
    public boolean UpdateEntity() {
        return EntityType.super.UpdateEntity();
    }
}
