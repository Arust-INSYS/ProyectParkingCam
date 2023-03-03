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

public class MainActivity extends AppCompatActivity {


    TextView txtPlazasB1,txtPlazasB2,txtNumPlazas,txtRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        txtPlazasB1 = findViewById(R.id.txtIdPlazas);
        txtNumPlazas = findViewById(R.id.txtNumPlazas);
        txtRegistro = findViewById(R.id.txtRegistro);*/
        Button btnInicio = findViewById(R.id.btnLogin);


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Menu.class);
                startActivity(it);
                //LeerWs();
            }
        });

    }
/*
    private void LeerWs() {

        String url = "https://19b5-181-211-10-245.sa.ngrok.io/api/block/search/1";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtPlazasB1.setText(jsonObject.getString("id_bloque"));
                    String plazas = jsonObject.getString("plazas");
                    txtRegistro.setText(plazas);
                    String registro = jsonObject.getString("registro");
                    txtRegistro.setText(registro);

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
    }*/
}