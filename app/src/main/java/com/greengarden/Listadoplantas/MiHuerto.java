package com.greengarden.Listadoplantas;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Listadoplantas.Adapter.MiHuertoAdapter;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.util.ArrayList;
import java.util.List;

public class MiHuerto extends AppCompatActivity implements MiHuertoAdapter.OnPlantDeleteListener {
    private Button menu;

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private MiHuertoAdapter adapter; // Debes crear esta clase de adaptador
    private ArrayList<ModelPlantas> MiHuertolist; // Lista para mantener las plantas seleccionadas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_huerto);
        // Inicializa Firebase Firestore
        db = FirebaseFirestore.getInstance();
        // Obtiene el UID del usuario actual (de alguna manera)
        String uid = obtenerUIDUsuarioActual();

        // Inicializa MiHuertolist
        MiHuertolist = new ArrayList<>();

        // Crea una referencia a la colección "Usuarios" y el documento específico del usuario
        DocumentReference usuarioRef = db.collection("Usuarios").document(uid);
        // Crea una referencia a la colección "MiHuerto" dentro del documento del usuario
        CollectionReference miHuertoRef = usuarioRef.collection("MiHuerto");


        // Después de obtener los datos, crea el adaptador
        adapter = new MiHuertoAdapter(MiHuertolist, this, this, null, this);
        // Configura el RecyclerView y el adaptador para mostrar los datos guardados
        recyclerView = findViewById(R.id.MiHuertoCreado);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        miHuertoRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<ModelPlantas> plantas = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ModelPlantas planta = document.toObject(ModelPlantas.class);
                        plantas.add(planta);
                    }
                    adapter.setPlantas(plantas);
                    // Aquí verificamos si la lista está vacía y mostramos el diálogo correspondiente
                    if (plantas.isEmpty()) {
                        adapter.mostrarDialogoHuertoVacio();

                    }

                } else {
                    Toast.makeText(MiHuerto.this, "Error al obtener datos de Firebase Firestore:", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Error al obtener datos de Firebase Firestore: " + task.getException().getMessage());

                }
            }
        });


        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MiHuerto.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(MiHuerto.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
        //fin menu
    }

    public void onPlantDelete(ModelPlantas plant) {
        String uid = obtenerUIDUsuarioActual();
        String id= plant.getTitulo();

        // Suponiendo que plant.getId() siempre devuelve 0
        DocumentReference plantaRef = db.collection("Usuarios")
                .document(uid)
                .collection("MiHuerto")
                .document(id);

        plantaRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Documento eliminado correctamente");

                        // Actualiza la lista local y la interfaz de usuario
                        MiHuertolist.remove(plant);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "Planta eliminada correctamente", Toast.LENGTH_SHORT).show();

                        if (MiHuertolist.isEmpty()) {
                            Toast.makeText(MiHuerto.this, "Tu huerto esta vacio", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error al intentar eliminar el documento", e);
                        Toast.makeText(getApplicationContext(), "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private String obtenerUIDUsuarioActual() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            // El usuario está autenticado, se puede obtener su UID
            return usuario.getUid();
        } else {
            // Si no hay usuario autenticado, puedes devolver un valor por defecto o manejar el caso según tus necesidades
            return "UID_POR_DEFECTO";
        }
    }
}

