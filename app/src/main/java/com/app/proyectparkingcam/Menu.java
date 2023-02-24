package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnRgEntrada = findViewById(R.id.btnRegistroEntrada);
        btnRgEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this, RegistroUsuarios.class);
                startActivity(it);
            }
        });

        Button btnRgSalida = findViewById(R.id.btnRegistroSalida);
        btnRgSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this, RegistroSalida.class);
                startActivity(it);
            }
        });
    }
}