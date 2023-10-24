package com.greengarden;
/*
  @Autor: @Sami
  @Creacion: 13/10/2023
  @Modificacion:
  @Descripcion: pantalla de inicio 4 seg
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
                finish();
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