package com.example.catatankeuangan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.catatankeuangan.model.Pengeluaran;

public class EditPengeluaranActivity extends AppCompatActivity {

    private EditText editTextDate, etNominal, etKeterangan;
    private Spinner spinnerKategori;
    private Button btnSimpan;
    private int pengeluaranId;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengeluaran);

        editTextDate = findViewById(R.id.editTextDate);
        etNominal = findViewById(R.id.etnom);
        etKeterangan = findViewById(R.id.etket);
        spinnerKategori = findViewById(R.id.spkat);
        btnSimpan = findViewById(R.id.btnsimpan);

        dbHelper = new DBHelper(this);

        pengeluaranId = getIntent().getIntExtra("pengeluaran_id", -1);

        Pengeluaran pengeluaran = dbHelper.getPengeluaranById(pengeluaranId);
        if (pengeluaran != null) {
            editTextDate.setText(pengeluaran.getTanggal());
            etNominal.setText(pengeluaran.getNominal());
            etKeterangan.setText(pengeluaran.getKeterangan());
            setupSpinner(pengeluaran.getKategori());
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePengeluaran();
            }
        });
    }

    private void setupSpinner(String kategori) {
        String[] kategoriPengeluaran = getResources().getStringArray(R.array.kategori_transaksi);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategoriPengeluaran);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        if (kategori != null) {
            int spinnerPosition = adapter.getPosition(kategori);
            spinnerKategori.setSelection(spinnerPosition);
        }
    }

    private void updatePengeluaran() {
        String tanggal = editTextDate.getText().toString().trim();
        String nominal = etNominal.getText().toString().trim();
        String kategori = spinnerKategori.getSelectedItem().toString();
        String keterangan = etKeterangan.getText().toString().trim();

        dbHelper.updatePengeluaran(pengeluaranId, tanggal, nominal, kategori, keterangan);

        // Tambahan: Anda mungkin ingin kembali ke aktivitas sebelumnya setelah mengupdate data
        onBackPressed();
    }
}
