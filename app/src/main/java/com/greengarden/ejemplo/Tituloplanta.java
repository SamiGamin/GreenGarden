package com.greengarden.ejemplo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Tituloplanta implements Parcelable {
    private String titulo;

    public Tituloplanta() {
    }

    public Tituloplanta(String titulo) {
        this.titulo = titulo;
    }

    protected Tituloplanta(Parcel in) {
        titulo = in.readString();
    }

    public static final Creator<Tituloplanta> CREATOR = new Creator<Tituloplanta>() {
        @Override
        public Tituloplanta createFromParcel(Parcel in) {
            return new Tituloplanta(in);
        }

        @Override
        public Tituloplanta[] newArray(int size) {
            return new Tituloplanta[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean getId() {

        return getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titulo);

    }
}
