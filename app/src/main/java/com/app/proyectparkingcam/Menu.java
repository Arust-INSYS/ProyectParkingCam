package com.app.proyectparkingcam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    TextView txtIdPlazas1,txtIdPlazas2,txtPLazas1,txtPlazas2;
    Button btnEnviar,btnRgSalida;
    RequestQueue requestQueue;
    ListView listaBloques;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //listaBloques = findViewById(R.id.listaBloques);
        //listarBloques();
        txtIdPlazas1 = findViewById(R.id.txtIdBloque1);
        txtPLazas1 = findViewById(R.id.txtPlazas1);
        txtIdPlazas2 = findViewById(R.id.txtIdBloque2);
        txtPlazas2 = findViewById(R.id.txtPlazasB2);

        //LeerBloque1();
        //LeerBloque2();

        requestQueue = Volley.newRequestQueue(this);

        btnEnviar = findViewById(R.id.btnRegistroEntrada);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Menu.this, EscanearPlaca.class);
                startActivity(it);

            }
        });

        btnRgSalida = findViewById(R.id.btnRegistroSalida);
        btnRgSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Menu.this, RegistroSalida.class);
                startActivity(it);
            }
        });
    }

/*
    private void listarBloques(){
        String Url = "https://3988-181-211-10-245.ngrok.io/api/bloque/list";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showListView(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Request: "+error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private void showListView(JSONObject obj){
        try{
            List<String> contes = new ArrayList<String>();
            JSONArray lista = obj.optJSONArray("");
            for (int i=0; i<lista.length();i++) {
                JSONObject json_data = lista.getJSONObject(i);
                String conte = json_data.getString("id_bloque")+" "+json_data.getString("nombre")+" "+json_data.getString("plazas")+" "+json_data.getString("estado");
                contes.add(conte);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,contes);
            listaBloques.setAdapter(adapter);

        }catch (JSONException e) {
            e.printStackTrace();
        }finally{

        }
    }

    private void LeerBloque1() {
        String url = "https://3988-181-211-10-245.ngrok.io/api/bloque/search/1";
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
        String url = "https://3988-181-211-10-245.ngrok.io/api/bloque/search/2";
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
*/

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la aplicación?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Intent.ACTION_MAIN);
                            i.addCategory(Intent.CATEGORY_HOME);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();


        }

        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        // Cerrar la actividad actual
        finish();
    }
/*
    @Override
    public void onBackPressed() {
        // Ignorar la acción del botón "Regresar"
        super.onBackPressed();
    }*/

    //comentarioooo
}