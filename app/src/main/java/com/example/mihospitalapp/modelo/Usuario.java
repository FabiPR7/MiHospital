package com.example.mihospitalapp.modelo;

public class Usuario {
    private String nombre;
    private String apellido;
    private boolean estaActivo;
    private boolean estaRegistrado;

    public Usuario(String nombre, String apellido, boolean estaActivo, boolean estaRegistrado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.estaActivo = estaActivo;
        this.estaRegistrado = estaRegistrado;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public boolean isEstaRegistrado() {
        return estaRegistrado;
    }

    public void setEstaRegistrado(boolean estaRegistrado) {
        this.estaRegistrado = estaRegistrado;
    }
}
