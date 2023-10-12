package com.greengarden;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greengarden.datos.DatePickerHelper;
import com.greengarden.datos.DbConntactos;
import com.greengarden.datos.DbHelper;
import com.greengarden.datos.SelectorOpcion;

public class Registration extends AppCompatActivity {

    TextView registro, nombre,fecha_de_nacimiento,corre_eletronico,usuario,contrasena,google;
    Spinner genero;
    CheckBox checkBox;
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

        nombre = findViewById(R.id.rg_name);
        fecha_de_nacimiento = findViewById(R.id.rg_fecha);
        corre_eletronico = findViewById(R.id.reg_editText_email);
        usuario = findViewById(R.id.reg_editText_usuario);
        contrasena = findViewById(R.id.editTextcontrasena);
        google = findViewById(R.id.google);
        genero = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);


        //guardatos
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***  DbHelper dbHelper = new DbHelper(Registration.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (db != null){
                    Toast.makeText(Registration.this, "BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Registration.this, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_SHORT).show();
                }

            }
        });***/
               DbConntactos dbConntactos = new DbConntactos(Registration.this);
                long id = dbConntactos.insertarContactos(nombre.getText().toString(),fecha_de_nacimiento.getText().toString(),
                        corre_eletronico.getText().toString(),usuario.getText().toString(),
                        contrasena.getText().toString());
                if (id > 0){
                    Toast.makeText(Registration.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                    limpiar();
                }else {
                    Toast.makeText(Registration.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar() {
        nombre.setText("");
        fecha_de_nacimiento.setText("");
        corre_eletronico.setText("");
        usuario.setText("");
       contrasena.setText("");

    }
}

