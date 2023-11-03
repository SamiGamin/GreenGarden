package com.greengarden.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greengarden.R;

public class RecuperarContrasena extends AppCompatActivity {

    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_contrasena);
        enviar = findViewById(R.id.btn_enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enviar = new Intent(RecuperarContrasena.this, Login.class);
                startActivity(enviar);

            }
        });
    }
}