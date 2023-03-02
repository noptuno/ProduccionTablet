package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_ELEGIRTAREA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import com.codekolih.producciontablet.adapter.AdapterImprentas;
import com.codekolih.producciontablet.clases.Imprentas;

import java.util.ArrayList;
import java.util.List;

public class Imprentas_Activity extends AppCompatActivity {

    private ArrayList<Imprentas> listImprentas = new ArrayList<>();
    private AdapterImprentas adapterImprentas = new AdapterImprentas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private SharedPreferences pref;
    private HttpLayer httpLayer;
    private Button btnregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprentas);

        httpLayer = new HttpLayer(this);
        requestQueue = Volley.newRequestQueue(this);


        //declaraciones
        requestQueue = Volley.newRequestQueue(this);
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);


        RecyclerView recyclerView = findViewById(R.id.verificacion_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterImprentas);

        btnregresar = findViewById(R.id.btn_regresar);
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapterImprentas.setOnNoteSelectedListener(new AdapterImprentas.OnNoteSelectedListener(){
            @Override
            public void onClick(Imprentas note) {

                Intent intent = new Intent(Imprentas_Activity.this, Login_Activity.class);
              //  intent.putExtra("NombreMaquina", note.getNombreMaquina());
              //  intent.putExtra("MaquinaId", note.getMaquinaId());

                pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(PREF_PRODUCCION_NOMBREMAQUINA, note.getNombreMaquina());
                editor.putString(PREF_PRODUCCION_MAQUINAID, ""+note.getMaquinaId());
                editor.putString(PREF_PRODUCCION_MAQUINATIPOID, ""+note.getMaquinaTipoId());
                editor.putString(PREF_PRODUCCION_ELEGIRTAREA, ""+note.getPermiteCambioPrioridad());
                editor.apply();


                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();


            }

        });

        cargarConfiguracion();

        cargarDatos();


    }


private void cargarDatos(){


        dialogProgress = ProgressHUD.show(Imprentas_Activity.this);

        String url = Urls.Imprentas;

        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            List<Imprentas> lista = GsonUtils.parseList(response, Imprentas[].class);
                             List<Imprentas> aux = new ArrayList<>();

                                for (Imprentas lg : lista) {

                                    if (lg.getHabilitada()){
                                        aux.add(lg);
                                    }
                                }

                            adapterImprentas.setNotes(aux);
                            adapterImprentas.notifyDataSetChanged();

                            dialogProgress.dismiss();


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Imprentas_Activity.this, "Fallo", Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();



                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);



}





private void cargarConfiguracion() {


    }

    public void actualizarReciclerView(boolean a) {
        runOnUiThread(() -> {
            if (a){
                adapterImprentas.setNotes(listImprentas);
                adapterImprentas.notifyDataSetChanged();
                Toast.makeText(Imprentas_Activity.this,"Actualizo",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Imprentas_Activity.this,"Error Api",Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menureload, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.seleccionar_imprenta:

                cargarDatos();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}



/*
private void Registrar(Login login) {
    final ProgressDialog dialog = ProgressDialog.show(this, "Cargando...", "Espere por favor", true);

    String url = URLs.REGISTER;
    JSONObject jsonObject = GsonUtils.toJSON(login);
    JsonObjectRequest request = new JsonObjectRequest(
            com.android.volley.Request.Method.POST,
            url,
            jsonObject,
            new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    dialog.dismiss();
                    Toast.makeText(RegistroUsuario.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    finish();
                }
            },
            new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    error.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(RegistroUsuario.this, "Reintentelo de conexion", Toast.LENGTH_LONG).show();
                }
            });

    requestQueue.add(request);
}
*/