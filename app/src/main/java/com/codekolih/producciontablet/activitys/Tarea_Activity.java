package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
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
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterTareas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;

public class Tarea_Activity extends AppCompatActivity {

    private TextView txt_imprenta, txt_usuario, txt_fecha,txt_hora;
    private String MAQUINATIPOID = "";
    private String MAQUINAID = "";
    private RequestQueue requestQueue;
    private ArrayList<Tareas> listImprentas = new ArrayList<>();
    private AdapterTareas adapterTareas = new AdapterTareas();
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private SharedPreferences pref;
    private RecyclerView recyclerViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        //declaraciones

        requestQueue = Volley.newRequestQueue(this);

        httpLayer = new HttpLayer(this);

        //recicler

        txt_imprenta = findViewById(R.id.tarea_txt_imprenta);
        txt_usuario= findViewById(R.id.tarea_txt_usuario);
        txt_fecha= findViewById(R.id.tarea_txt_fecha);
        txt_hora= findViewById(R.id.txt_tarea_hora);

        recyclerViewTareas = findViewById(R.id.tarea_recycler);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewTareas.setAdapter(adapterTareas);

        //pref
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        MAQUINATIPOID = pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "NO");
        MAQUINAID = pref.getString(PREF_PRODUCCION_MAQUINAID, "NO");
        txt_imprenta.setText(pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"));


        //TODO Validar maquinatipoid y aquinaid

        adapterTareas.setOnNoteSelectedListener(new AdapterTareas.OnNoteSelectedListener() {
            @Override
            public void onClick(Tareas note) {

                TareaSingleton.SingletonInstance().setTarea(note);
                Intent intent = new Intent(Tarea_Activity.this, Verificacion_Activity.class);
                intent.putExtra("tarea", note);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        //todo valdiar internet
        cargarTarea();

    }

    void cargarTarea(){

        dialogProgress = ProgressHUD.show(Tarea_Activity.this);
        httpLayer.getTareas("0/0",new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                adapterTareas.setNotes(response);
                adapterTareas.notifyDataSetChanged();
                dialogProgress.dismiss();

                for (Tareas lg : response) {
                    Log.e("Datos_tareas",lg.toString());
                }

            }
            @Override
            public void onError(Exception e) {

                Log.e("TareaActivity",e.toString());
                dialogProgress.dismiss();
            }
        });
    }


}