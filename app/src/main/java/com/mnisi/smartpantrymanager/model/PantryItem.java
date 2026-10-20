package com.mnisi.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry_items")
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double quantity;
    private String unit;

    // CONSTRUCTOR NEEDED FOR YOUR CODE
    public PantryItem(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // GETTERS AND SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}