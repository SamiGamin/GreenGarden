package com.greengarden.Noticias;

public class Noticiaslist {
    private String titulo;
    private String noticia;
    private String fecha;

    public Noticiaslist() {
    }

    public Noticiaslist(String titulo, String noticia, String fecha) {
        this.titulo = titulo;
        this.noticia = noticia;
        this.fecha = noticia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }
}
