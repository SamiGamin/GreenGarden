package com.greengarden.Consejos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ListaConsejos implements Parcelable {
    private String titulo;
    private String consejoabono;
    private String consejoagua;
    private int imagenUrl;

    public ListaConsejos() {
    }

    public ListaConsejos(String nombreplanta, String consejoplanta , String consejoagua) {
        this.titulo = nombreplanta;
        this.consejoabono = consejoplanta;
        this.consejoagua = consejoplanta;
        /*this.imagenUrl = imagenUrl;*/
    }

    protected ListaConsejos(Parcel in) {
        titulo = in.readString();
        consejoabono = in.readString();
        consejoagua = in.readString();
    }

   /* public int getImagenUrl() {
        return imagenUrl;
    }*/

   /* public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = Integer.parseInt(imagenUrl);
    }*/

    public String getConsejoabono() {
        return consejoabono;
    }

    public void setConsejoabono(String consejoabono) {
        this.consejoabono = consejoabono;
    }

    public String getConsejoagua() {
        return consejoagua;
    }

    public void setConsejoagua(String consejoagua) {
        this.consejoagua = consejoagua;
    }

    public static final Creator<ListaConsejos> CREATOR = new Creator<ListaConsejos>() {
        @Override
        public ListaConsejos createFromParcel(Parcel in) {
            return new ListaConsejos(in);
        }

        @Override
        public ListaConsejos[] newArray(int size) {
            return new ListaConsejos[size];
        }
    };

    public String getNombreplanta() {
        return titulo;
    }

    public void setNombreplanta(String nombreplanta) {
        this.titulo = nombreplanta;
    }

    public String getConsejoplanta() {
        return consejoabono;
    }

    public void setConsejoplanta(String consejoplanta) {
        this.consejoabono = consejoplanta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(titulo);
        dest.writeString(consejoabono);
        dest.writeString(consejoagua);
    }



}
