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
        holder.bind(plant);

    }

    @Override
    public int getItemCount() {
        return selectedPlants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView plantNameTextView;
        private TextView vriego;
        private TextView vabono;
        private TextView vagua;
        private TextView vtemperatura;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantNameTextView = itemView.findViewById(R.id.titulomihuerto);
            vriego = itemView.findViewById(R.id.riegomihuerto);
            vabono = itemView.findViewById(R.id.abonomihuerto);
            vtemperatura = itemView.findViewById(R.id.temperaturamihuerto);
            vagua = itemView.findViewById(R.id.holasoy);

        }

        public void bind(Tituloplanta plant) {
            plantNameTextView.setText(plant.getTitulo());
            vtemperatura.setText(plant.getTemperatura());
            vriego.setText("Riego cada  " + plant.getRiego()+ "  dias");
            vabono.setText("Abono  "+plant.getAbono()+ "  Gramos");
           // vagua.setText(plant.getAgua());
        }
    }
}
