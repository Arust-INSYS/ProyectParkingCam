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


    //TextView txtUser,txtClave,
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
        editxtClave = (EditText) findViewById(R.id.edittxtClave);
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
    private void Listar() {

        String username =editxtUser.getText().toString();
        String clave = editxtClave.getText().toString();
        url="https://49af-186-43-155-62.ngrok.io/api/usuario/searchname?filtro="+username+"&filter="+clave;
        Log.d("TAG", "Astoy antes del RQUEST");
        StringRequest data = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "CERCA DEL TRY");
                try {
                    JSONObject data = new JSONObject(response);

                    nombre_user=data.getString("username");
                    clave_user=data.getString("password");
                    Log.d("TAG", "HOLA SOY: "+nombre_user+""+clave_user);
                    txtMensaje.setText("Ok");
                    Acceso(true);


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
    //Validaciones
    public void Campos_vacios_incorrectos(int valor){
        String mensaje="";
        if (valor==1){
            mensaje= "Campos vacios";
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
            mensaje="Escriba una clave minima de 6 caracteres";

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
            Intent i = new Intent(MainActivity.this,Menu.class);
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



}