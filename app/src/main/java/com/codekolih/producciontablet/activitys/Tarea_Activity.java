package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterTareas;
import com.codekolih.producciontablet.clases.Imprentas;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;

public class Tarea_Activity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private ArrayList<Tareas> listImprentas = new ArrayList<>();
    private AdapterTareas adapterTareas = new AdapterTareas();
    private int maquinaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        //declaraciones
        requestQueue = Volley.newRequestQueue(this);


        maquinaId = getIntent().getIntExtra("MaquinaId",0);

        RecyclerView recyclerView = findViewById(R.id.tarea_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterTareas);

        cargarDatos();
    }


    void cargarDatos(){


        setProgressDialog();
        String url = Urls.Imprentas;

        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Tareas> lista = GsonUtils.parseList(response, Tareas[].class);
                        List<Tareas> aux = new ArrayList<>();

                        for (Tareas lg : lista) {

                            aux.add(lg);

                        }

                        adapterTareas.setNotes(aux);
                        adapterTareas.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Tarea_Activity.this, "Fallo", Toast.LENGTH_LONG).show();
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