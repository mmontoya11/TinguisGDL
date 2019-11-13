package com.perspective.tinaguisgdl;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bixolon.printer.BixolonPrinter;
import com.perspective.tinaguisgdl.Beans.AdapterPayment;
import com.perspective.tinaguisgdl.Beans.ItemPayment;
import com.perspective.tinaguisgdl.DB.GestionBD;
import com.perspective.tinaguisgdl.Model.DialogManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import static com.perspective.tinaguisgdl.ActivityAsistencia.mBixolonPrinter;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ActivityCobro extends AppCompatActivity implements View.OnClickListener {


    ArrayList<ItemPayment> listaPagos;
    private RecyclerView rvPagos;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvManager;


     public  Button btnQRscanner;
     private int idPuesto=0;
     private static String fecha = "",tianguis = "",nombre = "";
     public static GestionBD gestion = null;
     public static SQLiteDatabase db;
     private int iTianguis = 0;
     private static double metros = 0d,total = 0d,costo = 0d,saldo = 0d,saldoa = 0d,cobrol = 0d,subtotal = 0d,desc1 = 0;

     public static BixolonPrinter mBixolonPrinter;
    private static String mConnectedDeviceName = "";
    private Bitmap bm;
    private static Bitmap bm1;
    private static Context context;
    private boolean pago = false;
    private static NumberFormat format;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);
        btnQRscanner = findViewById(R.id.btn_nuevoCobro);

        btnQRscanner.setOnClickListener(this);

        gestion = new GestionBD(getApplicationContext(),"TianguisGDL",null,MainActivity.VERSION);

        db = gestion.getReadableDatabase();

        rvPagos = findViewById(R.id.rvPagos);

        listaPagos = new ArrayList<ItemPayment>();


        //listaPagos.add(new ItemPayment("JULIO PRECIADO LOPEZ","ZONA 2 LINEA B1", "Baratillo", 2.16,900,19.44,19.44));
        /*listaPagos.add(new ItemPayment("Juan","1a1", "Baratillo", 12,42,12,1));
        listaPagos.add(new ItemPayment("Juan","1a1", "Baratillo", 12,42,12,1));
        listaPagos.add(new ItemPayment("Juan","1a1", "Baratillo", 12,42,12,1));
        listaPagos.add(new ItemPayment("Juan","1a1", "Baratillo", 12,42,12,1));
*/
        rvManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) rvManager).setReverseLayout(true);
        ((LinearLayoutManager) rvManager).setStackFromEnd(true);
        rvManager.scrollToPosition(listaPagos.size()+1);
        rvPagos.setLayoutManager(rvManager);

        rvAdapter = new AdapterPayment(this, listaPagos);
        rvPagos.setAdapter(rvAdapter);

        bm = BitmapFactory.decodeResource(getResources(), R.drawable.escudo);

        bm1 = bm;
        context = ActivityCobro.this;
        format = NumberFormat.getCurrencyInstance(Locale.CANADA);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

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
                saldo = 0;
                saldoa = 0;

                if (dato.length > 0) {
                    pago = false;
                    iTianguis = parseInt(dato[2]);
                    tianguis = consultarTianguis(iTianguis);
                    nombre = dato[4].trim();
                    idPuesto = Integer.parseInt(dato[3].trim());
                    metros = parseDouble(dato[0]);
                    fecha = dato[1];
                    costo = consultarCosto();
                    total = costo * parseDouble(dato[0]);
                    saldo = gestion.consultarSalo(parseInt(dato[5]),db);
                    saldoa = saldo;
                    Log.v("saldos",saldo + "");

                    subtotal = total;

                    pago = gestion.consultaPagos(parseInt(dato[5]),fecha,db);

                    if(pago) {
                        Toast toast = Toast.makeText(getApplicationContext(),"Ya se escaneo el Ticket",Toast.LENGTH_LONG);
                        toast.setGravity(0,0,15);
                        toast.show();
                    }

                    if(saldo >= 0) {
                        if((saldo - total) > 0) {
                            cobrol = 0;
                            saldo -= total;
                        }
                        else {
                            cobrol = total - saldo;
                            saldo = 0d;
                        }
                    }

                    Log.v("saldos",saldo + " saldoa " + saldoa + " cobro " + cobrol);

                    listaPagos.add(new ItemPayment(nombre, idPuesto, tianguis,metros,saldoa, cobrol, total,saldo,parseInt(dato[5]),pago,fecha));

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

    public static void showQrCodeDialog(AlertDialog dialog, final Context context) {
        if (dialog == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_print_qrcode, null);

            dialog = new AlertDialog.Builder(context).setView(layout).setTitle("QR Code")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {


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

                            mBixolonPrinter.printText("Fecha: " + fecha  +  "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Tianguis: " + tianguis+ "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Comerciante: " + nombre + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Metros: " + metros + "\n",
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

                            mBixolonPrinter.printText("Total a cobrar: " + format.format(total) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            //if(saldo > 0) {
                                mBixolonPrinter.printText("Saldo antes de cobro: " + format.format(saldoa) + "\n",
                                        BixolonPrinter.ALIGNMENT_LEFT,
                                        BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                        BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                        false);

                            mBixolonPrinter.printText("A cobrar en efectivo: " + format.format(cobrol) + "\n",
                                    BixolonPrinter.ALIGNMENT_LEFT,
                                    BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,
                                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1,
                                    false);

                            mBixolonPrinter.printText("Saldo despues cobro: " + format.format(saldo) + "\n",
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
                            //}

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

    public static final Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {

                case BixolonPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonPrinter.KEY_STRING_DEVICE_NAME);
                    Toast.makeText(context, mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    return true;

                case BixolonPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(context, "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog1(context, (Set<BluetoothDevice>) msg.obj);
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
}
