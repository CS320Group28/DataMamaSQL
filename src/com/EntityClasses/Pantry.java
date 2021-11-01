package com.EntityClasses;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.DBInterface;
public class Pantry implements EntityType<Pantry>{
    private String username;
    private String ingredientName;
    private LocalDate expirationDate;
    private LocalDateTime purchaseDate;
    private String aisle;
    private int quantityBought;
    private int currentQuantity;

    private final DBInterface db;

    private Map<String, Object> attributes;

    public Pantry(DBInterface db){
        this.db = db;
    }

    @Override
    public void configEntity(Map<String, Object> attributes) {
        this.username = (String) Objects.requireNonNull(attributes.get("username"));
        this.ingredientName = (String) Objects.requireNonNull(attributes.get("ingredientname"));
        this.expirationDate = (LocalDate) Objects.requireNonNull(attributes.get("expirationdate"));
        this.purchaseDate = (LocalDateTime) Objects.requireNonNull(attributes.get("purchasedate"));
        this.aisle = (String) Objects.requireNonNull(attributes.get("aisle"));
        this.quantityBought = (int) Objects.requireNonNull(attributes.get("quantitybought"));
        this.currentQuantity = (int) Objects.requireNonNull(attributes.get("currentquantity"));
        this.attributes = attributes;
    }

    public String getAisle() {
        return aisle;
    }
    public int getCurrentQuantity() {
        return currentQuantity;
    }
    public DBInterface getDb() {
        return db;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public String getIngredientName() {
        return ingredientName;
    }
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }
    public int getQuantityBought() {
        return quantityBought;
    }
    public String getUsername() {
        return username;
    }
    public void setAisle(String aisle) {
        this.aisle = aisle;
    }
    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    } 
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public void setQuantityBought(int quantityBought) {
        this.quantityBought = quantityBought;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Pantry copyPantry(){
        Pantry p = new Pantry(db);
        p.configEntity(attributes);
        return p;
    }

    @Override
    public boolean InsertEntity(){
        String sql = "insert into \"PutsIntoPantry\" values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try{
            stmt.setString(1, username);
            stmt.setString(2, ingredientName);
            stmt.setObject(3, expirationDate);
            stmt.setObject(4, purchaseDate);
            stmt.setString(5, aisle);
            stmt.setInt(6, quantityBought);
            stmt.setInt(7, currentQuantity);
            db.execStatementUpdate(stmt);
            stmt.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            System.err.println("Failed to add to pantry");
            return false;
        }
    }


    /**
     * This method is to be called with a new object.
     */
    @Override
    public boolean UpdateEntity(){
        String sql = "update \"PutsIntoPantry\" set \"aisle\" = ?, \"quantitybought\" = ?, \"currentquantity\" = ?" +
                     "where \"username\" = ? and \"ingredientname\" = ? and \"expirationdate\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try{
            stmt.setString(1, aisle);
            stmt.setInt(2, quantityBought);
            stmt.setInt(3, currentQuantity);

            stmt.setString(4, username);
            stmt.setString(5, ingredientName);
            stmt.setObject(6, expirationDate);
            db.execStatementUpdate(stmt);
            stmt.close();
            return true;
        }catch(SQLException e){
            System.err.println("Failed to update existing pantry stock");
            return false;
        }
    }

    @Override
    public boolean DeleteEntity(){
        String sql = "delete from \"PutsIntoPantry\" where \"username\" = ? and \"ingredientname\" = ? and \"expirationdate\" = ?";
        PreparedStatement stmt = db.getPreparedStatement(sql);
        try{
            stmt.setString(1, this.username);
            stmt.setString(2, this.ingredientName);
            stmt.setObject(3, expirationDate);
            db.execStatementUpdate(stmt);
        }catch (SQLException e){
            return false;
        }
        return true;
    }
}
