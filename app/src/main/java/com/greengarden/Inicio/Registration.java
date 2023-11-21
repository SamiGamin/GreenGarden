package com.greengarden.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greengarden.Perfil.Perfil;
import com.greengarden.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Button registro;

    EditText nombre_usu,
            corre_eletronico,
            contrasena, corfirmarContrasena;


    FirebaseFirestore mFirestores;


    FirebaseAuth mAuth;

    private String Name, Email, Pass, Confpass;
    private boolean terminosAceptados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        mFirestores = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        registro = findViewById(R.id.btn_registro);

        corfirmarContrasena = findViewById(R.id.registro_confpass);
        nombre_usu = findViewById(R.id.registro_nombre);
        corre_eletronico = findViewById(R.id.registro_email2);
        contrasena = findViewById(R.id.registro_pass);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = nombre_usu.getText().toString().trim();
                Email = corre_eletronico.getText().toString().trim();
                Pass = contrasena.getText().toString().trim();
                Confpass = corfirmarContrasena.getText().toString().trim();
                terminosAceptados = ((CheckBox) findViewById(R.id.checkterminos)).isChecked();

                if (Name.isEmpty() || Email.isEmpty() || Pass.isEmpty() || Confpass.isEmpty()) {
                    Toast.makeText(Registration.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    if (!contienSololetras(Name)) {
                        Toast.makeText(Registration.this, "El nombre no debe contener numeros", Toast.LENGTH_SHORT).show();
                    } else if (!EmailValido(Email)) {
                        Toast.makeText(Registration.this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                    } else if (!validarPassword(Pass)) {
                        Toast.makeText(Registration.this, "La contraseña debe contener al menos una letra y un número", Toast.LENGTH_SHORT).show();
                    } else if (!terminosAceptados) {
                        Toast.makeText(Registration.this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                    } else if (!Pass.equals(Confpass)) {
                        Toast.makeText(Registration.this, "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT).show();
                    } else {
                        
                        Pass = getMD5(Pass);
                        mAuth.createUserWithEmailAndPassword(Email, Confpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("id", id);
                                    map.put("name", Name);
                                    map.put("email", Email);
                                    map.put("password", Pass);
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("User").child(id).setValue(map, new DatabaseReference.CompletionListener(){
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference){
                                            if (databaseError == null){
                                                //irinicio();
                                                irPerfil();
                                                finish();
                                                Toast.makeText(Registration.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(Registration.this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                  FirebaseUser user = mAuth.getCurrentUser();
                                  if (user != null){
                                      user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()){
                                             Toast.makeText(Registration.this, "Se ha enviado un correo de verificación. Por favor, verifica tu correo electrónico.", Toast.LENGTH_SHORT).show();
                                         }else{
                                             Toast.makeText(Registration.this, "Error al enviar el correo de verificación: ", Toast.LENGTH_SHORT).show();
                                         }
                                          }
                                      });
                                  }


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

    private void irPerfil() {
        Intent login = new Intent(Registration.this, Perfil.class);
        startActivity(login);
        finish();
    }

    private boolean validarPassword(String pass) {
        return pass.matches(".*[a-zA-Z].*") && pass.matches(".*\\d.*");
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
}

