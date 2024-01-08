package com.example.catatankeuangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.catatankeuangan.adapter.PemasukanAdapter;
import com.example.catatankeuangan.model.Pemasukan;

import java.util.List;

public class PemasukanReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PemasukanAdapter adapter;
    private List<Pemasukan> pemasukanList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan_report);

        recyclerView = findViewById(R.id.recyclerViewTransaksi);
        dbHelper = new DBHelper(this);
        pemasukanList = dbHelper.getPemasukanList();

        adapter = new PemasukanAdapter(this, pemasukanList);
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
                    // Jika di-swipe ke kiri, hapus pemasukan
                    deletePemasukan(position);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Jika di-swipe ke kanan, edit pemasukan
                    editPemasukan(position);
                }
            }
        };


        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


    }

    private void editPemasukan(int position) {
        Pemasukan selectedPemasukan = pemasukanList.get(position);

        Intent intent = new Intent(PemasukanReportActivity.this, EditPemasukanActivity.class);
        intent.putExtra("pemasukan_id", selectedPemasukan.getId());
        startActivity(intent);
    }

    private void deletePemasukan(int position) {
        Pemasukan deletedPemasukan = pemasukanList.get(position);

        dbHelper.hapusDataPemasukan(deletedPemasukan.getId());
        pemasukanList.remove(position);
        adapter.notifyItemRemoved(position);
    }

}