package com.greengarden.Cultivo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.greengarden.R;

public class MiAdapter extends  FirebaseRecyclerAdapter<Plantas, MiAdapter.ViewHolder>{

  private final Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param context
     */
    public MiAdapter(@NonNull FirebaseRecyclerOptions<Plantas> options, Context context) {
        super(options);
        this.context = context;
        Log.d("MiAdapter", "Datos en options: " + options.getSnapshots().toString());

    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Plantas model) {
        holder.Nombre.setText(model.getNombre());
        holder.Descripcion.setText(model.getDescripcion());
        holder.FechaSiembra.setText(model.getFechaSiembra());
        holder.Humedad.setText(model.getHumedad());
        holder.LuzSolar.setText(model.getLuzSolar());
        holder.Riego.setText(model.getRiego());
        holder.Temperatura.setText(model.getTemperatura());
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item,parent, false);
        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Nombre, Descripcion, FechaSiembra,Humedad,LuzSolar,Riego,Temperatura;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.nombre_lista);
            Descripcion = itemView.findViewById(R.id.descripcion);
            FechaSiembra = itemView.findViewById(R.id.fechasiembra_lista);
            Humedad = itemView.findViewById(R.id.humedad);
            LuzSolar = itemView.findViewById(R.id.luzSolar);
            Riego = itemView.findViewById(R.id.riego);
            Temperatura = itemView.findViewById(R.id.temperatura);
        }
    }
}
