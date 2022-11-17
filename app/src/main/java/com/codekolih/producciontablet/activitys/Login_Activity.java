package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Usuario;

import java.util.ArrayList;

public class Login_Activity extends AppCompatActivity {

    private Button btn_inicioSesion;
    private ImageButton btn_cargarImprenta;
    private ProgressDialog progressDialog;
    private TextView txt_nombreImprenta;
    private EditText edt_usaurio, edt_pass;
    private RequestQueue requestQueue;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private String nombreMaquina;
    private String maquinaId;
    private SharedPreferences pref;
    private boolean permisosEscritura;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        httpLayer = new HttpLayer(this);
        requestQueue = Volley.newRequestQueue(this);

        //Referencias
        btn_inicioSesion = findViewById(R.id.login_btn_IniciarSesion);
        txt_nombreImprenta = findViewById(R.id.login_txt_nombreimprenta);
        btn_cargarImprenta = findViewById(R.id.login_btn_cargarImprenta);
        edt_usaurio= findViewById(R.id.login_edt_user);
        edt_pass= findViewById(R.id.login_edt_password);;

        //Inicializar
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        //cargar

        cargarMaquinas();



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


                Intent intent = new Intent(Login_Activity.this, Tarea_Activity.class);
                intent.putExtra("MaquinaId", maquinaId);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

/*
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
*/

            }
        });

        pedir_permiso_escritura();
        }


    private void pedir_permiso_escritura() {

        int readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED || readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }else{
            permisosEscritura = false;
        }

    }

    private void cargarMaquinas() {

        nombreMaquina = pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO");
        maquinaId = pref.getString(PREF_PRODUCCION_MAQUINAID, "0");
        txt_nombreImprenta.setText("Maquina: " + nombreMaquina);

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
                        intent.putExtra("MaquinaId", maquinaId);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisosEscritura = true;

                } else {
                    permisosEscritura = false;
                }
                return;
            }
        }
    }


}