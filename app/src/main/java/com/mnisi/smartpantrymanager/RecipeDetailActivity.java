package com.mnisi.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView txtName = findViewById(R.id.detailName);
        TextView txtIng = findViewById(R.id.detailIngredients);
        TextView txtQty = findViewById(R.id.detailQty);
        TextView txtSteps = findViewById(R.id.detailSteps);

        txtName.setText(getIntent().getStringExtra("name"));
        txtIng.setText("Ingredients: " + getIntent().getStringExtra("ingredients"));
        txtQty.setText("Required: " + getIntent().getStringExtra("qty"));
        txtSteps.setText("Method: " + getIntent().getStringExtra("steps"));
    }
}