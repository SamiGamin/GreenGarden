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
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.Plantas.Agregar_Cultivos;
import com.greengarden.Noticias.ComunidadEventos;
import com.greengarden.Cuidados.Cuidados;
import com.greengarden.Estadisticas.Estadisticas;
import com.greengarden.Inicio.Inicio;
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
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Consejos.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(Consejos.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu

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