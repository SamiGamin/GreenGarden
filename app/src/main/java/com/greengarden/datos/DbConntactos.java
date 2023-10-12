package com.greengarden.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbConntactos extends DbHelper{

    Context context;
    public DbConntactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarContactos(String nombre, String fecha_de_nacimiento, String corre_eletronico,
                                  String usuario,  String contrasena){
        long id = 0;
        try {


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("corre_eletronico", corre_eletronico);


            values.put("fecha_de_nacimiento", fecha_de_nacimiento);
        values.put("usuario", usuario);

        values.put("contraseña", contrasena);

        id = db.insert( TABLE_CONTACTOS, null, values);
        }catch (Exception ex){

        }
        return id;
    }
}
