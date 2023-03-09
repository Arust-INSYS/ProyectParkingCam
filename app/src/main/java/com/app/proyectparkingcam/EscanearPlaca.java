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
    MainActivity main = new MainActivity();
    String valor;
    EditText txtPlacaBuscar,txtObservaciones,txtUsuario,txtBloque,txtCondicion,txtBuscarTicket;

    TextView txtIdPersona,txtEstadoEntrada,txtTicket,txtPlacaEntrada,txtMarca,txtNombrePersona,txtColor,txt_IdPersona,t2,txtIdVehiculo2,txtFecha,txtHoraEntrada,txtPersona;

    Button btnBuscar,btnGuardar;

    String dominio = main.Dominio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear_placa);
        txtIdPersona = findViewById(R.id.txtIdPersona);
        txtTicket = findViewById(R.id.txtTicket);
        txtIdVehiculo2 = findViewById(R.id.txtIdVehiculo2);
        txtBuscarTicket = findViewById(R.id.txtBuscarTicket);
        t2 = findViewById(R.id.t2);
        txtPlacaBuscar = findViewById(R.id.txtPlacaBuscar);
        txtPlacaEntrada = findViewById(R.id.txtPlacaEntrada);
        txtMarca = findViewById(R.id.txtMarca);
        txtNombrePersona = findViewById(R.id.txtNombrePersona);
        //txtColor = findViewById(R.id.txtColor);
        txt_IdPersona = findViewById(R.id.txtIdPersona);
        btnBuscar = findViewById(R.id.btnBuscar);
        //txt_IdRegistroE = findViewById(R.id.txtIdRegistroE);
        txtFecha = findViewById(R.id.txtFecha);
        txtHoraEntrada = findViewById(R.id.txtHoraEntrada);
        txtObservaciones = findViewById(R.id.txtObservaciones);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtEstadoEntrada = findViewById(R.id.txtEstadoEntrada);
        txtEstadoEntrada.setText("A");
        txtEstadoEntrada.setVisibility(View.GONE);
        txtBloque = findViewById(R.id.txtBloque);
        txtBloque.setVisibility(View.GONE);
        txtCondicion = findViewById(R.id.txtCondicion);
        btnGuardar = findViewById(R.id.btnGuardar);

        txtCondicion.setText("Entrada");

        //CAMBIOS CÓDIGO ARIEL
        valor=main.Dar_valor();
        txtUsuario.setText(valor);
        txtUsuario.setEnabled(false);
        //OCULTAR
        txtUsuario.setVisibility(View.GONE);
        Log.d("TAG", "EL USUARIO ES:"+valor);
        txtPersona = findViewById(R.id.txtviewPersona);
        txtPersona.setText(main.Dar_Nombre());
        //FIN CAMBIOS


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
        String url = "https://"+dominio+"/api/vehiculo/placa/"+id;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculo2.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlacaEntrada.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    txtTicket.setText(jsonObject.getString("ticket"));

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txt_IdPersona.setText(id_persona);

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
        String url = "https://"+dominio+"/api/vehiculo/ticket/"+ticket;

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtIdVehiculo2.setText(jsonObject.getString("id_vehiculo"));

                    String placa = jsonObject.getString("placa");
                    txtPlacaEntrada.setText(placa);

                    String marca = jsonObject.getString("marca");
                    txtMarca.setText(marca);

                    txtTicket.setText(jsonObject.getString("ticket"));

                    JSONObject relatedObject = jsonObject.getJSONObject("persona");
                    String id_persona = relatedObject.getString("id_persona");
                    txt_IdPersona.setText(id_persona);

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
        String urL ="https://"+dominio+"/api/registro/create";
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
            data.put("estado", txtEstadoEntrada.getText());

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
        txtIdVehiculo2.setText("");
        txtNombrePersona.setText("");
        t2.setText("");
        txtTicket.setText("");
        txtPlacaEntrada.setText("");
        txtMarca.setText("");
        //txtColor = findViewById(R.id.txtColor);
        txtIdPersona.setText("");
        //txtIdRegistroE.setText("");
        txtObservaciones.setText("");
        txtUsuario.setText("");
    }



}