package com.greengarden.ejemplo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.util.ArrayList;

public class ListadoPlantas extends AppCompatActivity {
    Button menu, agregar ;
    //decaramos las variabler para ver la informacion de firebase estore
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<Tituloplanta> plantarrayList;
    private ArrayList<Tituloplanta> selectedTitles = new ArrayList<>();
    private PlantaAdapter plantaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadoplantas);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        agregar = findViewById(R.id.agregar_planta);
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
        recyclerView = findViewById(R.id.recyclerplanta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        plantarrayList = new ArrayList<Tituloplanta>();
        selectedTitles = new ArrayList<>();
        plantaAdapter = new PlantaAdapter(this, plantarrayList, selectedTitles);

        recyclerView.setAdapter(plantaAdapter);
        tituloplanta();

       // recyclerView.addOnItemTouchListener(new MyItemClickListener());
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene las plantas seleccionadas de la lista selectedTitles
                ArrayList<Tituloplanta> selectedPlants = selectedTitles;

                // Abre la pantalla "MiHuerto" y pasa la lista de plantas seleccionadas
                Intent intent = new Intent(ListadoPlantas.this, MiHuerto.class);
                intent.putParcelableArrayListExtra("selected_plants", selectedPlants);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Tituloplanta> savePlants(ArrayList<Tituloplanta> selected) {
        // Obtener instancia de SharedPreferences
        SharedPreferences prefs = getSharedPreferences("mis_plantas", Context.MODE_PRIVATE);

        // Obtener editor para modificar prefs
        SharedPreferences.Editor editor = prefs.edit();

        // Convertir lista a JSON
        Gson gson = new Gson();
        String jsonPlants = gson.toJson(selected);

        // Guardar json en prefs
        editor.putString("plantas_seleccionadas", jsonPlants);

        // Aplicar cambios
        editor.apply();
        return selected;
    }

    private void tituloplanta() {
        db.collection("Plantas").orderBy("tipoplanta", Query.Direction.ASCENDING)
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
                            Tituloplanta primerConsejo = plantarrayList.get(0);
                            // Haz lo que necesites con el primer elemento
                        }
                    }

                });
    }

}