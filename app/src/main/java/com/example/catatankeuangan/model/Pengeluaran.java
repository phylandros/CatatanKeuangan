package com.example.catatankeuangan.model;

public class Pengeluaran {
    private int id;
    private String tanggal;
    private String nominal;
    private String kategori;
    private String keterangan;

    public Pengeluaran(int id, String tanggal, String nominal, String kategori, String keterangan) {
        this.id = id;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.kategori = kategori;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
