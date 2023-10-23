package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView Registro, ingreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Registro = findViewById(R.id.long_tex_reduistrate);
        ingreso = findViewById(R.id.long_btn_ingresar);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingreso = new Intent(Login.this, Inicio.class);
                startActivity(ingreso);
                finish();
            }
        });
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(Login.this, Registration.class);
                startActivity(Registro);
                finish();
            }
        });
    }
}