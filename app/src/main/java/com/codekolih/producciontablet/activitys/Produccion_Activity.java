package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.clases.Usuario;

import java.util.ArrayList;

public class Produccion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    private ArrayList<Produccion_Lista> listImprentas = new ArrayList<>();
    Produccion_Lista produccion_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private TareaSingleton tareaSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);


        Bundle parametros = getIntent().getExtras();

        if (parametros != null) {

            tareaSingleton = (TareaSingleton) parametros.getSerializable("tareaSingleton");
            tarea_Seleccionada = tareaSingleton.getTareaInstanciada();

        }


        RecyclerView recyclerView = findViewById(R.id.produccion_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);


        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {

            produccion_actual = lg;

        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterProduccion.notifyDataSetChanged();

    }
}