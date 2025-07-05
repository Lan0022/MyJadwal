package com.lan.myjadwal.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import com.lan.myjadwal.data.model.Kegiatan;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "jadwal.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KEGIATAN = "kegiatan";
    private static final String KEY_ID = "id";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESKRIPSI = "deskripsi";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_WAKTU = "waktu";
    private static final String KEY_LOKASI = "lokasi";
    private static final String KEY_KATEGORI = "kategori";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_KEGIATAN + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_JUDUL + " TEXT,"
                + KEY_DESKRIPSI + " TEXT,"
                + KEY_TANGGAL + " TEXT,"
                + KEY_WAKTU + " TEXT,"
                + KEY_LOKASI + " TEXT,"
                + KEY_KATEGORI + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEGIATAN);
        onCreate(db);
    }

    // Tambah kegiatan
    public void addKegiatan(Kegiatan kegiatan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, kegiatan.getJudul());
        values.put(KEY_DESKRIPSI, kegiatan.getDeskripsi());
        values.put(KEY_TANGGAL, kegiatan.getTanggal());
        values.put(KEY_WAKTU, kegiatan.getWaktu());
        values.put(KEY_LOKASI, kegiatan.getLokasi());
        values.put(KEY_KATEGORI, kegiatan.getKategori());
        db.insert(TABLE_KEGIATAN, null, values);
        db.close();
    }

    // Ambil semua kegiatan
    public List<Kegiatan> getAllKegiatan() {
        List<Kegiatan> kegiatanList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_KEGIATAN + " ORDER BY " + KEY_TANGGAL + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kegiatan kegiatan = new Kegiatan();
                kegiatan.setId(cursor.getInt(0));
                kegiatan.setJudul(cursor.getString(1));
                kegiatan.setDeskripsi(cursor.getString(2));
                kegiatan.setTanggal(cursor.getString(3));
                kegiatan.setWaktu(cursor.getString(4));
                kegiatan.setLokasi(cursor.getString(5));
                kegiatan.setKategori(cursor.getString(6));
                kegiatanList.add(kegiatan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return kegiatanList;
    }

    // Update kegiatan
    public void updateKegiatan(Kegiatan kegiatan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, kegiatan.getJudul());
        values.put(KEY_DESKRIPSI, kegiatan.getDeskripsi());
        values.put(KEY_TANGGAL, kegiatan.getTanggal());
        values.put(KEY_WAKTU, kegiatan.getWaktu());
        values.put(KEY_LOKASI, kegiatan.getLokasi());
        values.put(KEY_KATEGORI, kegiatan.getKategori());
        db.update(TABLE_KEGIATAN, values, KEY_ID + " = ?", new String[]{String.valueOf(kegiatan.getId())});
        db.close();
    }

    // Hapus kegiatan
    public void deleteKegiatan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KEGIATAN, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Get kegiatan by ID
    public Kegiatan getKegiatan(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KEGIATAN, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Kegiatan kegiatan = new Kegiatan();
            kegiatan.setId(cursor.getInt(0));
            kegiatan.setJudul(cursor.getString(1));
            kegiatan.setDeskripsi(cursor.getString(2));
            kegiatan.setTanggal(cursor.getString(3));
            kegiatan.setWaktu(cursor.getString(4));
            kegiatan.setLokasi(cursor.getString(5));
            kegiatan.setKategori(cursor.getString(6));
            cursor.close();
            return kegiatan;
        }
        return null;
    }
}
