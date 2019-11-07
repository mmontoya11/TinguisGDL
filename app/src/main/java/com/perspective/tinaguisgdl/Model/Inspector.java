package com.perspective.tinaguisgdl.Model;

public class Inspector {

    private int c_dependencia_id;
    private String nombre,paterno,materno,contrasena;

    public Inspector() {
        this.c_dependencia_id = 0;
        this.nombre = "";
        this.paterno = "";
        this.materno = "";
    }

    public Inspector(int c_dependencia_id) {
        this.c_dependencia_id = c_dependencia_id;
    }

    public Inspector(int c_dependencia_id, String nombre, String paterno, String materno, String contrasena) {
        this.c_dependencia_id = c_dependencia_id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.contrasena = contrasena;
    }

    public int getC_dependencia_id() {
        return c_dependencia_id;
    }

    public void setC_dependencia_id(int c_dependencia_id) {
        this.c_dependencia_id = c_dependencia_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
