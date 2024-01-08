package com.example.catatankeuangan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.model.TransaksiModel;
import java.util.ArrayList;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    private ArrayList<TransaksiModel> transaksiList;

    public TransaksiAdapter(ArrayList<TransaksiModel> transaksiList) {
        this.transaksiList = transaksiList;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaksi, parent, false);
        return new TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        TransaksiModel transaksi = transaksiList.get(position);
        holder.textViewTanggal.setText(transaksi.getTanggal());
        holder.textViewNominal.setText(transaksi.getNominal());
        holder.textViewKategori.setText(transaksi.getKategori());
        holder.textViewKeterangan.setText(transaksi.getKeterangan());
    }

    public void removeItem(int position) {
        transaksiList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    static class TransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTanggal, textViewNominal, textViewKategori, textViewKeterangan;

        TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            textViewNominal = itemView.findViewById(R.id.textViewNominal);
            textViewKategori = itemView.findViewById(R.id.textViewKategori);
            textViewKeterangan = itemView.findViewById(R.id.textViewKeterangan);
        }
    }
}
