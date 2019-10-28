package com.perspective.tinaguisgdl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.Tianguis;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ActivityAsistencia extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spTianguis,spPermisionario;
    private GestionBD gestionBD = null;
    private List<Tianguis> tianguis = null;
    private List<String> tia = null;
    private List<Integer> idTia = null;
    private ArrayAdapter<String> adapter = null,adapterC = null;
    private SQLiteDatabase db = null;
    private List<String> permisionario = null;
    private TextView tvTianguis,tvFecha,tvNombre,tvGiro,tvMetros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);


        ImageView imageView = findViewById(R.id.btn_serch);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAsistencia.this,"Esto es una prueba",Toast.LENGTH_LONG).show();
            }
        });

        spTianguis = findViewById(R.id.spTianguis);
        spPermisionario = findViewById(R.id.spPermisionario);
        tvTianguis = findViewById(R.id.tvTianguis);
        tvFecha = findViewById(R.id.tvFecha);

        spTianguis.setOnItemSelectedListener(this);
        spPermisionario.setOnItemSelectedListener(this);

        gestionBD = new GestionBD(getApplicationContext(),"TianguisGDL",null,MainActivity.VERSION);
        db = gestionBD.getReadableDatabase();

        String sql = "select * from " + GestionBD.TABLE_PERMISIONARIO;
        Log.v("sql",sql);
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()) {
            for (int i = 0;i < cursor.getColumnCount();i++) {
                Log.i("columnas",cursor.getColumnName(i));
            }
        }

        tianguis = gestionBD.getAllTianguis("",db);
        tia = new ArrayList<String>();
        idTia = new ArrayList<Integer>();

        tia.add("");
        idTia.add(0);

        for (int i = 0;i<tianguis.size();i++){
            Log.v("tianguis",tianguis.get(i).getNombre() + " " + tianguis.get(i).getId());
            tia.add(tianguis.get(i).getNombre());
            idTia.add(tianguis.get(i).getId());
        }

        permisionario = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,tia);
        adapterC = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,permisionario);

        spTianguis.setAdapter(adapter);
        spPermisionario.setAdapter(adapterC);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spTianguis:
                if(position > 0) {
                    consultarComerciante(idTia.get(position));
                }
                break;

            case R.id.spPermisionario:

                break;

            default:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void consultarComerciante(int idTianguis) {
        String sql = "select a.* from " + GestionBD.TABLE_PERMISIONARIO + " a " +
                "join " + GestionBD.TABLE_PUESTO + " b on a.id=b.iPERMISIO " +
                "join " + GestionBD.TABLE_C_TIANGUIS + " c on b.smlTIANGUIS=c.id " +
                "where c.id = " + idTianguis;
        Log.v("sql",sql);
        Cursor cursor = this.db.rawQuery(sql,null);
        permisionario.clear();
        permisionario.add("");
        if(cursor.moveToFirst()) {
            Log.v("if","entro");
            do {
                permisionario.add(cursor.getString(cursor.getColumnIndex("nombres")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapterC.notifyDataSetChanged();
        spPermisionario.setSelection(0);
    }
}
