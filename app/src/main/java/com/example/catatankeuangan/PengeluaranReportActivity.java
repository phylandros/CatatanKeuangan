package com.example.catatankeuangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.catatankeuangan.adapter.PengeluaranAdapter;
import com.example.catatankeuangan.model.Pengeluaran;

import java.util.List;

public class PengeluaranReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PengeluaranAdapter adapter;
    private List<Pengeluaran> pengeluaranList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_report);

        recyclerView = findViewById(R.id.recyclerViewTransaksi);
        dbHelper = new DBHelper(this);
        pengeluaranList = dbHelper.getPengeluaranList();

        adapter = new PengeluaranAdapter(this, pengeluaranList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // Mengizinkan geser ke kiri dan ke kanan

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    // Jika di-swipe ke kiri, hapus pengeluaran
                    deletePengeluaran(position);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Jika di-swipe ke kanan, edit pengeluaran
                    editPengeluaran(position);
                }
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void deletePengeluaran(int position) {
        Pengeluaran deletedPengeluaran = pengeluaranList.get(position);

        dbHelper.hapusDataPengeluaran(deletedPengeluaran.getId());
        pengeluaranList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void editPengeluaran(int position) {
        Pengeluaran selectedPengeluaran = pengeluaranList.get(position);

        Intent intent = new Intent(PengeluaranReportActivity.this, EditPengeluaranActivity.class);
        intent.putExtra("pengeluaran_id", selectedPengeluaran.getId());
        startActivity(intent);
    }

}
