package com.perspective.tinaguisgdl.Model;

public class Tianguis {

    private int id,lunes,martes,miercoles,jueves,viernes,sabado,domingo;
    private String estatus,nombre;

    public Tianguis() {
        this.id = 0;
        this.lunes = 0;
        this.martes = 0;
        this.miercoles = 0;
        this.jueves = 0;
        this.viernes = 0;
        this.sabado = 0;
        this.domingo = 0;
        this.estatus = "";
        this.nombre = "";
    }

    public Tianguis(int id,String nombre,String estatus,int lunes,int martes,int miercoles,int jueves,int viernes,int sabado,int domingo) {
        this.id = id;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.estatus = estatus;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLunes() {
        return lunes;
    }

    public void setLunes(int lunes) {
        this.lunes = lunes;
    }

    public int getMartes() {
        return martes;
    }

    public void setMartes(int martes) {
        this.martes = martes;
    }

    public int getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(int miercoles) {
        this.miercoles = miercoles;
    }

    public int getJueves() {
        return jueves;
    }

    public void setJueves(int jueves) {
        this.jueves = jueves;
    }

    public int getViernes() {
        return viernes;
    }

    public void setViernes(int viernes) {
        this.viernes = viernes;
    }

    public int getSabado() {
        return sabado;
    }

    public void setSabado(int sabado) {
        this.sabado = sabado;
    }

    public int getDomingo() {
        return domingo;
    }

    public void setDomingo(int domingo) {
        this.domingo = domingo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
