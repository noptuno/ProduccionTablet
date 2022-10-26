package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterBobinas;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;

import java.util.ArrayList;

public class Produccion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private TareaSingleton tareaSingleton;

    private Button btn_cantidad, btn_bobina,btn_scrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

       // requestQueue = Volley.newRequestQueue(this);


        btn_cantidad = findViewById(R.id.produccion_btn_cantidad);
        btn_bobina = findViewById(R.id.produccion_btn_bobina);
        btn_scrap = findViewById(R.id.produccion_btn_scrap);

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Error Instacia",Toast.LENGTH_LONG).show();
        }


        btn_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produccion_Lista produccion = new Produccion_Lista();

                float RollosEmpaquetados = 10;
                float RollosFabricdos=11;
                float MetrosImpresos = 12;

                Intent intent = new Intent(Produccion_Activity.this, CantidadActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        btn_bobina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bobinas bobinacargar = new Bobinas();

             float BobinaId;
             float TareaId;
             float ProduccionId;
             float ProveedorId;
             String ProveedorNombre;
             String Lote;
             float Ancho;
             float TipoMaterialId;
             String EsAbiertaoCerrada;
             float DefectuosaKg;
             String NombreTipoMaterial;

                Intent intent = new Intent(Produccion_Activity.this, BobinasActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

/*
                JSONObject jsonObject = GsonUtils.toJSON(bobinacargar);
                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        Urls.agregarbobinas,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext(), "Se cargo", Toast.LENGTH_LONG).show();
                               // dialogProgress.dismiss();

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                               // dialogProgress.dismiss();
                            }
                        });
                request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                */



            }
        });

        btn_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produccion_Lista produccion = new Produccion_Lista();

                float ScrapAjusteProduccion = 100;
                String ScrapAjusteProduccion_Unidades = "KG";

                Intent intent = new Intent(Produccion_Activity.this, ScrapActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });


        RecyclerView recyclerView = findViewById(R.id.produccion_cantidad_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);


        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {

            produccion_actual = lg;

        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterProduccion.notifyDataSetChanged();


        RecyclerView recyclerBobinas = findViewById(R.id.produccion_bobina_recycler);
        recyclerBobinas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerBobinas.setAdapter(adapterBobina);


        for (Bobinas lg : tarea_Seleccionada.getBobinas()) {

            bobinas_actual = lg;

        }

        adapterBobina.setNotes(tarea_Seleccionada.getBobinas());
        adapterBobina.notifyDataSetChanged();

    }


}