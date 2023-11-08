package com.greengarden.ejemplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MiHuerto extends AppCompatActivity {
    private Button menu, eliminar, masplantas;
    private RecyclerView recyclerView;
    private MiHuertoAdapter adapter; // Debes crear esta clase de adaptador
    private ArrayList<Tituloplanta> selectedPlants; // Lista para mantener las plantas seleccionadas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_huerto);

        // Inicializa la lista de plantas seleccionadas
        selectedPlants = new ArrayList<>();

        // Inicializa la RecyclerView
        recyclerView = findViewById(R.id.recyclerplanta);

        // Cargar plantas seleccionadas desde SharedPreferences
        selectedPlants.addAll(loadSelectedPlants());

        // Recibe nuevas plantas de la actividad 'prueba'
        ArrayList<Tituloplanta> newPlants = getIntent().getParcelableArrayListExtra("selected_plants");

        if (newPlants != null && !newPlants.isEmpty()) {
            // Agrega nuevas plantas a la lista de seleccionadas y elimina duplicados
            Set<Tituloplanta> set = new HashSet<>(selectedPlants);
            set.addAll(newPlants);
            selectedPlants.clear();
            selectedPlants.addAll(set);

            // Guarda las plantas actualizadas en SharedPreferences
            saveSelectedPlants(selectedPlants);
        }

        // Configura la RecyclerView y el adaptador
        if (selectedPlants.isEmpty()) {
            showEmptyHuertoMessage();
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MiHuertoAdapter(selectedPlants);
            recyclerView.setAdapter(adapter);
        }
        eliminar = findViewById(R.id.btn_elminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        masplantas = findViewById(R.id.masplantas);
        masplantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masplant = new Intent(MiHuerto.this, prueba.class);
                startActivity(masplant);
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
    private void showEmptyHuertoMessage() {
        Toast.makeText(this, "Tu huerto está vacío. ¡Agrega plantas para comenzar!", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Tituloplanta> loadSelectedPlants() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String serializedPlants = sharedPreferences.getString("selected_plants_key", null);
        if (serializedPlants == null) {
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Tituloplanta>>(){}.getType();
            ArrayList<Tituloplanta> plants = gson.fromJson(serializedPlants, type);
            return plants;
        }
    }

    private void saveSelectedPlants(ArrayList<Tituloplanta> selectedPlants) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(selectedPlants);

        editor.putString("selected_plants_key", json); // Corregido el nombre de la clave
        editor.apply();
    }
}