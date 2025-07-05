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

import com.lan.myjadwal.R;
import com.lan.myjadwal.data.database.DatabaseHelper;

public class EditKegiatanActivity extends AppCompatActivity {
    private EditText etJudul, etDeskripsi, etTanggal, etWaktu, etLokasi;
    private Spinner spinnerKategori;
    private Button btnUpdate, btnBatal;
    private DatabaseHelper dbHelper;
    private Kegiatan kegiatan;
    private int kegiatanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kegiatan);

        kegiatanId = getIntent().getIntExtra("kegiatan_id", -1);

        initViews();
        setupSpinner();
        loadKegiatanData();
        setupClickListeners();
    }

    private void initViews() {
        etJudul = findViewById(R.id.etJudul);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        etTanggal = findViewById(R.id.etTanggal);
        etWaktu = findViewById(R.id.etWaktu);
        etLokasi = findViewById(R.id.etLokasi);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBatal = findViewById(R.id.btnBatal);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupSpinner() {
        String[] kategori = {"Kuliah", "Tugas", "Ujian", "Organisasi", "Pribadi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
    }

    private void loadKegiatanData() {
        kegiatan = dbHelper.getKegiatan(kegiatanId);
        if (kegiatan != null) {
            etJudul.setText(kegiatan.getJudul());
            etDeskripsi.setText(kegiatan.getDeskripsi());
            etTanggal.setText(kegiatan.getTanggal());
            etWaktu.setText(kegiatan.getWaktu());
            etLokasi.setText(kegiatan.getLokasi());

            // Set spinner selection
            String[] kategori = {"Kuliah", "Tugas", "Ujian", "Organisasi", "Pribadi"};
            for (int i = 0; i < kategori.length; i++) {
                if (kategori[i].equals(kegiatan.getKategori())) {
                    spinnerKategori.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setupClickListeners() {
        etTanggal.setOnClickListener(v -> showDatePicker());
        etWaktu.setOnClickListener(v -> showTimePicker());

        btnUpdate.setOnClickListener(v -> updateKegiatan());
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

    private void updateKegiatan() {
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

        kegiatan.setJudul(judul);
        kegiatan.setDeskripsi(deskripsi);
        kegiatan.setTanggal(tanggal);
        kegiatan.setWaktu(waktu);
        kegiatan.setLokasi(lokasi);
        kegiatan.setKategori(kategori);

        dbHelper.updateKegiatan(kegiatan);

        Toast.makeText(this, "Kegiatan berhasil diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}
