package EntityClasses;

public class User implements EntityType<User>{
    @Override
    public boolean InsertEntity(User entity) {
        return false;
    }

    @Override
    public boolean DeleteEntity(User entity) {
        return false;
    }

    @Override
    public boolean UpdateEntity(User oldEntity, User newEntity) {
        return false;
    }
}
