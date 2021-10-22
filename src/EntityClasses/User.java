package EntityClasses;

public class User implements EntityType{
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
