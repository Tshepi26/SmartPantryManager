package com.mnisi.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String ingredients;
    public String requiredQuantities;
    public String steps;

    public Recipe(String name, String ingredients, String requiredQuantities, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.requiredQuantities = requiredQuantities;
        this.steps = steps;
    }
}