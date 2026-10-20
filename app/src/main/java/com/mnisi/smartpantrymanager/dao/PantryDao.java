package com.mnisi.smartpantrymanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.mnisi.smartpantrymanager.model.PantryItem;
import java.util.List;

@Dao
public interface PantryDao {
    @Insert
    void insert(PantryItem item); // CREATE

    @Query("SELECT * FROM pantry_items ORDER BY id DESC")
    List<PantryItem> getAllItems(); // READ

    @Update
    void update(PantryItem item); // UPDATE

    @Delete
    void delete(PantryItem item); // DELETE

    @Query("SELECT * FROM pantry_items WHERE id = :id")
    PantryItem getById(int id);
}