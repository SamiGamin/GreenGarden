package com.greengarden.Estadisticas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Listadoplantas.ModelPlantas;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.util.ArrayList;
import java.util.Map;

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
                            mostrarTotales();
//                            estadisticasAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
    private void mostrarTotales() {
        // Calcula los totales de agua y abono en el adaptador
        estadisticasAdapter.calcularTotalAguaYAbono();

        // Notifica al adaptador que los datos han cambiado
        estadisticasAdapter.actualizarDatos();


    }



}