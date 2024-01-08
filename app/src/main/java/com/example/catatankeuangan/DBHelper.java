package com.example.catatankeuangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.catatankeuangan.model.Pemasukan;
import com.example.catatankeuangan.model.Pengeluaran;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "catatan_keuangan.db";
    private static final int DATABASE_VERSION = 1;

    // Table Pengeluaran
    private static final String TABLE_PENGELUARAN = "pengeluaran";
    private static final String COLUMN_ID_PENGELUARAN = "id_pengeluaran";
    private static final String COLUMN_TANGGAL_PENGELUARAN = "tanggal_pengeluaran";
    private static final String COLUMN_NOMINAL_PENGELUARAN = "nominal_pengeluaran";
    private static final String COLUMN_KATEGORI_PENGELUARAN = "kategori_pengeluaran";
    private static final String COLUMN_KETERANGAN_PENGELUARAN = "keterangan_pengeluaran";

    // Table Pemasukan
    private static final String TABLE_PEMASUKAN = "pemasukan";
    private static final String COLUMN_ID_PEMASUKAN = "id_pemasukan";
    private static final String COLUMN_TANGGAL_PEMASUKAN = "tanggal_pemasukan";
    private static final String COLUMN_NOMINAL_PEMASUKAN = "nominal_pemasukan";
    private static final String COLUMN_KATEGORI_PEMASUKAN = "kategori_pemasukan";
    private static final String COLUMN_KETERANGAN_PEMASUKAN = "keterangan_pemasukan";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel Pengeluaran
        String createPengeluaranTableQuery = "CREATE TABLE " + TABLE_PENGELUARAN + " (" +
                COLUMN_ID_PENGELUARAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TANGGAL_PENGELUARAN + " TEXT, " +
                COLUMN_NOMINAL_PENGELUARAN + " TEXT, " +
                COLUMN_KATEGORI_PENGELUARAN + " TEXT, " +
                COLUMN_KETERANGAN_PENGELUARAN + " TEXT)";
        db.execSQL(createPengeluaranTableQuery);

        // Membuat tabel Pemasukan
        String createPemasukanTableQuery = "CREATE TABLE " + TABLE_PEMASUKAN + " (" +
                COLUMN_ID_PEMASUKAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TANGGAL_PEMASUKAN + " TEXT, " +
                COLUMN_NOMINAL_PEMASUKAN + " TEXT, " +
                COLUMN_KATEGORI_PEMASUKAN + " TEXT, " +
                COLUMN_KETERANGAN_PEMASUKAN + " TEXT)";
        db.execSQL(createPemasukanTableQuery);

        // Membuat tabel Semua Transaksi dengan relasi ke Pengeluaran dan Pemasukan
        String createSemuaTransaksiTableQuery = "CREATE TABLE semua_transaksi (" +
                "id_transaksi INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_pengeluaran INTEGER, " +
                "id_pemasukan INTEGER, " +
                "FOREIGN KEY(id_pengeluaran) REFERENCES pengeluaran(id_pengeluaran), " +
                "FOREIGN KEY(id_pemasukan) REFERENCES pemasukan(id_pemasukan))";
        db.execSQL(createSemuaTransaksiTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGELUARAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEMASUKAN);
        onCreate(db);
    }

    public void insertDataPengeluaran(String tanggal, String nominal, String kategori, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TANGGAL_PENGELUARAN, tanggal);
        values.put(COLUMN_NOMINAL_PENGELUARAN, nominal);
        values.put(COLUMN_KATEGORI_PENGELUARAN, kategori);
        values.put(COLUMN_KETERANGAN_PENGELUARAN, keterangan);
        db.insert(TABLE_PENGELUARAN, null, values);
        db.close();
    }

    public void insertDataPemasukan(String tanggal, String nominal, String kategori, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TANGGAL_PEMASUKAN, tanggal);
        values.put(COLUMN_NOMINAL_PEMASUKAN, nominal);
        values.put(COLUMN_KATEGORI_PEMASUKAN, kategori);
        values.put(COLUMN_KETERANGAN_PEMASUKAN, keterangan);
        db.insert(TABLE_PEMASUKAN, null, values);
        db.close();
    }

    public Cursor getSemuaTransaksi() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                COLUMN_TANGGAL_PENGELUARAN + " AS tanggal, " +
                COLUMN_NOMINAL_PENGELUARAN + " AS nominal, " +
                COLUMN_KATEGORI_PENGELUARAN + " AS kategori, " +
                COLUMN_KETERANGAN_PENGELUARAN + " AS keterangan " +
                "FROM " + TABLE_PENGELUARAN +
                " UNION ALL " +
                "SELECT " +
                COLUMN_TANGGAL_PEMASUKAN + ", " +
                COLUMN_NOMINAL_PEMASUKAN + ", " +
                COLUMN_KATEGORI_PEMASUKAN + ", " +
                COLUMN_KETERANGAN_PEMASUKAN +
                " FROM " + TABLE_PEMASUKAN;

        return db.rawQuery(query, null);
    }
    public void hapusDataPengeluaran(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PENGELUARAN, COLUMN_ID_PENGELUARAN + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void hapusDataPemasukan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PEMASUKAN, COLUMN_ID_PEMASUKAN + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    public List<Pengeluaran> getPengeluaranList() {
        List<Pengeluaran> pengeluaranList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PENGELUARAN,
                new String[]{COLUMN_ID_PENGELUARAN, COLUMN_TANGGAL_PENGELUARAN, COLUMN_NOMINAL_PENGELUARAN, COLUMN_KATEGORI_PENGELUARAN, COLUMN_KETERANGAN_PENGELUARAN},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PENGELUARAN));
                String tanggal = cursor.getString(cursor.getColumnIndex(COLUMN_TANGGAL_PENGELUARAN));
                String nominal = cursor.getString(cursor.getColumnIndex(COLUMN_NOMINAL_PENGELUARAN));
                String kategori = cursor.getString(cursor.getColumnIndex(COLUMN_KATEGORI_PENGELUARAN));
                String keterangan = cursor.getString(cursor.getColumnIndex(COLUMN_KETERANGAN_PENGELUARAN));

                Pengeluaran pengeluaran = new Pengeluaran(id, tanggal, nominal, kategori, keterangan);
                pengeluaranList.add(pengeluaran);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return pengeluaranList;
    }

    public List<Pemasukan> getPemasukanList() {
        List<Pemasukan> pemasukanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PEMASUKAN,
                new String[]{COLUMN_ID_PEMASUKAN, COLUMN_TANGGAL_PEMASUKAN, COLUMN_NOMINAL_PEMASUKAN, COLUMN_KATEGORI_PEMASUKAN, COLUMN_KETERANGAN_PEMASUKAN},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PEMASUKAN));
                String tanggal = cursor.getString(cursor.getColumnIndex(COLUMN_TANGGAL_PEMASUKAN));
                String nominal = cursor.getString(cursor.getColumnIndex(COLUMN_NOMINAL_PEMASUKAN));
                String kategori = cursor.getString(cursor.getColumnIndex(COLUMN_KATEGORI_PEMASUKAN));
                String keterangan = cursor.getString(cursor.getColumnIndex(COLUMN_KETERANGAN_PEMASUKAN));

                Pemasukan pemasukan = new Pemasukan(id, tanggal, nominal, kategori, keterangan);
                pemasukanList.add(pemasukan);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return pemasukanList;
    }

    public Pemasukan getPemasukanById(int pemasukanId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Pemasukan pemasukan = null;

        Cursor cursor = db.query(
                TABLE_PEMASUKAN,
                new String[]{COLUMN_ID_PEMASUKAN, COLUMN_TANGGAL_PEMASUKAN, COLUMN_NOMINAL_PEMASUKAN, COLUMN_KATEGORI_PEMASUKAN, COLUMN_KETERANGAN_PEMASUKAN},
                COLUMN_ID_PEMASUKAN + " = ?",
                new String[]{String.valueOf(pemasukanId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            pemasukan = new Pemasukan(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PEMASUKAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TANGGAL_PEMASUKAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOMINAL_PEMASUKAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_KATEGORI_PEMASUKAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_KETERANGAN_PEMASUKAN))
            );
            cursor.close();
        }

        return pemasukan;
    }

    public void updatePemasukan(int pemasukanId, String tanggal, String nominal, String kategori, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TANGGAL_PEMASUKAN, tanggal);
        values.put(COLUMN_NOMINAL_PEMASUKAN, nominal);
        values.put(COLUMN_KATEGORI_PEMASUKAN, kategori);
        values.put(COLUMN_KETERANGAN_PEMASUKAN, keterangan);

        db.update(TABLE_PEMASUKAN, values, COLUMN_ID_PEMASUKAN + " = ?", new String[]{String.valueOf(pemasukanId)});
        db.close();
    }

    public Pengeluaran getPengeluaranById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Pengeluaran pengeluaran = null;

        Cursor cursor = db.query(
                TABLE_PENGELUARAN,
                new String[]{COLUMN_ID_PENGELUARAN, COLUMN_TANGGAL_PENGELUARAN, COLUMN_NOMINAL_PENGELUARAN, COLUMN_KATEGORI_PENGELUARAN, COLUMN_KETERANGAN_PENGELUARAN},
                COLUMN_ID_PENGELUARAN + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            pengeluaran = new Pengeluaran(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID_PENGELUARAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TANGGAL_PENGELUARAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOMINAL_PENGELUARAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_KATEGORI_PENGELUARAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_KETERANGAN_PENGELUARAN))
            );
            cursor.close();
        }

        return pengeluaran;
    }

    public void updatePengeluaran(int id, String tanggal, String nominal, String kategori, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TANGGAL_PENGELUARAN, tanggal);
        values.put(COLUMN_NOMINAL_PENGELUARAN, nominal);
        values.put(COLUMN_KATEGORI_PENGELUARAN, kategori);
        values.put(COLUMN_KETERANGAN_PENGELUARAN, keterangan);

        db.update(TABLE_PENGELUARAN, values, COLUMN_ID_PENGELUARAN + "=?", new String[]{String.valueOf(id)});
        db.close();
    }



}
