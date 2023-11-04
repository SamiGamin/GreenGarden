package com.greengarden.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.greengarden.Consejos.Consejos;
import com.greengarden.Cuidados.Cuidados;
import com.greengarden.Estadisticas.Estadisticas;
import com.greengarden.Noticias.ComunidadEventos;
import com.greengarden.Plantas.Agregar_Cultivos;
import com.greengarden.Plantas.huerto;
import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.ejemplo.prueba;

public class Inicio extends AppCompatActivity {

    Button menu;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        LinearLayout ircuidados = findViewById(R.id.btncuidados);
        LinearLayout ircultivos = findViewById(R.id.layout_cultivo);
        LinearLayout ircosejos = findViewById(R.id.btn_consejos);
        LinearLayout irestadistica = findViewById(R.id.btn_estadisticas);
        LinearLayout ireventos = findViewById(R.id.btn_noticias);

        mAuth = FirebaseAuth.getInstance();
        ircuidados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), prueba.class);
                startActivity(intent);
            }
        });
        ircultivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Agregar_Cultivos.class);
                startActivity(intent);
            }
        });
        ircosejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Consejos.class);
                startActivity(intent);
            }
        });
        irestadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Estadisticas.class);
                startActivity(intent);
            }
        });
        ireventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComunidadEventos.class);
                startActivity(intent);
            }
        });



        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Inicio.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(Inicio.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu
    }
}