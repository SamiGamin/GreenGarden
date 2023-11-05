package com.greengarden.ejemplo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Tituloplanta implements Parcelable {
    private String titulo;
    private String riego;
    private String agua;
    private String abono;
    private String temperatura;
    private int imagenResId;


 public Tituloplanta() {
    }



    public Tituloplanta(String titulo, String riego, String abono, String temperatura, String agua, int imagenResId) {
        this.titulo = titulo;
        this.riego = riego;
        this.abono = abono;
        this.agua = agua;
        this.temperatura = temperatura;
        this.imagenResId = imagenResId;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    protected Tituloplanta(Parcel in) {
        titulo = in.readString();
        riego = in.readString();
        abono = in.readString();
        agua = in.readString();
        temperatura = in.readString();

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

    public String getRiego() {
        return riego;
    }

    public void setRiego(String riego) {
        this.riego = riego;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

   /* public int getImagenResId() {
        return imagenResId;
    }

    public void setImagenResId(int imagenResId) {
        this.imagenResId = imagenResId;
    }*/

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
        dest.writeString(riego);
        dest.writeString(abono);
        dest.writeString(agua);
        dest.writeString(temperatura);

    }
}
