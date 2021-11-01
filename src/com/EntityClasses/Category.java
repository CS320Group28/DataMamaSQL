package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.DBInterface;

public class Category implements EntityType<Category>{
    private String categoryName;
    private int categoryID;
    private DBInterface db;

    /**
     * Constructor for instantiating a Category object for a category, where the category's username and category ID has not been determined by the table yet.
     */
    public Category(DBInterface db){
        this.db = db;
    }

    /**
     * Constructor for instantiating a Category object for a category from the database, where the category's category ID already exists.
     */
    public Category(String categoryName, int categoryID){
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setCategoryName(String name){
        this.categoryName = name;
    }

    public int getCategoryID(){
        return this.categoryID;
    }

    public void setCategoryID(int id){
        this.categoryID = id;
    }

    // Set the attributes for the Category object
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.categoryName = (String) attributes.get("categoryName");
    }

    // Inserts a Category into the database
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

    // Deletes a Category from the database
    @Override
    public boolean DeleteEntity() {
        return false;
    }

    // Modifies the attributes of a Category in the database
    @Override
    public boolean UpdateEntity() {
        return false;
    }
}
