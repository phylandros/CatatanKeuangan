package com.example.catatankeuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.catatankeuangan.adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> transactionList = generateTransactionList();

        adapter = new MenuAdapter(this, transactionList);
        recyclerView.setAdapter(adapter);
    }

    private List<String> generateTransactionList() {
        List<String> transactionList = new ArrayList<>();
        transactionList.add("Pengeluaran");
        transactionList.add("Pemasukan");
        transactionList.add("Pemasukan Report");
        transactionList.add("Pengeluaran Report");
        transactionList.add("Semua Transaksi");
        // Tambahkan transaksi lainnya sesuai kebutuhan

        return transactionList;
    }
}