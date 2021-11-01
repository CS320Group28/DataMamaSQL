package com.EntityClasses;

import com.DBInterface;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class Requires implements EntityType<Requires>{
    private final DBInterface db;
    private int quantity;
    private int recipeID;
    private String ingredientName;

    /**
     * Constructor for instantiating a Requires relation object for an ingredient and a User, where the ingredient's name and the User's username has not been determined by the table yet.
     */
    public Requires(DBInterface db){
        this.db = db;
    }

    // Set the attributes for the Requires relation object
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.quantity = (int) attributes.get("quantity");
        this.recipeID = (int) attributes.get("recipeid");
        this.ingredientName = (String) attributes.get("ingredientname");
    }

    // Inserts a Requires relation into the database
    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement("Insert into \"Requires\" values(?, ?, ?)");
        try {
            stmt.setInt(1, this.recipeID);
            stmt.setString(2, this.ingredientName);
            stmt.setInt(3, this.quantity);
            db.execStatementUpdate(stmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Deletes a Requires relation from the database
    @Override
    public boolean DeleteEntity() {
        return EntityType.super.DeleteEntity();
    }

    // Modifies the attributes of a Requires relation in the database
    @Override
    public boolean UpdateEntity() {
        return EntityType.super.UpdateEntity();
    }
}
