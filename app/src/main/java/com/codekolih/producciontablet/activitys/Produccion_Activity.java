package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.clases.Usuario;

public class Produccion_Activity extends AppCompatActivity {


    Tareas tarea_Seleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        Bundle parametros = getIntent().getExtras();

        if (parametros != null) {
            tarea_Seleccionada =  (Tareas) parametros.getSerializable("tarea");
        }else{
            Toast.makeText(getApplicationContext(), "No hay datos a mostrar", Toast.LENGTH_LONG).show();
        }





    }
}