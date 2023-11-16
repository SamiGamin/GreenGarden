package com.greengarden.Listadoplantas.Adapter;


import static com.bumptech.glide.util.Util.getSnapshot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greengarden.Listadoplantas.CrearCultivo;
import com.greengarden.Listadoplantas.MiHuerto;
import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class MiHuertoAdapter extends RecyclerView.Adapter<MiHuertoAdapter.ViewHolder> {
    private ArrayList<ModelPlantas> MiHuertoCreado;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int currentPosition;

    private OnPlantDeleteListener listener;
    Activity activity;
    private ArrayList<ModelPlantas> selectedTitles;
    private Context context;

    public MiHuertoAdapter(ArrayList<ModelPlantas> miHuertoCreado, Context context, OnPlantDeleteListener deleteListener, ArrayList<ModelPlantas> selectedTitles, Activity activity) {
        MiHuertoCreado = miHuertoCreado;
        this.listener = deleteListener;
        this.context = context;
        this.activity = activity;
        this.selectedTitles = (selectedTitles != null) ? selectedTitles : new ArrayList<>();
    }

    @NonNull
    @Override
    public MiHuertoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_mi_huerto, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MiHuertoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ModelPlantas plant = MiHuertoCreado.get(position);
        holder.bind(plant);
        currentPosition = position;

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onPlantDelete(plant);
                }
            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlantas(plant.getId());
            }
        });
    }
    private void deletePlantas(String titulo) {

        // Obtén la instancia de FirebaseAuth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // Verifica si hay un usuario autenticado
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String idUser = currentUser.getUid();
            DocumentReference usuarioRef = db.collection("Usuarios").document(idUser);
            for (ModelPlantas planta : MiHuertoCreado) {
                Log.d("TAG", "Título de planta: " + planta.getTitulo());
            }

            ModelPlantas plantaAEliminar = null;
            int posicionAEliminar = -1;

            for (int i = 0; i < MiHuertoCreado.size(); i++) {
                if (MiHuertoCreado.get(i).getTitulo().equals(titulo)) {
                    plantaAEliminar = MiHuertoCreado.get(i);
                    posicionAEliminar = i;
                    break;
                }
            }

            if (plantaAEliminar != null) {
                final int finalPosicionAEliminar = posicionAEliminar;
                usuarioRef.update("MiHuerto", FieldValue.arrayRemove(plantaAEliminar.getId()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                MiHuertoCreado.remove(finalPosicionAEliminar);
                                notifyItemRemoved(finalPosicionAEliminar);
                                notifyItemRangeChanged(finalPosicionAEliminar, MiHuertoCreado.size());
                                Toast.makeText(activity, "Planta eliminada correctamente", Toast.LENGTH_SHORT).show();

                                if (MiHuertoCreado.isEmpty()) {
                                    mostrarDialogoHuertoVacio();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(activity, "Planta no encontrada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return MiHuertoCreado.size();
    }
    public void setPlantas(List<ModelPlantas> plantas) {
        MiHuertoCreado.clear();  // Borra la lista actual
        MiHuertoCreado.addAll(plantas);  // Agrega la nueva lista de plantas
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Titulo,vriego,vabono,cantidad,vtemperatura;

        ImageButton eliminar;
        private Button deleteButton;
        private ImageView plantaimagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulomihuerto);
            vriego = itemView.findViewById(R.id.riegomihuerto);
            vabono = itemView.findViewById(R.id.abonomihuerto);
            vtemperatura = itemView.findViewById(R.id.temperaturamihuerto);
            cantidad = itemView.findViewById(R.id.numeroplantas);
            plantaimagen = itemView.findViewById(R.id.imaulr);
            eliminar = itemView.findViewById(R.id.btn_eliminar_dos);

            deleteButton = itemView.findViewById(R.id.buttonEliminar);
            if (deleteButton !=null){
                deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarDialogoConfirmacion(getAdapterPosition());
                    }
            });
            }
        }

        public void bind(ModelPlantas plant) {
            Titulo.setText(plant.getTitulo());
            vriego.setText("Riego cada " + plant.getRiego() + " días");
            vabono.setText("Abono " + plant.getAbono() + " gramos");
            vtemperatura.setText(plant.getTemperatura());
            cantidad.setText(String.valueOf(plant.getCantidad()));

            String imageUrl = plant.getUlr();

            Log.d("TAG", "URL de la imagen: " + imageUrl);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(context)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.albahaca)
                        .error(R.drawable.abono)
                        .into(plantaimagen);
            }

            // Cambia el color de fondo si la planta está seleccionada
            if (selectedTitles.contains(plant)) {
                itemView.setBackgroundColor(context.getResources().getColor(R.color.fondotexto));
            } else {
                itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }
        }
    }

    private void mostrarDialogoConfirmacion(int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar planta");
        builder.setMessage("¿Estás seguro de que quieres eliminar esta planta?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Eliminar la planta y notificar al adaptador
                if (!MiHuertoCreado.isEmpty() && adapterPosition != RecyclerView.NO_POSITION) {
                    ModelPlantas deletedPlant = MiHuertoCreado.get(adapterPosition);
                    MiHuertoCreado.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, MiHuertoCreado.size());
                    listener.onPlantDelete(deletedPlant);

                    // Verificar si el huerto está vacío después de la eliminación
                    if (MiHuertoCreado.isEmpty()) {
                        mostrarDialogoHuertoVacio();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el cuadro de diálogo
            }
        });
        builder.show();
    }

    public void mostrarDialogoHuertoVacio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Huerto Vacío");
        builder.setMessage("Tu huerto está vacío. ¿Quieres agregar más plantas?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirigir al usuario a la pantalla de agregar plantas (CrearCultivo)
                Intent intent = new Intent(context, CrearCultivo.class);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el cuadro de diálogo o manejar según tus necesidades
            }
        });
        builder.show();
    }

    public interface OnPlantDeleteListener {
        void onPlantDelete(ModelPlantas plant);

//        void onPlantEdit(ModelPlantas plant);
    }
}
