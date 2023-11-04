package com.greengarden.ejemplo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Consejos.ConsejoAdapter;
import com.greengarden.Consejos.Consejos;
import com.greengarden.Consejos.ListaConsejos;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.util.ArrayList;

public class prueba extends AppCompatActivity {
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
        setContentView(R.layout.prueba);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        agregar = findViewById(R.id.agregar_planta);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(prueba.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(prueba.this);
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
                // Abre la pantalla "Mi Huerto" y pasa la lista de plantas seleccionadas
                Intent intent = new Intent(prueba.this, MiHuerto.class);
                intent.putParcelableArrayListExtra("selected_plants", selectedTitles);
                startActivity(intent);
            }
        });
    }

    private void tituloplanta() {
        db.collection("Plantas").orderBy("titulo", Query.Direction.ASCENDING)
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
    /*public class MyItemClickListener implements RecyclerView.OnItemTouchListener{

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());

            if (childView != null) {
                int itemPosition = rv.getChildLayoutPosition(childView);
                if (itemPosition != RecyclerView.NO_POSITION){
                    Tituloplanta selectedPlant = plantarrayList.get(itemPosition);
                    // Si la planta ya está en la lista de selección, elimínala; de lo contrario, agrégala
                    if (selectedTitles.contains(selectedPlant)) {
                        selectedTitles.remove(selectedPlant);
                    } else {
                        selectedTitles.add(selectedPlant);
                    }
                    // Ejemplo 1: Cambiar el fondo del elemento si está seleccionado
                    if (!selectedTitles.contains(selectedPlant)) {
                        selectedTitles.add(selectedPlant);
                    } else {
                        selectedTitles.remove(selectedPlant);
                    }
                    plantaAdapter.notifyDataSetChanged();
                }
            }

            return false;
        }


        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }*/
}