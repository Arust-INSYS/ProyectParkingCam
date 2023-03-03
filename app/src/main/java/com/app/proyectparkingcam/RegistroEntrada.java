package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
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
    EditText edtxtFecha,edtxtEntrada,edtxtSalida,edtxtObservaciones,edtxtUsuario,edtxtVehiculo,edtxtBloque;
    Button btnGuardar;
    //URL DE LA API
    String url="https://49af-186-43-155-62.ngrok.io/api/registro/create";
    //https://49af-186-43-155-62.ngrok.io/api/registro/create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrada);
        t1 = (TextView) findViewById(R.id.txtMensaje);
        edtxtFecha = (EditText) findViewById(R.id.editxtfecha);
        edtxtEntrada = (EditText) findViewById(R.id.editxtEntrada);
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
                Guardar();
                Borar();

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
            //data.put("usuario", Integer.parseInt(edtxtUsuario.getText().toString()));
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
}