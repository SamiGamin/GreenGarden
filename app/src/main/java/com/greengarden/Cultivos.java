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

public class Cultivos extends AppCompatActivity {
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultivos);
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Cultivos.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.navigation_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Toast.makeText(Cultivos.this, " " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        if (id == R.id.inicio) {
                            Intent inicio = new Intent(Cultivos.this, Inicio.class);
                            startActivity(inicio);
                            return true;}
                        if (id == R.id.consumo) {

                            Intent consumo = new Intent(Cultivos.this, Consumo.class);
                            startActivity(consumo);
                            return true;
                        }
                        if (id == R.id.categorias) {
                            Intent categorias = new Intent(Cultivos.this, Categories.class);
                            startActivity(categorias);
                            return true;}
                        if (id == R.id.estadisticas) {
                            Intent estadisticas = new Intent(Cultivos.this, Estadisticas.class);
                            startActivity(estadisticas);
                            return true;}
                        if (id == R.id.consejos) {
                            Intent consejos = new Intent(Cultivos.this, Consejos.class);
                            startActivity(consejos);
                            return true;}
                        if (id == R.id.cultivos) {
                            Intent cultivos = new Intent(Cultivos.this, Cultivos.class);
                            startActivity(cultivos);
                            return true;}
                        if (id == R.id.comu_even) {
                            Intent eventos = new Intent(Cultivos.this, ComunidadEventos.class);
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