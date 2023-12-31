package com.greengarden.Listadoplantas;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ModelPlantas implements Parcelable {
    private String titulo;
    private String riego;
    private String Cantidadagua;
    private String abono;
    private String Cantidadabono;
    private String consejoabono;
    private String consejoSiembra;
    private String consejoriego;
    private String temperatura;
    private String  tipoplanta;
    private String imagenResId;
    private String ulr;
    private  Integer  cantidad;
    private String id;

    public ModelPlantas(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelPlantas() {
    }



    public ModelPlantas(String titulo, String riego, String abono, String temperatura,
                        String Cantidadagua, String tipoplanta, String imagenResId,
                        String ulr, String consejoabono, String consejoSiembra,
                        String consejoriego, Integer  cantidad, String cantidadabono) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.consejoSiembra = consejoSiembra;
        this.consejoabono = consejoabono;
        this.consejoriego = consejoriego;
        this.riego = riego;
        this.abono = abono;
        this.Cantidadabono = cantidadabono;
        this.Cantidadagua = Cantidadagua;
        this.tipoplanta = tipoplanta;
        this.ulr = ulr;
        this.temperatura = temperatura;
        this.imagenResId = imagenResId;
    }

    public String getCantidadabono() {
        return Cantidadabono;
    }

    public void setCantidadabono(String cantidadabono) {
        Cantidadabono = cantidadabono;
    }

    public String getConsejoSiembra() {
        return consejoSiembra;
    }

    public void setConsejoSiembra(String consejoSiembra) {
        this.consejoSiembra = consejoSiembra;
    }

    public String getId() {
        return id;
    }

    public Integer  getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer  cantidad) {
        this.cantidad = cantidad;
    }

    public String getConsejoabono() {
        return consejoabono;
    }

    public void setConsejoabono(String consejoabono) {
        this.consejoabono = consejoabono;
    }

    public String getConsejoriego() {
        return consejoriego;
    }

    public void setConsejoriego(String consejoriego) {
        this.consejoriego = consejoriego;
    }

    public String getTipoplanta() {
        return tipoplanta;
    }

    public void setTipoplanta(String tipoplanta) {
        this.tipoplanta = tipoplanta;
    }

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public String getCantidadagua() {
        return Cantidadagua;
    }

    public void setCantidadagua(String cantidadagua) {
        this.Cantidadagua = cantidadagua;
    }

    protected ModelPlantas(Parcel in) {
        titulo = in.readString();
        riego = in.readString();
        abono = in.readString();
        Cantidadagua = in.readString();
        tipoplanta = in.readString();
        temperatura = in.readString();
        consejoriego = in.readString();
        consejoabono = in.readString();
        consejoSiembra = in.readString();
        Cantidadabono = in.readString();

    }

    public static final Creator<ModelPlantas> CREATOR = new Creator<ModelPlantas>() {
        @Override
        public ModelPlantas createFromParcel(Parcel in) {
            return new ModelPlantas(in);
        }

        @Override
        public ModelPlantas[] newArray(int size) {
            return new ModelPlantas[size];
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

    public String getImagenResId() {
        return imagenResId;
    }

    public void setImagenResId(String imagenResId) {
        this.imagenResId = imagenResId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;

    }

    /*public String getId() {

        return getId();
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(riego);
        dest.writeString(abono);
        dest.writeString(Cantidadagua);
        dest.writeString(temperatura);
        dest.writeString(consejoriego);
        dest.writeString(consejoabono);
        dest.writeString(tipoplanta);
        dest.writeString(consejoSiembra);
        dest.writeString(Cantidadabono);

    }
}
