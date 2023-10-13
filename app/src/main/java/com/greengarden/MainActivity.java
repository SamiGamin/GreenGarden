package com.greengarden;
/*
  @Autor: @Sami
  @Creacion: 13/10/2023
  @Modificacion:
  @Descripcion: pantalla de inicio 4 seg
  */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pantallaLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(pantallaLogin);
                finish();
            }
        }, 0);
    }
}