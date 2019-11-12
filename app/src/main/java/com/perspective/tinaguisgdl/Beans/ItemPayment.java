package com.perspective.tinaguisgdl.Beans;

public class ItemPayment {
    public String NombrePermisionario;
    private int idPuesto;
    private String Tianguis;
    private double MetrosLineales;
    private double Saldo;
    private double SaldoDespues;
    private double CobroLiquido;
    private double CobroTotal;
    private int iPermisio,idTianguis;
    private boolean pago;
    private String fecha;

    public double getSaldoDespues() {
        return saldoDespues;
    }

    public void setSaldoDespues(double saldoDespues) {
        this.saldoDespues = saldoDespues;
    }

    private double saldoDespues;

    public ItemPayment() {
        NombrePermisionario = "";
        idPuesto = 0;
        Tianguis = "";
        MetrosLineales = 0;
        Saldo = 0;
        CobroLiquido = 0;
        CobroTotal = 0;
        saldoDespues = 0d;
        iPermisio = 0;
        pago = false;
        fecha = "";
    }

    public ItemPayment(String nombrePermisionario, int idPuesto, String tianguis, double metroLineal, double saldo, double cobroLiquido, double cobroTotal,double saldoDespues,int iPermisio, boolean pago,String fecha) {
        NombrePermisionario = nombrePermisionario;
        this.idPuesto = idPuesto;
        Tianguis = tianguis;
        MetrosLineales = metroLineal;
        Saldo = saldo;
        CobroLiquido = cobroLiquido;
        CobroTotal = cobroTotal;
        SaldoDespues = saldoDespues;
        this.iPermisio = iPermisio;
        this.pago = pago;
        this.fecha = fecha;
    }

    public String getNombrePermisionario() {
        return NombrePermisionario;
    }

    public void setNombrePermisionario(String nombrePermisionario) {
        NombrePermisionario = nombrePermisionario;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getTianguis() {
        return Tianguis;
    }

    public void setTianguis(String tianguis) {
        Tianguis = tianguis;
    }

    public double getMetrosLineales() {
        return MetrosLineales;
    }

    public void setMetrosLineales(double metrosLineales) {
        MetrosLineales = metrosLineales;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public double getCobroLiquido() {
        return CobroLiquido;
    }

    public void setCobroLiquido(double cobroLiquido) {
        CobroLiquido = cobroLiquido;
    }

    public double getCobroTotal() {
        return CobroTotal;
    }

    public void setCobroTotal(double cobroTotal) {
        CobroTotal = cobroTotal;
    }

    public int getIPermisio() {
        return iPermisio;
    }

    public void setIPermisio(int IPermisio) {
        this.iPermisio = IPermisio;
    }

    public int getIdTianguis() {
        return idTianguis;
    }

    public void setIdTianguis(int idTianguis) {
        this.idTianguis = idTianguis;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ItemPayment{" +
                "NombrePermisionario='" + NombrePermisionario + '\'' +
                ", Puesto='" + idPuesto + '\'' +
                ", Tianguis='" + Tianguis + '\'' +
                ", MetrosLineales=" + MetrosLineales +
                ", Saldo=" + Saldo +
                ", CobroLiquido=" + CobroLiquido +
                ", CobroTotal=" + CobroTotal +
                '}';
    }
}
