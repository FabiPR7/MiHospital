package com.example.mihospitalapp.modelo;

public class Habitacion {
    private String nombre;
    private int camillas;
    private int piso;

    // Constructor vacío
    public Habitacion() {}

    // Constructor con parámetros
    public Habitacion(String nombre, int camillas, int piso) {
        this.nombre = nombre;
        this.camillas = camillas;
        this.piso = piso;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCamillas() {
        return camillas;
    }

    public void setCamillas(int camillas) {
        this.camillas = camillas;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }
}
