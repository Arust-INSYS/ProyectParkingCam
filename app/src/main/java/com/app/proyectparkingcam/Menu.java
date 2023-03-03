package com.app.proyectparkingcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {
    TextView txtIdPlazas1,txtIdPlazas2,txtPLazas1,txtPlazas2;
    Button btnEnviar;
    RequestQueue requestQueue;

    //private static final String url = "https://cf4c-181-188-201-139.sa.ngrok.io/api/bloque/list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtIdPlazas1 = findViewById(R.id.txtIdBloque1);
        txtPLazas1 = findViewById(R.id.txtPlazas1);
        txtIdPlazas2 = findViewById(R.id.txtIdBloque2);
        txtPlazas2 = findViewById(R.id.txtPlazasB2);

        LeerBloque1();
        LeerBloque2();

        btnEnviar = findViewById(R.id.btnRegistroEntrada);

        requestQueue = Volley.newRequestQueue(this);



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LeerWs();
                //enviarWs( txtBody.getText().toString(), txtTitle.getText().toString());
                //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //eliminarWs();
                Intent it = new Intent(Menu.this, EscanearPlaca.class);
                startActivity(it);

            }
        });
        /*
        Button btnRgEntrada = findViewById(R.id.btnRegistroEntrada);
        btnRgEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(Menu.this, RegistroUsuarios.class);
                startActivity(it);
            }
        });*/

        Button btnRgSalida = findViewById(R.id.btnRegistroSalida);
        btnRgSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this, RegistroSalida.class);
                startActivity(it);
            }
        });
    }


    private void LeerBloque1() {
        String url = "https://5558-45-236-151-105.sa.ngrok.io/api/bloque/search/1";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //txtIdPlazas1.setText(jsonObject.getString("id_bloque"));
                    String nombre = jsonObject.getString("nombre");
                    txtIdPlazas1.setText(nombre);
                    String plazas = jsonObject.getString("plazas");
                    txtPLazas1.setText(plazas+" plazas");
                    //String nombre = jsonObject.getString("nombre");
                    //txtNombre.setText(nombre);

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

    private void LeerBloque2() {
        String url = "https://5558-45-236-151-105.sa.ngrok.io/api/bloque/search/2";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //txtIdPlazas1.setText(jsonObject.getString("id_bloque"));
                    String nombre = jsonObject.getString("nombre");
                    txtIdPlazas2.setText(nombre);
                    String plazas = jsonObject.getString("plazas");
                    txtPlazas2.setText(plazas+" plazas");
                    //String nombre = jsonObject.getString("nombre");
                    //txtNombre.setText(nombre);

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