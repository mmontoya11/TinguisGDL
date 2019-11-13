package com.perspective.tinaguisgdl.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.perspective.tinaguisgdl.Model.Asistencia;
import com.perspective.tinaguisgdl.Model.Direccion;
import com.perspective.tinaguisgdl.Model.Inspector;
import com.perspective.tinaguisgdl.Model.Pagos;
import com.perspective.tinaguisgdl.Model.Tianguis;

import java.util.ArrayList;
import java.util.List;

public class GestionBD extends SQLiteOpenHelper {


    public static final String TABLE_PERMISIONARIO = "permisionario";
    public static final String TABLE_PUESTO = "puestos";
    public static final String TABLE_CONFIGURACIONES = "configuraciones";
    public static final String TABLE_C_ADMINISTRADOR = "c_administrador";
    public static final String TABLE_C_GIROS_COMERCIALES = "c_giros_comerciales";
    public static final String TABLE_C_TIANGUIS = "c_tianguis";
    public static final String TABLE_C_POBLACION = "c_poblacion";
    public static final String TABLE_C_ZONA_TIANGUIS = "c_zona_tianguis";
    public static final String TABLE_C_INSPECTORES = "c_inspectores";
    public static final String TABLE_C_DEPENDENCIAS = "c_dependencias";
    public static final String TABLE_ASISTENCIA = "asistemcias";
    public static final String TABLE_PAGOS = "pagos";
    private ContentValues cv = null;

    private String sqlCrearTablaPermisionario = "CREATE TABLE " + TABLE_PERMISIONARIO +"(id integer,poblacion integer,nombres TEXT,domicilio TEXT,apellidoP TEXT,apellidoM TEXT,status TEXT,EstCiv TEXT,estatus TEXT)";
    private String sqlCrearTablaAdministrador = "CREATE TABLE " + TABLE_C_ADMINISTRADOR +"(id integer,vchMaterno TEXT,tynSexo TEXT,chCurp TEXT,vchPaterno TEXT,vchNombre TEXT,EstCiv TEXT)";
    private String sqlCrearGiros  = "CREATE TABLE " + TABLE_C_GIROS_COMERCIALES +"(id integer,smlFamilia INTEGER,tynEstatus TEXT,vchGiroComercial TEXT)";
    private String sqlCrearTablaCTianguis = "CREATE TABLE " + TABLE_C_TIANGUIS +"(id integer,categoria TEXT,estatus TEXT,colonia TEXT,dia TEXT,nombre TEXT,calle_ubicacion TEXT,lunes TEXT,martes TEXT, miercoles Text,jueves text,viernes text,sabado text,domingo text)";
    private String sqlCrearTablaCPoblacion = "CREATE TABLE " + TABLE_C_POBLACION +"(id integer,c_poblaciones TEXT)";
    private String sqlCrearTablaPuesto = "CREATE TABLE " + TABLE_PUESTO +"(id integer,iPERMISIO integer,smlTIANGUIS integer,tynDia text,tynSITUACION text)";
    private String sqlCrearTablaConfiguracion = "CREATE TABLE " + TABLE_CONFIGURACIONES +"(id integer,vchPresidente text,vchDirector text,costo_m float,chPeriodo text,vchDependencia text)";
    private String sqlCrearTablaZonaTianguis = "CREATE TABLE " + TABLE_C_ZONA_TIANGUIS +"(id integer,smlZonaTianguis integer,estatus text,smlTianguis integer,CalleCruceIni text,CallePrincipal text,chZonaTianguis text,CalleCruceFin text)";
    private String sqlCrearTablaInspectores = "CREATE TABLE " + TABLE_C_INSPECTORES +"(c_dependencia_id integer,nombre TEXT,paterno text,materno text,contrasena text)";
    private String sqlCrearTablaDependencias = "CREATE TABLE " + TABLE_C_DEPENDENCIAS +"(id integer,nombre text)";
    private String sqlCrearTablaAsistencia = "CREATE TABLE " + TABLE_ASISTENCIA + "(id integer PRIMARY KEY AUTOINCREMENT,smlAnno integer,vchSemanas text,smlTianguis integer,iPermisionar integer,tynEstado integer,puesto integer,estatus TEXT)";
    private String sqlCrearTablaPago = "CREATE TABLE " + TABLE_PAGOS + "(id integer PRIMARY KEY AUTOINCREMENT,permisionario integer,cargo real,concepto TEXT,puesto integer,saldo real,abono real,estatus TEXT,saldoa real,fecha numeric)";

