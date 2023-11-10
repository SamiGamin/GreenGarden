package com.greengarden.Listadoplantas.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.greengarden.Listadoplantas.Tituloplanta;
import com.greengarden.R;

import java.util.ArrayList;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.MiViewHolder> {
    private final Context context;
    private final ArrayList<Tituloplanta> tituloplanta;
    private final ArrayList<Tituloplanta> selectedTitles;

    private int Position = RecyclerView.NO_POSITION;
    public void setPosition(int position) {
        this.Position = position;
        notifyDataSetChanged();
    }


    public PlantaAdapter(Context context, ArrayList<Tituloplanta> tituloplanta, ArrayList<Tituloplanta> selectedTitles) {
        this.context = context;
        this.tituloplanta = tituloplanta;
        this.selectedTitles = selectedTitles;
    }

    public interface OnItemClickListener {
        void onItemClick(Tituloplanta planta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
    }

    @NonNull
    @Override
    public PlantaAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lista_plantas, parent, false);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        Tituloplanta planta = tituloplanta.get(position);

        holder.Titulo.setText(planta.getTitulo());
        holder.tipoplanta.setText(planta.getTipoplanta());

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

            // holder.plantaimagen.setImageURI(R.drawable.dangerous);
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
                mostrarDialogoCantidad(planta);
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
    private AlertDialog alertDialog;
    // Método para mostrar el cuadro de diálogo de cantidad
    private void mostrarDialogoCantidad(Tituloplanta planta) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        // Configura el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        //builder.setTitle("Ingrese la cantidad de Plantas " + planta.getTitulo());

        // Encuentra las vistas dentro del diseño personalizado
        EditText editTextCantidad = dialogView.findViewById(R.id.editTextCantidad);
        Button btnAceptar = dialogView.findViewById(R.id.btnAceptar);
        // Crea y muestra el AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la cantidad ingresada por el usuario
                String cantidadIngresada = editTextCantidad.getText().toString();
                planta.setCantidad(Integer.parseInt(cantidadIngresada));

                // Cierra el AlertDialog
                alertDialog.dismiss();
                notifyItemChanged(Position);
            }
        });



        // Configurar el cuadro de texto de entrada para la cantidad
        /*final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);*/

       /* // Configurar los botones del cuadro de diálogo
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener la cantidad ingresada por el usuario
                String cantidadStr = input.getText().toString();
                if (!TextUtils.isEmpty(cantidadStr)) {
                    int cantidad = Integer.parseInt(cantidadStr);
                    // Actualizar la cantidad en el objeto Tituloplanta
                    planta.setCantidad(cantidad);
                    // Notificar cambios en el conjunto de datos
                    notifyDataSetChanged();
                }
            }
        });*/

        /*builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();*/
    }

    @Override
    public int getItemCount() {
        return
                tituloplanta.size();
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
