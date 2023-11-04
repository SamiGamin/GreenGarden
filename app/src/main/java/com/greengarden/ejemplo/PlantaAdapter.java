package com.greengarden.ejemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greengarden.R;

import java.util.ArrayList;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.MiViewHolder> {
    private Context context;
    private ArrayList<Tituloplanta> tituloplanta;
    private ArrayList<Tituloplanta> selectedTitles;
    private OnItemClickListener listener;


    public PlantaAdapter(Context context, ArrayList<Tituloplanta> tituloplanta, ArrayList<Tituloplanta> selectedTitles) {
        this.context = context;
        this.tituloplanta = tituloplanta;
        this.selectedTitles = selectedTitles;
    }
    public interface OnItemClickListener {
        void onItemClick(Tituloplanta planta);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlantaAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lista_plantas, parent , false);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        Tituloplanta planta = tituloplanta.get(position);

        holder.Titulo.setText(planta.getTitulo());
        // Cambia el fondo del elemento si está seleccionado
        if (selectedTitles.contains(planta)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_del_borde));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        // Agregar un OnClickListener para manejar la selección/deselección
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTitles.contains(planta)) {
                    selectedTitles.remove(planta);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                } else {
                    selectedTitles.add(planta);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_del_borde));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tituloplanta.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView Titulo;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulo);


        }
    }
}
