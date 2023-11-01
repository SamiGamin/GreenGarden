package com.greengarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greengarden.Cultivo.MiAdapter;
import com.greengarden.Cultivo.Plantas;

import java.util.ArrayList;


public class Agregar_Cultivos extends AppCompatActivity {
    Button menu, ircuidados;
    private RecyclerView recyclerView;
    private MiAdapter plantaAdapter;
    private SearchView buscarView;
    private DatabaseReference databaseReference;
    private ArrayList<Plantas> listaDePlantas;
    private ArrayList<Plantas> filteredPlantas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultivos);
       // menu = findViewById(R.id.btn_menu);
       // ircuidados = findViewById(R.id.btn_agregar_cultivo);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         // Inicializa la lista filtrada
        databaseReference = FirebaseDatabase.getInstance().getReference().child("plantas");
        listaDePlantas = new ArrayList<>();
        filteredPlantas = new ArrayList<>();
        getPlantas();

      /*  ircuidados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ircuidados = new Intent(Agregar_Cultivos.this , Cuidados.class);
                startActivity(ircuidados);
            }
        });
      /*  menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Agregar_Cultivos.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.navigation_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Toast.makeText(Agregar_Cultivos.this, " " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if (id == R.id.inicio) {
                            Intent inicio = new Intent(Agregar_Cultivos.this, Inicio.class);
                            startActivity(inicio);
                            return true;}
                        if (id == R.id.consumo) {

                            Intent consumo = new Intent(Agregar_Cultivos.this, Cuidados.class);
                            startActivity(consumo);
                            return true;
                        }
                        if (id == R.id.estadisticas) {
                            Intent estadisticas = new Intent(Agregar_Cultivos.this, Estadisticas.class);
                            startActivity(estadisticas);
                            return true;}
                        if (id == R.id.consejos) {
                            Intent consejos = new Intent(Agregar_Cultivos.this, Consejos.class);
                            startActivity(consejos);
                            return true;}
                        if (id == R.id.cultivos) {
                            Intent cultivos = new Intent(Agregar_Cultivos.this, Agregar_Cultivos.class);
                            startActivity(cultivos);
                            return true;}
                        if (id == R.id.comu_even) {
                            Intent eventos = new Intent(Agregar_Cultivos.this, ComunidadEventos.class);
                            startActivity(eventos);
                            return true;}
                        if (id == R.id.salir) {
                            finish();
                            return true;}

                        return false;
                    }
                });
                popupMenu.show();
            }
        });*/
    }

    private void getPlantas() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

             //   Log.d("MiApp", "Datos recuperados de Firebase: " + snapshot.toString());

                for (DataSnapshot data : snapshot.getChildren()){
                    Plantas plantasAux = data.getValue(Plantas.class);
                    if (plantasAux != null){
                        listaDePlantas.add(plantasAux);

                    }
                }
                Log.d("DEBUG", "Tamaño de listaDePlantas: " + listaDePlantas.size());

                // Configura el adaptador
                FirebaseRecyclerOptions<Plantas> options = new FirebaseRecyclerOptions.Builder<Plantas>()
                        .setQuery(databaseReference, Plantas.class)
                        .build();

                plantaAdapter = new MiAdapter(options, Agregar_Cultivos.this);
                recyclerView.setAdapter(plantaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Agregar_Cultivos.this, "Error al obtener datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (plantaAdapter != null){
            plantaAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (plantaAdapter != null){
            plantaAdapter.stopListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buscar_btn, menu);

        MenuItem menuItem = menu.findItem(R.id.btn_buscar_barra);
        SearchView buscarView = (SearchView) menuItem.getActionView();
        if (buscarView !=null){
            FiltraItem(buscarView);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void FiltraItem(SearchView buscarView) {
        buscarView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Si la búsqueda está vacía, muestra todos los elementos
                    filteredPlantas.clear();
                    filteredPlantas.addAll(listaDePlantas);
                }else {
                    filteredPlantas.clear();
                    for (Plantas plantas : listaDePlantas){
                        if (plantas.getNombre().toLowerCase().contains(newText.toLowerCase())){
                            filteredPlantas.add(plantas);
                        }
                    }
                }
                FirebaseRecyclerOptions<Plantas> filteredOptions = new FirebaseRecyclerOptions.Builder<Plantas>()
                        .setQuery(databaseReference, Plantas.class)
                        .build();
                plantaAdapter = new MiAdapter(filteredOptions, Agregar_Cultivos.this);
                recyclerView.setAdapter(plantaAdapter);
                plantaAdapter.startListening();
                return false;
            }
        });
    }
}