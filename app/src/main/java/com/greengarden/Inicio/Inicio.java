package com.greengarden.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.greengarden.Consejos.Consejos;
import com.greengarden.Estadisticas.Estadisticas;
import com.greengarden.Noticias.ComunidadEventos;
import com.greengarden.Plantas.Agregar_Cultivos;
import com.greengarden.Plantas.MyViewFlipper;
import com.greengarden.Plantas.huerto;
import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;
import com.greengarden.ejemplo.ListadoPlantas;
import com.greengarden.ejemplo.MiHuerto;

public class Inicio extends AppCompatActivity {

    Button menu;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        TextView ircuidados = findViewById(R.id.cultivos);
        TextView ircultivos = findViewById(R.id.cuidaos);
        TextView ircosejos = findViewById(R.id.consejos);
        TextView irestadistica = findViewById(R.id.estadisticas);
        TextView ireventos = findViewById(R.id.noticias);

        // inicio animaciones
        ViewFlipper viewFlipper1 = findViewById(R.id.home_slider1);
        MyViewFlipper myViewFlipper1 = new MyViewFlipper(this, viewFlipper1);
        ViewFlipper viewFlipper2 = findViewById(R.id.home_slider2);
        MyViewFlipper myViewFlipper2 = new MyViewFlipper(this, viewFlipper2);
        ViewFlipper viewFlipper3 = findViewById(R.id.home_slider3);
        MyViewFlipper myViewFlipper3 = new MyViewFlipper(this, viewFlipper3);
        ViewFlipper viewFlipper4 = findViewById(R.id.home_slider4);
        MyViewFlipper myViewFlipper4 = new MyViewFlipper(this, viewFlipper4);
        ViewFlipper viewFlipper5 = findViewById(R.id.home_slider5);
        MyViewFlipper myViewFlipper5 = new MyViewFlipper(this, viewFlipper5);

        ViewFlipper viewFlipper6 = findViewById(R.id.home_slider6);
        MyViewFlipper myViewFlipper6 = new MyViewFlipper(this, viewFlipper6);
        ViewFlipper viewFlipper7 = findViewById(R.id.home_slider7);
        MyViewFlipper myViewFlipper7 = new MyViewFlipper(this, viewFlipper7);

//fin animaciones


        mAuth = FirebaseAuth.getInstance();
        ircuidados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListadoPlantas.class);
                startActivity(intent);
            }
        });
        ircultivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MiHuerto.class);
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