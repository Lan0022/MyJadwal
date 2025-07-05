package com.lan.myjadwal.data.model;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import com.lan.myjadwal.data.database.DatabaseHelper;
import com.lan.myjadwal.R;

public class TambahKegiatanActivity extends AppCompatActivity {
    private EditText etJudul, etDeskripsi, etTanggal, etWaktu, etLokasi;
    private Spinner spinnerKategori;
    private Button btnSimpan, btnBatal;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        initViews();
        setupSpinner();
        setupClickListeners();
    }

    private void initViews() {
        etJudul = findViewById(R.id.etJudul);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        etTanggal = findViewById(R.id.etTanggal);
        etWaktu = findViewById(R.id.etWaktu);
        etLokasi = findViewById(R.id.etLokasi);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupSpinner() {
        String[] kategori = {"Kuliah", "Tugas", "Ujian", "Organisasi", "Pribadi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
    }

    private void setupClickListeners() {
        etTanggal.setOnClickListener(v -> showDatePicker());
        etWaktu.setOnClickListener(v -> showTimePicker());

        btnSimpan.setOnClickListener(v -> simpanKegiatan());
        btnBatal.setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String tanggal = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                    etTanggal.setText(tanggal);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String waktu = String.format("%02d:%02d", hourOfDay, minute);
                    etWaktu.setText(waktu);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    private void simpanKegiatan() {
        String judul = etJudul.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();
        String tanggal = etTanggal.getText().toString().trim();
        String waktu = etWaktu.getText().toString().trim();
        String lokasi = etLokasi.getText().toString().trim();
        String kategori = spinnerKategori.getSelectedItem().toString();

        if (judul.isEmpty() || tanggal.isEmpty() || waktu.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi data yang diperlukan", Toast.LENGTH_SHORT).show();
            return;
        }

        Kegiatan kegiatan = new Kegiatan(judul, deskripsi, tanggal, waktu, lokasi, kategori);
        dbHelper.addKegiatan(kegiatan);

        Toast.makeText(this, "Kegiatan berhasil disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }
}
