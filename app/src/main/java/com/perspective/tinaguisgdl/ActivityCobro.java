package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.perspective.tinaguisgdl.DB.GestionBD;

public class ActivityCobro extends AppCompatActivity implements View.OnClickListener {
     public TextView tvTianguis,tvNombre,tvCosto,tvFecha,tvMetro,tvCobro;
     public  Button btnQRscanner;
     private String tianguis = "",nombre = "",fecha = "";
     private GestionBD gestion = null;
     private SQLiteDatabase db;
     private int iTianguis = 0;
     private double metros = 0d,total = 0d,costo = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);
        tvTianguis = findViewById(R.id.tvTianguis);
        tvNombre = findViewById(R.id.tvNombre);
        tvCosto = findViewById(R.id.tvCosto);
        tvFecha = findViewById(R.id.tvFecha);
        tvMetro = findViewById(R.id.tvMetros);
        tvCobro = findViewById(R.id.tvCobro);
        btnQRscanner = findViewById(R.id.btn_nuevoCobro);

        btnQRscanner.setOnClickListener(this);

        gestion = new GestionBD(getApplicationContext(),"TianguisGDL",null,MainActivity.VERSION);

        db = gestion.getReadableDatabase();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                iTianguis = 0;
                String contents = data.getStringExtra("SCAN_RESULT").trim();
                System.err.println(contents);
                String [] dato = contents.split("\\|");
                System.err.println(contents.split("\\|")[3] + " da ");
                if (dato.length > 0) {
                    iTianguis = Integer.parseInt(dato[2]);
                    tianguis = consultarTianguis(iTianguis);
                    tvTianguis.setText(tianguis);
                    nombre = dato[4].trim();
                    tvNombre.setText(nombre);
                    metros = Double.parseDouble(dato[0]);
                    tvMetro.setText(dato[0]);
                    fecha = dato[1];
                    tvFecha.setText(fecha);
                    costo = consultarCosto();
                    tvCosto.setText(String.valueOf(costo));
                    total = costo * Double.parseDouble(dato[0]);
                    tvCobro.setText(String.valueOf(total));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_nuevoCobro:
                Log.i("boton","entre");
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                intent.putExtra("PROMPT_MESSAGE", "Escanear codigo QR");
                startActivityForResult(intent, 0);
                break;
        }

    }

    public String consultarTianguis(int idTianguis) {
        String tianguis = "";
        String sql = "select nombre from " + GestionBD.TABLE_C_TIANGUIS + " where id = " + idTianguis;
        Log.v("sql",sql);
        Cursor cursor = null;
        cursor =  db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            tianguis = cursor.getString(cursor.getColumnIndex("nombre"));
        }
        return tianguis;
    }

    public double consultarCosto() {
        double costo = 0d;
        String sql = "select costo_m from " + GestionBD.TABLE_CONFIGURACIONES;
        Log.v("sql",sql);
        Cursor cursor = null;
        cursor =  db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            costo = cursor.getDouble(cursor.getColumnIndex("costo_m"));
        }
        return costo;
    }
}
