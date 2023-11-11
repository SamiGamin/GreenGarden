package com.greengarden.Listadoplantas.Adapter;


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
import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;

import java.util.ArrayList;
import java.util.List;

public class MiHuertoAdapter extends RecyclerView.Adapter<MiHuertoAdapter.ViewHolder> {
    private ArrayList<ModelPlantas> MiHuertoCreado;

    private Context context;

    public MiHuertoAdapter(ArrayList<ModelPlantas> miHuertoCreado, Context context) {
        MiHuertoCreado = miHuertoCreado;
        this.context = context;
    }

    @NonNull
    @Override
    public MiHuertoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_mi_huerto, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MiHuertoAdapter.ViewHolder holder, int position) {

        ModelPlantas plant = MiHuertoCreado.get(position);
       // holder.bind(plant);
        holder.Titulo.setText(plant.getTitulo());
        holder.vtemperatura.setText(plant.getTemperatura());
        holder.vriego.setText("Riego cada  " + plant.getRiego()+ "  dias");
        holder.vabono.setText("Abono  "+plant.getAbono()+ "  Gramos");
        holder.cantidad.setText(String.valueOf(plant.getCantidad()));

        String imageUrl = plant.getUlr();

        Log.d("TAG", "URL de la imagen: " + imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.albahaca)
                    .error(R.drawable.abono)
                    .into(holder.plantaimagen);
        }else {
            // holder.plantaimagen.setUlr(R.drawable.bgp);
        }

    }

    @Override
    public int getItemCount() {
        return MiHuertoCreado.size();
    }

    public void setPlantas(List<ModelPlantas> plantas) {
        MiHuertoCreado.clear();  // Borra la lista actual
        MiHuertoCreado.addAll(plantas);  // Agrega la nueva lista de plantas
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Titulo;
        private TextView vriego;
        private TextView vabono;
        private TextView cantidad;

        private TextView vtemperatura;
        private ImageView plantaimagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulomihuerto);
            vriego = itemView.findViewById(R.id.riegomihuerto);
            vabono = itemView.findViewById(R.id.abonomihuerto);
            vtemperatura = itemView.findViewById(R.id.temperaturamihuerto);
            cantidad = itemView.findViewById(R.id.numeroplantas);
            plantaimagen = itemView.findViewById(R.id.imaulr);

        }
    }
}
