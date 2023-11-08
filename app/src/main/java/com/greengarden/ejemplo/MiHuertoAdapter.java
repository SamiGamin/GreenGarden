package com.greengarden.ejemplo;


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

import java.util.ArrayList;

public class MiHuertoAdapter extends RecyclerView.Adapter<MiHuertoAdapter.ViewHolder> {
    private ArrayList<Tituloplanta> selectedPlants;

    private Context context;

    public MiHuertoAdapter( ArrayList<Tituloplanta> selectedPlants) {

        if (selectedPlants == null) {
            this.selectedPlants = new ArrayList<>();
        } else {
            this.selectedPlants = selectedPlants;
        }
    }

    @NonNull
    @Override
    public MiHuertoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mihuerto, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MiHuertoAdapter.ViewHolder holder, int position) {

        Tituloplanta plant = selectedPlants.get(position);
       // holder.bind(plant);
        holder.Titulo.setText(plant.getTitulo());
        holder.vtemperatura.setText(plant.getTemperatura());
        holder.vriego.setText("Riego cada  " + plant.getRiego()+ "  dias");
        holder.vabono.setText("Abono  "+plant.getAbono()+ "  Gramos");

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
       /* // Cambia el fondo del elemento si está seleccionado
        if (selectedPlants.contains(plant)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_del_borde));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        // Agregar un OnClickListener para manejar la selección/deselección
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPlants.contains(plant)) {
                    selectedPlants.remove(plant);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                } else {
                    selectedPlants.add(plant);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_del_borde));

                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return selectedPlants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Titulo;
        private TextView vriego;
        private TextView vabono;

        private TextView vtemperatura;
        private ImageView plantaimagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulomihuerto);
            vriego = itemView.findViewById(R.id.riegomihuerto);
            vabono = itemView.findViewById(R.id.abonomihuerto);
            vtemperatura = itemView.findViewById(R.id.temperaturamihuerto);

            plantaimagen = itemView.findViewById(R.id.imaulr);

        }

        /*public void bind(Tituloplanta plant) {
            Titulo.setText(plant.getTitulo());

           // vagua.setText(plant.getAgua());
            // Obtener la URL de la imagen de Firebase Firestore
            String imageUrl =  plant.getUlr();
            }*/
    }
}
