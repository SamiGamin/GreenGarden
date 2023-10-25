package com.greengarden;
/*
  @Autor: @Salomon Martinez Barrera
  @Autor: @Tobias Martinez Barrera
  @Autor: @Leydi Peña
  @Autor: @Jonatha David Moreno Molina
  @Autor: @Maria Fernanda Alemam Ovalle
  @Creacion: 24/10/2023
  @Modificacion:
  @Descripcion: Proyecto todos ala U Intermedio
  */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benvenida);
        login = findViewById(R.id.btn_bienbenida);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);

            }
        });
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pantallaLogin = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(pantallaLogin);
                finish();
            }
        }, 0);*/
    }
}