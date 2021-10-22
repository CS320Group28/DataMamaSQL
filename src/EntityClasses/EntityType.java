package EntityClasses;// file: EntityClasses.EntityType.java
//

/**
 * This interface will be implemented by all Entity Type classes.
 */
public interface EntityType<T> {

    /**
     * Insert a new entity into the table.
     * @param entity
     * @return
     */
    default public boolean InsertEntity(T entity){
        throw new UnsupportedOperationException();
    }

    /**
     * Delete an entity from the table.
     * @param entity
     * @return
     */
    default public boolean DeleteEntity(T entity){
        throw new UnsupportedOperationException();
    }

    /**
     * Update an entity for a given EntityClasses.EntityType.
     * @param entity
     * @return
     */
    default public boolean UpdateEntity(T oldEntity, T newEntity){
        throw new UnsupportedOperationException();
    }

}
