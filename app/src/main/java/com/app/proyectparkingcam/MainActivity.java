package com.app.proyectparkingcam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txtMensaje;
    Button btnInicio;
    EditText editxtUser;
    EditText editxtClave;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnInicio = findViewById(R.id.btnLogin);
        editxtClave = (EditText) findViewById(R.id.edittxtClave1);
        editxtUser = (EditText)findViewById(R.id.edittxtUser);
        txtMensaje = (TextView) findViewById(R.id.txtViewMensaje);
        //VALIDAR LIMITE CARACTERRES
        //Se añade el TextWatcher al EditText
        editxtUser.addTextChangedListener(mTextEditorWatcher);
        editxtClave.addTextChangedListener(mTextEditorWatcher);
        //PETICION GET
        //TextUtils.isEmpty(editText.getText().toString())
         btnInicio = findViewById(R.id.btnLogin);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editxtUser.getText().toString()) ||TextUtils.isEmpty(editxtClave.getText().toString())){
                    Campos_vacios_incorrectos(1);

                }if(editxtUser.length() < 10){
                    Campos_vacios_incorrectos(4);

                }if(editxtClave.length()<6){
                    Campos_vacios_incorrectos(5);
                }
                else{
                    Listar();
                }
            }
        });
   }
    
    String nombre_user="", clave_user="";
    //Valor de id;
    static String txt_id;
    static String txtx_id_persona;
    String rol;
    String dominio=Dominio();
    private void Listar() {

        String username =editxtUser.getText().toString();
        String clave = editxtClave.getText().toString();
        url=dominio+"/api/usuario/searchname?filtro="+username+"&filter="+clave;
        Log.d("TAG", "Astoy antes del RQUEST");
        StringRequest data = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "CERCA DEL TRY");
                try {
                    JSONObject data = new JSONObject(response);
                    txt_id = data.getString("id_usuario");
                    Log.d("TAG", "EL USUARIO ES:"+txt_id);
                    nombre_user=data.getString("username");
                    clave_user=data.getString("password");
                    rol=data.getString("rol");
                    //CLAVE FORANEA
                    JSONObject relatedObject = data.getJSONObject("persona");
                    txtx_id_persona = relatedObject.getString("id_persona");
                    //FIN CLAVE FORANEA
                    //BUSCAR A PERSONA
                    Buscar_Persona();

                    Log.d("TAG", "HOLA SOY: "+nombre_user+" "+clave_user);
                    Log.d("TAG", "ID DE  PERSONA: "+txtx_id_persona);
                    txtMensaje.setText("Ok");
                    if(rol.equalsIgnoreCase("Guardia")){
                        Acceso(true);
                    }else{
                        Campos_vacios_incorrectos(6);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                    Campos_vacios_incorrectos(2);
                    txtMensaje.setText("ERROR");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtMensaje.setText("ERROR");
                Campos_vacios_incorrectos(3);
            }
        });
        Volley.newRequestQueue(this).add(data);
    }
    static String name_completo;
    String name_persona, last_persona;

    private void Buscar_Persona() {

        url=dominio+"/api/persona/search/"+txtx_id_persona;
        Log.d("TAG", "ESTOY EN BUSCAR PERSONA");
        StringRequest data = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "CERCA DEL TRY");
                try {
                    JSONObject data = new JSONObject(response);
                    name_persona = data.getString("nombre");
                    last_persona=data.getString("apellido");
                    //Almacenar nombre y apellido en variable static
                    name_completo="Guardia: "+name_persona+" "+last_persona;

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

    //Validaciones
    public void Campos_vacios_incorrectos(int valor){
        String mensaje="";
        if (valor==1){
            mensaje= "Campos vacíos";
        }if(valor==2){

            mensaje="Usuario o Contraseña Incorrecta";
            Borrar_campos(1);
        }if(valor==3){
            mensaje="Error del servidor o dominio";
            Borrar_campos(1);
        }if(valor==4){
            mensaje="No se admiten menos de 10 caracteres en en el usuario";
            Borrar_campos(1);
        }
        if(valor==5){
            mensaje="Escriba una clave mínima de 6 caracteres";

        }if(valor==6){
            mensaje="El usuario ingresado no pertenece al personal de seguridad.";
            Borrar_campos(1);

        }

        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setMessage(mensaje).setCancelable(false).setPositiveButton(
                "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Advertencia");
        titulo.show();

    }

    public void Acceso(boolean valor){
        if(valor ==true){
            Borrar_campos(2);
            Intent i = new Intent(MainActivity.this, MenuApp.class);
            startActivity(i);

        }

    }

    public void Borrar_campos(int valor){
        if(valor==1){
            editxtClave.setText("");

        }if(valor==2){
            editxtClave.setText("");
            editxtUser.setText("");
        }

    }

    // Se crea un TextWatcher
    TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Establece el límite de caracteres del EditText
            int limiteCaracteres=10;
            // Si el usuario ha llegado al límite de caracteres, muestra un mensaje
            if (s.length() > limiteCaracteres) {
                Toast.makeText(getApplicationContext(), "Has llegado al límite de caracteres", Toast.LENGTH_SHORT).show();
            } // Si el usuario ha llegado al límite de caracteres, borra los últimos caracteres
            if (s.length() > limiteCaracteres) {
                editxtUser.setText(s.subSequence(0, limiteCaracteres));
                editxtUser.setSelection(limiteCaracteres);

            }
            if (editxtClave.length() > limiteCaracteres) {
                editxtClave.setText(s.subSequence(0, limiteCaracteres));
                editxtClave.setSelection(limiteCaracteres);
            }

        }

        public void afterTextChanged(Editable s) {

        }
    };
    public String Dar_valor(){
        String cod_user=txt_id;
        return cod_user;
    }

    public String Dar_valor2(){
        String cod_persona=txtx_id_persona;
        return cod_persona;
    }

    public String Dar_Nombre(){
        String nombre_persona=name_completo;
        return nombre_persona;
    }
    public String Dominio(){
        //String dominio="http://138.197.127.252:8080";
        String dominio="http://138.197.127.252:8080";
        return dominio;
    }
    ///SALIR DE LA APP




}