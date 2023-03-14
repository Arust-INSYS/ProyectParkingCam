package com.app.proyectparkingcam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuApp extends AppCompatActivity {
    //Objeto Declarado del Main
    MainActivity main = new MainActivity();
    TextView txtIdPlazas1,txtIdPlazas2,txtPLazas1,txtPlazas2;
    TextView textoId, textoName, textoApellido;

    String dominio= main.Dominio();
    static String valor;
    static String valor2;
    Button btnEnviar,btnRgSalida;
    RequestQueue requestQueue;
    ListView listaBloques;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //CAMBIOS ARIEL
        //--CADENA


        //textoId = findViewById(R.id.textViewID);
        textoName=findViewById(R.id.textViewIName);
                ///

        valor2= main.Dar_valor2();
        Log.d("TAG", "La cadena es: "+valor);
        Buscar_Persona(valor2);


        requestQueue = Volley.newRequestQueue(this);

        btnEnviar = findViewById(R.id.btnRegistroEntrada);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuApp.this, RegistroEntrada.class);
                startActivity(it);

            }
        });

        btnRgSalida = findViewById(R.id.btnRegistroSalida);
        btnRgSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuApp.this, RegistroSalida.class);
                startActivity(it);
            }
        });

    }
    ///MENÚ DE OPCIONES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // manejar la opción de menú "Settings"
            return true;
        } else if (id == R.id.action_help) {
            // manejar la opción de menú "Help"
            return true;
        }else if (id == R.id.action_logout) {
            Mensaje_Verificacion(1);

            return true;
        }else

        return super.onOptionsItemSelected(item);
    }
    //USO DEL BOTON BACK
    @Override
    public void onBackPressed() {
        // Agregar la lógica para validar el botón "back"
        // ...
        Mensaje_Verificacion(1);
        // Si la validación es exitosa, llamar al método padre para manejar el evento

    }

    public void Mensaje_Verificacion(int opcion){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Está seguro?");
        if(opcion==1){
            builder.setMessage("¿Desea cerrar sesión?");
        }

        //COLOCAR OTRO MENSAJE

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aquí puede agregar el código para cerrar sesión
                if(opcion==1){
                    Cerrar_sesion();
                }
                //COLOCAR OTRO METODO
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();

    }
    public void Cerrar_sesion(){
        // Aquí va tu código para cerrar la sesión del usuario
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    ////
    String url,name_persona,last_persona;
    static String name_completo;
    private void Buscar_Persona(String valor) {

        url=dominio+"/api/persona/search/"+valor;
        Log.d("TAG", "ESTOY EN BUSCAR PERSONA");
        StringRequest data = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "CERCA DEL TRY");
                try {
                    JSONObject data = new JSONObject(response);
                    name_persona = data.getString("nombre");
                    last_persona=data.getString("apellido");

                    //textoId.setText("ID Usuario: "+valor);
                    textoName.setText("Nombre de Usuario: "+name_persona+" "+last_persona);
                    //textoApellido.setText(last_persona);

                    Log.d("TAG", "HOLA SOY: "+name_persona+""+last_persona);

                }catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(data);
    }





/*
    @Override
    public void onBackPressed() {
        // Cerrar la actividad actual
        finish();
    }

    @Override
    public void onBackPressed() {
        // Ignorar la acción del botón "Regresar"
        super.onBackPressed();
    }*/
}