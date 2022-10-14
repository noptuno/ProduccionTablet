package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.RequestVolleySingleton;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterTareas;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;

public class Tarea_Activity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ArrayList<Tareas> listImprentas = new ArrayList<>();
    private AdapterTareas adapterTareas = new AdapterTareas();
    private int maquinaId;
    private ProgressHUD dialogProgress;

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

        adapterTareas.setOnNoteSelectedListener(new AdapterTareas.OnNoteSelectedListener() {
            @Override
            public void onClick(Tareas note) {

              // TareaInstance.Set(note);

                TareaSingleton singleton = TareaSingleton.SingletonInstance(note);

                Intent intent = new Intent(Tarea_Activity.this, Verificacion_Activity.class);
                intent.putExtra("tarea", singleton);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        cargarDatos();







    }

    void cargarDatos(){

       // RequestVolleySingleton.getInstance(this).request(Urls.Tareas,0);


        dialogProgress = ProgressHUD.show(Tarea_Activity.this);
        String url = Urls.Tareas;
        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Tarea Response",response);
                        List<Tareas> lista = GsonUtils.parseList(response, Tareas[].class);

/*
                        for (Tareas lg : lista) {
                            List<EstadosOp> list = lg.getEstadosOp();
                           for (EstadosOp est : list) {
                              Log.e("Estados:",est.getNombreEstado());
                           }
                        }
*/

                        adapterTareas.setNotes(lista);
                        adapterTareas.notifyDataSetChanged();
                        dialogProgress.dismiss();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Tarea_Activity.this, "Fallo", Toast.LENGTH_LONG).show();
                        dialogProgress.dismiss();

                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }



}