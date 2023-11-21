package com.greengarden.Inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.greengarden.R;

public class RecuperarContrasena extends AppCompatActivity {

    EditText correoRecuperar;
    FirebaseAuth mAuth;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_contrasena);
        mAuth = FirebaseAuth.getInstance();
        correoRecuperar = findViewById(R.id.recuperarcontraseña);
        enviar = findViewById(R.id.btn_enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = correoRecuperar.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RecuperarContrasena.this, "Ingrese su correo electrónico registrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RecuperarContrasena.this, "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RecuperarContrasena.this, "Error al enviar el correo: ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}