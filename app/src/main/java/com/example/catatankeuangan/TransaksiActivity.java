package com.example.catatankeuangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.catatankeuangan.DBHelper;


public class TransaksiActivity extends AppCompatActivity {

    private Spinner spinnerKategori;
    private EditText editTextDate, etnom, etket;
    private String iteminput;
    private DBHelper dbHelper;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        dbHelper = new DBHelper(this);
        title = findViewById(R.id.title);

        // Inisialisasi komponen UI
        editTextDate = findViewById(R.id.editTextDate);
        etnom = findViewById(R.id.etnom);
        etket = findViewById(R.id.etket);
        spinnerKategori = findViewById(R.id.spkat);
        Button btnSimpan = findViewById(R.id.btnsimpan);

        // Mendapatkan data dari Intent
        iteminput = getIntent().getStringExtra("menu");
        title.setText("Transaksi " + iteminput); // Set judul sesuai dengan data yang diterima

        // Mendapatkan daftar kategori transaksi dari resources
        String[] kategoriTransaksi = getResources().getStringArray(R.array.kategori_transaksi);

        // Inisialisasi spinner dengan daftar kategori transaksi
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategoriTransaksi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        // Ketika tombol Simpan ditekan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal = editTextDate.getText().toString();
                String nominal = etnom.getText().toString();
                String kategori = spinnerKategori.getSelectedItem().toString();
                String keterangan = etket.getText().toString();

                // Menyimpan data ke dalam database sesuai dengan jenis transaksi
                if (iteminput.equals("Pengeluaran")) {
                    dbHelper.insertDataPengeluaran(tanggal, nominal, kategori, keterangan);
                } else if (iteminput.equals("Pemasukan")) {
                    dbHelper.insertDataPemasukan(tanggal, nominal, kategori, keterangan);
                }

                // Lakukan apa yang diperlukan setelah penyimpanan, misalnya tampilkan pesan sukses atau navigasi kembali
                finish();
            }
        });


    }
}