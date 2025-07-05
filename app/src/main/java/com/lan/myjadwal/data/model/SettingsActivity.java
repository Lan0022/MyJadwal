package com.lan.myjadwal.data.model;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.lan.myjadwal.R;
import com.lan.myjadwal.data.database.DatabaseHelper;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchNotifikasi, switchDarkMode;
    private Button btnResetData, btnKembali;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        loadSettings();
        setupClickListeners();
    }

    private void initViews() {
        switchNotifikasi = findViewById(R.id.switchNotifikasi);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnResetData = findViewById(R.id.btnResetData);
        btnKembali = findViewById(R.id.btnKembali);
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);
    }

    private void loadSettings() {
        switchNotifikasi.setChecked(sharedPreferences.getBoolean("notifikasi", true));
        switchDarkMode.setChecked(sharedPreferences.getBoolean("dark_mode", false));
    }

    private void setupClickListeners() {
        switchNotifikasi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notifikasi", isChecked);
            editor.apply();
            Toast.makeText(this, "Pengaturan notifikasi " + (isChecked ? "diaktifkan" : "dinonaktifkan"), Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();
            Toast.makeText(this, "Mode gelap " + (isChecked ? "diaktifkan" : "dinonaktifkan"), Toast.LENGTH_SHORT).show();
        });

        btnResetData.setOnClickListener(v -> {
            // Reset semua data
            dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 1);
            Toast.makeText(this, "Data berhasil direset", Toast.LENGTH_SHORT).show();
        });

        btnKembali.setOnClickListener(v -> finish());
    }
}
