package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Console;

public class ActivityCobro extends AppCompatActivity {
     public static TextView prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);
        prueba = findViewById(R.id.TXprueba);
        Button btnQRscanner = findViewById(R.id.btn_nuevoCobro);
        btnQRscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new IntentIntegrator(ActivityCobro.this).initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=  null){
            if(result.getContents() != null){
                prueba.setText(result.getContents());
                Log.e("Exito al escaner", "El escaner trajo algo");

            }else{
                Log.e("Fallo el escaner", "el escaner fallo y trajo "+ result);
            }
        }
    }
}
