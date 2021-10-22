package EntityClasses;

import java.util.Objects;

public class Ingredient implements EntityType<Ingredient>{

    private String IngredientName;
    public Ingredient(String IngredientName){
        Objects.requireNonNull(IngredientName, "Ingredient Name must not be Null");
        this.IngredientName = IngredientName;
    }

    @Override
    public boolean InsertEntity(Ingredient entity) {
        return false;
    }

    @Override
    public boolean DeleteEntity(Ingredient entity) {
        return false;
    }

    @Override
    public boolean UpdateEntity(Ingredient oldEntity, Ingredient newEntity) {
        return false;
    }
}
