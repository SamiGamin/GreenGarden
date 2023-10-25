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
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benvenida);
        setTheme(R.style.Theme_SplashTheme);
        login = findViewById(R.id.btn_bienbenida);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);

            }
        });

    }
}