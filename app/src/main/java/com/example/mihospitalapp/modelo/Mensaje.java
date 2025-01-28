package com.example.mihospitalapp.modelo;

import androidx.annotation.NonNull;

public class Mensaje {

   private String contenido;
   private String autor;
   private String receptor;
   private String fecha_hora;

    public Mensaje(String receptor, String fecha_Hora, String autor, String contenido) {
        this.receptor = receptor;
        this.fecha_hora = fecha_Hora;
        this.autor = autor;
        this.contenido = contenido;
    }
    public Mensaje(){}


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
        return fecha_hora;
    }

    public void setFecha_Hora(String fecha_Hora) {
        fecha_hora = fecha_Hora;
    }

    @NonNull
    @Override
    public String toString() {
        return "Autor: "+getAutor() +"\n Mensaje: "+getContenido()+" \n para :"+receptor+" enviado en "+getFecha_Hora();
    }
}
