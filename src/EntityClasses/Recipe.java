package EntityClasses;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private float servings;
    private int cookTime;
    private Difficulty difficulty;
    private LocalDateTime creationDate;


    //region Constructors

    //for making a new recipe, where the recipe ID has not been determined by the table yet.
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

    public void setServings(float servings) {
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
    public boolean InsertEntity(Recipe entity) {
        //this is where the new recipe will be added to the database
        return false;
    }

    @Override
    public boolean DeleteEntity(Recipe entity) {
        return false;
    }

    @Override
    public boolean UpdateEntity(Recipe oldEntity, Recipe newEntity) {
        return false;
    }

    public static void main(String[] args) {
        new Recipe(1, "recipeName", "steps", 1, "description",2, 5, 6, LocalDateTime.now());
    }
}
