package com.greengarden.Consejos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ListaConsejos implements Parcelable {
    private String nombreplanta;
    private String consejoplanta;

    public ListaConsejos() {
    }

    public ListaConsejos(String nombreplanta, String consejoplanta) {
        this.nombreplanta = nombreplanta;
        this.consejoplanta = consejoplanta;
    }

    protected ListaConsejos(Parcel in) {
        nombreplanta = in.readString();
        consejoplanta = in.readString();
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
        return nombreplanta;
    }

    public void setNombreplanta(String nombreplanta) {
        this.nombreplanta = nombreplanta;
    }

    public String getConsejoplanta() {
        return consejoplanta;
    }

    public void setConsejoplanta(String consejoplanta) {
        this.consejoplanta = consejoplanta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(nombreplanta);
        dest.writeString(consejoplanta);
    }



}
