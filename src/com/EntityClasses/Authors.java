package com.EntityClasses;

import com.DBInterface;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class Authors implements EntityType{
    private String username;
    private int recipeID;
    private DBInterface db;
    public Authors(DBInterface db){
        this.db = db;
    }
    @Override
    public void configEntity(Map attributes) {
        this.username = (String) attributes.get("username");
        this.recipeID = (int) attributes.get("recipeid");
    }

    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement("Insert into \"Authors\" values(?, ?)");
        try {
            stmt.setString(1, this.username);
            stmt.setInt(2, this.recipeID);
            return true;
        }catch (SQLException se){
            return false;
        }
    }

    @Override
    public boolean DeleteEntity() {
        return EntityType.super.DeleteEntity();
    }

    @Override
    public boolean UpdateEntity(Object oldEntity) {
        return EntityType.super.UpdateEntity(oldEntity);
    }
}
