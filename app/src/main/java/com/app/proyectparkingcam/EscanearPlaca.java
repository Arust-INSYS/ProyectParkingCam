package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EscanearPlaca extends AppCompatActivity {

    EditText txtId;

    TextView txtPlaca,txtMarca,txtModelo,txtColor,txtIdPersona;

    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_placa);

        txtId = findViewById(R.id.txtIdVehiculo);
        txtPlaca = findViewById(R.id.txtPlacaR);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtColor = findViewById(R.id.txtColor);
        txtIdPersona = findViewById(R.id.txtIdPersona);
        btnBuscar = findViewById(R.id.btnBuscar);

        Button btnRegreso = findViewById(R.id.btnRegresar);
        btnRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(EscanearPlaca.this, RegistroSalida.class);
                startActivity(it);*/
                enviarVehi();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerVehiculo();
            }
        });
    }


    private void LeerVehiculo() {
        String id=txtId.getText().toString();
        String url = "https://c8d6-45-236-151-105.sa.ngrok.io/api/vehiculo/search/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtId.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlaca.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    String modelo = jsonObject.getString("modelo");
                    txtModelo.setText(modelo);

                    String color = jsonObject.getString("color");
                    txtColor.setText(color);

                    JSONArray jsonArray = new JSONArray();



                    String id_persona = jsonObject.getString("id_persona");
                    txtIdPersona.setText(id_persona);

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

    private void enviarVehi()  {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...","Actualizando Datos",false,false);
        try {
            String url = "https://c8d6-45-236-151-105.sa.ngrok.io/api/vehiculo/create";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id_vehiculo", "5");
            jsonBody.put("placa", "PCM-6069");
            jsonBody.put("marca", "MAZDA");
            jsonBody.put("modelo", "BT-50");
            jsonBody.put("color", "NEGRO");
            //jsonBody.put("id_persona", "1");

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