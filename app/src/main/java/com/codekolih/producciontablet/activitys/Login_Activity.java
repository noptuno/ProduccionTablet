package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Metodos;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Cuenta;
import com.codekolih.producciontablet.clases.Imprentas;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity {

    private Button btn_inicioSesion, btn_cargarImprenta;
    private ProgressDialog progressDialog;
    private TextView txt_nombreImprenta;
    private EditText edt_usaurio, edt_pass;
    private RequestQueue requestQueue;


    private String NombreMaquina;
    private int MaquinaId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Referencias

        btn_inicioSesion = findViewById(R.id.login_btn_IniciarSesion);
        txt_nombreImprenta = findViewById(R.id.login_txt_nombreimprenta);
        btn_cargarImprenta = findViewById(R.id.login_btn_cargarImprenta);


        edt_usaurio= findViewById(R.id.login_edt_user);
        edt_pass= findViewById(R.id.login_edt_password);;


        //Inicializar
        Metodos metodos = new Metodos(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);

        //cargar

         NombreMaquina = getIntent().getStringExtra("NombreMaquina");
         MaquinaId = getIntent().getIntExtra("MaquinaId",0);

        txt_nombreImprenta.setText(NombreMaquina + " " + MaquinaId);


        btn_cargarImprenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this, Imprentas_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });


        btn_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setProgressDialog();
                String url = Urls.Usuario;
                Cuenta login = new Cuenta(edt_usaurio.getText().toString(),edt_pass.getText().toString(),"");

                JSONObject jsonObject = GsonUtils.toJSON(login);
                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        url,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                              //  Cuenta a = GsonUtils.parse(response.toString(),Cuenta.class);
                              //  String nombre = a.getUserName();

                              //  Toast.makeText(Login_Activity.this, nombre, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Login_Activity.this, Tarea_Activity.class);
                                intent.putExtra("MaquinaId", MaquinaId);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                progressDialog.dismiss();

                            }
                        },

                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();
                            }
                        });
                requestQueue.add(request);

/*
                setProgressDialog();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {

                        Intent intent = new Intent(Login_Activity.this, Tarea_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        progressDialog.dismiss();
                    }

                }, 2000);
*/


            }
        });
    }


    public void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }

}