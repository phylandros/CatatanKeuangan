package com.example.catatankeuangan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catatankeuangan.model.Pemasukan;

public class EditPemasukanActivity extends AppCompatActivity {

    private EditText editTextDate, etnom, etket;
    private Spinner spkat;
    private Button btnsimpan;
    private int pemasukanId; // ID pemasukan yang diedit
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pemasukan);

        editTextDate = findViewById(R.id.editTextDate);
        etnom = findViewById(R.id.etnom);
        etket = findViewById(R.id.etket);
        spkat = findViewById(R.id.spkat);
        btnsimpan = findViewById(R.id.btnsimpan);

        dbHelper = new DBHelper(this);

        // Mendapatkan ID pemasukan yang diedit dari Intent
        pemasukanId = getIntent().getIntExtra("pemasukan_id", -1);

        // Ambil data pemasukan berdasarkan ID dari database dan tampilkan di UI
        Pemasukan pemasukan = dbHelper.getPemasukanById(pemasukanId);
        if (pemasukan != null) {
            editTextDate.setText(pemasukan.getTanggal());
            etnom.setText(pemasukan.getNominal());
            etket.setText(pemasukan.getKeterangan());
            setupSpinner(pemasukan.getKategori());

            // Set nilai spinner sesuai dengan kategori pemasukan
            // spkat.setSelection(pemasukan.getKategori()); // Sesuaikan ini dengan cara set nilai Spinner
        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePemasukan();
            }
        });
    }

    private void setupSpinner(String kategori) {
        String[] kategoriTransaksi = getResources().getStringArray(R.array.kategori_transaksi); // Sesuaikan dengan nama array Anda

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategoriTransaksi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spkat.setAdapter(adapter);

        if (kategori != null) {
            int spinnerPosition = adapter.getPosition(kategori);
            spkat.setSelection(spinnerPosition);
        }
    }

    private void updatePemasukan() {
        String tanggal = editTextDate.getText().toString().trim();
        String nominal = etnom.getText().toString().trim();
        String keterangan = etket.getText().toString().trim();
        String kategori = spkat.getSelectedItem().toString(); // Ambil kategori dari Spinner, sesuaikan dengan cara mengambil nilai Spinner

        // Validasi data yang diubah
        if (tanggal.isEmpty() || nominal.isEmpty()) {
            // Tampilkan pesan kesalahan jika ada input yang kosong
            Toast.makeText(this, "Tanggal dan Nominal tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update data ke SQLite menggunakan DBHelper
        dbHelper.updatePemasukan(pemasukanId, tanggal, nominal, kategori, keterangan);

        // Tampilkan pesan berhasil dan kembali ke PemasukanActivity
        Toast.makeText(this, "Data pemasukan diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}
