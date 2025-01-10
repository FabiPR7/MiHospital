package com.example.mihospitalapp.modelo;

public class Paciente {
    private String nombre;
    private String habitacion;
    private String enfermedades;
    private String alergias;
    private String cuidados;
    private String fechaIngreso;

    // Constructor vacío
    public Paciente() {}

    // Constructor con parámetros
    public Paciente(String nombre, String habitacion, String enfermedades, String alergias, String cuidados, String fechaIngreso) {
        this.nombre = nombre;
        this.habitacion = habitacion;
        this.enfermedades = enfermedades;
        this.alergias = alergias;
        this.cuidados = cuidados;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
