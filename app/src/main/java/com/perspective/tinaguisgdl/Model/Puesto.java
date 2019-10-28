package com.perspective.tinaguisgdl.Model;

public class Puesto {
    private int id,iPERMISIO,smlTIANGUIS,tynDia,tynSITUACION,tynestatus,imovimiento,iTarjeton,iZonaT,iDescuento,smlGIRO1;
    private String vchCALLE1,vchCALLE2,vchSUPLENTE1,vchSUPLENTE2;
    private float smmTERMINA,smmLONGITUD,smmINICIA,vchCOMENTARIO;

    public Puesto() {
        this.id = 0;
        this.iPERMISIO = 0;
        this.smlTIANGUIS = 0;
        this.tynDia = 0;
        this.tynSITUACION = 0;
        this.tynestatus = 0;
        this.imovimiento = 0;
        this.iTarjeton = 0;
        this.iZonaT = 0;
        this.iDescuento = 0;
        this.smlGIRO1 = 0;
        this.vchCALLE1 = "";
        this.vchCALLE2 = "";
        this.vchSUPLENTE1 = "";
        this.vchSUPLENTE2 = "";
        this.smmTERMINA = 0f;
        this.smmLONGITUD = 0f;
        this.smmINICIA = 0f;
        this.vchCOMENTARIO = 0f;
    }

    public Puesto(int id, int iPERMISIO, int smlTIANGUIS, int tynDia, int tynSITUACION, int tynestatus, int imovimiento, int iTarjeton, int iZonaT, int iDescuento, int smlGIRO1, String vchCALLE1, String vchCALLE2, String vchSUPLENTE1, String vchSUPLENTE2, float smmTERMINA, float smmLONGITUD, float smmINICIA, float vchCOMENTARIO) {
        this.id = id;
        this.iPERMISIO = iPERMISIO;
        this.smlTIANGUIS = smlTIANGUIS;
        this.tynDia = tynDia;
        this.tynSITUACION = tynSITUACION;
        this.tynestatus = tynestatus;
        this.imovimiento = imovimiento;
        this.iTarjeton = iTarjeton;
        this.iZonaT = iZonaT;
        this.iDescuento = iDescuento;
        this.smlGIRO1 = smlGIRO1;
        this.vchCALLE1 = vchCALLE1;
        this.vchCALLE2 = vchCALLE2;
        this.vchSUPLENTE1 = vchSUPLENTE1;
        this.vchSUPLENTE2 = vchSUPLENTE2;
        this.smmTERMINA = smmTERMINA;
        this.smmLONGITUD = smmLONGITUD;
        this.smmINICIA = smmINICIA;
        this.vchCOMENTARIO = vchCOMENTARIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiPERMISIO() {
        return iPERMISIO;
    }

    public void setiPERMISIO(int iPERMISIO) {
        this.iPERMISIO = iPERMISIO;
    }

    public int getSmlTIANGUIS() {
        return smlTIANGUIS;
    }

    public void setSmlTIANGUIS(int smlTIANGUIS) {
        this.smlTIANGUIS = smlTIANGUIS;
    }

    public int getTynDia() {
        return tynDia;
    }

    public void setTynDia(int tynDia) {
        this.tynDia = tynDia;
    }

    public int getTynSITUACION() {
        return tynSITUACION;
    }

    public void setTynSITUACION(int tynSITUACION) {
        this.tynSITUACION = tynSITUACION;
    }

    public int getTynestatus() {
        return tynestatus;
    }

    public void setTynestatus(int tynestatus) {
        this.tynestatus = tynestatus;
    }

    public int getImovimiento() {
        return imovimiento;
    }

    public void setImovimiento(int imovimiento) {
        this.imovimiento = imovimiento;
    }

    public int getiTarjeton() {
        return iTarjeton;
    }

    public void setiTarjeton(int iTarjeton) {
        this.iTarjeton = iTarjeton;
    }

    public int getiZonaT() {
        return iZonaT;
    }

    public void setiZonaT(int iZonaT) {
        this.iZonaT = iZonaT;
    }

    public int getiDescuento() {
        return iDescuento;
    }

    public void setiDescuento(int iDescuento) {
        this.iDescuento = iDescuento;
    }

    public int getSmlGIRO1() {
        return smlGIRO1;
    }

    public void setSmlGIRO1(int smlGIRO1) {
        this.smlGIRO1 = smlGIRO1;
    }

    public String getVchCALLE1() {
        return vchCALLE1;
    }

    public void setVchCALLE1(String vchCALLE1) {
        this.vchCALLE1 = vchCALLE1;
    }

    public String getVchCALLE2() {
        return vchCALLE2;
    }

    public void setVchCALLE2(String vchCALLE2) {
        this.vchCALLE2 = vchCALLE2;
    }

    public String getVchSUPLENTE1() {
        return vchSUPLENTE1;
    }

    public void setVchSUPLENTE1(String vchSUPLENTE1) {
        this.vchSUPLENTE1 = vchSUPLENTE1;
    }

    public String getVchSUPLENTE2() {
        return vchSUPLENTE2;
    }

    public void setVchSUPLENTE2(String vchSUPLENTE2) {
        this.vchSUPLENTE2 = vchSUPLENTE2;
    }

    public float getSmmTERMINA() {
        return smmTERMINA;
    }

    public void setSmmTERMINA(float smmTERMINA) {
        this.smmTERMINA = smmTERMINA;
    }

    public float getSmmLONGITUD() {
        return smmLONGITUD;
    }

    public void setSmmLONGITUD(float smmLONGITUD) {
        this.smmLONGITUD = smmLONGITUD;
    }

    public float getSmmINICIA() {
        return smmINICIA;
    }

    public void setSmmINICIA(float smmINICIA) {
        this.smmINICIA = smmINICIA;
    }

    public float getVchCOMENTARIO() {
        return vchCOMENTARIO;
    }

    public void setVchCOMENTARIO(float vchCOMENTARIO) {
        this.vchCOMENTARIO = vchCOMENTARIO;
    }
}
