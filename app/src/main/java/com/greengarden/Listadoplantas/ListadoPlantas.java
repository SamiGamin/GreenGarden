package com.greengarden.Listadoplantas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Listadoplantas.Adapter.PlantaAdapter;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListadoPlantas extends AppCompatActivity {
    Button menu, agregar ;
    //decaramos las variabler para ver la informacion de firebase estore
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<Tituloplanta> plantarrayList;
    private ArrayList<Tituloplanta> selectedTitles = new ArrayList<>();
    private PlantaAdapter plantaAdapter;
    private ArrayList<Tituloplanta> MiHuertoCreado; // Lista para mantener los datos guardados

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadoplantas);
        agregar = findViewById(R.id.agregar_planta);

        // Inicializa Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializa el adaptador y la lista de plantas seleccionadas
        plantarrayList = new ArrayList<Tituloplanta>();
        selectedTitles = new ArrayList<>();

        // Inicializa y configura el RecyclerView
        recyclerView = findViewById(R.id.MiHuertoCreado);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantaAdapter = new PlantaAdapter(this, plantarrayList, selectedTitles);
        recyclerView.setAdapter(plantaAdapter);



        // Configurar el botón "Agregar"
        agregar = findViewById(R.id.agregar_planta);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPlantasSeleccionadasEnFirestore();
                Intent mostrarDatosIntent = new Intent(ListadoPlantas.this, MiHuerto.class);
                mostrarDatosIntent.putParcelableArrayListExtra("MiHuertoCreado", MiHuertoCreado);
                startActivity(mostrarDatosIntent);

            }
        });
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ListadoPlantas.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(ListadoPlantas.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
        //fin menu
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando datos porfavor espere");
        progressDialog.show();
        tituloplanta();
    }

    private void guardarPlantasSeleccionadasEnFirestore() {
        // Asegúrate de que el usuario esté autenticado
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userUid = user.getUid();

            // Obtén una instancia de Firebase Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Crea una colección "PlantasSeleccionadas" en Firestore para almacenar las plantas seleccionadas
            CollectionReference plantasSeleccionadasRef = db.collection("Usuarios").document(userUid).collection("MiHuerto");

            // Itera a través de las plantas seleccionadas y agrégalas a Firestore
            for (Tituloplanta planta : selectedTitles) {
                // Convierte el objeto Tituloplanta a un mapa (HashMap) o un objeto JavaBean
                // y luego agrégalo a Firestore
                Map<String, Object> plantaData = new HashMap<>();
                plantaData.put("titulo", planta.getTitulo());
                plantaData.put("tipoplanta", planta.getTipoplanta());
                plantaData.put("abono", planta.getAbono());
                plantaData.put("riego", planta.getRiego());
                plantaData.put("agua", planta.getAgua());
                plantaData.put("temperatura", planta.getTemperatura());
                plantaData.put("ulr", planta.getUlr());
                plantaData.put("cantidad", planta.getCantidad());


                // Agrega el documento a Firestore con un identificador único (por ejemplo, el título de la planta)
                plantasSeleccionadasRef.document(planta.getTitulo())
                        .set(plantaData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Plantas seleccionadas guardadas exitosamente en Firestore
                                //Toast.makeText(ListadoPlantas.this, "Plantas seleccionadas guardadas en Firestore", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error al guardar las plantas seleccionadas
                                Toast.makeText(ListadoPlantas.this, "Error al guardar las plantas seleccionadas", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else {
            Toast.makeText(this, "Debes iniciar sesión para guardar las plantas seleccionadas", Toast.LENGTH_SHORT).show();
        }
    }


    private void tituloplanta() {
        db.collection("Plantas")
                .orderBy("tipoplanta", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Error de Datos", error.getMessage());
                            return;
                        }for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                plantarrayList.add(dc.getDocument().toObject(Tituloplanta.class));
                            }
                            plantaAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                        // Accede al primer elemento de consejoArrayList
                        if (plantarrayList.size() > 0) {
                            Tituloplanta primerPlanta = plantarrayList.get(0);
                            // Haz lo que necesites con el primer elemento
                        }
                    }

                });
    }



}
