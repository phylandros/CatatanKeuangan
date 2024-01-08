package com.example.catatankeuangan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.model.Pengeluaran;

import java.util.List;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.ViewHolder> {

    private List<Pengeluaran> pengeluaranList;
    private Context context;

    public PengeluaranAdapter(Context context, List<Pengeluaran> pengeluaranList) {
        this.context = context;
        this.pengeluaranList = pengeluaranList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pengeluaran pengeluaran = pengeluaranList.get(position);
        holder.txtTanggal.setText(pengeluaran.getTanggal());
        holder.txtNominal.setText(pengeluaran.getNominal());
        holder.txtKategori.setText(pengeluaran.getKategori());
        holder.txtKeterangan.setText(pengeluaran.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return pengeluaranList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtNominal, txtKategori, txtKeterangan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.textViewTanggal);
            txtNominal = itemView.findViewById(R.id.textViewNominal);
            txtKategori = itemView.findViewById(R.id.textViewKategori);
            txtKeterangan = itemView.findViewById(R.id.textViewKeterangan);
        }
    }


}

