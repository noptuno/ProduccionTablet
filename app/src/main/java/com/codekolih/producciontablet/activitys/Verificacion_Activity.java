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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
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
    private ArrayList<Produccion_Lista> listImprentas = new ArrayList<>();
    Produccion_Lista produccion_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;

    private TareaSingleton tareaSingleton;

    private EditText
            edt_AnchoFinalRolloYGap,
            edt_CantidadPistasImpresas,
            edt_CantidadTintas,
            edt_ScrapAjusteInicial,
            edt_UnidadIdScrapInicial,
            edt_AnchoFinalRollo,
            edt_CantidadPistasCortadas,edt_PistasTroquelUsadas;


    private TextView
            txt_verificacion_txt_NroDeSobre,
            txt_verificacion_txt_Descripcion,
            txt_verificacion_txt_MetrosAImprimir,
            txt_verificacion_txt_MetrosPorRollo,
            txt_verificacion_txt_MetrosMatTroquelar,
            txt_verificacion_txt_Observaciones;

    private TextView
            txt_verificacion_txt_Z_AltoMasGap,
            txt_verificacion_txt_Cilindro,
            txt_verificacion_txt_Pistas,
            txt_verificacion_txt_EtiquetasEnBanda,
            txt_verificacion_txt_EtiquetasPorRollo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);


        btn_guardar = findViewById(R.id.verificacion_btn_guardar);


        edt_AnchoFinalRolloYGap = findViewById(R.id.verificacion_edt_AnchoFinalRolloYGap);
        edt_CantidadPistasImpresas= findViewById(R.id.verificacion_edt_CantidadPistasImpresas);
        edt_CantidadTintas= findViewById(R.id.verificacion_edt_CantidadTintas);
        edt_ScrapAjusteInicial= findViewById(R.id.verificacion_edt_ScrapAjusteInicial);
        edt_UnidadIdScrapInicial= findViewById(R.id.verificacion_edt_UnidadIdScrapInicial);
        edt_AnchoFinalRollo= findViewById(R.id.verificacion_edt_AnchoFinalRollo);
        edt_CantidadPistasCortadas= findViewById(R.id.verificacion_edt_CantidadPistasCortadas);
        edt_PistasTroquelUsadas= findViewById(R.id.verificacion_edt_PistasTroquelUsadas);


        txt_verificacion_txt_NroDeSobre = findViewById(R.id.verificacion_txt_NroDeSobre);
        txt_verificacion_txt_Descripcion = findViewById(R.id.verificacion_txt_Descripcion);
        txt_verificacion_txt_MetrosAImprimir = findViewById(R.id.verificacion_txt_MetrosAImprimir);
        txt_verificacion_txt_MetrosPorRollo = findViewById(R.id.verificacion_txt_MetrosPorRollo);
        txt_verificacion_txt_MetrosMatTroquelar = findViewById(R.id.verificacion_txt_MetrosMatTroquelar);
        txt_verificacion_txt_Observaciones = findViewById(R.id.verificacion_txt_Observaciones);

        txt_verificacion_txt_Z_AltoMasGap= findViewById(R.id.verificacion_txt_Z_AltoMasGap);
        txt_verificacion_txt_Cilindro= findViewById(R.id.verificacion_txt_Cilindro);
        txt_verificacion_txt_Pistas= findViewById(R.id.verificacion_txt_Pistas);
        txt_verificacion_txt_EtiquetasEnBanda= findViewById(R.id.verificacion_txt_EtiquetasEnBanda);
        txt_verificacion_txt_EtiquetasPorRollo= findViewById(R.id.verificacion_txt_EtiquetasPorRollo);


        //tarea_Seleccionada = TareaInstance.Get();

