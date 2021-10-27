package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.DBInterface;

public class Category implements EntityType<Category>{
    private String categoryName;
    private int categoryID;
    private DBInterface db;
    public Category(DBInterface db){
        this.db = db;
    }

    
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.categoryName = (String) attributes.get("categoryName");
    }

    public Category(String categoryName, int categoryID){
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public void setCategoryName(String name){
        this.categoryName = name;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setCategoryID(int id){
        this.categoryID = id;
    }

    public int getCategoryID(){
        return this.categoryID;
    }

    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement("INSERT INTO \"Category\"(\"CategoryName\") VALUES(?)");
        try{
            stmt.setString(1, categoryName);
            db.execStatementUpdate(stmt);
        } catch(SQLException e){
            e.getMessage();
            return false;
        }
        return true;


    }

    @Override
    public boolean DeleteEntity() {
        return false;
    }

    @Override
    public boolean UpdateEntity(Category oldCategory) {
        return false;
    }
}
