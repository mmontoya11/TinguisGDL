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
import android.widget.EditText;
import android.widget.Spinner;

import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.Direccion;
import com.perspective.tinaguisgdl.Model.Inspector;

import java.util.ArrayList;
import java.util.List;

public class ActivityLoing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spDireccion,spInspector;
    private List<String> Inspectores = null;
    private ArrayAdapter<String> adapterInspectores = null,adapter = null;
    private List<Direccion> direcciones;
    private List<String> direccion;
    private List<Integer> idDireccion;
    private GestionBD gestionBD = null;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loing);

        final EditText etContraseña = findViewById(R.id.EditText_login_contraseña);
        Button btn_Login = findViewById(R.id.button_login_ingresar);
        spDireccion = findViewById(R.id.Spinner_Direccion);
        spInspector = findViewById(R.id.Spinner_Inspector);
        gestionBD = new GestionBD(getApplicationContext(), "TianguisGDL", null, MainActivity.VERSION);
        db = gestionBD.getReadableDatabase();

        Inspectores = new ArrayList<>();

        //Inspectores = NombresInspectores(db,1);

        adapterInspectores = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown_layout,Inspectores);
        spInspector.setAdapter(adapterInspectores);


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Contraseña = etContraseña.getText().toString();
                Log.e("Contraseña", Contraseña);

                if(gestionBD.ingresar(db,spInspector.getSelectedItem().toString(),etContraseña.getText().toString())) {
                    Intent intent = new Intent(ActivityLoing.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        direcciones = gestionBD.getAllDireccion("",db);
        direccion = new ArrayList<>();
        idDireccion = new ArrayList<>();

        for (int i = 0;i < direcciones.size();i++) {
            direccion.add(direcciones.get(i).getNombre());
            idDireccion.add(direcciones.get(i).getId());
        }

        adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_layout,direccion);
        spDireccion.setAdapter(adapter);

        spDireccion.setOnItemSelectedListener(this);
    }

    public void NombresInspectores(SQLiteDatabase db,int idDireccion){
        Cursor cursor = db.rawQuery("select * from c_inspectores where c_dependencia_id = " + idDireccion,null);
        ArrayList<String> nombreInspectores = new ArrayList<>();
        String nombre;
        Inspectores.clear();
        while (cursor.moveToNext()){
            nombre = cursor.getString(2) +" "+ cursor.getString(3) + " "+cursor.getString(4) ;
            nombreInspectores.add(nombre.toUpperCase());
            Inspectores.add(nombre.toUpperCase());
            Log.e("Nombre inspectore.add", nombre);
        }
        adapterInspectores.notifyDataSetChanged();
    }
    public boolean LoginValidation(SQLiteDatabase db, String username, String password){
        Log.e("LoginValidation User",username );
        Cursor cursor = db.rawQuery("select * from c_inspectores where nombre = '"+username+"' ",null);
        String TruePass = "";

        while (cursor.moveToNext()){
            TruePass = cursor.getString(5);

        }

        if(TruePass == password){
            return true;
        }else
            return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.Spinner_Direccion:
                Log.v("id d",idDireccion.get(position) + " ");
                //Inspectores.clear();
                /*Inspectores = gestionBD.getAllInspector("c_dependencia_id = " + idDireccion.get(position),db);
                Log.v("inspectores", Inspectores.size() + " total");
                for (int i = 0;i<Inspectores.size();i++) {
                    Log.v("INSPECTOR", Inspectores.get(i));
                }*/
                NombresInspectores(db,idDireccion.get(position));
                //spDireccion.setAdapter(adapter);
                //adapterInspectores.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
