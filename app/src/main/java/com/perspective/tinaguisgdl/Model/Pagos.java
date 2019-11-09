package com.perspective.tinaguisgdl.Model;

public class Pagos {

    private int id,permisionario,puesto;
    private String concepto,estatus;
    private double cargo,saldo,abono,saldoa;

    public Pagos() {
        this.id = 0;
        this.permisionario = 0;
        this.puesto = 0;
        this.concepto = "";
        this.estatus = "";
        this.cargo = 0d;
        this.saldo = 0d;
        this.abono = 0d;
        this.saldoa = 0d;
    }

    public Pagos(int id, int permisionario, int puesto, String concepto, String estatus, double cargo, double saldo, double abono,double saldoa) {
        this.id = id;
        this.permisionario = permisionario;
        this.puesto = puesto;
        this.concepto = concepto;
        this.estatus = estatus;
        this.cargo = cargo;
        this.saldo = saldo;
        this.abono = abono;
        this.saldoa = saldoa;
    }

    public Pagos(int permisionario, int puesto, String concepto, String estatus, double cargo, double saldo, double abono,double saldoa) {
        this.permisionario = permisionario;
        this.puesto = puesto;
        this.concepto = concepto;
        this.estatus = estatus;
        this.cargo = cargo;
        this.saldo = saldo;
        this.abono = abono;
        this.saldoa = saldoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPermisionario() {
        return permisionario;
    }

    public void setPermisionario(int permisionario) {
        this.permisionario = permisionario;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public double getSaldoa() {
        return saldoa;
    }

    public void setSaldoa(double saldoa) {
        this.saldoa = saldoa;
    }
}
