package com.perspective.tinaguisgdl.Model;

public class Configuracion {

    private int id;
    private String presidente,director,dependencia;
    private double costo_m;

    public Configuracion() {
        this.id = 0;
        this.presidente = "";
        this.director = "";
        this.dependencia = "";
        this.costo_m = 0d;
    }

    public Configuracion(int id, String presidente, String director, String dependencia, double costo_m) {
        this.id = id;
        this.presidente = presidente;
        this.director = director;
        this.dependencia = dependencia;
        this.costo_m = costo_m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public double getCosto_m() {
        return costo_m;
    }

    public void setCosto_m(double costo_m) {
        this.costo_m = costo_m;
    }
}
