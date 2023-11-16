package com.greengarden.Estadisticas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;

import java.util.ArrayList;

public class AdapterEstadisticas extends RecyclerView.Adapter<AdapterEstadisticas.EstaViewHolder> {
    private final Context context;
    private final ArrayList<ModelPlantas> estadisticaslist;
    private OnItemLongClickListener longClickListener;

    public AdapterEstadisticas(Context context, ArrayList<ModelPlantas> estadisticaslist) {
        this.context = context;
        this.estadisticaslist = estadisticaslist;
    }

    @NonNull
    @Override
    public AdapterEstadisticas.EstaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listestadisticas, parent, false);
        return new EstaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstadisticas.EstaViewHolder holder, int position) {
        ModelPlantas planta = estadisticaslist.get(position);
        holder.Titulo.setText(planta.getTitulo());
        holder.Cantidad.setText(String.valueOf(planta.getCantidad()));
        holder.Agua.setText(planta.getRiego());
        holder.Abono.setText(planta.getAbono());

        holder.Eleminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Position = holder.getAdapterPosition();
                if (Position != RecyclerView.NO_POSITION && longClickListener != null){
                    longClickListener.onDeleteClick(Position);

                }
            }
        });


       /* holder.Eleminar.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null){
                    longClickListener.onItemLongClick(position);
                }
                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return estadisticaslist.size();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);

        void onDeleteClick(int Position);

    }


    public class EstaViewHolder extends RecyclerView.ViewHolder {

        ImageButton Eleminar;
        TextView Titulo, Cantidad, Agua, Abono;
        public EstaViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.estaTitulo);
            Cantidad = itemView.findViewById(R.id.estaCantidad);
            Agua = itemView.findViewById(R.id.estaagua);
            Abono = itemView.findViewById(R.id.estaabono);
            Eleminar = itemView.findViewById(R.id.btnborrar);
        }
    }
}
