package com.mnisi.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mnisi.smartpantrymanager.adapter.PantryAdapter;
import com.mnisi.smartpantrymanager.database.PantryDatabase;
import com.mnisi.smartpantrymanager.model.PantryItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    //ecyclerView and Room database

    private RecyclerView recyclerView;
    private Button btnAddItem, btnViewRecipes;
    private PantryDatabase db;
    private PantryAdapter adapter;
    private List<PantryItem> itemList = new ArrayList<>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerPantry);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnViewRecipes = findViewById(R.id.btnViewRecipes);

        db = PantryDatabase.getDatabase(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(itemList, db);
        recyclerView.setAdapter(adapter);

        // SEED 20 RECIPES
        RecipeSeeder.seed(db);

        loadPantryItems();

        btnAddItem.setOnClickListener(v -> showAddDialog());
        btnViewRecipes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SuggestedRecipesActivity.class));
        });

        // BOTTOM NAV - NOW SAFE
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_pantry);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_pantry) return true;
                else if (id == R.id.nav_recipes) {
                    startActivity(new Intent(this, SuggestedRecipesActivity.class));
                    return true;
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;
                }
                return false;
            });
        }
    }

    private void loadPantryItems() {
        executor.execute(() -> {
            List<PantryItem> items = db.pantryDao().getAllItems();
            runOnUiThread(() -> {
                itemList.clear();
                itemList.addAll(items);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtQty = view.findViewById(R.id.edtQuantity);
        EditText edtUnit = view.findViewById(R.id.edtUnit);

        new AlertDialog.Builder(this)
                .setTitle("Add Pantry Item")
                .setView(view)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = edtName.getText().toString().trim();
                    String qtyStr = edtQty.getText().toString().trim();
                    String unit = edtUnit.getText().toString().trim();
                    if (name.isEmpty()) {
                        edtName.setError("Name required");
                        return;
                    }
                    if (qtyStr.isEmpty()) {
                        edtQty.setError("Qty required");
                        return;
                    }
                    try {
                        double qty = Double.parseDouble(qtyStr);
                        if (qty <= 0) throw new NumberFormatException();
                        PantryItem item = new PantryItem(name, qty, unit);
                        executor.execute(() -> {
                            db.pantryDao().insert(item);
                            runOnUiThread(this::loadPantryItems);
                        });
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Enter valid number > 0", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}