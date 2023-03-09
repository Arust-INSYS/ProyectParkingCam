package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EscanearPlaca extends AppCompatActivity {

    EditText txtPlacaBuscar,txtIdRegistroE,txtObservaciones,txtUsuario,txtBloque,txtCondicion,txtBuscarTicket;

    TextView txtTicket,txtPlaca,txtMarca,txtNombrePersona,txtColor,txtIdPersona,t2,txtIdVehiculo2,txtFecha,txtHoraEntrada,txtEstado;

    Button btnBuscar,btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_placa);
        txtTicket = findViewById(R.id.txtTicket);
        txtIdVehiculo2 = findViewById(R.id.txtIdVehiculo2);
        txtBuscarTicket = findViewById(R.id.txtBuscarTicket);
        t2 = findViewById(R.id.t2);
        txtPlacaBuscar = findViewById(R.id.txtPlacaBuscar);
        txtPlaca = findViewById(R.id.txtPlacaR);
        txtMarca = findViewById(R.id.txtMarca);
        txtNombrePersona = findViewById(R.id.txtNombrePersona);
        //txtColor = findViewById(R.id.txtColor);
        txtIdPersona = findViewById(R.id.txtIdPersona);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtIdRegistroE = findViewById(R.id.txtIdRegistroE);
        txtFecha = findViewById(R.id.txtFecha);
        txtHoraEntrada = findViewById(R.id.txtHoraEntrada);
        txtObservaciones = findViewById(R.id.txtObservaciones);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtBloque = findViewById(R.id.txtBloque);
        txtBloque.setVisibility(View.GONE);
        txtCondicion = findViewById(R.id.txtCondicion);
        btnGuardar = findViewById(R.id.btnGuardar);

        //txtEstado.setText("A");
        txtCondicion.setText("Entrada");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        txtFecha.setText(fecha);

        // Obtener la hora actual
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String time = String.format("%02d:%02d", hour, minute);
        txtHoraEntrada.setText(time);

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
                BuscarPorCampo();
                txtPlacaBuscar.setText("");
                txtBuscarTicket.setText("");

            }
        });
    }


    private void LeerVehiculoPorPlaca() {
        String id=txtPlacaBuscar.getText().toString();
        String url = "https://83e7-45-236-151-105.sa.ngrok.io/api/vehiculo/placa/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculo2.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlaca.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    txtTicket.setText(jsonObject.getString("ticket"));

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txtIdPersona.setText(id_persona);

                    String nombrePer = relatedObject.getString("nombre");
                    String apellidoPer = relatedObject.getString("apellido");
                    txtNombrePersona.setText(nombrePer+" "+apellidoPer);

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

    private void LeerVehiculoPorTicket() {
        String ticket=txtBuscarTicket.getText().toString();
        String url = "https://83e7-45-236-151-105.sa.ngrok.io/api/vehiculo/ticket/"+ticket;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculo2.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlaca.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    txtTicket.setText(jsonObject.getString("ticket"));

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txtIdPersona.setText(id_persona);

                    String nombrePer = relatedObject.getString("nombre");
                    String apellidoPer = relatedObject.getString("apellido");
                    txtNombrePersona.setText(nombrePer+" "+apellidoPer);

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

    public void BuscarPorCampo(){
        String porPlaca = txtPlacaBuscar.getText().toString();
        String porTicket = txtBuscarTicket.getText().toString();

        if (porPlaca.isEmpty()){
            LeerVehiculoPorTicket();
        }if(porTicket.isEmpty()){
            LeerVehiculoPorPlaca();
        }
    }

    public void GuardarRegistro(){

        String urL ="https://83e7-45-236-151-105.sa.ngrok.io/api/registro/create";
        JSONObject data = new JSONObject();

        try {
            data.put("fecha", txtFecha.getText());
            data.put("hora_entrada", txtHoraEntrada.getText());
            data.put("hora_salida", "-- : --");
            data.put("observaciones", txtObservaciones.getText());
            data.put("usuario", txtUsuario.getText());
            data.put("bloque", txtBloque.getText());
            data.put("condicion", txtCondicion.getText());
            data.put("vehiculo", txtIdVehiculo2.getText());
            //data.put("estado", txtEstado.getText());

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
        txtPlaca.setText("");
        txtMarca.setText("");
        //txtColor = findViewById(R.id.txtColor);
        txtIdPersona.setText("");
        //txtIdRegistroE.setText("");
        txtObservaciones.setText("");
        txtUsuario.setText("");

    }



}