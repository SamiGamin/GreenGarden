package com.greengarden.ejemplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.greengarden.R;

import java.util.ArrayList;

public class MiHuerto extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MiHuertoAdapter adapter; // Debes crear esta clase de adaptador


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_huerto);
        // Recibe la lista de plantas seleccionadas de la actividad prueba
        ArrayList<Tituloplanta> selectedPlants = getIntent().getParcelableArrayListExtra("selected_plants");
        Log.d("MiHuerto", "Número de plantas seleccionadas: " + selectedPlants.size());

        // Inicializa el RecyclerView y el adaptador
        recyclerView = findViewById(R.id.recyclerplanta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MiHuertoAdapter(selectedPlants);
        recyclerView.setAdapter(adapter);
    }
}