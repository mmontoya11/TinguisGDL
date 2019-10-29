package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.perspective.tinaguisgdl.DB.GestionBD;

import java.util.ArrayList;
import java.util.List;

public class ActivityLoing extends AppCompatActivity {

    private ArrayList Inspectores = null;
    private ArrayAdapter<String> adapterInspectores = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loing);

        Button btn_Login = findViewById(R.id.button_login_ingresar);


        Spinner spDireccion = findViewById(R.id.Spinner_Direccion);

        GestionBD gestionBD = new GestionBD(getApplicationContext(), "TianguisGDL", null, MainActivity.VERSION);
        SQLiteDatabase db = gestionBD.getReadableDatabase();

        String sql = " " + GestionBD.TABLE_C_ADMINISTRADOR;

        Inspectores = NombresInspectores(db);
        adapterInspectores = new ArrayAdapter<>(this, R.layout.spinner_dropdown_layout,Inspectores);
        spDireccion.setAdapter(adapterInspectores);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLoing.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> NombresInspectores(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from c_administrador",null);
        ArrayList<String> nombreInspectores = new ArrayList<>();
        String nombre;

        while (cursor.moveToNext()){
            nombre = cursor.getString(5) +" "+ cursor.getString(4) + " "+cursor.getString(1) ;
            nombreInspectores.add(nombre.toUpperCase());
            Log.e("Nombre inspectore.add", nombre);

        }

        return nombreInspectores;
    }


}
