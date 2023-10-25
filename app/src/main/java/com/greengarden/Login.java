package com.greengarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText email, pass;
    FirebaseAuth mAuth;
    private String Email;
    private String Pass;

    Button ingreso;
    TextView Registro, recuperarcontrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Registro = findViewById(R.id.long_tex_reduistrate);
        ingreso = findViewById(R.id.long_btn_ingresar);
        recuperarcontrasena = findViewById(R.id.btn_recuperarcontrasena);
        email = findViewById(R.id.long_editText_email);
        pass = findViewById(R.id.long_editText_contrasena);
        mAuth = FirebaseAuth.getInstance();
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registro = new Intent(Login.this, Registration.class);
                startActivity(Registro);

            }
        });

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
                Email = email.getText().toString().trim();
                Pass = pass.getText().toString().trim();

                if (Email.isEmpty() || Pass.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Verifica si el correo es válido
                    if (EmailValido(Email)) {
                        mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //ingreso
                                    irinicio();
                                    Toast.makeText(Login.this, "Ingreso Correcto", Toast.LENGTH_SHORT).show();
                                } else {
                                    //crendenciales incorrectas
                                    Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }

                }
            }
        });


    }

    private boolean EmailValido(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    //verificar que el usuario tenga un secion abierta

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuario = mAuth.getCurrentUser();
        if (usuario != null) {
            irinicio();
        }
    }

    private void irinicio() {
        Intent irinicio = new Intent(Login.this, Inicio.class);
        startActivity(irinicio);
        finish();
    }
}