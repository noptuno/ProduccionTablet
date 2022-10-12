package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterImprentas;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Imprentas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verificacion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    Button btn_guardar;
    private ProgressDialog progressDialog;
    private ArrayList<Produccion_Lista> listImprentas = new ArrayList<>();
    Produccion_Lista produccion_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        btn_guardar = findViewById(R.id.verificacion_btn_guardar);

        Bundle parametros = getIntent().getExtras();

        if (parametros != null) {
            tarea_Seleccionada =  (Tareas) parametros.getSerializable("tarea");
        }else{
            Toast.makeText(getApplicationContext(), "No hay datos a mostrar", Toast.LENGTH_LONG).show();
        }

        requestQueue = Volley.newRequestQueue(this);


        RecyclerView recyclerView = findViewById(R.id.verificacion_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);

        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {


            produccion_actual = lg;

        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterProduccion.notifyDataSetChanged();

        adapterProduccion.setOnNoteSelectedListener(new AdapterProduccion.OnNoteSelectedListener() {
            @Override
            public void onClick(Produccion_Lista note) {
                produccion_actual = note;
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setProgressDialog();
                String url = Urls.agregarProduccion;

                produccion_actual.setFecha("12/10/2022");
                produccion_actual.setObservacionesCierre("HOLA2");

                JSONObject jsonObject = GsonUtils.toJSON(produccion_actual);

                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        url,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext(), "Se cargo", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });

                request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);

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