    public GestionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCrearTablaPermisionario);
        db.execSQL(sqlCrearTablaAdministrador);
        db.execSQL(sqlCrearGiros);
        db.execSQL(sqlCrearTablaCTianguis);
        db.execSQL(sqlCrearTablaCPoblacion);
        db.execSQL(sqlCrearTablaPuesto);
        db.execSQL(sqlCrearTablaConfiguracion);
        db.execSQL(sqlCrearTablaZonaTianguis);
        db.execSQL(sqlCrearTablaInspectores);
        db.execSQL(sqlCrearTablaDependencias);
        db.execSQL(sqlCrearTablaAsistencia);
        db.execSQL(sqlCrearTablaPago);

        ingresarDir(db);
        ingresarIns(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Tianguis> getAllTianguis(String condition, SQLiteDatabase db) {
        if(TextUtils.isEmpty(condition))
            condition = "1=1";
        List<Tianguis> tianguis = new ArrayList<Tianguis>();
        String sql = "select * from " + TABLE_C_TIANGUIS + " where " + condition;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                Tianguis tianguis1 = cursorToTianguis(cursor);
                tianguis.add(tianguis1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tianguis;
    }

    public Tianguis cursorToTianguis(Cursor cursor) {
        return new Tianguis(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("nombre")),cursor.getString(cursor.getColumnIndex("estatus")),cursor.getInt(cursor.getColumnIndex("lunes")),cursor.getInt(cursor.getColumnIndex("martes")),cursor.getInt(cursor.getColumnIndex("miercoles")),cursor.getInt(cursor.getColumnIndex("jueves")),cursor.getInt(cursor.getColumnIndex("viernes")),cursor.getInt(cursor.getColumnIndex("sabado")),cursor.getInt(cursor.getColumnIndex("domingo")));
    }

    public double getCosto(String condition, SQLiteDatabase db) {
        if(TextUtils.isEmpty(condition))
            condition = "1=1";
        double costo = 0;
        String sql = "select costo_m from " + GestionBD.TABLE_CONFIGURACIONES + " where " + condition;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                costo = cursor.getDouble(cursor.getColumnIndex("costo_m"));
            } while (cursor.moveToNext());
        }
        return costo;
    }

    public boolean insertarAsistencia(SQLiteDatabase db, Asistencia asistencia) {
        boolean res = false;
        cv = new ContentValues();
        cv.put("smlAnno",asistencia.getAnno());
        cv.put("vchSemanas",asistencia.getSemanas());
        cv.put("smlTianguis",asistencia.getIdTianguis());
        cv.put("iPermisionar",asistencia.getIdPermisionario());
        cv.put("tynEstado",asistencia.getEstado());
        cv.put("puesto",asistencia.getPuesto());
        cv.put("estatus",asistencia.getEstatus());
        res = db.insert(this.TABLE_ASISTENCIA,null,cv) > 0;
        return res;
    }

    public boolean insertarPagos(SQLiteDatabase db, Pagos pago) {
        boolean res = false;
        cv = new ContentValues();
        cv.put("permisionario",pago.getPermisionario());
        cv.put("cargo",pago.getCargo());
        cv.put("concepto",pago.getConcepto());
        cv.put("puesto",pago.getPuesto());
        cv.put("saldo",pago.getSaldo());
        cv.put("abono",pago.getAbono());
        cv.put("estatus",pago.getEstatus());
        cv.put("saldoa",pago.getSaldoa());
        cv.put("fecha",pago.getFecha());
        res = db.insert(this.TABLE_PAGOS,null,cv) > 0;
        return res;
    }

    public int consultarAsistencia(Asistencia asistencia,SQLiteDatabase db) {
        String sql = "SELECT * FROM " + TABLE_ASISTENCIA + " where smlanno = " + asistencia.getAnno() +
                " and vchSemanas = '" + asistencia.getSemanas() + "' and smlTianguis = " + asistencia.getIdTianguis() + " and iPermisionar = " +
                asistencia.getIdPermisionario()+ " and puesto = " + asistencia.getPuesto();
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        Log.v("total",count + " total");
        return count;
    }

    public List<Direccion> getAllDireccion(String condicion,SQLiteDatabase db) {
        if(TextUtils.isEmpty(condicion))
            condicion = "1=1";
        List<Direccion> direccion = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_C_DEPENDENCIAS + " WHERE " + condicion;
        Log.v("SQL" , sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                Direccion direcciones = cursorToDireccion(cursor);
                direccion.add(direcciones);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return direccion;
    }

    public Direccion cursorToDireccion(Cursor cursor) {
        return new Direccion(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("nombre")));
    }

    public List<String> getAllInspector(String condicion, SQLiteDatabase db) {
        if(TextUtils.isEmpty(condicion))
            condicion = "1=1";
        List<String> inspector = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_C_INSPECTORES + " WHERE " + condicion;
        Log.v("SQL" , sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                inspector.add(cursorToInspector(cursor));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return inspector;
    }

    public String cursorToInspector(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("nombre")) + " " + cursor.getString(cursor.getColumnIndex("paterno")) + " " + cursor.getString(cursor.getColumnIndex("materno"));
    }

    public boolean ingresar(SQLiteDatabase db,String... params) {
        boolean res = false;
        String sql = "Select * from " + TABLE_C_INSPECTORES + " WHERE contrasena = '" + params[1] + "'";
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                Log.v("if1",cursor.getString(cursor.getColumnIndex("contrasena")));
                if((cursor.getString(cursor.getColumnIndex("nombre")) + " " + cursor.getString(cursor.getColumnIndex("paterno")) + " " + cursor.getString(cursor.getColumnIndex("materno"))).equalsIgnoreCase(params[0])) {
                    Log.v("if",cursor.getString(1));
                    res = true;
                }
            }while (cursor.moveToNext());
        }
        return res;
    }

    public void ingresarDir(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("id",1);
        cv.put("nombre","Administración");
        db.insert(TABLE_C_DEPENDENCIAS,null,cv);
    }

    public void ingresarIns(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("c_dependencia_id",1);
        cv.put("nombre","administrador");
        cv.put("paterno","del");
        cv.put("materno","sistema");
        cv.put("contrasena","4dm1n");
        cv.put("c_dependencia_id",1);
        db.insert(TABLE_C_INSPECTORES,null,cv);
    }

    public double consultarSalo(int idPermisio,SQLiteDatabase db) {
        double saldo = 0;
        String sql = "SELECT * FROM " + TABLE_PERMISIONARIO + " where ID = " + idPermisio;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
        Log.v("total",saldo + " saldo");
        return saldo;
    }

    public boolean consultaPagos(int idPermisio,String fecha,SQLiteDatabase db) {
        boolean res = false;
        String sql = "SELECT * FROM " + TABLE_PAGOS + " where permisionario = " + idPermisio/* + " and fecha between '" + fecha + " 00:00' and '" + fecha + " 23:59'"*/;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        Log.v("total",cursor.getCount() + " <-");
        try {
            if (cursor.moveToFirst()) {
                Log.v("fecha", cursor.getString(cursor.getColumnIndex("fecha")) + " " + fecha );
                do {
                    if (fecha.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("fecha"))))
                        return true;
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {

        }
        return res;
    }

    public static void updatePunto(SQLiteDatabase db,int idPermisio) {
        String sql = "SELECT puntos FROM " + TABLE_PERMISIONARIO + " WHERE id = " + idPermisio;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);
        int puntos = 0;
        if(cursor.moveToFirst()) {
            do {
                puntos = cursor.getInt(cursor.getColumnIndex("puntos")) + 1;
            }while(cursor.moveToNext());
        }
        ContentValues cv = new ContentValues();
        cv.put("puntos",puntos);
        cv.put("estatus","F");
        db.update(TABLE_PERMISIONARIO,cv,null,null);
    }

}
