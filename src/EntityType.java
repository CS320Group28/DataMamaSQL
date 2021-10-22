// file: EntityType.java
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
    public boolean InsertEntity(T entity);

    /**
     * Delete an entity from the table.
     * @param entity
     * @return
     */
    public boolean DeleteEntity(T entity);

    /**
     * Update an entity for a given EntityType. 
     * @param entity
     * @return
     */
    default public boolean UpdateEntity(T oldEntity, T newEntity){
        throw new UnsupportedOperationException();
    }

}
