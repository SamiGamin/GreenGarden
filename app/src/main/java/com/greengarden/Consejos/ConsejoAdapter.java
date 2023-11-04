package com.greengarden.Consejos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greengarden.R;

import java.util.ArrayList;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.MiViewHolder> {
    Context context;
    ArrayList<ListaConsejos> listaConsejos;

    public ConsejoAdapter(Context context, ArrayList<ListaConsejos> listaConsejos) {
        this.context = context;
        this.listaConsejos = listaConsejos;
    }

    @NonNull
    @Override
    public ConsejoAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listaconsejo, parent, false);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoAdapter.MiViewHolder holder, int position) {
        ListaConsejos consejo = listaConsejos.get(position);

        holder.NombrePlanta.setText(consejo.getNombreplanta());
        holder.ConsejoAbono.setText(consejo.getConsejoabono());
        holder.ConsejoAgua.setText(consejo.getConsejoagua());


    }


    @Override
    public int getItemCount() {
        return listaConsejos.size();
    }
    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView NombrePlanta, ConsejoAbono, ConsejoAgua ;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            NombrePlanta = itemView.findViewById(R.id.listtitulo);
            ConsejoAbono = itemView.findViewById(R.id.consejoabono);
            ConsejoAgua = itemView.findViewById(R.id.consejoagua);

        }
    }

}
