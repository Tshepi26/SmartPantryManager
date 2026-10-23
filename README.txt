SMART PANTRY MANAGER - README

Student: Tshepiso
Project: Android Studio - SmartPantryManager
Database: SQLite via Room Version 9

1. PROJECT OVERVIEW
SmartPantryManager is an offline Android app that helps users manage pantry ingredients and suggests recipes they can make based on what they have. App uses two lists: "You Can Make" (strict matching) and "Almost There" (missing 1 ingredient).

2. FEATURES
- Add, edit, delete pantry ingredients with quantity
- Persistent storage using Room (SQLite) - data remains after app close
- View Recipes with strict-matching algorithm
- Two sections: Strict (missingCount == 0) and Almost There (missingCount == 1)
- Delete proof: deleting ingredient removes recipe from list, adding brings it back
- Offline first - no internet needed

3. TECH STACK
- Language: Java
- IDE: Android Studio
- Database: Room Persistence Library - SQLite (smart_pantry_db)
- Architecture: Singleton database pattern, Executors for background threading
- UI: RecyclerView, CardLayout, LinearLayoutManager

4. DATABASE STRUCTURE
File: PantryDatabase.java
- @Database(entities = {PantryItem.class, Recipe.class}, version = 9, exportSchema = false)
- DAOs: pantryDao() and recipeDao()
- Singleton: getDatabase(context) - synchronized block prevents multiple instances
- Migration: fallbackToDestructiveMigration() used during development v7 to v9
- Callback: sRoomCallback onCreate seeds 20 recipes on first install

Tables:
- pantry_items (id, name, quantity, unit)
- recipes (id, name, ingredients string format "eggs:2,milk:0.5,salt:1", instructions)

Seeding: RecipeSeeder.java seed(db) inserts 20 recipes e.g. Scrambled Eggs, Tomato Soup, Pancakes

5. CORE FILES AND LINES (FOR VIDEO)
- MainActivity.java Line 20-44: onCreate sets content view, RecyclerView (24-26), database init Line 31 PantryDatabase.getDatabase(this), adapter init Line 34-35 FIXED order Context first then list
- MainActivity.java Line 63-67: onResume() calls loadPantryItems() to refresh list after returning from AddEditIngredientActivity - demonstrates Activity Lifecycle
- PantryDatabase.java Line 16: version 9, Line 25-41 singleton, Line 33 fallbackToDestructiveMigration, Line 45-62 callback
- SuggestedRecipesActivity.java Line 44-76: loadRecipes() gets pantry + recipes in background Executor, splits into strictList and almostList
- Line 79-109: countMissingIngredients() - splits requiredIngredients by comma, then by colon to get name and quantity, normalizes names, checks quantity >= required
- Line 112-121: normalize() - removes "ies","es","s" to match "tomatoes" with "tomato"
- Line 53: if missingCount == 0 add to strictList, if missingCount == 1 add to almostList

6. STRICT-MATCHING ALGORITHM EXPLANATION
Input: pantry list from DB, recipe required string e.g. "eggs:2,milk:0.5"
Process:
- For each recipe, count missing ingredients
- For each required ingredient, check if pantry contains same name (normalize) and enough quantity
- If not found or not enough, missing++
- If missing == 0 -> You Can Make, if missing == 1 -> Almost There
Example: Have Tomato 5, Cheese 1. Pancakes needs flour:2,milk:1,eggs:1 -> missing 1 (flour) -> Almost There. Add flour -> moves to You Can Make.

7. INSTALLATION
- Clone: git clone https://github.com/yourusername/SmartPantryManager.git
- Open in Android Studio
- Sync Gradle
- Run on emulator or device (API 24+)
- No extra setup - database auto-creates as smart_pantry_db

8. HOW TO DEMO (2-3 MIN)
- App starts MainActivity loads pantry from DB
- Click Add -> add Tomato qty 5, Cheese 1, Eggs 3
- Click View Recipes -> shows Strict and Almost There
- Add missing ingredient -> recipe moves from Almost to Strict
- Delete Eggs -> recipe disappears from Strict
- Close/reopen app -> data persists (Room proof)

9. DATABASE JUSTIFICATION
Chose SQLite via Room v9 over Firebase/PostgreSQL because:
- Offline support essential for kitchen
- No server/backend needed
- Compile-time checks, background threading with Executors, persistence
- Easy migration during dev with fallbackToDestructiveMigration

10. GITHUB COMMITS
[REPLACE WITH YOUR REAL COMMITS AFTER SCREENSHOT]
Example placeholder:
- Initial commit - project setup
- Add PantryItem model and PantryDao
- Add PantryDatabase version 7 singleton
- Fix PantryAdapter constructor order - Context first
- Add Recipe model and RecipeDao
- Update Database to version 9 with Recipe
- Add RecipeSeeder with 20 recipes
- Implement SuggestedRecipesActivity strict matching
- Add normalize() for plural handling
- Fix onResume refresh bug

11. VIDEO SCRIPT REFERENCE
Use the rewritten script I gave you - Part 2 Live Demo, Part 3 Concept Explanation with exact line numbers.

END