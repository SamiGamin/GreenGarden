package com.greengarden.Estadisticas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;

import java.util.ArrayList;

public class AdapterEstadisticas extends RecyclerView.Adapter<AdapterEstadisticas.EstaViewHolder> {
    private final Context context;
    private final ArrayList<ModelPlantas> estadisticaslist;



    public AdapterEstadisticas(Context context, ArrayList<ModelPlantas> estadisticaslist) {
        this.context = context;
        this.estadisticaslist = estadisticaslist;
    }


    @NonNull
    @Override
    public AdapterEstadisticas.EstaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_estadisticas, parent, false);
        return new EstaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstadisticas.EstaViewHolder holder, int position) {
        ModelPlantas planta = estadisticaslist.get(position);
        holder.Titulo.setText(planta.getTitulo());
        holder.Cantidad.setText(String.valueOf(planta.getCantidad()));
        holder.Agua.setText(planta.getRiego());
        holder.Abono.setText(planta.getAbono());

    }


    @Override
    public int getItemCount() {
        return estadisticaslist.size();
    }


    public class EstaViewHolder extends RecyclerView.ViewHolder {


        TextView Titulo, Cantidad, Agua, Abono;
        public EstaViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.estaTitulo);
            Cantidad = itemView.findViewById(R.id.estaCantidad);
            Agua = itemView.findViewById(R.id.estaagua);
            Abono = itemView.findViewById(R.id.estaabono);

        }
    }
}
