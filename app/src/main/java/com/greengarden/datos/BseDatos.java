package com.greengarden.datos;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BseDatos extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Usuarios";
    public static final String TABLE_USUARIOS = "create table usuario(id_usu integer primary key autoincrement, nombre_completo_usu text," +
            "  email_usu text,  passward_usu text, fecha_regis_usu text)";

    public BseDatos(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    public BseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**public BseDatos(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }**/


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usurio");
        db.execSQL(TABLE_USUARIOS);
    }

    public boolean agregarUsuario(String nombre_completo_usu,   String email_usu, String passward_us, String fecha_regis_us) {
        SQLiteDatabase miBdd = getWritableDatabase();
        if (miBdd != null) {
            miBdd.execSQL("insert into usuario(nombre_completo_usu,email_usu,passward_us,fecha_regis_us) " +
                    "values('" + nombre_completo_usu + "','"  + "','" + email_usu +
                    "','" + passward_us + "','" + fecha_regis_us + "')");
            miBdd.close();
            return true;
        }
        return false;
    }
}

