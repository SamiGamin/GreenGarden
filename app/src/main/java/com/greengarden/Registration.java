package com.greengarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Button registro;
   // TextView login;
    EditText nombre_usu,
            corre_eletronico,
            contrasena, corfirmarContrasena;

    CheckBox checkBox;
    //BseDatos miBdd;

    FirebaseAuth mAuth;

    private String Usu, Email, Pass, Confpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        mAuth = FirebaseAuth.getInstance();

        //login = findViewById(R.id.rg_login);
        registro = findViewById(R.id.btn_registro);

        corfirmarContrasena = findViewById(R.id.registro_confpass);
        nombre_usu = findViewById(R.id.registro_nombre);
        corre_eletronico = findViewById(R.id.registro_email2);
        contrasena = findViewById(R.id.registro_pass);

        checkBox = findViewById(R.id.checkBox);

        //miBdd = new BseDatos(getApplicationContext());
      /*  login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Registration.this, Login.class);
                startActivity(login);

            }
        });*/
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usu = nombre_usu.getText().toString().trim();
                Email = corre_eletronico.getText().toString().trim();
                Pass = contrasena.getText().toString().trim();
                Confpass = corfirmarContrasena.getText().toString().trim();

                if (Usu.isEmpty() || Email.isEmpty() || Pass.isEmpty() || Confpass.isEmpty()) {
                    Toast.makeText(Registration.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    if (!contienSololetras(Usu)) {
                        Toast.makeText(Registration.this, "El nombre no debe contener numeros", Toast.LENGTH_SHORT).show();
                    } else if (!EmailValido(Email)) {
                        Toast.makeText(Registration.this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                    }else if (!validarPassword(Pass)) {
                        Toast.makeText(Registration.this, "La contraseña debe contener al menos una letra y un número", Toast.LENGTH_SHORT).show();
                    } else if (!Pass.equals(Confpass)) {
                        Toast.makeText(Registration.this, "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT).show();
                    } else {
                        // Registra al usuario
                        Pass = getMD5(Pass);
                        mAuth.createUserWithEmailAndPassword(Email, Confpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                    irInicio();
                                    finish();
                                } else {
                                    Toast.makeText(Registration.this, "Error al registrar el usuario: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    private boolean validarPassword(String pass) {
        return pass.matches(".*[a-zA-Z].*") && pass.matches(".*\\d.*");
    }

    private void irInicio() {
        Intent login = new Intent(Registration.this, Login.class);
        startActivity(login);
        finish();
    }

    private boolean EmailValido(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void irinicio() {
        Intent login = new Intent(Registration.this, Login.class);
        startActivity(login);
        finish();
    }





    private String getMD5(String password) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }



    private boolean contienSololetras(String nombre) {
        return nombre.matches("[a-zA-ZñÑÁáéÉíÍóÓúÚ ]*");
    }
     /*public void registrarUsuario(View vista) {

        String nombre = nombre_usu.getText().toString();
        String email = corre_eletronico.getText().toString();
        String password = contrasena.getText().toString();
        String passwordConfirmada = corfirmarContrasena.getText().toString();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String fecha_registro = df.format(Calendar.getInstance().getTime());

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmada.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados", Toast.LENGTH_SHORT).show();


        } else {
            if (contienSololetras(nombre) == false) {
                Toast.makeText(this, "El nombre no debe contener numeros", Toast.LENGTH_SHORT).show();
            } else {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (pattern.matcher(email).matches() == false) {
                    Toast.makeText(this, "Ingrese un Email Valido", Toast.LENGTH_SHORT).show();
                    if (validarpassword(password) == false) {
                        Toast.makeText(this, "La contraseña debe tener numeros y letras", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(passwordConfirmada)) {
                            password = getMD5(password);
                         //   miBdd.agregarUsuario(nombre, email, password, fecha_registro);
                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Las contraseñas ingresadas no considen ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        }
    }*/
}

