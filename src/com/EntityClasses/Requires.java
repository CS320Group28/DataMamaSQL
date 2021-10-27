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
    public Requires(DBInterface db){
        this.db = db;
    }
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.quantity = (int) attributes.get("quantity");
        this.recipeID = (int) attributes.get("recipeid");
        this.ingredientName = (String) attributes.get("ingredientname");
    }

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

    @Override
    public boolean DeleteEntity() {
        return EntityType.super.DeleteEntity();
    }

    @Override
    public boolean UpdateEntity(Requires oldEntity) {
        return EntityType.super.UpdateEntity(oldEntity);
    }
}
