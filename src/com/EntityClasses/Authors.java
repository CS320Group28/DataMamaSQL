package com.EntityClasses;

import java.util.Map;

public class Authors implements EntityType{
    @Override
    public void configEntity(Map attributes) {

    }

    @Override
    public boolean InsertEntity() {
        return EntityType.super.InsertEntity();
    }

    @Override
    public boolean DeleteEntity() {
        return EntityType.super.DeleteEntity();
    }

    @Override
    public boolean UpdateEntity(Object oldEntity) {
        return EntityType.super.UpdateEntity(oldEntity);
    }
}
