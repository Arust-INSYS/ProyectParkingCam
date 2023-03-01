package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    EditText txtId, txtPlaza;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida);

        txtId = findViewById(R.id.txtfecha);
        txtPlaza = findViewById(R.id.txtEntrada);
        btnOk = findViewById(R.id.btnGuardar);
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
                //LeerWs();
                enviarWs3();
                //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //eliminarWs();
            }
        });

    }

    private void enviarWs(final String id_bloque, final String plazas) {

        String url = "https://8052-45-236-151-105.sa.ngrok.io/api/bloque/create";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegistroSalida.this, "RESULTADO POST = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_bloque", id_bloque.toString());
                params.put("plazas", plazas);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void enviarWs2() {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...","Actualizando Datos",false,false);

        String url = "https://8052-45-236-151-105.sa.ngrok.io/api/bloque/create";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();
                params.put("id_bloque", txtId.getText().toString());
                params.put("plazas", txtPlaza.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void enviarWs3()  {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...","Actualizando Datos",false,false);
    try {
        String url = "https://8052-45-236-151-105.sa.ngrok.io/api/bloque/create";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("id_bloque", txtId.getText().toString());
        jsonBody.put("plazas", txtPlaza.getText().toString());

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
}