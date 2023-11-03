package com.greengarden.Consejos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Agregar_Cultivos;
import com.greengarden.ComunidadEventos;
import com.greengarden.Cuidados;
import com.greengarden.Estadisticas;
import com.greengarden.Inicio;
import com.greengarden.R;

import java.util.ArrayList;

public class Consejos extends AppCompatActivity {
    /*esto es la variable de menu*/
    Button menu;
    //decaramos las variabler para ver la informacion de firebase estore
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<ListaConsejos> consejoArrayList;
    private ConsejoAdapter consejoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consejos);
        menu = findViewById(R.id.btn_menu_consejos);
        /*esto es el menu*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Consejos.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.navigation_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Toast.makeText(Consejos.this, " " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if (id == R.id.inicio) {
                            Intent inicio = new Intent(Consejos.this, Inicio.class);
                            startActivity(inicio);
                            return true;}
                        if (id == R.id.consumo) {

                            Intent consumo = new Intent(Consejos.this, Cuidados.class);
                            startActivity(consumo);
                            return true;
                        }
                        if (id == R.id.estadisticas) {
                            Intent estadisticas = new Intent(Consejos.this, Estadisticas.class);
                            startActivity(estadisticas);
                            return true;}
                        if (id == R.id.consejos) {
                            Intent consejos = new Intent(Consejos.this, Consejos.class);
                            startActivity(consejos);
                            return true;}
                        if (id == R.id.cultivos) {
                            Intent cultivos = new Intent(Consejos.this, Agregar_Cultivos.class);
                            startActivity(cultivos);
                            return true;}
                        if (id == R.id.comu_even) {
                            Intent eventos = new Intent(Consejos.this, ComunidadEventos.class);
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
        });/*aca caba el menu*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando datos porfavor espere");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycler_consejo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        consejoArrayList = new ArrayList<ListaConsejos>();
        consejoAdapter = new ConsejoAdapter(this, consejoArrayList);

        recyclerView.setAdapter(consejoAdapter);
        mostarlistadoconsejos();

    }

    private void mostarlistadoconsejos() {
    db.collection("Consejos")/*.orderBy("titulo" , Query.Direction.ASCENDING)*/
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
                            consejoArrayList.add(dc.getDocument().toObject(ListaConsejos.class));
                        }
                        consejoAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                    // Accede al primer elemento de consejoArrayList
                    if (consejoArrayList.size() > 0) {
                        ListaConsejos primerConsejo = consejoArrayList.get(0);
                        // Haz lo que necesites con el primer elemento
                    }
                }
            });
    }
}