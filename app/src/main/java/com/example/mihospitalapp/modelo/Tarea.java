package com.example.mihospitalapp.modelo;

public class Tarea {
    private String paciente;
    private String habitacion;
    private String tarea;
    private String fecha;
    private String hora;
    private String asignado;

    // Constructor vacío
    public Tarea() {}

    // Constructor con parámetros
    public Tarea(String paciente, String habitacion, String tarea, String fecha, String hora, String asignado) {
        this.paciente = paciente;
        this.habitacion = habitacion;
        this.tarea = tarea;
        this.fecha = fecha;
        this.hora = hora;
        this.asignado = asignado;
    }

    // Getters y Setters
    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }
}
