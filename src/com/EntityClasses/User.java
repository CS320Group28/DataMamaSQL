package com.EntityClasses;

import java.time.LocalDateTime;
import java.util.Map;

import com.DBInterface;

public class User implements EntityType<User>{
    private String username;
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime lastAccessDate;
    private DBInterface db;
    public User(DBInterface db){
        this.db = db;
    }
    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.username = (String) attributes.get("Username");
        this.password = (String) attributes.get("Password");
        this.creationDate = LocalDateTime.now();
        lastAccessDate = creationDate;        
    }

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
    }

    public void setLastAccessDate(LocalDateTime lastAccessDate){
        this.lastAccessDate = lastAccessDate;
    }

    public LocalDateTime getLastAccessDate(){
        return this.lastAccessDate;
    }

    @Override
    public boolean InsertEntity() {
        return false;
    }

    @Override
    public boolean DeleteEntity() {
        return false;
    }

    @Override
    public boolean UpdateEntity(User oldUser) {
        return false;
    }

}
