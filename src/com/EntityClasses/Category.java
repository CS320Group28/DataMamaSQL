package com.EntityClasses;

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
        this.categoryID = (int) attributes.get("categoryID");
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
    public boolean InsertEntity(DBInterface db) {


        return false;
    }

    @Override
    public boolean DeleteEntity(DBInterface db) {
        return false;
    }

    @Override
    public boolean UpdateEntity(DBInterface db, Category oldCategory) {
        return false;
    }
}
