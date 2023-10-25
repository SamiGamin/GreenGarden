package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity {



    Button ingreso;
    TextView Registro, recuperarcontrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Registro = findViewById(R.id.long_tex_reduistrate);
        ingreso = findViewById(R.id.long_btn_ingresar);
        recuperarcontrasena = findViewById(R.id.btn_recuperarcontrasena);


        recuperarcontrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(Login.this, RecuperarContrasena.class);
                startActivity(Registro);

            }
        });
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingreso = new Intent(Login.this, Inicio.class);
                startActivity(ingreso);

            }
        });
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(Login.this, Registration.class);
                startActivity(Registro);

            }
        });
    }
}