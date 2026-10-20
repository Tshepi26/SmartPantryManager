package com.mnisi.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mnisi.smartpantrymanager.database.PantryDatabase;
import com.mnisi.smartpantrymanager.model.PantryItem;
import java.util.concurrent.Executors;

public class AddEditIngredientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        EditText edtName = findViewById(R.id.edtName);
        EditText edtQty = findViewById(R.id.edtQuantity);
        EditText edtUnit = findViewById(R.id.edtUnit);
        Button btnSave = findViewById(R.id.btnSave);

        // Input validation example
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String qtyStr = edtQty.getText().toString().trim();
            String unit = edtUnit.getText().toString().trim();

            if (name.isEmpty()) {
                edtName.setError("Name required");
                return;
            }
            if (qtyStr.isEmpty()) {
                edtQty.setError("Quantity required");
                return;
            }
            try {
                double qty = Double.parseDouble(qtyStr);
                if (qty <= 0) throw new NumberFormatException();

                PantryItem item = new PantryItem(name, qty, unit);
                Executors.newSingleThreadExecutor().execute(() -> {
                    PantryDatabase.getDatabase(this).pantryDao().insert(item);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });
            } catch (NumberFormatException e) {
                edtQty.setError("Enter valid number > 0");
            }
        });
    }
}