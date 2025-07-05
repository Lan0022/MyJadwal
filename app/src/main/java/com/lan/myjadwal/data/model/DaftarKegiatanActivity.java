package com.lan.myjadwal.data.model;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.lan.myjadwal.R;
import com.lan.myjadwal.data.database.DatabaseHelper;

public class DaftarKegiatanActivity extends AppCompatActivity {
    private RecyclerView recyclerViewKegiatan;
    private KegiatanAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        initViews();
        setupRecyclerView();
        loadKegiatan();
    }

    private void initViews() {
        recyclerViewKegiatan = findViewById(R.id.recyclerViewKegiatan);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupRecyclerView() {
        recyclerViewKegiatan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KegiatanAdapter(this, dbHelper.getAllKegiatan());
        recyclerViewKegiatan.setAdapter(adapter);
    }

    private void loadKegiatan() {
        List<Kegiatan> kegiatanList = dbHelper.getAllKegiatan();
        adapter.updateData(kegiatanList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKegiatan();
    }
}
