package com.pgm.tianguis.Model;

public class Permisionario {

    private int id,poblacion,c_p,no_ext,descuento;
    private String nombres,domicilio,apellidoP,apellidoM,curp,colonia,telefono,fecha_nacimiento,sexo,rfc,no_int,tynEstatus,motivo_descuento;
    private Double saldo;

    public Permisionario() {
        this.id = 0;
        this.poblacion = 0;
        this.c_p = 0;
        this.no_ext = 0;
        this.descuento = 0;
        this.nombres = "";
        this.domicilio = "";
        this.apellidoP = "";
        this.apellidoM = "";
        this.curp = "";
        this.colonia = "";
        this.telefono = "";
        this.fecha_nacimiento = "";
        this.sexo = "";
        this.rfc = "";
        this.no_int = "";
        this.tynEstatus = "";
        this.motivo_descuento = "";
        this.saldo = 0d;
    }

    public Permisionario(int id, int poblacion, int c_p, int no_ext, int descuento, String nombres, String domicilio, String apellidoP, String apellidoM, String curp, String colonia, String telefono, String fecha_nacimiento, String sexo, String rfc, String no_int, String tynEstatus, String motivo_descuento, Double saldo) {
        this.id = id;
        this.poblacion = poblacion;
        this.c_p = c_p;
        this.no_ext = no_ext;
        this.descuento = descuento;
        this.nombres = nombres;
        this.domicilio = domicilio;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.curp = curp;
        this.colonia = colonia;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.sexo = sexo;
        this.rfc = rfc;
        this.no_int = no_int;
        this.tynEstatus = tynEstatus;
        this.motivo_descuento = motivo_descuento;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public int getC_p() {
        return c_p;
    }

    public void setC_p(int c_p) {
        this.c_p = c_p;
    }

    public int getNo_ext() {
        return no_ext;
    }

    public void setNo_ext(int no_ext) {
        this.no_ext = no_ext;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNo_int() {
        return no_int;
    }

    public void setNo_int(String no_int) {
        this.no_int = no_int;
    }

    public String getTynEstatus() {
        return tynEstatus;
    }

    public void setTynEstatus(String tynEstatus) {
        this.tynEstatus = tynEstatus;
    }

    public String getMotivo_descuento() {
        return motivo_descuento;
    }

    public void setMotivo_descuento(String motivo_descuento) {
        this.motivo_descuento = motivo_descuento;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
