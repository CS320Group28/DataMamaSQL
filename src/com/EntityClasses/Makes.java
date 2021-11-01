package com.EntityClasses;

import com.DBInterface;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Makes implements EntityType<Makes>{
    private int recipeID;
    private String username;
    private double quantityMade;
    private LocalDate dateMade;
    private DBInterface db;
    public Makes(DBInterface db){
        this.db = db;
    }

    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.recipeID = (int)attributes.get("recipeid");
        this.username = (String)attributes.get("username");
        this.dateMade = (LocalDate)attributes.get("datemade");
        this.quantityMade = (double)attributes.get("quantitymade");
    }

    @Override
    public boolean InsertEntity() {
        String sql = "Insert into \"Makes\" values(?, ?, ?, ?)";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try{
            stmt.setInt(1, this.recipeID);
            stmt.setString(2, this.username);
            stmt.setDouble(3, this.quantityMade);
            stmt.setObject(4, this.dateMade);
            db.execStatementUpdate(stmt);
            stmt.close();
        }catch(SQLException e){
            System.err.println("Failed to make recipe.");
            return false;
        }
        return true;
    }

    @Override
    public boolean UpdateEntity() {
        String sql = "Update \"Makes\" set \"recipeid\" = ?, \"username\" = ?, \"quantitymade\" = \"quantitymade\" + ?, \"datemade\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try{
            stmt.setInt(1, this.recipeID);
            stmt.setString(2, this.username);
            stmt.setDouble(3, this.quantityMade);
            stmt.setObject(4, this.dateMade);
            db.execStatementUpdate(stmt);
            stmt.close();
        }catch(SQLException e){
            System.err.println("Failed to make recipe.");
            return false;
        }
        return true;    }
}
