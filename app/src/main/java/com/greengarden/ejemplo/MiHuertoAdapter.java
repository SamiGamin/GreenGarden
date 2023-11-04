package com.greengarden.ejemplo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greengarden.R;

import java.util.ArrayList;

public class MiHuertoAdapter extends RecyclerView.Adapter<MiHuertoAdapter.ViewHolder> {
    private ArrayList<Tituloplanta> selectedPlants;

    public MiHuertoAdapter(ArrayList<Tituloplanta> selectedPlants) {
        this.selectedPlants = selectedPlants;
    }

    @NonNull
    @Override
    public MiHuertoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_plantas, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MiHuertoAdapter.ViewHolder holder, int position) {

        Tituloplanta plant = selectedPlants.get(position);
        holder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return selectedPlants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView plantNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantNameTextView = itemView.findViewById(R.id.titulo);

        }

        public void bind(Tituloplanta plant) {
            plantNameTextView.setText(plant.getTitulo());
        }
    }
}
