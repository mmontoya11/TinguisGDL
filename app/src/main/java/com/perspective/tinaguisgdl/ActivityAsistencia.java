package com.perspective.tinaguisgdl;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.bixolon.printer.BixolonPrinter;
import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.Asistencia;
import com.perspective.tinaguisgdl.Model.DialogManager;
import com.perspective.tinaguisgdl.Model.Tianguis;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ActivityAsistencia extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static Spinner spTianguis,spPermisionario;
    private GestionBD gestionBD = null;
    private List<Tianguis> tianguis = null;
    private List<String> tia = null;
    private List<Integer> idTia = null;
    private ArrayAdapter<String> adapter = null,adapterC = null;
    private SQLiteDatabase db = null;
    private List<String> permisionario = null;
    private TextView tvTianguis,tvFecha,tvNombre,tvGiro,tvMetros;
    private List<Integer> idPermisionario;
    private static Calendar calendar = null;
    private String mConnectedDeviceName = "";
    private static String fecha = "",nombre = "",giro = "",puesto = "",inspector = "";
    public static BixolonPrinter mBixolonPrinter;
    private Button btnImprimir;
    private static int idTianguis = 0,idPuesto = 0,desc1 = 0,idPermisio = 0;
    private Bitmap bm;
    private static Bitmap bm1;
    private static double metros = 0,subtotal = 0d,total = 0d,costo = 0d,saldo = 0d,saldoa = 0;
    private int anno = 0;
    private Asistencia asistencia = null;
    private CardView CardView_permisionario;
    private CardView CardView_datos_permisionario;
    private static NumberFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        inspector = getIntent().getExtras().getString("inspector");


        final Context context = getApplicationContext();
        Button btn_AdminComer = findViewById(R.id.btn_rolero);
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
        btnImprimir = findViewById(R.id.btnImprimir);
        CardView_permisionario = findViewById(R.id.cardview_spinner_permisionario);
        CardView_datos_permisionario = findViewById(R.id.cardview_datos_permisionario);

        tvTianguis.setText("");
        tvFecha.setText("");
        tvNombre.setText("");
        tvGiro.setText("");
        tvMetros.setText("");


        spTianguis.setOnItemSelectedListener(this);
        spPermisionario.setOnItemSelectedListener(this);
        btnImprimir.setOnClickListener(this);

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
        cursor.close();

        tianguis = gestionBD.getAllTianguis("",db);
        tia = new ArrayList<>();
        idTia = new ArrayList<>();

        tia.add("Seleccione su tianguis");
        idTia.add(0);

        for (int i = 0;i<tianguis.size();i++){
            Log.v("tianguis",tianguis.get(i).getNombre() + " " + tianguis.get(i).getId());
            tia.add(tianguis.get(i).getNombre());
            idTia.add(tianguis.get(i).getId());
        }

        permisionario = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_color_layout,tia);
        adapterC = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_dropdown_layout,permisionario);
        idPermisionario = new ArrayList<>();



        spTianguis.setAdapter(adapter);
        spPermisionario.setAdapter(adapterC);

        calendar = Calendar.getInstance();
        fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);

        tvFecha.setText(fecha);

        bm = BitmapFactory.decodeResource(getResources(), R.drawable.escudo);

        bm1 = bm;

        costo = gestionBD.getCosto("",db);

        anno = calendar.get(Calendar.YEAR);

        format = NumberFormat.getCurrencyInstance(Locale.CANADA);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spTianguis:
                if(position > 0) {
                    consultarComerciante(idTia.get(position));
                    tvTianguis.setText("Tianguis " + spTianguis.getSelectedItem().toString() + " del ");
                    idTianguis = idTia.get(position);
                    CardView_permisionario.setVisibility(View.VISIBLE);
                    CardView_datos_permisionario.setVisibility(View.GONE);
                }
                break;

            case R.id.spPermisionario:
                if(position > 0) {
                    datosPermisionario(idTia.get(spTianguis.getSelectedItemPosition()),idPermisionario.get(position));
                    CardView_datos_permisionario.setVisibility(View.VISIBLE);
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        permisionario.add("Seleccione su comerciante");
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

        String sql = "SELECT distinct a.id,a.nombres,a.apellidoP,a.apellidoM,b.smmLONGITUD,d.vchGiroComercial,b.id as idP,b.iDescuento,e.smlZonaTianguis,e.chZonaTianguis,a.saldo FROM " + GestionBD.TABLE_PERMISIONARIO + " a " +
                "join " + gestionBD.TABLE_PUESTO + " b on a.id=b.iPERMISIO " +
                "join " + gestionBD.TABLE_C_TIANGUIS + " c on b.smlTIANGUIS=c.id " +
                "join " + GestionBD.TABLE_C_GIROS_COMERCIALES + " d on b.smlGIRO1=d.id " +
                "join " + GestionBD.TABLE_C_ZONA_TIANGUIS + " e on b.iZonaT=e.id " +
                "where c.id = " + idTianguis + " and a.id = " + idPermisionario;
        Log.v("sql",sql);
        Cursor cursor = this.db.rawQuery(sql,null);
        tvNombre.setText("");
        tvGiro.setText("");
        tvMetros.setText("");
        idPuesto = 0;
        desc1 = 0;
        metros = 0;
        puesto = "";
        saldo = 0;
        idPermisio = 0;
        if(cursor.moveToFirst()){
            do{
                tvNombre.setText(cursor.getString(cursor.getColumnIndex("nombres")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")) + " "+ cursor.getString(cursor.getColumnIndex("apellidoM")));
                tvGiro.setText(cursor.getString(cursor.getColumnIndex("vchGiroComercial")));
                tvMetros.setText("" + cursor.getDouble(cursor.getColumnIndex("smmLONGITUD")));
                idPuesto = cursor.getInt(cursor.getColumnIndex("idP"));
                desc1 = cursor.getInt(cursor.getColumnIndex("iDescuento"));
                metros = cursor.getDouble(cursor.getColumnIndex("smmLONGITUD"));
                nombre = cursor.getString(cursor.getColumnIndex("nombres")) + " " + cursor.getString(cursor.getColumnIndex("apellidoP")) + " "+ cursor.getString(cursor.getColumnIndex("apellidoM"));
                giro = cursor.getString(cursor.getColumnIndex("vchGiroComercial"));
                puesto = "Zona: " + cursor.getString(cursor.getColumnIndex("smlZonaTianguis")) + " Linea: " + cursor.getString(cursor.getColumnIndex("chZonaTianguis"));
                saldo = cursor.getDouble(cursor.getColumnIndex("saldo"));
                idPermisio = cursor.getInt(cursor.getColumnIndex("id"));
                Log.i("desc",desc1 + "");
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnImprimir:

                subtotal = Double.parseDouble(tvMetros.getText().toString())*costo;
                total = subtotal;

                mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
                mBixolonPrinter.findBluetoothPrinters();

                AlertDialog dialog = null;

                showQrCodeDialog(dialog, ActivityAsistencia.this, 0);

                asistencia = new Asistencia(anno,idTianguis,idPermisio,1,idPuesto,fecha,"N");

                if(gestionBD.consultarAsistencia(asistencia,db) == 0) {
                    saldoa = saldo;
                    Log.v("entro","if");
                    /*if(saldo > 0) {
                        if((saldo - total) > 0)
                            saldo -= total;
                         else
                            saldo = 0;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put("saldo",saldo);
                    cv.put("estatus","N");
                    System.err.print(db.update(gestionBD.TABLE_PERMISIONARIO,cv,"id = " + idPermisio,null) + " update");*/
                    gestionBD.insertarAsistencia(db, asistencia);
                }
                else {
                    Log.v("entro","no inserto, hay un registro");
                    System.err.print("no inserto, hay un registro");
                }

                break;
            default:

                    break;
        }
    }

    public final Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {

                case BixolonPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonPrinter.KEY_STRING_DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    return true;

                case BixolonPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog(ActivityAsistencia.this, (Set<BluetoothDevice>) msg.obj);
                    }
                    return true;

                case BixolonPrinter.MESSAGE_STATE_CHANGE:

                    if(msg.arg1 == BixolonPrinter.STATE_CONNECTED) {

                    }
                    break;
                case BixolonPrinter.STATE_NONE:
                    break;
                case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
                    //mBixolonPrinter.disconnect();
                    //mHandler.sendEmptyMessage(0);
                    break;
            }


            return true;
        }
    });

     static void showQrCodeDialog(AlertDialog dialog, final Context context,final int desc) {
        calendar = Calendar.getInstance();
        if (dialog == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_print_qrcode, null);

            dialog = new AlertDialog.Builder(context).setView(layout).setTitle("QR Code")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String data = metros+"|"+fecha + "|"+idTianguis + "|" + idPuesto + "|"  + nombre + "|" + idPermisio;


                            mBixolonPrinter.setSingleByteFont(BixolonPrinter.CODE_PAGE_1252_LATIN1);

                            mBixolonPrinter.setPrintDirection(BixolonPrinter.DIRECTION_0_DEGREE_ROTATION);
                            mBixolonPrinter.setAbsoluteVerticalPrintPosition(200);
                            mBixolonPrinter.setAbsolutePrintPosition(120);
                            mBixolonPrinter.printBitmap(bm1, BixolonPrinter.ALIGNMENT_CENTER, 200, 65, false);

                            mBixolonPrinter.printText("Municipio de Guadalajara\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Fecha: " + fecha + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) +  "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Inspector: " + inspector +  "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Tianguis: " + spTianguis.getSelectedItem().toString() + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Comerciante: " + nombre + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Giro: " + giro + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Puesto: " + puesto + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Metros: " + format.format(metros) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Costo metro lineal: " + format.format(costo) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Subtotal: " + format.format(subtotal) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Descuento: " + format.format(desc1) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Total: " + format.format(total) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            if(saldo > 0) {
                                mBixolonPrinter.printText("Saldo a favor: " + format.format(saldoa) + "\n",
                                        BixolonPrinter.ALIGNMENT_LEFT,
                                        BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                        BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                        false);

                                /*if((saldo) > 0) {

                                    mBixolonPrinter.printText("Saldo despues de cobro: " + saldo + "\n",
                                            BixolonPrinter.ALIGNMENT_LEFT,
                                            BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                            BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                            false);

                                } else {
                                    saldo = 0;
                                    mBixolonPrinter.printText("Saldo despues de cobro: 0 \n",
                                            BixolonPrinter.ALIGNMENT_LEFT,
                                            BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                            BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                            false);
                                }*/
                            }

                            mBixolonPrinter.printQrCode(data, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.QR_CODE_MODEL2, 8, true);
                            mBixolonPrinter.lineFeed(3, false);


                            mBixolonPrinter.disconnect();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
        }
        dialog.show();
    }



}
