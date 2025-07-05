package com.lan.myjadwal.data.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lan.myjadwal.R;
import com.lan.myjadwal.data.database.DatabaseHelper;

public class DetailKegiatanActivity extends AppCompatActivity {
    private TextView tvJudul, tvDeskripsi, tvTanggal, tvWaktu, tvLokasi, tvKategori;
    private Button btnEdit, btnHapus, btnKembali;
    private DatabaseHelper dbHelper;
    private Kegiatan kegiatan;
    private int kegiatanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        kegiatanId = getIntent().getIntExtra("kegiatan_id", -1);

        initViews();
        loadDetailKegiatan();
        setupClickListeners();
    }

    private void initViews() {
        tvJudul = findViewById(R.id.tvJudul);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvTanggal = findViewById(R.id.tvTanggal);
        tvWaktu = findViewById(R.id.tvWaktu);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvKategori = findViewById(R.id.tvKategori);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);
        btnKembali = findViewById(R.id.btnKembali);
        dbHelper = new DatabaseHelper(this);
    }

    private void loadDetailKegiatan() {
        kegiatan = dbHelper.getKegiatan(kegiatanId);
        if (kegiatan != null) {
            tvJudul.setText(kegiatan.getJudul());
            tvDeskripsi.setText(kegiatan.getDeskripsi());
            tvTanggal.setText(kegiatan.getTanggal());
            tvWaktu.setText(kegiatan.getWaktu());
            tvLokasi.setText(kegiatan.getLokasi());
            tvKategori.setText(kegiatan.getKategori());
        }
    }

    private void setupClickListeners() {
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailKegiatanActivity.this, EditKegiatanActivity.class);
            intent.putExtra("kegiatan_id", kegiatanId);
            startActivity(intent);
        });

        btnHapus.setOnClickListener(v -> showDeleteConfirmation());

        btnKembali.setOnClickListener(v -> finish());
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus kegiatan ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    dbHelper.deleteKegiatan(kegiatanId);
                    Toast.makeText(this, "Kegiatan berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDetailKegiatan();
    }
}
