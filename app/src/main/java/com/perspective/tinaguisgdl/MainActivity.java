package com.perspective.tinaguisgdl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.perspective.tinaguisgdl.DB.Conexion;
import com.perspective.tinaguisgdl.DB.GestionBD;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Conexion conn = new Conexion();
    private Conexion c;
    public static final int VERSION  = 1;
    private String msj = "";
    private CardView cvActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Asistencia = findViewById(R.id.btn_Asistencia);
        CardView Cardview_Faltas = findViewById(R.id.cardview);
        cvActualizar = findViewById(R.id.cvActualizar);
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

        c = new Conexion(getApplicationContext());

        btn_Asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityAsistencia.class);
                startActivity(intent);
            }
        });

    }

    public void insertar() {
        msj = "";
        if (!conn.search("http://192.168.15.7/getPermisionario.php").trim().equals("null")) {
            if(!conn.search("http://192.168.15.7/getPermisionario.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_PERMISIONARIO);
                c.insetarRegistros("http://192.168.15.7/getPermisionario.php", GestionBD.TABLE_PERMISIONARIO);
            }
            if(!conn.search("http://192.168.15.7/getPuestos.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_PUESTO);
                c.insetarRegistros("http://192.168.15.7/getPuestos.php", GestionBD.TABLE_PUESTO);
            }
            if(!conn.search("http://192.168.15.7/getConfiguraciones.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_CONFIGURACIONES);
                c.insetarRegistros("http://192.168.15.7/getConfiguraciones.php", GestionBD.TABLE_CONFIGURACIONES);
            }
            if(!conn.search("http://192.168.15.7/getCAdministrador.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_ADMINISTRADOR);
                c.insetarRegistros("http://192.168.15.7/getCAdministrador.php", GestionBD.TABLE_C_ADMINISTRADOR);
            }
            if(!conn.search("http://192.168.15.7/getCGirosComerciales.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_GIROS_COMERCIALES);
                c.insetarRegistros("http://192.168.15.7/getCGirosComerciales.php", GestionBD.TABLE_C_GIROS_COMERCIALES);
            }
            if(!conn.search("http://192.168.15.7/getCTianguis.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_TIANGUIS);
                c.insetarRegistros("http://192.168.15.7/getCTianguis.php", GestionBD.TABLE_C_TIANGUIS);
            }
            if(!conn.search("http://192.168.15.7/getCpoblacion.php").trim().equals("null")) {
                eliminaRegistros(GestionBD.TABLE_C_POBLACION);
                c.insetarRegistros("http://192.168.15.7/getCpoblacion.php", GestionBD.TABLE_C_POBLACION);
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

}
