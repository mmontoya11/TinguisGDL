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
import android.widget.Toast;

import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.Tianguis;

import java.util.ArrayList;
import java.util.List;

public class ActivityAsistencia extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spTianguis,spPermisionario;
    private GestionBD gestionBD = null;
    private List<Tianguis> tianguis = null;
    private List<String> tia = null;
    private List<Integer> idTia = null;
    private ArrayAdapter<String> adapter = null,adapterId = null;

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

        spTianguis.setOnItemSelectedListener(this);
        spPermisionario.setOnItemSelectedListener(this);

        gestionBD = new GestionBD(getApplicationContext(),"TianguisGDL",null,MainActivity.VERSION);
        SQLiteDatabase db = gestionBD.getReadableDatabase();

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

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,tia);

        spTianguis.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spTianguis:
                if(position > 0) {

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
}
