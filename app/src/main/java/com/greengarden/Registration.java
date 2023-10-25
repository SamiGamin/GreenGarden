package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greengarden.datos.DatePickerHelper;
import com.greengarden.datos.BseDatos;
import com.greengarden.datos.SelectorOpcion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    TextView registro, login;
    EditText nombre_usu,
            corre_eletronico,
            contrasena, corfirmarContrasena;

    CheckBox checkBox;
    BseDatos miBdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);


        login = findViewById(R.id.rg_login);
        registro = findViewById(R.id.reg_btn_Registrarse);
        corfirmarContrasena = findViewById(R.id.editcofircontrasena);
        nombre_usu = findViewById(R.id.rg_name);
        corre_eletronico = findViewById(R.id.reg_editText_email);
        contrasena = findViewById(R.id.editTextcontrasena);
        checkBox = findViewById(R.id.checkBox);
        miBdd = new BseDatos(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Registration.this, Login.class);
                startActivity(login);

            }
        });
    }

    public void registrarUsuario(View vista) {

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
                            miBdd.agregarUsuario(nombre, email, password, fecha_registro);
                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Las contraseñas ingresadas no considen ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        }
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
        return "";
    }

    private boolean validarpassword(String password) {
        boolean numeros = false;
        boolean letras = false;
        for (int x = 0; x < password.length(); x++) {
            char c = password.charAt(x);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == ' ')
                    || (c == 'ñ') || (c == 'Ñ') || (c == 'Á') || (c == 'á') ||
                    (c == 'é') || (c == 'É') || (c == 'í') ||
                    (c == 'Í') || (c == 'ó') || (c == 'Ó') ||
                    (c == 'ú') || (c == 'Ú'))) {
                letras = true;
            }
            if ((c >= '0' && c <= '9')) {
                numeros = true;
            }
        }
        if (numeros == true && letras == true) {
            return true;
        }
        return false;
    }

    private boolean contienSololetras(String nombre) {
        for (int x = 0; x < nombre.length(); x++) {
            char c = nombre.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == ' ')
                    || (c == 'ñ') || (c == 'Ñ') || (c == 'Á') || (c == 'á') ||
                    (c == 'é') || (c == 'É') || (c == 'í') || (c == 'Í') || (c == 'ó')
                    || (c == 'Ó') || (c == 'ú') || (c == 'Ú'))) {
                return false;
            }
        }
        return true;
    }
}

