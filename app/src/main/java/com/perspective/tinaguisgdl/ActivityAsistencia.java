package com.perspective.tinaguisgdl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.Tianguis;
import java.util.ArrayList;
import java.util.Calendar;
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
    private List<Integer> idPermisionario;
    private Calendar calendar = null;
    private String fecha = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);


        final Context context = getApplicationContext();
        Button btn_AdminComer = findViewById(R.id.btn_administrar);
        ImageView btn_BuscarComerciante = findViewById(R.id.btn_serch);

        btn_BuscarComerciante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAsistencia.this,"Buscando comerciante",Toast.LENGTH_LONG).show();
            }
        });


        btn_AdminComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdminComer();
            }
        });

        spTianguis = findViewById(R.id.spTianguis);
        spPermisionario = findViewById(R.id.spPermisionario);
        tvTianguis = findViewById(R.id.tvTianguis);
        tvFecha = findViewById(R.id.tvFecha);
        tvNombre = findViewById(R.id.tvPermisionario);
        tvGiro = findViewById(R.id.tvGiro);
        tvMetros = findViewById(R.id.tvMetros);

        tvTianguis.setText("");
        tvFecha.setText("");
        tvNombre.setText("");
        tvGiro.setText("");
        tvMetros.setText("");


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
        idPermisionario = new ArrayList<Integer>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,tia);
        adapterC = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,permisionario);

        spTianguis.setAdapter(adapter);
        spPermisionario.setAdapter(adapterC);

        calendar = Calendar.getInstance();
        fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);

        tvFecha.setText(fecha);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spTianguis:
                if(position > 0) {
                    consultarComerciante(idTia.get(position));
                    tvTianguis.setText("Tianguis " + spTianguis.getSelectedItem().toString() + " del ");
                }
                break;

            case R.id.spPermisionario:
                if(position > 0) {
                    Log.v("spPermisionario",position + "");
                    datosPermisionario(idTia.get(spTianguis.getSelectedItemPosition()),idPermisionario.get(position));
                }
                break;

            default:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDialogAdminComer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAsistencia.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_admin_comerciante, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
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
        idPermisionario.clear();
        idPermisionario.add(0);
        if(cursor.moveToFirst()) {
            do {
                permisionario.add(cursor.getString(cursor.getColumnIndex("nombres")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")));
                idPermisionario.add(cursor.getInt(cursor.getColumnIndex("id")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapterC.notifyDataSetChanged();
        spPermisionario.setSelection(0);

    }

    public void datosPermisionario(int idTianguis , int idPermisionario) {

        String sql = "SELECT distinct a.id,a.nombres,a.apellidoP,a.apellidoM,b.smmLONGITUD,d.vchGiroComercial FROM " + GestionBD.TABLE_PERMISIONARIO + " a " +
                "join " + gestionBD.TABLE_PUESTO + " b on a.id=b.iPERMISIO " +
                "join " + gestionBD.TABLE_C_TIANGUIS + " c on b.smlTIANGUIS=c.id " +
                "join " + GestionBD.TABLE_C_GIROS_COMERCIALES + " d on b.smlGIRO1=d.id " +
                "where c.id = " + idTianguis + " and a.id = " + idPermisionario;
        Log.v("sql",sql);
        Cursor cursor = this.db.rawQuery(sql,null);
        Log.v("total",cursor.getCount() + " total");
        tvNombre.setText("");
        tvGiro.setText("");
        tvMetros.setText("");
        if(cursor.moveToFirst()){
            do{
                Log.v("datos",cursor.getString(cursor.getColumnIndex("id")));
                tvNombre.setText(cursor.getString(cursor.getColumnIndex("nombres")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")) + " "+ cursor.getString(cursor.getColumnIndex("apellidoM")));
                tvGiro.setText(cursor.getString(cursor.getColumnIndex("vchGiroComercial")));
                tvMetros.setText("" + cursor.getDouble(cursor.getColumnIndex("smmLONGITUD")));
            }while (cursor.moveToNext());
        }
    }

}
