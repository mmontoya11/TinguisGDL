package com.perspective.tinaguisgdl.Model;

public class Asistencia {

    private int id,anno,idTianguis,idPermisionario,estado,puesto;
    private String semanas;

    public Asistencia() {
        this.id = 0;
        this.anno = 0;
        this.idTianguis = 0;
        this.idPermisionario = 0;
        this.estado = 0;
        this.puesto = 0;
        this.semanas = "";
    }

    public Asistencia(int id, int anno, int idTianguis, int idPermisionario, int estado, int puesto, String semanas) {
        this.id = id;
        this.anno = anno;
        this.idTianguis = idTianguis;
        this.idPermisionario = idPermisionario;
        this.estado = estado;
        this.puesto = puesto;
        this.semanas = semanas;
    }

    public Asistencia(int anno, int idTianguis, int idPermisionario, int estado, int puesto, String semanas) {
        this.anno = anno;
        this.idTianguis = idTianguis;
        this.idPermisionario = idPermisionario;
        this.estado = estado;
        this.puesto = puesto;
        this.semanas = semanas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getIdTianguis() {
        return idTianguis;
    }

    public void setIdTianguis(int idTianguis) {
        this.idTianguis = idTianguis;
    }

    public int getIdPermisionario() {
        return idPermisionario;
    }

    public void setIdPermisionario(int idPermisionario) {
        this.idPermisionario = idPermisionario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public String getSemanas() {
        return semanas;
    }

    public void setSemanas(String semanas) {
        this.semanas = semanas;
    }
}
