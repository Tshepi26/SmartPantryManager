package com.mnisi.smartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.mnisi.smartpantrymanager.model.Recipe;
import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT COUNT(*) FROM recipes")
    int getCount();

    @Query("DELETE FROM recipes")
    void deleteAll();
}