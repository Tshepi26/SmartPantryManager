package com.mnisi.smartpantrymanager;

import com.mnisi.smartpantrymanager.database.PantryDatabase;
import com.mnisi.smartpantrymanager.model.Recipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//RecioeSeeder waste reduction recipes based on pantry items
public class RecipeSeeder {
    public static void seed(PantryDatabase db) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            if (db.recipeDao().getCount() == 0) {
                db.recipeDao().insert(new Recipe("Pancakes", "flour, eggs, milk, sugar", "flour:2,eggs:2,milk:1,sugar:0.5", "1. Mix flour and sugar. 2. Add eggs and milk. 3. Fry"));
                db.recipeDao().insert(new Recipe("Omelette", "eggs, milk, cheese", "eggs:3,milk:0.2,cheese:0.5", "1. Beat eggs. 2. Add milk. 3. Fry in pan"));
                db.recipeDao().insert(new Recipe("Sweet Milk", "milk, sugar", "milk:1,sugar:0.5", "1. Heat milk. 2. Add sugar and stir"));
                db.recipeDao().insert(new Recipe("Scrambled Eggs", "eggs, butter, salt", "eggs:3,butter:0.2,salt:0.1", "1. Melt butter. 2. Add beaten eggs. 3. Stir till set"));
                db.recipeDao().insert(new Recipe("French Toast", "bread, eggs, milk, sugar", "bread:4,eggs:2,milk:0.5,sugar:0.3", "1. Mix eggs milk sugar. 2. Dip bread. 3. Fry"));
                db.recipeDao().insert(new Recipe("Cheese Sandwich", "bread, cheese, butter", "bread:2,cheese:1,butter:0.2", "1. Butter bread. 2. Add cheese. 3. Toast"));
                db.recipeDao().insert(new Recipe("Boiled Eggs", "eggs, water, salt", "eggs:4,water:2,salt:0.1", "1. Boil water. 2. Add eggs 10min. 3. Peel"));
                db.recipeDao().insert(new Recipe("Milk Tea", "milk, tea, sugar, water", "milk:0.5,tea:0.1,sugar:0.3,water:1", "1. Boil water + tea. 2. Add milk sugar. 3. Serve"));
                db.recipeDao().insert(new Recipe("Sugar Bread", "flour, sugar, yeast, water", "flour:3,sugar:1,yeast:0.1,water:1", "1. Mix all. 2. Knead. 3. Bake 180C 30min"));
                db.recipeDao().insert(new Recipe("Fried Eggs", "eggs, oil, salt", "eggs:2,oil:0.2,salt:0.1", "1. Heat oil. 2. Break eggs. 3. Fry"));
                db.recipeDao().insert(new Recipe("Cheese Omelette", "eggs, cheese, milk, butter", "eggs:3,cheese:1,milk:0.2,butter:0.2", "1. Beat eggs with milk. 2. Fry with cheese"));
                db.recipeDao().insert(new Recipe("Butter Toast", "bread, butter, sugar", "bread:2,butter:0.3,sugar:0.2", "1. Toast bread. 2. Spread butter sugar"));
                db.recipeDao().insert(new Recipe("Salted Popcorn", "corn, salt, oil", "corn:1,salt:0.1,oil:0.2", "1. Heat oil. 2. Add corn. 3. Add salt"));
                db.recipeDao().insert(new Recipe("Plain Rice", "rice, water, salt", "rice:2,water:4,salt:0.1", "1. Wash rice. 2. Boil water add rice. 3. Cook 15min"));
                db.recipeDao().insert(new Recipe("Tomato Sandwich", "bread, tomato, salt", "bread:2,tomato:1,salt:0.1", "1. Slice tomato. 2. Put on bread. 3. Add salt"));
                db.recipeDao().insert(new Recipe("Egg Fried Rice", "rice, eggs, oil, salt", "rice:2,eggs:2,oil:0.3,salt:0.2", "1. Fry eggs. 2. Add cooked rice. 3. Mix"));
                db.recipeDao().insert(new Recipe("Milk Rice", "rice, milk, sugar", "rice:1,milk:1,sugar:0.5", "1. Cook rice in milk. 2. Add sugar"));
                db.recipeDao().insert(new Recipe("Flour Tortilla", "flour, water, oil, salt", "flour:2,water:1,oil:0.2,salt:0.1", "1. Mix dough. 2. Roll flat. 3. Fry dry pan"));
                db.recipeDao().insert(new Recipe("Cheese Toast", "bread, cheese, tomato", "bread:2,cheese:1,tomato:0.5", "1. Toast bread. 2. Melt cheese + tomato on top"));
                db.recipeDao().insert(new Recipe("Sweet Eggs", "eggs, sugar, milk", "eggs:2,sugar:0.5,milk:0.5", "1. Mix eggs sugar milk. 2. Fry sweet"));
            }
        });
    }
}