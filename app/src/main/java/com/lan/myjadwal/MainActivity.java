package com.lan.myjadwal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.lan.myjadwal.data.database.DatabaseHelper;
import com.lan.myjadwal.data.model.DaftarKegiatanActivity;
import com.lan.myjadwal.data.model.Kegiatan;
import com.lan.myjadwal.data.model.KegiatanAdapter;
import com.lan.myjadwal.data.model.SettingsActivity;
import com.lan.myjadwal.data.model.TambahKegiatanActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnTambahKegiatan, btnDaftarKegiatan, btnSettings;
    private RecyclerView recyclerViewHariIni;
    private KegiatanAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupClickListeners();
        loadKegiatanHariIni();
    }

    private void initViews() {
        btnTambahKegiatan = findViewById(R.id.btnTambahKegiatan);
        btnDaftarKegiatan = findViewById(R.id.btnDaftarKegiatan);
        btnSettings = findViewById(R.id.btnSettings);
        recyclerViewHariIni = findViewById(R.id.recyclerViewHariIni);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupRecyclerView() {
        recyclerViewHariIni.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KegiatanAdapter(this, new ArrayList<>());
        recyclerViewHariIni.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnTambahKegiatan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahKegiatanActivity.class);
            startActivity(intent);
        });

        btnDaftarKegiatan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DaftarKegiatanActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void loadKegiatanHariIni() {
        String tanggalHariIni = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        List<Kegiatan> semuaKegiatan = dbHelper.getAllKegiatan();
        List<Kegiatan> kegiatanHariIni = new ArrayList<>();

        for (Kegiatan kegiatan : semuaKegiatan) {
            if (kegiatan.getTanggal().equals(tanggalHariIni)) {
                kegiatanHariIni.add(kegiatan);
            }
        }

        adapter.updateData(kegiatanHariIni);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKegiatanHariIni();
    }
}