package com.mnisi.smartpantrymanager.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.mnisi.smartpantrymanager.dao.PantryDao;
import com.mnisi.smartpantrymanager.model.PantryItem;
import com.mnisi.smartpantrymanager.model.Recipe;

@Database(entities = {PantryItem.class, Recipe.class}, version = 6, exportSchema = false)
public abstract class PantryDatabase extends RoomDatabase {

    public abstract PantryDao pantryDao();
    public abstract RecipeDao recipeDao();

    private static volatile PantryDatabase INSTANCE;

    public static PantryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PantryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    PantryDatabase.class,
                                    "smart_pantry_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}