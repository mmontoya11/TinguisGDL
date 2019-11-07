package com.perspective.tinaguisgdl.Model;

public class Direccion {

    private int id;
    private String nombre;

    public Direccion() {
        this.id = 0;
        this.nombre = "";
    }

    public Direccion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Direccion(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
