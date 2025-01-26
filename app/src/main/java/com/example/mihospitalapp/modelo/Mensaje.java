package com.example.mihospitalapp.modelo;

public class Mensaje {

   private String contenido;
   private String autor;
   private String receptor;
   private String Fecha_Hora;

    public Mensaje(String receptor, String fecha_Hora, String autor, String contenido) {
        this.receptor = receptor;
        this.Fecha_Hora = fecha_Hora;
        this.autor = autor;
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getFecha_Hora() {
        return Fecha_Hora;
    }

    public void setFecha_Hora(String fecha_Hora) {
        Fecha_Hora = fecha_Hora;
    }
}
