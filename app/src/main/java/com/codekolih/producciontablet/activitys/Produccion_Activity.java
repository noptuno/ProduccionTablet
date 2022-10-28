package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.codekolih.producciontablet.dialogs.CuadrodeDialogo;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

public class Produccion_Activity extends AppCompatActivity implements CuadrodeDialogo.finalizarCuadro, ScrapDialogo.finalizarScrapDialog {

    Tareas tarea_Seleccionada;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private TareaSingleton tareaSingleton;

    private float produccionId;

    private Button btn_cantidad, btn_bobina,btn_scrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        requestQueue = Volley.newRequestQueue(this);


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



                new CuadrodeDialogo(Produccion_Activity.this,Produccion_Activity.this);
               // productoGlobal = producto;
/*
                Intent intent = new Intent(Produccion_Activity.this, CantidadActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
*/
            }
        });

        btn_bobina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cargarBobina();

/*

*/

            }
        });

        btn_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produccion_Lista produccion = new Produccion_Lista();

                new ScrapDialogo(Produccion_Activity.this,Produccion_Activity.this);

                float ScrapAjusteProduccion = 100;
                String ScrapAjusteProduccion_Unidades = "KG";


            }
        });


        RecyclerView recyclerView = findViewById(R.id.produccion_cantidad_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);


        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {

            produccion_actual = lg;

        }

        adapterProduccion.setOnNoteSelectedListener(new AdapterProduccion.OnNoteSelectedListener() {
            @Override
            public void onClick(Produccion_Lista note) {
                produccionId = note.getProduccionId();
            }
        });

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

    @Override
    public void ResultadoCuadroDialogo(float valorFloat) {

        Toast.makeText(getApplicationContext(),"Valor: " + valorFloat,Toast.LENGTH_LONG).show();

     //   cantidadimprimir = numero;
       // ProcesoImprimir(productoGlobal,cantidadimprimir);
    }

    @Override
    public void ResultadoScrapDialogo(float cantidad) {

        Toast.makeText(getApplicationContext(),"Valor: " + cantidad,Toast.LENGTH_LONG).show();

    }

    private void cargarBobina() {

        Bobinas bobinacargar = new Bobinas();

        bobinacargar.setBobinaId(0);
        bobinacargar.setTareaId(tarea_Seleccionada.getTareaId());
        bobinacargar.setProduccionId(produccionId);
        bobinacargar.setProveedorId(2);
        bobinacargar.setProveedorNombre("dos");
        bobinacargar.setLote("lote123");
        bobinacargar.setAncho(5);
        bobinacargar.setTipoMaterialId(1);
        bobinacargar.setEsAbiertaoCerrada("true");
        bobinacargar.setDefectuosaKg(5);
        bobinacargar.setNombreTipoMaterial("nombretipo");

/*
        Intent intent = new Intent(Produccion_Activity.this, BobinasActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
*/

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
                       // cargarBobina();
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);


    }



}