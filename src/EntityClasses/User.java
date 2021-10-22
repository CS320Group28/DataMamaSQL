package EntityClasses;

import java.time.LocalDateTime;

public class User implements EntityType<User>{
    private String username;
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime lastAccessDate;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        creationDate = LocalDateTime.now();
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
