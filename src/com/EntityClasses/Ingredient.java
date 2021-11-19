package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import com.DBInterface;

public class Ingredient implements EntityType<Ingredient>{
    private String ingredientName = null;
    private DBInterface db;

    /**
     * Constructor for instantiating an Ingredient object for an ingredient, where the ingredient's name has not been determined by the table yet.
     */
    public Ingredient(DBInterface db){
        Objects.requireNonNull(db, "There must be a database for this entity");
        this.db = db;
    }

    /**
     * Getter for ingredient's name
     * @return ingredientname String
     */
    public String getIngredientName(){
        return this.ingredientName;
    }

    /**
     * Setter for ingredient's name
     * @param name
     */
    public void setIngredientName(String name){
        this.ingredientName = name;
    }

    // Set the attributes for the Ingredient object
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.ingredientName = (String) attributes.get("ingredientname");
    }

    // Inserts an Ingredient into the database
    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement("INSERT INTO \"Ingredient\" VALUES(?)");
        try{
            stmt.setString(1, ingredientName);
            db.execStatementUpdate(stmt);
        }
        catch(SQLException e){
            e.getMessage();
        }

        return false;
    }

    // Deletes an Ingredient from the database
    @Override
    public boolean DeleteEntity() {
        return false;
    }

    // Modifies the attributes of an Ingredient in the database
    @Override
    public boolean UpdateEntity() {
        return false;
    }


}
