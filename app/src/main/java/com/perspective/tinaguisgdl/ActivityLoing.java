package com.perspective.tinaguisgdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

public class ActivityLoing extends AppCompatActivity {

    private List<String> Inspectores = null;
    private ArrayAdapter<String> adapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loing);

        Button btn_Login = findViewById(R.id.button_login_ingresar);


        Spinner spDireccion = findViewById(R.id.Spinner_Direccion);
        spDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLoing.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
