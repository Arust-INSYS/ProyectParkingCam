package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EscanearPlaca extends AppCompatActivity {

    EditText txtId,txtIdRegistroE,txtFecha,txtHoraEntrada,txtHoraSalida,txtObservaciones,txtUsuario,txtBloque;

    TextView txtPlaca,txtMarca,txtModelo,txtColor,txtIdPersona,t2;

    Button btnBuscar,btnGuardar;

    String urL ="https://5558-45-236-151-105.sa.ngrok.io/api/registro/create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_placa);
        t2 = findViewById(R.id.t2);
        txtId = findViewById(R.id.txtIdVehiculo);
        txtPlaca = findViewById(R.id.txtPlacaR);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        //txtColor = findViewById(R.id.txtColor);
        txtIdPersona = findViewById(R.id.txtIdPersona);
        btnBuscar = findViewById(R.id.btnBuscar);

        txtIdRegistroE = findViewById(R.id.txtIdRegistroE);
        txtFecha = findViewById(R.id.txtFecha);
        txtHoraEntrada = findViewById(R.id.txtHoraEntrada);
        txtHoraSalida = findViewById(R.id.txtHoraSalida);
        txtObservaciones = findViewById(R.id.txtObservaciones);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtBloque = findViewById(R.id.txtBloque);
        btnGuardar = findViewById(R.id.btnGuardar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);

        txtFecha.setText(fecha);

        // Obtener la hora actual
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

// Formatear la hora en una cadena
        String time = String.format("%02d:%02d", hour, minute);

// Establecer el texto en el TextView
        txtHoraEntrada.setText(time);

        txtHoraSalida.setText("xx:xx");

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bloques, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "Seleccionado: "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

                txtBloque.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(EscanearPlaca.this, RegistroSalida.class);
                startActivity(it);*/
                //enviarVehi();
                //enviaRegiss();
                GuardarRegistro();
                Borrar();
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
        String url = "https://5558-45-236-151-105.sa.ngrok.io/api/vehiculo/search/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtPlaca.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlaca.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    String modelo = jsonObject.getString("modelo");
                    txtModelo.setText(modelo);

                    //String color = jsonObject.getString("color");
                    //txtColor.setText(color);

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");

                    //String per = persona.getString("persona");
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


    public void GuardarRegistro(){
        JSONObject data = new JSONObject();

        try {
            data.put("fecha", txtFecha.getText());
            data.put("hora_entrada", txtHoraEntrada.getText());
            data.put("hora_salida", txtHoraSalida.getText());
            data.put("observaciones", txtObservaciones.getText());
            data.put("usuario", txtUsuario.getText());
            data.put("bloque", txtBloque.getText());
            data.put("vehiculo", txtId.getText());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // El servidor ha recibido los datos y los ha guardado en el archivo JSON
                        // aquí puedes leer la respuesta y procesarla
                        t2.setText("Guardado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // aquí puedes manejar los errores
                        t2.setText("Page not available");
                    }
                });

// Agregar la solicitud a la cola
        Volley.newRequestQueue(this).add(request);


    }

    public void Borrar(){

        t2.setText("");
        txtId.setText("");
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        //txtColor = findViewById(R.id.txtColor);
        txtIdPersona.setText("");

        txtIdRegistroE.setText("");
        txtObservaciones.setText("");
        txtUsuario.setText("");
        txtBloque.setText("");
    }



}