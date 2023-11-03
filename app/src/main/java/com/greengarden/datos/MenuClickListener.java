package com.greengarden.datos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.greengarden.Agregar_Cultivos;
import com.greengarden.ComunidadEventos;

import com.greengarden.Consejos.Consejos;
import com.greengarden.Cuidados;
import com.greengarden.Estadisticas;
import com.greengarden.Inicio;
import com.greengarden.R;

public class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
    private Context context;

    public MenuClickListener(Context context) {
        this.context = context;
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
            Intent consumo = new Intent(context, Cuidados.class);
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
            Intent cultivos = new Intent(context, Agregar_Cultivos.class);
            context.startActivity(cultivos);
            return true;
        }
        if (id == R.id.comu_even) {
            Intent eventos = new Intent(context, ComunidadEventos.class);
            context.startActivity(eventos);
            return true;
        }
        if (id == R.id.salir) {
            ((Activity) context).finish();
            return true;
        }
        return false;
    }
}