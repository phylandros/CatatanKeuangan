package com.example.catatankeuangan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.AllTransaksiActivity;
import com.example.catatankeuangan.PemasukanReportActivity;
import com.example.catatankeuangan.PengeluaranReportActivity;
import com.example.catatankeuangan.R;
import com.example.catatankeuangan.TransaksiActivity;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<String> menuList;
    private Context context;

    public MenuAdapter(Context context, List<String> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        String menu = menuList.get(position);
        holder.textViewItem.setText(menu);

        // Contoh pengaturan gambar (pastikan untuk menyesuaikan dengan sumber gambar yang benar)
        if (menu.equals("Pengeluaran")) {
            holder.imageViewIcon.setImageResource(R.drawable.spending);
            holder.cardView.setOnClickListener(v -> {
                // Pindah ke TransaksiActivity saat item Pengeluaran diklik
                Intent intent = new Intent(context, TransaksiActivity.class);
                intent.putExtra("menu", "Pengeluaran");
                context.startActivity(intent);
            });
        } else if (menu.equals("Pemasukan")) {
            holder.imageViewIcon.setImageResource(R.drawable.salary);
            holder.cardView.setOnClickListener(v -> {
                // Pindah ke TransaksiActivity saat item Pemasukan diklik
                Intent intent = new Intent(context, TransaksiActivity.class);
                intent.putExtra("menu", "Pemasukan");
                context.startActivity(intent);
            });
        } else if (menu.equals("Pengeluaran Report")) {
            holder.imageViewIcon.setImageResource(R.drawable.salary);
            holder.cardView.setOnClickListener(v -> {
                // Pindah ke TransaksiActivity saat item Pemasukan diklik
                Intent intent = new Intent(context, PengeluaranReportActivity.class);
                context.startActivity(intent);
            });
        } else if (menu.equals("Pemasukan Report")) {
            holder.imageViewIcon.setImageResource(R.drawable.salary);
            holder.cardView.setOnClickListener(v -> {
                // Pindah ke TransaksiActivity saat item Pemasukan diklik
                Intent intent = new Intent(context, PemasukanReportActivity.class);
                context.startActivity(intent);
            });
        } else if (menu.equals("Semua Transaksi")) {
            holder.imageViewIcon.setImageResource(R.drawable.alltransaction);
            holder.cardView.setOnClickListener(v -> {
                // Pindah ke TransaksiActivity saat item Semua Transaksi diklik
                Intent intent = new Intent(context, AllTransaksiActivity.class);
                intent.putExtra("menu", "Semua Transaksi");
                context.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewItem;
        ImageView imageViewIcon;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textViewItem = itemView.findViewById(R.id.text_view_item);
            imageViewIcon = itemView.findViewById(R.id.image_view_icon);
        }
    }
}
