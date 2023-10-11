package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Registro = findViewById(R.id.long_tex_reduistrate);
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(Login.this, Registration.class);
                startActivity(Registro);
            }
        });
    }
}