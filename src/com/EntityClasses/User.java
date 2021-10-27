package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    public User(String un, String pw, LocalDateTime cd, LocalDateTime ld){
        this.username = un;
        this.password = pw;
        this.creationDate = cd;
        this.lastAccessDate = ld;
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
        PreparedStatement stmt = db.getPreparedStatement("INSERT INTO \"User\"(Username, userPassword, creationDate, lastAccessDate) VALUES(?, ?, ?, ?)");
        try{
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setObject(3, creationDate);
            stmt.setObject(4, lastAccessDate);
            db.execStatementUpdate(stmt);
        } catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean DeleteEntity() {
        return false;
    }

    @Override
    public boolean UpdateEntity(User oldUser) {
        return false;
    }
    public static void main(String[] args) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Username", "username");
        userMap.put("Password", "password");
        DBInterface db = new DBInterface();
        User user = new User(db);
        user.configEntity(userMap);
        user.InsertEntity();
        try{
            db.endSSH();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", lastAccessDate=" + lastAccessDate +
                ", db=" + db +
                '}';
    }
}
