package com.greengarden.Plantas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ViewFlipper;

import com.greengarden.R;
import com.greengarden.Menu.MenuClickListener;

public class zanahoria extends AppCompatActivity {

    Button menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zanahoria);
        //inicio menu
        menu = findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(zanahoria.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());

                MenuClickListener menuClickListener = new MenuClickListener(zanahoria.this);
                popupMenu.setOnMenuItemClickListener(menuClickListener);

                popupMenu.show();
            }
        });
//fin menu

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
//fin animaciones
    }
}