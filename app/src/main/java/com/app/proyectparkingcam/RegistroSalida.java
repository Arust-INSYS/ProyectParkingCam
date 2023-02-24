package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroSalida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida);

        Button btnlistaV = findViewById(R.id.btnListarVh);
        btnlistaV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegistroSalida.this, EscanearPlaca.class);
                startActivity(it);
            }
        });

        Button btnSlidaVehiculo = findViewById(R.id.btnSalidaVehiculo);
        btnSlidaVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegistroSalida.this, MenuPrincipal.class);
                startActivity(it);
            }
        });
    }
}