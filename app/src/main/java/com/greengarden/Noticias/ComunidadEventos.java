package com.greengarden.Noticias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;

import java.util.ArrayList;

public class ComunidadEventos extends AppCompatActivity {
    Button menu;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<Noticiaslist> noticiaslistArrayList;
    private NtAdapter noticiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comunidad_eventos);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ComunidadEventos.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(ComunidadEventos.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando datos porfavor espere");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclernt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        noticiaslistArrayList = new ArrayList<Noticiaslist>();
        noticiaAdapter = new NtAdapter(this, noticiaslistArrayList);

        recyclerView.setAdapter(noticiaAdapter);
        mostrarnoticia();


    }

    private void mostrarnoticia() {
        db.collection("Noticias").orderBy("fecha", Query.Direction.ASCENDING)
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
                                noticiaslistArrayList.add(dc.getDocument().toObject(Noticiaslist.class));
                            }
                            noticiaAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }


                    }
                });
    }
}