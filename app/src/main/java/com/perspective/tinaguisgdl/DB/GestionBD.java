package com.perspective.tinaguisgdl.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.perspective.tinaguisgdl.Model.Asistencia;
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
    private ContentValues cv = null;

    private String sqlCrearTablaPermisionario = "CREATE TABLE " + TABLE_PERMISIONARIO +"(id integer,poblacion integer,nombres TEXT,domicilio TEXT,apellidoP TEXT,apellidoM TEXT,status TEXT,EstCiv TEXT)";
    private String sqlCrearTablaAdministrador = "CREATE TABLE " + TABLE_C_ADMINISTRADOR +"(id integer,vchMaterno TEXT,tynSexo TEXT,chCurp TEXT,vchPaterno TEXT,vchNombre TEXT,EstCiv TEXT)";
    private String sqlCrearGiros  = "CREATE TABLE " + TABLE_C_GIROS_COMERCIALES +"(id integer,smlFamilia INTEGER,tynEstatus TEXT,vchGiroComercial TEXT)";
    private String sqlCrearTablaCTianguis = "CREATE TABLE " + TABLE_C_TIANGUIS +"(id integer,categoria TEXT,estatus TEXT,colonia TEXT,dia TEXT,nombre TEXT,calle_ubicacion TEXT,lunes TEXT,martes TEXT, miercoles Text,jueves text,viernes text,sabado text,domingo text)";
    private String sqlCrearTablaCPoblacion = "CREATE TABLE " + TABLE_C_POBLACION +"(id integer,c_poblaciones TEXT)";
    private String sqlCrearTablaPuesto = "CREATE TABLE " + TABLE_PUESTO +"(id integer,iPERMISIO integer,smlTIANGUIS integer,tynDia text,tynSITUACION text)";
    private String sqlCrearTablaConfiguracion = "CREATE TABLE " + TABLE_CONFIGURACIONES +"(id integer,vchPresidente text,vchDirector text,costo_m float,chPeriodo text,vchDependencia text)";
    private String sqlCrearTablaZonaTianguis = "CREATE TABLE " + TABLE_C_ZONA_TIANGUIS +"(id integer,smlZonaTianguis integer,estatus text,smlTianguis integer,CalleCruceIni text,CallePrincipal text,chZonaTianguis text,CalleCruceFin text)";
    private String sqlCrearTablaInspectores = "CREATE TABLE " + TABLE_C_INSPECTORES +"(id integer)";
    private String sqlCrearTablaDependencias = "CREATE TABLE " + TABLE_C_DEPENDENCIAS +"(id integer)";
    private String sqlCrearTablaAsistencia = "CREATE TABLE " + TABLE_ASISTENCIA + "(id integer PRIMARY KEY AUTOINCREMENT,smlAnno integer,vchSemanas text,smlTianguis integer,iPermisionar integer,tynEstado integer,puesto integer,estatus TEXT)";


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

    public int consultarAsistencia(Asistencia asistencia,SQLiteDatabase db) {
        String sql = "SELECT * FROM " + TABLE_ASISTENCIA + " where smlanno = " + asistencia.getAnno() +
                " and vchSemanas = '" + asistencia.getSemanas() + "' and smlTianguis = " + asistencia.getIdTianguis() + " and iPermisionar = " +
                asistencia.getIdPermisionario()+ " and puesto = " + asistencia.getPuesto();
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        Log.v("total",count + " total");
        return count;
    }
}
