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

public class RegistroSalida extends AppCompatActivity {
    MainActivity main = new MainActivity();
    String valor;
    String dominio= main.Dominio();
    EditText txtId, txtPlaza,txtNombre;
    TextView txtEstadoSalida,txtBuscarPorTicket,txtTicketSalida,txt3,txtBuscarPlaca,txtNombrePer,txtFechaSalida,txtHoraSalida2,txtObservacionesSalida,txtIdUsuarioSalida,txtIdBloqueSalida,txtIdVehiculoSalida,txtPlacaSalida,txtIdPersonaSalida,txtCondicionSalida,textViewUser2;
    Button btnBuscarIdRegistro,btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida);
        txtBuscarPorTicket = findViewById(R.id.txtBuscarPorTicket);
        txtTicketSalida = findViewById(R.id.txtTicketSalida);
        txt3 = findViewById(R.id.t3);
        txtBuscarPlaca = findViewById(R.id.txtBuscarPlaca);
        txtPlacaSalida = findViewById(R.id.txtPlacaEntrada);
        txtNombrePer = findViewById(R.id.txtNombrePer);
        txtFechaSalida = findViewById(R.id.txtFechaSalida);
        txtHoraSalida2 = findViewById(R.id.txtHoraSalida2);
        txtObservacionesSalida = findViewById(R.id.txtObservacionesSalida);
        txtIdUsuarioSalida = findViewById(R.id.txtIdUsuarioSalida);
        txtIdBloqueSalida = findViewById(R.id.txtIdBloqueSalida);
        txtIdBloqueSalida.setVisibility(View.GONE);


        txtEstadoSalida = findViewById(R.id.txtEstadoSalida);
        txtEstadoSalida.setText("A");
        txtEstadoSalida.setVisibility(View.GONE);


        txtIdVehiculoSalida = findViewById(R.id.txtIdVehiculoSalida);
        txtIdPersonaSalida = findViewById(R.id.txtIdPersonaSalida);
        txtCondicionSalida = findViewById(R.id.txtCondicionSalida);
        txtId = findViewById(R.id.txtPlaca);
        txtPlaza = findViewById(R.id.txtPropietario);
        txtNombre = findViewById(R.id.txtBuscarPlaca);
        btnGuardar = findViewById(R.id.btnGuardarSalida);
        btnBuscarIdRegistro = findViewById(R.id.btnBuscarIdRegistro);

        //CAMBIOS CÓDIGO ARIEL
        valor=main.Dar_valor();
        txtIdUsuarioSalida.setText(valor);
        txtIdUsuarioSalida.setEnabled(false);
        //OCULTAR
        txtIdUsuarioSalida.setVisibility(View.GONE);
        Log.d("TAG", "EL USUARIO ES:"+valor);
        textViewUser2 = findViewById(R.id.textViewUser2);
        textViewUser2.setText(main.Dar_Nombre());
        //FIN CAMBIOS

        //txtEstadoSalida.setText("A");

        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bloques, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "Seleccionado: "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

                txtIdBloqueSalida.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        txtFechaSalida.setText(fecha);

        // Obtener la hora actual
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String time = String.format("%02d:%02d", hour, minute);
        txtHoraSalida2.setText(time);

        txtCondicionSalida.setText("Salida");


        btnBuscarIdRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarPorCampo();
                txtBuscarPorTicket.setText("");
                txtBuscarPlaca.setText("");
                //BuscarVehiculo();
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarRegistroSalida();
                BorrarCampos();
            }
        });

    }
    /*
    public void EditarRegistro(){

        String id=txtIdRegistro.getText().toString();

        String urL ="https://b3cd-45-236-151-105.sa.ngrok.io/api/registro/update/"+id;
        JSONObject data = new JSONObject();

        try {
            data.put("id_registro", txtIdRegistro.getText());
            data.put("fecha", txtFecha.getText());
            data.put("hora_entrada", txtFechaEntradaSalida.getText());
            data.put("hora_salida", txtHoraSalida2.getText());
            data.put("observaciones", txtObservacionesSalida.getText());
            data.put("usuario", txtIdUsuarioSalida.getText());
            data.put("bloque", txtIdBloqueSalida.getText());
            data.put("vehiculo", txtIdVehiculoSalida.getText());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, urL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // El servidor ha recibido los datos y los ha guardado en el archivo JSON
                        // aquí puedes leer la respuesta y procesarla
                        txt3.setText("Guardado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // aquí puedes manejar los errores
                        txt3.setText("Page not available");
                    }
                });

// Agregar la solicitud a la cola
        Volley.newRequestQueue(this).add(request);


    }*/

   /*
    private void BuscarRegistro() {
        String id=txtIdRegistroSalida1.getText().toString();
        String url = "https://b3cd-45-236-151-105.sa.ngrok.io/api/registro/search/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdRegistro.setText(jsonObject.getString("id_registro"));

                    String fecha = jsonObject.getString("fecha");
                    txtFecha.setText(fecha);

                    String hora_entrada = jsonObject.getString("hora_entrada");
                    txtFechaEntradaSalida.setText(hora_entrada);

                    String hora_salida = jsonObject.getString("hora_salida");
                    txtHoraSalida2.setText(hora_salida);

                    String observaciones = jsonObject.getString("observaciones");
                    txtObservacionesSalida.setText(observaciones);

                    JSONObject relatedObject = jsonObject.getJSONObject("usuario");
                    String id_usuario = relatedObject.getString("id_usuario");
                    txtIdUsuarioSalida.setText(id_usuario);

                    JSONObject relatedObject1 = jsonObject.getJSONObject("bloque");
                    String id_bloque = relatedObject1.getString("id_bloque");
                    txtIdBloqueSalida.setText(id_bloque);

                    JSONObject relatedObject2 = jsonObject.getJSONObject("vehiculo");
                    String id_vehiculo = relatedObject2.getString("id_vehiculo");
                    txtIdVehiculoSalida.setText(id_vehiculo);

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


    private void BuscarVehiculoPorPlaca() {
        String placa=txtBuscarPlaca.getText().toString();
        String url =dominio+"/api/vehiculo/placa/"+placa;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculoSalida.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlacaSalida.setText(placa);

                    txtTicketSalida.setText(jsonObject.getString("ticket"));

                    //String marca = jsonObject.getString("marca");
                    //txtMarca.setText(marca);

                    //String color = jsonObject.getString("color");
                    //txtColor.setText(color);

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txtIdPersonaSalida.setText(id_persona);

                    String nombrePer = relatedObject.getString("nombre");
                    String apellidoPer = relatedObject.getString("apellido");
                    txtNombrePer.setText(nombrePer+" "+apellidoPer);

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

    private void BuscarVehiculoPorTicket() {
        String ticket=txtBuscarPorTicket.getText().toString();
        String url = dominio+"/api/vehiculo/ticket/"+ticket;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculoSalida.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlacaSalida.setText(placa);

                    txtTicketSalida.setText(jsonObject.getString("ticket"));

                    //String marca = jsonObject.getString("marca");
                    //txtMarca.setText(marca);

                    //String color = jsonObject.getString("color");
                    //txtColor.setText(color);

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txtIdPersonaSalida.setText(id_persona);

                    String nombrePer = relatedObject.getString("nombre");
                    String apellidoPer = relatedObject.getString("apellido");
                    txtNombrePer.setText(nombrePer+" "+apellidoPer);

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


    public void GuardarRegistroSalida(){

        String urL =dominio+"/api/registro/create";
        JSONObject data = new JSONObject();

        try {
            data.put("fecha", txtFechaSalida.getText());
            data.put("hora_entrada", "-- : --");
            data.put("hora_salida", txtHoraSalida2.getText());
            data.put("observaciones", txtObservacionesSalida.getText());
            data.put("usuario", txtIdUsuarioSalida.getText());
            data.put("bloque", txtIdBloqueSalida.getText());
            data.put("condicion", txtCondicionSalida.getText());
            data.put("vehiculo", txtIdVehiculoSalida.getText());
            data.put("estado", txtEstadoSalida.getText());


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // El servidor ha recibido los datos y los ha guardado en el archivo JSON
                        // aquí puedes leer la respuesta y procesarla
                        txt3.setText("Guardado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // aquí puedes manejar los errores
                        txt3.setText("Page not available");
                    }
                });

// Agregar la solicitud a la cola
        Volley.newRequestQueue(this).add(request);


    }

    public void BuscarPorCampo(){
        String porPlaca = txtBuscarPlaca.getText().toString();
        String porTicket = txtBuscarPorTicket.getText().toString();

        if (porPlaca.isEmpty()){
            BuscarVehiculoPorTicket();
        }if(porTicket.isEmpty()){
            BuscarVehiculoPorPlaca();
        }
    }

    public void BorrarCampos(){
        txt3.setText("");
        txtBuscarPlaca.setText("");
        txtNombrePer.setText("");
        txtObservacionesSalida.setText("");
        txtIdUsuarioSalida.setText("");
        txtIdVehiculoSalida.setText("");
        txtPlacaSalida.setText("");
        txtIdPersonaSalida.setText("");
        txtTicketSalida.setText("");
        //txtTicket.setText("");
    }
}