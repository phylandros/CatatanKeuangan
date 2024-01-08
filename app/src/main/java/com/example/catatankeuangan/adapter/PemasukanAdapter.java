package com.example.catatankeuangan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.model.Pemasukan;

import java.util.List;

public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.ViewHolder> {

    private List<Pemasukan> pemasukanList;
    private Context context;

    public PemasukanAdapter(Context context, List<Pemasukan> pemasukanList) {
        this.context = context;
        this.pemasukanList = pemasukanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pemasukan pemasukan = pemasukanList.get(position);
        holder.txtTanggal.setText(pemasukan.getTanggal());
        holder.txtNominal.setText(pemasukan.getNominal());
        holder.txtKategori.setText(pemasukan.getKategori());
        holder.txtKeterangan.setText(pemasukan.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return pemasukanList.size();
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
