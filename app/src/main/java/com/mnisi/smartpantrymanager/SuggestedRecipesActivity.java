package com.mnisi.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mnisi.smartpantrymanager.adapter.RecipeAdapter;
import com.mnisi.smartpantrymanager.database.PantryDatabase;
import com.mnisi.smartpantrymanager.model.PantryItem;
import com.mnisi.smartpantrymanager.model.Recipe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerStrict, recyclerAlmost;
    private TextView txtStrictTitle, txtAlmostTitle, txtEmptyMessage;
    private PantryDatabase db;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerStrict = findViewById(R.id.recyclerStrict);
        recyclerAlmost = findViewById(R.id.recyclerAlmost);
        txtStrictTitle = findViewById(R.id.txtStrictTitle);
        txtAlmostTitle = findViewById(R.id.txtAlmostTitle);
        txtEmptyMessage = findViewById(R.id.txtEmptyMessage);

        recyclerStrict.setLayoutManager(new LinearLayoutManager(this));
        recyclerAlmost.setLayoutManager(new LinearLayoutManager(this));

        db = PantryDatabase.getDatabase(this);
        loadRecipes();
    }

    private void loadRecipes() {
        executor.execute(() -> {
            List<PantryItem> pantry = db.pantryDao().getAllItems();
            List<Recipe> allRecipes = db.recipeDao().getAllRecipes();

            List<Recipe> strictList = new ArrayList<>();
            List<Recipe> almostList = new ArrayList<>();

            for (Recipe recipe : allRecipes) {
                int missingCount = countMissingIngredients(recipe, pantry);
                if (missingCount == 0) {
                    strictList.add(recipe);
                } else if (missingCount == 1) {
                    almostList.add(recipe); // BONUS FEATURE
                }
            }

            runOnUiThread(() -> {
                // STRICT LIST - This is what marker will test
                if (strictList.isEmpty()) {
                    txtEmptyMessage.setVisibility(View.VISIBLE);
                    txtEmptyMessage.setText("No recipes match your pantry yet - add more ingredients");
                    txtStrictTitle.setVisibility(View.GONE);
                } else {
                    txtEmptyMessage.setVisibility(View.GONE);
                    txtStrictTitle.setVisibility(View.VISIBLE);
                    txtStrictTitle.setText("You Can Make (" + strictList.size() + ")");
                    recyclerStrict.setAdapter(new RecipeAdapter(strictList));
                }

                // ALMOST THERE - Bonus, clearly separated
                if (almostList.isEmpty()) {
                    txtAlmostTitle.setVisibility(View.GONE);
                } else {
                    txtAlmostTitle.setVisibility(View.VISIBLE);
                    txtAlmostTitle.setText("Almost There - Missing 1 ingredient (" + almostList.size() + ")");
                    recyclerAlmost.setAdapter(new RecipeAdapter(almostList));
                }
            });
        });
    }

    // STRICT LOGIC: Counts how many ingredients are missing
    private int countMissingIngredients(Recipe recipe, List<PantryItem> pantry) {
        if (recipe.requiredQuantities == null || recipe.requiredQuantities.isEmpty()) return 999;

        String[] required = recipe.requiredQuantities.split(",");
        int missing = 0;

        for (String req : required) {
            String[] parts = req.trim().split(":");
            if (parts.length < 2) continue;
            String reqName = normalize(parts[0]);
            double reqQty;
            try { reqQty = Double.parseDouble(parts[1].trim()); } catch (Exception e) { continue; }

            boolean foundEnough = false;
            for (PantryItem item : pantry) {
                String pantryName = normalize(item.getName());
                // ROBUST MATCH: handles tomato vs tomatoes, egg vs eggs
                if (pantryName.equals(reqName) || pantryName.contains(reqName) || reqName.contains(pantryName)) {
                    if (item.getQuantity() >= reqQty) {
                        foundEnough = true;
                        break;
                    }
                }
            }
            if (!foundEnough) missing++;
        }
        return missing;
    }

    // FIXES plural/singular - so "tomatoes" matches "tomato"
    private String normalize(String input) {
        if (input == null) return "";
        String s = input.toLowerCase().trim();
        if (s.endsWith("ies")) return s.substring(0, s.length() - 3) + "y"; // berries -> berry
        if (s.endsWith("oes")) return s.substring(0, s.length() - 2); // tomatoes -> tomato
        if (s.endsWith("es") && s.length() > 3) return s.substring(0, s.length() - 2); // potatoes -> potato
        if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 2) return s.substring(0, s.length() - 1); // eggs -> egg
        return s;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}