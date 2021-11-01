package com.EntityClasses;// file: EntityClasses.EntityType.java
//

import java.util.Map;


/**
 /**
 * This interface will be implemented by all Entity Type classes.
 */
public interface EntityType<T> {

    public void configEntity(Map<String, Object> attributes);

    /**
     * Insert a new entity into the table.
     * @param entity
     * @return
     */
    default public boolean InsertEntity(){
        throw new UnsupportedOperationException();
    }

    /**
     * Delete an entity from the table.
     * @param entity
     * @return
     */
    default public boolean DeleteEntity(){
        throw new UnsupportedOperationException();
    }

    /**
     * Update an entity for a given EntityClasses.EntityType.
     * @param oldEntity,newEntity
     * @return
     */
    default public boolean UpdateEntity(){
        throw new UnsupportedOperationException();
    }
}
