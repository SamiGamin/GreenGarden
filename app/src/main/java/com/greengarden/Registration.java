package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

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

    TextView registro;
    EditText nombre_usu,
            fecha_de_nacimiento,
            corre_eletronico, usuario,
            contrasena, corfirmarContraseña;
    Spinner genero;
    CheckBox checkBox;
    BseDatos miBdd;
    private TextView editTextFechaNacimiento;
    private DatePickerHelper datePickerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        editTextFechaNacimiento = findViewById(R.id.rg_fecha);
        datePickerHelper = new DatePickerHelper(this, editTextFechaNacimiento);
        Spinner spinner = findViewById(R.id.spinner);
        SelectorOpcion selectorOpcion = new SelectorOpcion(spinner);

        registro = findViewById(R.id.reg_btn_Registrarse);
        corfirmarContraseña = findViewById(R.id.editcofircontrasena);
        nombre_usu = findViewById(R.id.rg_name);
        fecha_de_nacimiento = findViewById(R.id.rg_fecha);
        corre_eletronico = findViewById(R.id.reg_editText_email);
        usuario = findViewById(R.id.reg_editText_usuario);
        contrasena = findViewById(R.id.editTextcontrasena);
        genero = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        miBdd = new BseDatos(getApplicationContext());
    }

    public void registrarUsuario(View vista) {

        String nombre = nombre_usu.getText().toString();
        String fechaN = fecha_de_nacimiento.getText().toString();
        String email = corre_eletronico.getText().toString();
        String Usuario = usuario.getText().toString();
        String password = contrasena.getText().toString();
        String passwordConfirmada = corfirmarContraseña.getText().toString();
        // String Genero =genero.get().toString();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String fecha_registro = df.format(Calendar.getInstance().getTime());
        if (!checkBox.isChecked()){
            Toast.makeText(this, "Por favor aceptar terminos y condiciones  ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nombre.isEmpty() || fechaN.isEmpty() || email.isEmpty() || Usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados", Toast.LENGTH_SHORT).show();

        } else {
            if (contienSololetras(nombre) == false) {
                Toast.makeText(this, "El nombre no debe contener numeros", Toast.LENGTH_SHORT).show();
            } else {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (pattern.matcher(email).matches() == false) {
                    Toast.makeText(this, "Ingrese un Email Valido", Toast.LENGTH_SHORT).show();
                } else {
                    if (validarUsuario(Usuario) == false) {
                        Toast.makeText(this, "Ingrese un usuario minimo de 4 digitos", Toast.LENGTH_SHORT).show();
                    }
                    if (password.length() < 6) {
                        Toast.makeText(this, "Ingrese una contraseña minimo de 6 digitos", Toast.LENGTH_SHORT).show();
                    } else {
                        if (validarpassword(password) == false) {
                            Toast.makeText(this, "La contraseña debe tener numeros y letras", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password.equals(passwordConfirmada)) {
                                password = getMD5(password);
                                miBdd.agregarUsuario(nombre, fechaN, email, password, fecha_registro);
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
    }

    private boolean validarUsuario(String usuario) {
        boolean numeros = false;
        boolean letras = false;
        for (int x = 0; x < usuario.length(); x++) {
            char c = usuario.charAt(x);
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

