package com.greengarden.Listadoplantas.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;

import java.util.ArrayList;

public class CrearcultivoAdapter extends RecyclerView.Adapter<CrearcultivoAdapter.MiViewHolder> {
    private final Context context;
    private final ArrayList<ModelPlantas> modelPlantas;
    private final ArrayList<ModelPlantas> selectedTitles;

    public CrearcultivoAdapter(Context context, ArrayList<ModelPlantas> modelPlantas, ArrayList<ModelPlantas> selectedTitles) {
        this.context = context;
        this.modelPlantas = modelPlantas;
        this.selectedTitles = selectedTitles;
    }

    public interface OnItemClickListener {
        void onItemClick(ModelPlantas planta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
    }

    @NonNull
    @Override
    public CrearcultivoAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listado_crear_cultivo, parent, false);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelPlantas planta = modelPlantas.get(position);

        holder.Titulo.setText(planta.getTitulo());
        holder.tipoplanta.setText(planta.getTipoplanta());

        // Actualizar la cantidad en el TextView
        int cantidad = planta.getCantidad() != null ? planta.getCantidad() : 0;
        holder.cantidaplanta.setText(String.valueOf(cantidad));

        String imageUrl = planta.getUlr();
        Log.d("TAG", "URL de la imagen: " + imageUrl);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.icono)
                    .error(R.drawable.dangerous)
                    .into(holder.plantaimagen);
        } else {


        }

        // Cambia el fondo del elemento si está seleccionado
        if (selectedTitles.contains(planta)) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondotexto));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        // Agregar un OnClickListener para manejar la selección/deselección
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCantidad(planta, position);
                if (selectedTitles.contains(planta)) {
                    selectedTitles.remove(planta);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                } else {
                    selectedTitles.add(planta);
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondotexto));
                }
            }
        });
        // Actualizar la cantidad en el TextView
        holder.cantidaplanta.setText(String.valueOf(planta.getCantidad()));

    }

    // Método para mostrar el cuadro de diálogo de cantidad
    private void mostrarDialogoCantidad(ModelPlantas planta, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        // Configura el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        // Evita que el AlertDialog se cierre al tocar fuera de él
        builder.setCancelable(false);

        // Encuentra las vistas dentro del diseño personalizado
        EditText editTextCantidad = dialogView.findViewById(R.id.editTextCantidad);
        Button btnAceptar = dialogView.findViewById(R.id.btnAceptar);
        // Crea y muestra el AlertDialog
        AlertDialog alertDialog = builder.create();

        // Añade una vista transparente como fondo para evitar el cierre al tocar fuera
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la cantidad ingresada por el usuario
                String cantidadIngresada = editTextCantidad.getText().toString();
                if (cantidadIngresada.isEmpty()){
                    Toast.makeText(context, "Agrega la cantidad de sus plantas \n               Para continuar", Toast.LENGTH_SHORT).show();
                }else {
                    planta.setCantidad(Integer.parseInt(cantidadIngresada));

                    // Cierra el AlertDialog
                    alertDialog.dismiss();
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return
                modelPlantas.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView Titulo, tipoplanta, cantidaplanta;
        ImageView plantaimagen;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulo);
            tipoplanta = itemView.findViewById(R.id.tipoplanta);
            plantaimagen = itemView.findViewById(R.id.imaulr);
            cantidaplanta = itemView.findViewById(R.id.textcantidad);
        }
    }
}
