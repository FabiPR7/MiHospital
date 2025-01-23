package com.example.mihospitalapp.modelo;

import androidx.annotation.NonNull;

public class Personal {
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String codigo;
    private String estado;

    // Constructor vacío
    public Personal() {}

    // Constructor con parámetros
    public Personal(String nombre, String apellido, String correo, String contraseña, String codigo,String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.codigo = codigo;
        this.estado = estado;
    }

    // Getters y Setters
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nombre:"+nombre+" Apellido:"+apellido+" Codigo:"+codigo;
    }
}
