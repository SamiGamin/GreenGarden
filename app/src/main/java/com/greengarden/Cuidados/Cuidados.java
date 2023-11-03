package com.greengarden.Cuidados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;

public class Cuidados extends AppCompatActivity {
    Button menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuidados);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Cuidados.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(Cuidados.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu

    }
}