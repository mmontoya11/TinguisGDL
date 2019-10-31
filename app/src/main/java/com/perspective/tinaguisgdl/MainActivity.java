package com.perspective.tinaguisgdl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.perspective.tinaguisgdl.DB.Conexion;
import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.DB.JSONParser;
import com.perspective.tinaguisgdl.Model.Asistencia;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Conexion conn = new Conexion();
    private Conexion c;
    public static final int VERSION  = 1;
    private String msj = "";
    private CardView cvActualizar,cvCarga;
    private GestionBD gestion = null;
    private SQLiteDatabase db = null;
    private JSONParser jparser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Asistencia = findViewById(R.id.btn_Asistencia);
        CardView Cardview_Faltas = findViewById(R.id.cardview);
        cvActualizar = findViewById(R.id.cvActualizar);
        cvCarga = findViewById(R.id.cardview_carga);
        CardView cardView_cobro = findViewById(R.id.cardview_cobro);

        cardView_cobro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this,ActivityCobro.class);
                startActivity(intent);
                return true;
            }
        });

        cvActualizar.setOnTouchListener(this);
        cvCarga.setOnTouchListener(this);

        c = new Conexion(getApplicationContext());

        btn_Asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityAsistencia.class);
                startActivity(intent);
            }
        });

        gestion = new GestionBD(getApplicationContext(),"TianguisGDL",null,VERSION);
        db = gestion.getReadableDatabase();
        jparser = new JSONParser();

    }

    public void insertar() {
        msj = "";
        if (!conn.search("http://192.168.43.73/getPermisionario.php").trim().equals("null")) {
            if(!conn.search("http://192.168.43.73/getPermisionario.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_PERMISIONARIO);
                c.insetarRegistros("http://192.168.43.73/getPermisionario.php", GestionBD.TABLE_PERMISIONARIO);
            }
            if(!conn.search("http://192.168.43.73/getPuestos.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_PUESTO);
                c.insetarRegistros("http://192.168.43.73/getPuestos.php", GestionBD.TABLE_PUESTO);
            }
            if(!conn.search("http://192.168.43.73/getConfiguraciones.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_CONFIGURACIONES);
                c.insetarRegistros("http://192.168.43.73/getConfiguraciones.php", GestionBD.TABLE_CONFIGURACIONES);
            }
            if(!conn.search("http://192.168.43.73/getCAdministrador.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_ADMINISTRADOR);
                c.insetarRegistros("http://192.168.43.73/getCAdministrador.php", GestionBD.TABLE_C_ADMINISTRADOR);
            }
            if(!conn.search("http://192.168.43.73/getCGirosComerciales.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_GIROS_COMERCIALES);
                c.insetarRegistros("http://192.168.43.73/getCGirosComerciales.php", GestionBD.TABLE_C_GIROS_COMERCIALES);
            }
            if(!conn.search("http://192.168.43.73/getCTianguis.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_TIANGUIS);
                c.insetarRegistros("http://192.168.43.73/getCTianguis.php", GestionBD.TABLE_C_TIANGUIS);
            }
            if(!conn.search("http://192.168.43.73/getCpoblacion.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_POBLACION);
                c.insetarRegistros("http://192.168.43.73/getCpoblacion.php", GestionBD.TABLE_C_POBLACION);
            }
            if(!conn.search("http://192.168.43.73/CZonaTianguis.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_ZONA_TIANGUIS);
                c.insetarRegistros("http://192.168.43.73/CZonaTianguis.php", GestionBD.TABLE_C_ZONA_TIANGUIS);
            }
            // aqui agrege lo mio
            if(!conn.search("http://192.168.43.73/getCInspectores.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_INSPECTORES);
                c.insetarRegistros("http://192.168.43.73/getCInspectores.php", GestionBD.TABLE_C_INSPECTORES);
            }
            if(!conn.search("http://192.168.43.73/CZonaTianguis.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_DEPENDENCIAS);
                c.insetarRegistros("http://192.168.43.73/CZonaTianguis.php", GestionBD.TABLE_C_DEPENDENCIAS);
            }
            msj += "Se actualizo COMPLETAMENTE  ";
        } else
            msj += "No se pudo conectar con el servidor";
    }

    public void eliminaRegistros(String tabla) {
        GestionBD gestion = new GestionBD(getApplicationContext(), "TianguisGDL",null,MainActivity.VERSION);
        SQLiteDatabase db = gestion.getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
            if (c.moveToFirst()) {
                db.delete(tabla, "1", null);
            }
            c.close();
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.e("SQLiteException ", e.getMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.cvActualizar:
                new ActualizarBD().execute("hola");
                break;
            case R.id.cardview_carga:
                Log.v("envias","datos");
                new EnviasDatos().execute();
                break;
        }
        return false;
    }

    public class ActualizarBD extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.v("doin","back " + strings[0]);
            insertar();
            return msj;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.v("termino",result);
            Toast toast = Toast.makeText(getApplicationContext(),msj,Toast.LENGTH_SHORT);
            toast.setGravity(0,0,15);
            toast.show();
        }
    }

    public class EnviasDatos extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            mandarAsistencia();
            actualizarSaldos();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public void mandarAsistencia() {
        String sql = "SELECT * FROM " + GestionBD.TABLE_ASISTENCIA + " where estatus = 'N'";
        Cursor cursor = db.rawQuery(sql,null);
        Log.v("total" , cursor.getCount() + " count");
        if(cursor.moveToFirst()) {
            do {
                ArrayList<NameValuePair> asistencia = new ArrayList<>();
                asistencia.add(new BasicNameValuePair("smlAnno",cursor.getInt(cursor.getColumnIndex("smlAnno")) + " "));
                asistencia.add(new BasicNameValuePair("vchSemanas",cursor.getString(cursor.getColumnIndex("vchSemanas"))));
                asistencia.add(new BasicNameValuePair("smlTianguis",cursor.getInt(cursor.getColumnIndex("smlTianguis")) + " "));
                asistencia.add(new BasicNameValuePair("iPermisionar",cursor.getInt(cursor.getColumnIndex("iPermisionar")) + " "));
                asistencia.add(new BasicNameValuePair("tynEstado",cursor.getInt(cursor.getColumnIndex("tynEstado")) + " "));
                asistencia.add(new BasicNameValuePair("puesto",cursor.getInt(cursor.getColumnIndex("puesto")) + " "));

                JSONObject jo;

                jo = jparser.realizarHttpRequest("http://192.168.43.73/insertAsistencias.php", "GET", asistencia);

                try {
                    Log.v("dato1",cursor.getInt(cursor.getColumnIndex("id")) + cursor.getColumnName(0));
                    if(jo.getInt("estatus") == 1) {
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", "S");
                        Log.v("update",db.update(GestionBD.TABLE_ASISTENCIA, cv, "id = " + cursor.getInt(0), null) + " update");
                    }
                }catch(JSONException e) {
                    System.err.println(e.getMessage());
                }

            } while(cursor.moveToNext());
        }
    }

    public void actualizarSaldos() {
        String sql = "SELECT * FROM " + GestionBD.TABLE_PERMISIONARIO + " where estatus = 'N'";
        Cursor cursor = db.rawQuery(sql,null);
        Log.v("total saldo" , cursor.getCount() + " count");
        if(cursor.moveToFirst()) {
            do {
                ArrayList<NameValuePair> asistencia = new ArrayList<>();
                asistencia.add(new BasicNameValuePair("saldo",cursor.getDouble(cursor.getColumnIndex("saldo")) + ""));
                asistencia.add(new BasicNameValuePair("id",cursor.getInt(cursor.getColumnIndex("id")) + ""));

                JSONObject jo;

                jo = jparser.realizarHttpRequest("http://192.168.43.73/updateSaldo.php", "GET", asistencia);

                try {
                    Log.v("dato saldo",cursor.getInt(cursor.getColumnIndex("id")) + " " + cursor.getColumnName(0));
                    if(jo.getInt("estatus") == 1) {
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", "S");
                        Log.v("update",db.update(GestionBD.TABLE_PERMISIONARIO, cv, "id = " + cursor.getInt(0), null) + " update");
                    }
                }catch(JSONException e) {
                    System.err.println(e.getMessage());
                }

            } while(cursor.moveToNext());
        }
    }

}
