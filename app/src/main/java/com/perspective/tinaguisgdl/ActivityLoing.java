package com.perspective.tinaguisgdl;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

        final EditText etContraseña = findViewById(R.id.EditText_login_contraseña);
        Button btn_Login = findViewById(R.id.button_login_ingresar);
        Spinner spDireccion = findViewById(R.id.Spinner_Direccion);
        final Spinner spInspector = findViewById(R.id.Spinner_Inspector);
        GestionBD gestionBD = new GestionBD(getApplicationContext(), "TianguisGDL", null, MainActivity.VERSION);
        final SQLiteDatabase db = gestionBD.getReadableDatabase();



        Inspectores = NombresInspectores(db);
        adapterInspectores = new ArrayAdapter<>(this, R.layout.spinner_dropdown_layout,Inspectores);
        spInspector.setAdapter(adapterInspectores);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Contraseña = etContraseña.getText().toString();
                Log.e("Contraseña", Contraseña);

                if(!Contraseña.isEmpty()){
                    Intent intent = new Intent(ActivityLoing.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

    public ArrayList<String> NombresInspectores(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from c_inspectores",null);
        ArrayList<String> nombreInspectores = new ArrayList<>();
        String nombre;

        while (cursor.moveToNext()){
            nombre = cursor.getString(2) +" "+ cursor.getString(3) + " "+cursor.getString(4) ;
            nombreInspectores.add(nombre.toUpperCase());
            Log.e("Nombre inspectore.add", nombre);

        }

        return nombreInspectores;
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
}
