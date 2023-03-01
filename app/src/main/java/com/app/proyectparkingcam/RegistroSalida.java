package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroSalida extends AppCompatActivity {
    EditText txtId, txtPlaza,txtNombre;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida);

        txtId = findViewById(R.id.txtPlaca);
        txtPlaza = findViewById(R.id.txtPropietario);
        txtNombre = findViewById(R.id.txtNombre);
        btnOk = findViewById(R.id.btnSalidaVehiculo);
        /*
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
        });*/

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeerWs();
                //enviarWs3();
            }
        });

    }


    private void enviarWs3()  {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...","Actualizando Datos",false,false);
    try {
        String url = "https://1138-181-211-10-245.sa.ngrok.io/api/bloque/create";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("id_bloque", txtId.getText().toString());
        jsonBody.put("plazas", txtPlaza.getText().toString());
        jsonBody.put("nombre", txtNombre.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta del servidor
                        loading.dismiss();
                        Log.d("RESPONSE", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la solicitud
                        loading.dismiss();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    }

    private void LeerWs() {
        String id=txtId.getText().toString();
        String url = "https://c8d6-45-236-151-105.sa.ngrok.io/api/bloque/search/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtId.setText(jsonObject.getString("id_bloque"));
                    String plazas = jsonObject.getString("plazas");
                    txtPlaza.setText(plazas);
                    String nombre = jsonObject.getString("nombre");
                    txtNombre.setText(nombre);

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