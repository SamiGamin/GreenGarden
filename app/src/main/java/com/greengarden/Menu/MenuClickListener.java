package com.greengarden.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.greengarden.Plantas.Agregar_Cultivos;
import com.greengarden.Noticias.ComunidadEventos;

import com.greengarden.Consejos.Consejos;
import com.greengarden.Cuidados.Cuidados;
import com.greengarden.Estadisticas.Estadisticas;
import com.greengarden.Inicio.Inicio;
import com.greengarden.Plantas.huerto;
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
            Intent cultivos = new Intent(context, huerto.class);
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