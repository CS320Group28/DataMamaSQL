package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    //region Constructors

    public Recipe(DBInterface db){
        this.db = db;
    }

    //for making a new recipe, where the recipe ID has not been determined by the table yet.

    /** 
    public Recipe(String recipeName, String steps, int rating, String description, float servings, int cookTime, int difficulty, LocalDateTime creationDate){
        Objects.requireNonNull(recipeName, "Recipe Name must not be null");
        Objects.requireNonNull(steps, "steps must not be null");
        Objects.requireNonNull(creationDate, "creation date must not be null");
        if(difficulty < 0 || difficulty > 4)
            throw new IndexOutOfBoundsException();

        this.recipeID = 0;
        this.recipeName = recipeName;
        this.cookTime = cookTime;
        this.steps = steps;
        this.rating = rating;
        this.description = description;
        this.servings = servings;
        this.difficulty = Difficulty.values()[difficulty];
        this.creationDate = creationDate;
    }

    //for instantiating a recipe from the database, where the recipe ID already exists.
    public Recipe(int recipeID, String recipeName, String steps, int rating, String description, float servings, int cookTime, int difficulty, LocalDateTime creationDate){
        Objects.requireNonNull(recipeName, "Recipe Name must not be null");
        Objects.requireNonNull(steps, "steps must not be null");
        Objects.requireNonNull(creationDate, "creation date must not be null");
        if(difficulty < 0 || difficulty > 4)
            throw new IndexOutOfBoundsException();

        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.cookTime = cookTime;
        this.steps = steps;
        this.rating = rating;
        this.description = description;
        this.servings = servings;
        this.difficulty = Difficulty.values()[difficulty];
        this.creationDate = creationDate;
    }
    */
    


    /**
     * TODO: MAKE THIS ACTUALLY DO THINGS
     */
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
    //endregion

    //region Getters and Setters

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
    //endregion

    //used for creating the recipe / inserting into the database
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

    @Override
    public boolean UpdateEntity() {
        String sql = "Update \"Recipe\" Set \"RecipeName\" = ?, \"Steps\" = ?, \"Description\" = ?, \"Servings\" = ?, \"CookTime\", \"Difficulty\"";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try {
            stmt.setString(2, steps);
            stmt.setString(3, description);
            stmt.setInt(4, servings);
            stmt.setInt(5, cookTime);
            stmt.setString(1, recipeName);
            stmt.setInt(6, difficulty.ordinal());
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
        return String.format("Name: %s, ID:%s", this.recipeName, this.recipeID);
    }

}
