package com.example.catatankeuangan.model;

public class TransaksiModel {
    private String tanggal;
    private String nominal;
    private String kategori;
    private String keterangan;
    private String type;
    public TransaksiModel(String tanggal, String nominal, String kategori, String keterangan) {
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.kategori = kategori;
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNominal() {
        return nominal;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
