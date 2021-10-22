package EntityClasses;

public class Recipe implements EntityType{
    @Override
    public boolean InsertEntity(Object entity) {
        return false;
    }

    @Override
    public boolean DeleteEntity(Object entity) {
        return false;
    }

    @Override
    public boolean UpdateEntity(Object oldEntity, Object newEntity) {
        return false;
    }
}
