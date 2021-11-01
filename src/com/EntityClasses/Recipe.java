package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.DBInterface;

public class Recipe implements EntityType<Recipe>{

    public enum Difficulty {
        EASY,
        EASYMEDIUM,
        MEDIUM,
        MEDIUMHARD,
        HARD;
    }

    private int recipeID;
    private String recipeName;
    private String steps;
    private int rating;
    private String description;
    private int servings;
    private int cookTime;
    private Difficulty difficulty;
    private LocalDateTime creationDate;
    private DBInterface db;

    /**
     * Constructor for instantiating a Recipe object for a recipe, where the recipe's recipe ID has not been determined by the table yet.
     */
    public Recipe(DBInterface db){
        this.db = db;
    }

    /**
     * Constructor for instantiating a Recipe object for a recipe from the database, where the recipe's recipe ID already exists.
     */
    public Recipe(int recipeID, String recipeName, String steps, String description, int servings, int cookTime, Difficulty difficulty, DBInterface db) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.steps = steps;
        this.description = description;
        this.servings = servings;
        this.cookTime = cookTime;
        this.difficulty = difficulty;
        this.db = db;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Set the attributes for the Recipe object
    @Override
    public void configEntity(Map<String, Object> attributes) {
        int difficulty = (int) attributes.get("difficulty");
        if(difficulty < 1 || difficulty > 5)
            throw new IndexOutOfBoundsException();
        this.difficulty = Difficulty.values()[difficulty-1];
        this.recipeName = (String) attributes.get("recipename");
        this.cookTime = (int) attributes.get("cooktime");
        this.steps = (String) attributes.get("steps");
        this.servings = (int) attributes.get("servings");
        this.description = (String) attributes.get("description");
        this.creationDate = LocalDateTime.now();
    }

    // Inserts a Recipe into the database
    @Override
    public boolean InsertEntity() {
        PreparedStatement stmt = db.getPreparedStatement(
                "Insert into \"Recipe\"(\"RecipeName\", \"Steps\", \"Description\", \"Servings\" " +
                        ", \"CookTime\", \"Difficulty\", \"CreationDate\") values(?, ?, ?, ?, ?, ?, ?)"
        );
        try {
            stmt.setString(1, this.recipeName);
            stmt.setString(2, this.steps);
            stmt.setString(3, this.description);
            stmt.setInt(4, this.servings);
            stmt.setInt(5, this.cookTime);
            stmt.setInt(6, this.difficulty.ordinal());
            stmt.setObject(7, this.creationDate);

            db.execStatementUpdate(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Deletes a Recipe from the database
    @Override
    public boolean DeleteEntity() {
        PreparedStatement stmt = db.getPreparedStatement("Delete from \"Recipe\" where \"RecipeID\" = ?");
        try {
            stmt.setInt(1, this.recipeID);
            db.execStatementUpdate(stmt);
            stmt.close();
            return true;
        }catch(SQLException e) {
            System.err.println("Failed to delete recipe");
            return false;
        }
    }

    // Modifies the attributes of a Recipe in the database
    @Override
    public boolean UpdateEntity() {
        String sql = "Update \"Recipe\" Set \"RecipeName\" = ?, \"Steps\" = ?, \"Description\" = ?, \"Servings\" = ?, \"CookTime\" = ?, \"Difficulty\" = ? WHERE \"RecipeID\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try {
            stmt.setString(2, steps);
            stmt.setString(3, description);
            stmt.setInt(4, servings);
            stmt.setInt(5, cookTime);
            stmt.setString(1, recipeName);
            stmt.setInt(6, difficulty.ordinal());
            stmt.setInt(7, recipeID);
            db.execStatementUpdate(stmt);
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Could not update recipe.");
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("Name: %s \nID: %s \nDescription: %s\nDifficulty: %s", this.recipeName, this.recipeID, this.description, this.difficulty.toString());
    }

    public String recipeRepresentation(){
        return String.format("%s\nCook Time: %s\nServings: %s\nSTEPS\n%s", toString(),cookTime, servings, steps);
    }

}
