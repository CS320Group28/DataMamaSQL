package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import com.DBInterface;

public class Ingredient implements EntityType<Ingredient>{

    private String ingredientName = null;
    private DBInterface db;
    public Ingredient(DBInterface db){
        Objects.requireNonNull(db, "There must be a database for this entity");
        this.db = db;
    }

    public String getIngredientName(){
        return this.ingredientName;
    }

    public void setIngredientName(String name){
        this.ingredientName = name;
    }


    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.ingredientName = (String) attributes.get("ingredientname");
    }

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

    @Override
    public boolean DeleteEntity() {
        return false;
    }

    @Override
    public boolean UpdateEntity() {
        return false;
    }


}
