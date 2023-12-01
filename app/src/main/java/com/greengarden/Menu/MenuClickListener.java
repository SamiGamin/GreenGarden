package com.greengarden.Menu;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.greengarden.Consejos.Consejos;
import com.greengarden.Estadisticas.Estadisticas;
import com.greengarden.Inicio.Inicio;
import com.greengarden.Inicio.Login;
import com.greengarden.Listadoplantas.CrearCultivo;
import com.greengarden.Listadoplantas.MiHuerto;
import com.greengarden.Noticias.Noticias;
import com.greengarden.Perfil.Perfil;
import com.greengarden.R;

public class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
    private Context context;
    FirebaseAuth mAuth;

    public MenuClickListener(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(context, " " + item.getTitle(), Toast.LENGTH_SHORT).show();
        if (id == R.id.inicio) {
            Intent inicio = new Intent(context, Inicio.class);
            context.startActivity(inicio);
            return true;
        }
        if (id == R.id.consumo) {
            Intent consumo = new Intent(context, CrearCultivo.class);
            context.startActivity(consumo);
            return true;
        }
        if (id == R.id.estadisticas) {
            Intent estadisticas = new Intent(context, Estadisticas.class);
            context.startActivity(estadisticas);
            return true;
        }
        if (id == R.id.consejos) {
            Intent consejos = new Intent(context, Consejos.class);
            context.startActivity(consejos);
            return true;
        }
        if (id == R.id.cultivos) {
            Intent cultivos = new Intent(context, MiHuerto.class);
            context.startActivity(cultivos);
            return true;
        }
        if (id == R.id.comu_even) {
            Intent eventos = new Intent(context, Noticias.class);
            context.startActivity(eventos);
            return true;
        }
        if (id == R.id.perfil) {
            Intent perfil = new Intent(context, Perfil.class);
            context.startActivity(perfil);
            return true;
        }
        if (id == R.id.salir) {
            mAuth.signOut();
            Intent login = new Intent(context, Login.class);
            context.startActivity(login);


            return true;
        }
        return false;
    }
}