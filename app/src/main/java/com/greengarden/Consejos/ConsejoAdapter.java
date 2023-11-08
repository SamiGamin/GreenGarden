package com.greengarden.Consejos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.greengarden.R;
import com.greengarden.ejemplo.Tituloplanta;

import java.util.ArrayList;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.MiViewHolder> {
    Context context;
    ArrayList<Tituloplanta> listaConsejos;

    public ConsejoAdapter(Context context, ArrayList<Tituloplanta> listaConsejos) {
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
        Tituloplanta consejo = listaConsejos.get(position);

        holder.titulo.setText(consejo.getTitulo());
        holder.ConsejoAbono.setText(consejo.getConsejoabono());
        holder.ConsejoAgua.setText(consejo.getConsejoriego());

        String imageUrl = consejo.getUlr();
        Log.d("TAG", "URL de la imagen: " + imageUrl);

        if (imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.icono)
                    .error(R.drawable.dangerous)
                    .into(holder.imageurl);
        }else {
            // holder.plantaimagen.setUlr(R.drawable.bgp);
        }

    }


    @Override
    public int getItemCount() {
        return listaConsejos.size();
    }
    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, ConsejoAbono, ConsejoAgua ;
        ImageView imageurl;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.listtitulo);
            ConsejoAbono = itemView.findViewById(R.id.consejoabono);
            ConsejoAgua = itemView.findViewById(R.id.consejoagua);
            imageurl = itemView.findViewById(R.id.imageurl);

        }
    }

}
