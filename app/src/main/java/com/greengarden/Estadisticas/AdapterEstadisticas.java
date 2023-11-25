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
import java.util.HashMap;
import java.util.Map;

public class AdapterEstadisticas extends RecyclerView.Adapter<AdapterEstadisticas.EstaViewHolder> {
    private final Context context;
    private final ArrayList<ModelPlantas> estadisticaslist;

    private double totalAgua;
    private double totalAbono;

    public double getTotalAgua() {
        return totalAgua;
    }

    public void setTotalAgua(double totalAgua) {
        this.totalAgua = totalAgua;
    }

    public double getTotalAbono() {
        return totalAbono;
    }

    public void setTotalAbono(double totalAbono) {
        this.totalAbono = totalAbono;
    }

    public AdapterEstadisticas(Context context, ArrayList<ModelPlantas> estadisticaslist) {
        this.context = context;
        this.estadisticaslist = estadisticaslist;
    }

    public void calcularTotalAguaYAbono() {
        double totalAgua = 0;
        double totalAbono = 0;

        for (ModelPlantas planta : estadisticaslist) {
            int cantidad = planta.getCantidad();
            double cantidadAgua = Double.parseDouble(planta.getCantidadagua());
            double cantidadAbono = Double.parseDouble(planta.getCantidadabono());

            totalAgua += cantidad * cantidadAgua;
            totalAbono += cantidad * cantidadAbono;
        }

        this.totalAgua = totalAgua;
        this.totalAbono = totalAbono;
    }
    public void actualizarDatos() {
        notifyDataSetChanged();
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

        holder.Cantidad.setText(String.valueOf(planta.getCantidad()) + "      " + planta.getTitulo());

        // Calcula los totales de agua y abono para la planta actual
        int cantidadAgua = (int) Math.round(planta.getCantidad() * Double.parseDouble(planta.getCantidadagua()));
        int cantidadAbono = (int) Math.round(planta.getCantidad() * Double.parseDouble(planta.getCantidadabono()));
        // Muestra los totales de agua y abono
        holder.Agua.setText("Cada vez que riegas gastas: " + cantidadAgua + " mililitros");
        holder.Abono.setText("Cada vez que abonas gastas: " + cantidadAbono + " gramos");

    }



    @Override
    public int getItemCount() {
        return estadisticaslist.size();
    }


    public class EstaViewHolder extends RecyclerView.ViewHolder {
        TextView  Cantidad, Agua, Abono;
        public EstaViewHolder(@NonNull View itemView) {
            super(itemView);

            Cantidad = itemView.findViewById(R.id.estaCantidad);
            Agua = itemView.findViewById(R.id.estaagua);
            Abono = itemView.findViewById(R.id.estaabono);

        }
    }
}
