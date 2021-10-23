package com.EntityClasses;

import java.util.Map;
import java.util.Objects;

import com.DBInterface;

public class Ingredient implements EntityType<Ingredient>{

    private String IngredientName = null;
    private DBInterface db;
    public Ingredient(DBInterface db){
        Objects.requireNonNull(db, "There must be a database for this entity");
        this.db = db;
    }

    @Override
    public boolean InsertEntity() {
        return false;
    }

    @Override
    public boolean DeleteEntity() {
        return false;
    }

    @Override
    public boolean UpdateEntity(Ingredient oldEntity) {
        return false;
    }

    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.IngredientName = (String) attributes.get("IngredientName");
    }
}
