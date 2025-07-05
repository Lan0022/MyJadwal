package com.lan.myjadwal.data.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.lan.myjadwal.R;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.ViewHolder> {
    private Context context;
    private List<Kegiatan> kegiatanList;

    public KegiatanAdapter(Context context, List<Kegiatan> kegiatanList) {
        this.context = context;
        this.kegiatanList = kegiatanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kegiatan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kegiatan kegiatan = kegiatanList.get(position);
        holder.tvJudul.setText(kegiatan.getJudul());
        holder.tvTanggalWaktu.setText(kegiatan.getTanggal() + " - " + kegiatan.getWaktu());
        holder.tvLokasi.setText(kegiatan.getLokasi());
        holder.tvKategori.setText(kegiatan.getKategori());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailKegiatanActivity.class);
            intent.putExtra("kegiatan_id", kegiatan.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return kegiatanList.size();
    }

    public void updateData(List<Kegiatan> newList) {
        this.kegiatanList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvTanggalWaktu, tvLokasi, tvKategori;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvTanggalWaktu = itemView.findViewById(R.id.tvTanggalWaktu);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvKategori = itemView.findViewById(R.id.tvKategori);
        }
    }
}
