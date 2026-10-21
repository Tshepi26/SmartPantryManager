package com.mnisi.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
//Settings with notification

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchExpiry;
    private Spinner spinnerUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiry = findViewById(R.id.switchExpiryAlert);
        spinnerUnits = findViewById(R.id.spinnerUnits);

        // Setup spinner
        String[] units = {"Metric (kg, g)", "Imperial (lb, oz)", "Both"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnits.setAdapter(adapter);

        // Load saved preferences
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        switchExpiry.setChecked(prefs.getBoolean("expiry_alert", true));
        spinnerUnits.setSelection(prefs.getInt("units_index", 0));

        // Save when changed
        switchExpiry.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("expiry_alert", isChecked).apply();
        });

        spinnerUnits.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                prefs.edit().putInt("units_index", position).apply();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Back button in ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
