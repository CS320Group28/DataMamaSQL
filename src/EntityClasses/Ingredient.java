package EntityClasses;

public class Ingredient implements EntityType<Ingredient>{
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
