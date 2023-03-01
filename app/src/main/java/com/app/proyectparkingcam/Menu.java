package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {
    TextView txtPlazasB1,txtPlazasB2,txtNumPlazas,txtRegistro;
    Button btnEnviar;
    Button btnEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtPlazasB1 = findViewById(R.id.txtIdPlazas);
        txtNumPlazas = findViewById(R.id.txtNumPlazas);
        txtRegistro = findViewById(R.id.txtRegistro);
        btnEnviar = findViewById(R.id.btnRegistroEntrada);
        //btnEntrada = findViewById(R.id.btnRegistroEntrada);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LeerWs();
                //enviarWs( txtBody.getText().toString(), txtTitle.getText().toString());
                //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //eliminarWs();
                Intent i = new Intent(Menu.this,RegistroEntrada.class);
                startActivity(i);
            }
        });
        /*
        Button btnRgEntrada = findViewById(R.id.btnRegistroEntrada);
        btnRgEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(Menu.this, RegistroUsuarios.class);
                startActivity(it);
            }
        });*/

        Button btnRgSalida = findViewById(R.id.btnRegistroSalida);
        btnRgSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this, RegistroSalida.class);
                startActivity(it);
            }
        });
    }

    private void LeerWs() {

        String url = "https://8052-45-236-151-105.sa.ngrok.io/api/bloque/search/1 ";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtPlazasB1.setText(jsonObject.getString("id_bloque"));
                    String plazas = jsonObject.getString("plazas");
                    txtRegistro.setText(plazas);
                    //String registro = jsonObject.getString("registro");
                    //txtRegistro.setText(registro);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}