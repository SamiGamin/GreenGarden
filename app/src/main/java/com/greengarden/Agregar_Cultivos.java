package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Agregar_Cultivos extends AppCompatActivity {
    Button menu, ircuidados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cultivos);
        menu = findViewById(R.id.btn_menu);
        ircuidados = findViewById(R.id.long_btn_ingresar);

        ircuidados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ircuidados = new Intent(Agregar_Cultivos.this , Cuidados.class);
                startActivity(ircuidados);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
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
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}