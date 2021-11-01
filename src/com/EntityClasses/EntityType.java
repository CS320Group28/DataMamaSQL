package com.EntityClasses;

import java.util.Map;

import com.DBInterface;

public interface EntityType<T> {
    /**
     * Set the attributes for an Entity object using a map of attributes
     */
    public void configEntity(Map<String, Object> attributes);

    /**
     * Inserts an Entity object into the database
     * @return a boolean determining whether the Entity has been added to the database
     */
    default public boolean InsertEntity(){
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes an Entity object from the database
     * @return a boolean determining whether the Entity has been deleted from the database
     */
    default public boolean DeleteEntity(){
        throw new UnsupportedOperationException();
    }

    /**
     * Modifies the attributes of an Entity object in the database
     * @return a boolean determining whether the attributes for the Entity has been updated in the database
     */
    default public boolean UpdateEntity(){
        throw new UnsupportedOperationException();
    }
}