//        Log.e("Mensaje",tarea_Seleccionada.getDescripcion());


        Bundle parametros = getIntent().getExtras();

        if (parametros != null) {

            tareaSingleton =  (TareaSingleton) parametros.getSerializable("tarea");
            tarea_Seleccionada = tareaSingleton.getTareaInstanciada();


            txt_verificacion_txt_NroDeSobre.setText(""+tarea_Seleccionada.getNroDeSobre());
            txt_verificacion_txt_Descripcion.setText(""+tarea_Seleccionada.getDescripcion());
            txt_verificacion_txt_MetrosAImprimir.setText(""+tarea_Seleccionada.getMetrosAImprimir());
            txt_verificacion_txt_MetrosPorRollo.setText(""+tarea_Seleccionada.getMetrosPorRollo());
            txt_verificacion_txt_MetrosMatTroquelar.setText(""+tarea_Seleccionada.getMetrosMatTroquelar());
            txt_verificacion_txt_Observaciones.setText(""+tarea_Seleccionada.getObservaciones());

            txt_verificacion_txt_Z_AltoMasGap.setText(""+tarea_Seleccionada.getZ_AltoMasGap());
            txt_verificacion_txt_Cilindro.setText(""+tarea_Seleccionada.getCilindro());
            txt_verificacion_txt_Pistas.setText(""+tarea_Seleccionada.getPistas());
            txt_verificacion_txt_EtiquetasEnBanda.setText(""+tarea_Seleccionada.getEtiquetasEnBanda());
            txt_verificacion_txt_EtiquetasPorRollo.setText(""+tarea_Seleccionada.getEtiquetasPorRollo());

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

                edt_AnchoFinalRolloYGap.setText(""+ produccion_actual.getAnchoFinalRolloYGap());
                edt_CantidadPistasImpresas.setText(""+ produccion_actual.getCantidadPistasImpresas());
                edt_CantidadTintas.setText(""+ produccion_actual.getCantidadTintas());
                edt_ScrapAjusteInicial.setText(""+ produccion_actual.getScrapAjusteInicial());
                edt_UnidadIdScrapInicial.setText(""+ produccion_actual.getScrapAjusteInicial_Unidades());
                edt_AnchoFinalRollo.setText(""+ produccion_actual.getAnchoFinalRollo());
                edt_CantidadPistasCortadas.setText(""+ produccion_actual.getCantidadPistasCortadas());
                edt_PistasTroquelUsadas.setText(""+ produccion_actual.getPistasTroquelUsadas());

            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogProgress = ProgressHUD.show(Verificacion_Activity.this);

                String url = Urls.agregarProduccion;

                produccion_actual.setFecha("13/10/2022");
                produccion_actual.setObservacionesCierre("HOLA2");
                produccion_actual.setAnchoFinalRollo(Float.parseFloat(edt_AnchoFinalRolloYGap.getText().toString()));
                produccion_actual.setCantidadPistasImpresas(Float.parseFloat(edt_CantidadPistasImpresas.getText().toString()));
                produccion_actual.setCantidadTintas(Float.parseFloat(edt_CantidadTintas.getText().toString()));
                produccion_actual.setScrapAjusteInicial(Float.parseFloat(edt_ScrapAjusteInicial.getText().toString()));
                produccion_actual.setScrapAjusteInicial_Unidades(edt_UnidadIdScrapInicial.getText().toString());
                produccion_actual.setAnchoFinalRollo(Float.parseFloat(edt_AnchoFinalRollo.getText().toString()));
                produccion_actual.setCantidadPistasCortadas(Float.parseFloat(edt_CantidadPistasCortadas.getText().toString()));
                produccion_actual.setPistasTroquelUsadas(Float.parseFloat(edt_PistasTroquelUsadas.getText().toString()));

                Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                intent.putExtra("tareaSingleton", tareaSingleton);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                /*
                JSONObject jsonObject = GsonUtils.toJSON(produccion_actual);

                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        url,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext(), "Se cargo", Toast.LENGTH_LONG).show();
                                dialogProgress.dismiss();

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                dialogProgress.dismiss();
                            }
                        });
                request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                */


            }
        });


    }


}