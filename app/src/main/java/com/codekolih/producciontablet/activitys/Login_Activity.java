package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.BuildConfig;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.codekolih.producciontablet.aciones.Validarinternet;
import com.codekolih.producciontablet.clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Login_Activity extends OcultarTeclado {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private Set<String> history;


    private Button btn_inicioSesion;
    private ImageButton btn_cargarImprenta;
    private ProgressDialog progressDialog;
    private TextView txt_nombreImprenta, txtversionapp;
    private EditText edt_pass;
    private RequestQueue requestQueue;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private String nombreMaquina;
    private String maquinaId,tipomaquinaid;
    private SharedPreferences pref;
    private boolean permisosEscritura;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private AutoCompleteTextView edt_usaurio;

    private static final String[] COUNTRIES = new String[]{
            "rubach", "david", "peter", "enrique", "jose"
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuimprentaschange, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.seleccionar_imprenta:

                Intent intent = new Intent(Login_Activity.this, Imprentas_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public String getVersionName(){
        return BuildConfig.VERSION_NAME;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);


        txtversionapp = findViewById(R.id.txtversion);

        txtversionapp.setText("Versión: " + getVersionName());


        httpLayer = new HttpLayer(this);
        requestQueue = Volley.newRequestQueue(this);
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);

        //Referencias
        btn_inicioSesion = findViewById(R.id.login_btn_IniciarSesion);
        txt_nombreImprenta = findViewById(R.id.login_txt_nombreimprenta);
        //edt_usaurio= findViewById(R.id.login_edt_user);
        edt_pass = findViewById(R.id.login_edt_password);
        edt_pass.setTransformationMethod(new PasswordTransformationMethod());


        ConstraintLayout constraintLayout = findViewById(R.id.constrain_login);

       // addKeyboardHideListener(constraintLayout);


        edt_usaurio = findViewById(R.id.login_edt_login);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        edt_usaurio.setAdapter(adapter);

        edt_usaurio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_pass.requestFocus();
            }
        });


        //Inicializar

        requestQueue = Volley.newRequestQueue(this);

        //cargar


        btn_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String imprenta = txt_nombreImprenta.getText().toString();
                String usuario = edt_usaurio.getText().toString();
                String pass = edt_pass.getText().toString();

                if (!imprenta.equals("NO") && !usuario.equals("") && !pass.equals("")) {

                    Map<String, Object> login = new HashMap<>();
                    login.put("UserName", edt_usaurio.getText().toString());
                    login.put("Password", edt_pass.getText().toString());
                    login.put("MacAddress", "");

                    dialogProgress = ProgressHUD.show(Login_Activity.this);
                    httpLayer.login(GsonUtils.toJSON(login), new HttpLayer.HttpLayerResponses<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            try {

                                String estado = response.getString("Estado");

                                if (estado.equals("200")) {


                                    edt_pass.setText("");
                                    //CargarUsuario
                                    TareaSingleton.SingletonInstance().setUsuarioIniciado(edt_usaurio.getText().toString());

                                    Intent intent = new Intent(Login_Activity.this, Tarea_Activity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                }else{
                                    Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();

                                }
                                Log.e("http_login", response.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            dialogProgress.dismiss();

                        }

                        @Override
                        public void onError(Exception e) {

                            Toast.makeText(getApplicationContext(), "No existe usuario", Toast.LENGTH_SHORT).show();
                            Log.e("http_login", "Fallo");
                            dialogProgress.dismiss();

                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Faltan datos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        pedir_permiso_escritura();



    }

    @Override
    protected void onResume() {
        super.onResume();

        Validarinternet.validarConexionInternet(this);

        if (TareaSingleton.SingletonInstance().getUsuarioIniciado() != null && !TareaSingleton.SingletonInstance().getUsuarioIniciado().isEmpty()) {
            edt_usaurio.setText(TareaSingleton.SingletonInstance().getUsuarioIniciado());
            edt_pass.requestFocus();

        } else {
            edt_usaurio.requestFocus();
        }



    }

    @Override
    protected void onStop() {
        super.onStop();


    }




    private void pedir_permiso_escritura() {

        int readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED || readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            permisosEscritura = false;
        }

    }

    private void cargarMaquinas() {

        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        nombreMaquina = pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO");
        maquinaId = pref.getString(PREF_PRODUCCION_MAQUINAID, "0");
        tipomaquinaid = pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "0");

        txt_nombreImprenta.setText(String.format("%s", nombreMaquina));

        if (!tipomaquinaid.equals("0")) {
            TareaSingleton.SingletonInstance().setTipoMaquina((tipomaquinaid));
        } else {
            Intent intent = new Intent(Login_Activity.this, Imprentas_Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        cargarMaquinas();

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

                        Log.e("Usuario Response", response);

                        Usuario a = GsonUtils.parse(response, Usuario.class);
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