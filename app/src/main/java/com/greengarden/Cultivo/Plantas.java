package com.greengarden.Cultivo;

public class Plantas {

    private String descripcion;
    private String fechaSiembra;
    private String humedad;
    private String luzSolar;
    private String nombre;
    public Plantas(String descripcion, String fechaSiembra, String humedad, String luzSolar, String nombre, String riego, String temperatura) {
        this.descripcion = descripcion;
        this.fechaSiembra = fechaSiembra;
        this.humedad = humedad;
        this.luzSolar = luzSolar;
        this.nombre = nombre;
        this.riego = riego;
        this.temperatura = temperatura;
    }

    public Plantas(){

    }

    public String getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(String fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getLuzSolar() {
        return luzSolar;
    }

    public void setLuzSolar(String luzSolar) {
        this.luzSolar = luzSolar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRiego() {
        return riego;
    }

    public void setRiego(String riego) {
        this.riego = riego;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    private String riego;
    private String temperatura;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}
