package com.app.proyectparkingcam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

=======
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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




>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======


        Button btnInicio = findViewById(R.id.btnLogin);
=======
        editxtClave = (EditText) findViewById(R.id.edittxtClave);
        editxtUser = (EditText)findViewById(R.id.edittxtUser);
        txtMensaje = (TextView) findViewById(R.id.txtViewMensaje);
        //PETICION GET
        //TextUtils.isEmpty(editText.getText().toString())
         btnInicio = findViewById(R.id.btnLogin);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editxtUser.getText().toString()) ||TextUtils.isEmpty(editxtClave.getText().toString())){
                    Campos_vacios_incorrectos(1);

                }else{
                    Listar();
                }

>>>>>>> Stashed changes


            }
        });

<<<<<<< Updated upstream
>>>>>>> Stashed changes
    }
=======


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

>>>>>>> Stashed changes
}