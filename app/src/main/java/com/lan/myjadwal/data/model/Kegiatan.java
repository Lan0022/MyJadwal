package com.lan.myjadwal.data.model;

public class Kegiatan {
    private int id;
    private String judul;
    private String deskripsi;
    private String tanggal;
    private String waktu;
    private String lokasi;
    private String kategori;

    // Constructor kosong
    public Kegiatan() {}

    // Constructor dengan parameter
    public Kegiatan(String judul, String deskripsi, String tanggal, String waktu, String lokasi, String kategori) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.lokasi = lokasi;
        this.kategori = kategori;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
}
