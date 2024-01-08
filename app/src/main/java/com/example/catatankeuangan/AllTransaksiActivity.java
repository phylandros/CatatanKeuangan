package com.example.catatankeuangan;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.adapter.TransaksiAdapter;
import com.example.catatankeuangan.model.TransaksiModel;

import java.util.ArrayList;

public class AllTransaksiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransaksiAdapter transaksiAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaksi);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerViewTransaksi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = dbHelper.getSemuaTransaksi();
        ArrayList<TransaksiModel> transaksiList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String tanggal = cursor.getString(cursor.getColumnIndex("tanggal"));
                String nominal = cursor.getString(cursor.getColumnIndex("nominal"));
                String kategori = cursor.getString(cursor.getColumnIndex("kategori"));
                String keterangan = cursor.getString(cursor.getColumnIndex("keterangan"));

                TransaksiModel transaksi = new TransaksiModel(tanggal, nominal, kategori, keterangan);
                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }

        cursor.close();

        transaksiAdapter = new TransaksiAdapter(transaksiList);
        recyclerView.setAdapter(transaksiAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                transaksiAdapter.removeItem(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}



