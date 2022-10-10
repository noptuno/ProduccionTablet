package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Metodos;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.clases.Usuario;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                String url = Urls.login;

                Map<String, Object> login = new HashMap<>();
                login.put("UserName", edt_usaurio.getText().toString());
                login.put("Password", edt_pass.getText().toString());
                login.put("MacAddress", "");

                JSONObject jsonObject = GsonUtils.toJSON(login);
                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        url,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.e("Login Response",response.toString());


                                ObtenerDatosDelUsuario();



                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();
                            }
                        });
                requestQueue.add(request);


            }
        });
    }
    public void ObtenerDatosDelUsuario() {


        setProgressDialog();

        String url = Urls.getUsuario;
        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Usuario Response",response);

                        Usuario a = GsonUtils.parse(response,Usuario.class);
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
                        Toast.makeText(Login_Activity.this, "Fallo", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();


                    }
                });
        requestQueue.add(request);







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