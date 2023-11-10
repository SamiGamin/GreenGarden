package com.greengarden.ejemplo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.R;

import java.util.ArrayList;
import java.util.List;

public class MiHuerto extends AppCompatActivity {
    private Button menu, eliminar, masplantas;

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private MiHuertoAdapter adapter; // Debes crear esta clase de adaptador
    private ArrayList<Tituloplanta> MiHuertolist; // Lista para mantener las plantas seleccionadas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_huerto);
        // Inicializa Firebase Firestore
        db = FirebaseFirestore.getInstance();
        // Obtiene el UID del usuario actual (de alguna manera)
        String uid = obtenerUIDUsuarioActual();

        // Inicializa MiHuertolist
        MiHuertolist = new ArrayList<>();

        // Crea una referencia a la colección "Usuarios" y el documento específico del usuario
        DocumentReference usuarioRef = db.collection("Usuarios").document(uid);
        // Crea una referencia a la colección "MiHuerto" dentro del documento del usuario
        CollectionReference miHuertoRef = usuarioRef.collection("MiHuerto");

     //   Log.d("MiHuerto", "Cantidad de elementos en MiHuertoCreado: " + MiHuertoCreado.size());

        // Después de obtener los datos, crea el adaptador
        adapter = new MiHuertoAdapter(MiHuertolist, this);
            // Configura el RecyclerView y el adaptador para mostrar los datos guardados
            recyclerView = findViewById(R.id.MiHuertoCreado);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(adapter);

        miHuertoRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
           if (task.isSuccessful()){
               List<Tituloplanta> plantas = new ArrayList<>();
               for (QueryDocumentSnapshot document : task.getResult()){
                   Tituloplanta planta = document.toObject(Tituloplanta.class);
                   plantas.add(planta);
               }
               adapter.setPlantas(plantas);
           }else {
               Toast.makeText(MiHuerto.this, "Error al obtener datos de Firebase Firestore:", Toast.LENGTH_SHORT).show();
               Log.e("Error", "Error al obtener datos de Firebase Firestore: " + task.getException().getMessage());

           }
            }
        });


        

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
                Intent masplant = new Intent(MiHuerto.this, ListadoPlantas.class);
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



    private String obtenerUIDUsuarioActual() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            // El usuario está autenticado, se puede obtener su UID
            return usuario.getUid();
        } else {
            // Si no hay usuario autenticado, puedes devolver un valor por defecto o manejar el caso según tus necesidades
            return "UID_POR_DEFECTO";
        }
    }


    private void showEmptyHuertoMessage() {
        Toast.makeText(this, "Tu huerto está vacío. ¡Agrega plantas para comenzar!", Toast.LENGTH_SHORT).show();
    }


}

 /* private void MiHuertoli() {
        MiHuertolist = new ArrayList<Tituloplanta>();
        db.collection("MiHuerto")
                .orderBy("tipoplanta", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Error de Datos", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                MiHuertolist.add(dc.getDocument().toObject(Tituloplanta.class));
                            }
                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                        // Accede al primer elemento de consejoArrayList
                        if (MiHuertolist.size() > 0) {
                            Tituloplanta primerPlanta = MiHuertolist.get(0);
                            // Haz lo que necesites con el primer elemento
                        }
                    }
                });
    }*/