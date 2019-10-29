package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityCobro extends AppCompatActivity implements View.OnClickListener {
     public TextView tvTianguis,tvNombre,tvCosto;
     public  Button btnQRscanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);
        tvTianguis = findViewById(R.id.tvTianguis);
        tvNombre = findViewById(R.id.tvNombre);
        tvCosto = findViewById(R.id.tvCosto);
        btnQRscanner = findViewById(R.id.btn_nuevoCobro);

        btnQRscanner.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=  null){
            if(result.getContents() != null){
                prueba.setText(result.getContents());
                Log.e("Exito al escaner", "El escaner trajo algo");

            }else{
                Log.e("Fallo el escaner", "el escaner fallo y trajo "+ result);
            }
        }*/
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
}
