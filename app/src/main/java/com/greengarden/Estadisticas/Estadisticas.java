package com.greengarden.Estadisticas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;
import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {
    Button menu;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<ModelPlantas> estadisticaslist;
    private AdapterEstadisticas estadisticasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Estadisticas.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(Estadisticas.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu
        db = FirebaseFirestore.getInstance();
        estadisticaslist = new ArrayList<ModelPlantas>();
        estadisticasAdapter = new AdapterEstadisticas(this, estadisticaslist);

        recyclerView = findViewById(R.id.estadisticasRecicler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(estadisticasAdapter);
        verestadisticas();


    }
    public void onDeleteClick(int position) {
        // Lógica para eliminar el elemento en la posición dada
        // Puedes acceder a tu lista de datos (estadisticaslist) y eliminar el elemento en la posición 'position'
        if (position >= 0 && position < estadisticaslist.size()) {
            estadisticaslist.remove(position);
            estadisticasAdapter.notifyItemRemoved(position);
        }
    }

    private void verestadisticas() {
        // Obtener la ID del usuario autenticado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String idUser = currentUser.getUid();


            db.collection("Usuarios").document(idUser)
                    .collection("MiHuerto")
                    .orderBy("tipoplanta", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                // Manejar el error, si es necesario
                                return;
                            }
                            estadisticaslist.clear();

                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    estadisticaslist.add(dc.getDocument().toObject(ModelPlantas.class));
                                }
                            }
                            estadisticasAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }


    private void eliminarPlanta(ModelPlantas plantaSeleccionada, int position) {
        // Obtener la ID del usuario autenticado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String idUser = currentUser.getUid();

            // Obtener la referencia al documento que se va a eliminar
            DocumentReference plantaRef = db.collection("Usuarios").document(idUser)
                    .collection("MiHuerto").document(plantaSeleccionada.getId());

            // Eliminar el documento de Firestore
            plantaRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Éxito al eliminar el documento, actualiza la UI
                    estadisticaslist.remove(position);
                    estadisticasAdapter.notifyItemRemoved(position);
                    Toast.makeText(getApplicationContext(), "Planta eliminada", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Manejar el error al eliminar la planta
                    Toast.makeText(getApplicationContext(), "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}