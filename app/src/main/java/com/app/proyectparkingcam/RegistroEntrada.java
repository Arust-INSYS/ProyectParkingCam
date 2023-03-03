package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroEntrada extends AppCompatActivity {
    //Instanciamos los elementos del activity
    //TextView
    TextView t1;
    //edittext
    EditText edtxtFecha,edtxtEntrada,edtxtSalida,edtxtObservaciones,edtxtUsuario,edtxtVehiculo,edtxtBloque,txtIdRegistro;
    Button btnGuardar;
    //URL DE LA API
    String url="https://3d4e-181-188-201-139.sa.ngrok.io/registro/create";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrada);
        t1 = (TextView) findViewById(R.id.txtMensaje);
        edtxtFecha = (EditText) findViewById(R.id.editxtfecha);
        edtxtEntrada = (EditText) findViewById(R.id.editxtEntrada);
        txtIdRegistro = (EditText) findViewById(R.id.txtIdRegistro);
        edtxtSalida = (EditText) findViewById(R.id.editxtSalida);
        edtxtObservaciones = (EditText) findViewById(R.id.editxtObservaciones);
        edtxtUsuario = (EditText) findViewById(R.id.editxtUsuario);
        edtxtVehiculo = (EditText) findViewById(R.id.editxtvehiculo);
        edtxtBloque = (EditText) findViewById(R.id.editxtBloque);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        //Accion de boton guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar();
                //Borar();
                enviaRegiss();

            }
        });
    }
    public void Guardar(){
        JSONObject data = new JSONObject();

        try {

            data.put("hora_entrada", edtxtEntrada.getText());
            data.put("hora_salida", edtxtSalida.getText());
            data.put("observaciones", edtxtObservaciones.getText());
            data.put("usuario", edtxtUsuario.getText());
            data.put("bloque", edtxtBloque.getText());
            data.put("vehiculo", edtxtVehiculo.getText());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // El servidor ha recibido los datos y los ha guardado en el archivo JSON
                        // aquí puedes leer la respuesta y procesarla
                        t1.setText("Guardado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // aquí puedes manejar los errores
                        t1.setText("Page not available");
                    }
                });

// Agregar la solicitud a la cola
        Volley.newRequestQueue(this).add(request);


    }
    public void Borar(){

        edtxtEntrada.setText("");
        edtxtSalida.setText("");
        edtxtObservaciones.setText("");
        edtxtUsuario.setText("");
        edtxtBloque.setText("");
        edtxtVehiculo.setText("");
    }


    private void enviaRegiss()  {
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...","Actualizando Datos",false,false);
        try {
            String url = "https://3d4e-181-188-201-139.sa.ngrok.io/api/registro/create";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id_registro", txtIdRegistro.getText().toString());
            jsonBody.put("fecha", edtxtFecha.getText().toString());
            jsonBody.put("hora_entrada", edtxtEntrada.getText().toString());
            jsonBody.put("hora_salida", edtxtSalida.getText().toString());
            jsonBody.put("observaciones", edtxtObservaciones.getText().toString());
            jsonBody.put("usuario", edtxtUsuario.getText().toString());
            jsonBody.put("bloque", edtxtBloque.getText().toString());
            jsonBody.put("vehiculo", edtxtVehiculo.getText().toString());

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