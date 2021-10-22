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

    public Recipe(int recipeID, String recipeName, String steps, int rating, String description, float servings, int cookTime, int difficulty, LocalDateTime creationDate){
        Objects.requireNonNull(recipeName, "Recipe Name must not be null");
        Objects.requireNonNull(steps, "steps must not be null");
        Objects.requireNonNull(recipeID, "Recipe ID must not be null");
        Objects.requireNonNull(creationDate, "creation date must not be null");
        Objects.requireNonNull(difficulty, "difficulty must not be null");
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
    @Override
    public boolean InsertEntity(Recipe entity) {
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
