package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.AccionClasses;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterBobinas;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Produccion_Activity extends AppCompatActivity implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog {

    Tareas tarea_Seleccionada;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private TareaSingleton tareaSingleton;
    private HttpLayer httpLayer;
    private float produccionId;
    private Button btn_cantidad, btn_bobina,btn_scrap, btn_finalizar, btn_cancelar;

    private Produccion_Lista produccion_Lista_seleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        AccionClasses.PrintMesajeLog("ProduccionActivity","INICIO");

        btn_cantidad = findViewById(R.id.produccion_btn_cantidad);
        btn_bobina = findViewById(R.id.produccion_btn_bobina);
        btn_scrap = findViewById(R.id.produccion_btn_scrap);
        btn_finalizar = findViewById(R.id.produccion_btn_finalziar);
        btn_cancelar= findViewById(R.id.produccion_btn_cancelar);


        requestQueue = Volley.newRequestQueue(this);
        httpLayer = new HttpLayer(this);

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){

            AccionClasses.PrintMesajeLog("ProduccionActivity","Error Instacia");

        }


        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent = new Intent(Produccion_Activity.this, Observaciones_Activity.class);
                //intent.putExtra("tarea", note);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                */

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produccion_Lista produccion = new Produccion_Lista();

                float RollosEmpaquetados = 10;
                float RollosFabricdos=11;
                float MetrosImpresos = 12;

                new CantidadDialog(Produccion_Activity.this,Produccion_Activity.this);

            }
        });

        btn_bobina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarBobina();
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
        RecyclerView recyclerBobinas = findViewById(R.id.produccion_bobina_recycler);
        recyclerBobinas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerBobinas.setAdapter(adapterBobina);


        CargarReciclerViews();

    }


    void CargarReciclerViews(){

        AccionClasses.PrintMesajeLog(getApplicationContext().toString(),"CargarRecicler");

        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
            produccion_actual = lg;
            produccionId = lg.getProduccionId();
            produccion_Lista_seleccionada = lg;
        }

        AccionClasses.PrintMesajeLog("ProduccionActivity","ProduccionID cargado: " + produccion_Lista_seleccionada.getProduccionId());


        for (Bobinas lg : tarea_Seleccionada.getBobinas()) {
            bobinas_actual = lg;
        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterProduccion.notifyDataSetChanged();
        adapterBobina.setNotes(tarea_Seleccionada.getBobinas());
        adapterBobina.notifyDataSetChanged();
    }

    @Override
    public void ResultadoCantidadDialogo(float valorFloat) {

        AccionClasses.PrintMesajeLog("ProduccionActivity","Cantidad: " + valorFloat);

        produccion_Lista_seleccionada.setRollosEmpaquetados(valorFloat);
        produccion_Lista_seleccionada.setRollosFabricdos(valorFloat);
        produccion_Lista_seleccionada.setMetrosImpresos(valorFloat);

        Map<String, Object> produccion = new HashMap<>();
        produccion.put("ProduccionId", produccion_Lista_seleccionada.getProduccionId());
        produccion.put("PedidoId", produccion_Lista_seleccionada.getPedidoId());
        produccion.put("TareaId", produccion_Lista_seleccionada.getTareaId());
        produccion.put("MetrosImpresos", produccion_Lista_seleccionada.getMetrosImpresos());
        produccion.put("AnchoFinalRolloYGap", produccion_Lista_seleccionada.getAnchoFinalRollo());
        produccion.put("CantidadPistasImpresas", produccion_Lista_seleccionada.getCantidadPistasImpresas());
        produccion.put("CantidadTintas", produccion_Lista_seleccionada.getCantidadTintas());
        produccion.put("AnchoBobinaUsadoCm", produccion_Lista_seleccionada.getAnchoBobinaUsadoCm());
        produccion.put("ScrapAjusteInicial", produccion_Lista_seleccionada.getScrapAjusteInicial());
        produccion.put("ScrapAjusteInicial_Unidades", produccion_Lista_seleccionada.getScrapAjusteInicial_Unidades());
        produccion.put("ScrapAjusteProduccion", produccion_Lista_seleccionada.getScrapAjusteProduccion());
        produccion.put("ScrapAjusteProduccion_Unidades", produccion_Lista_seleccionada.getScrapAjusteProduccion_Unidades());
        produccion.put("ObservacionesCierre", produccion_Lista_seleccionada.getObservacionesCierre());
        produccion.put("RollosFabricdos", produccion_Lista_seleccionada.getRollosFabricdos());
        produccion.put("AnchoFinalRollo", produccion_Lista_seleccionada.getAnchoFinalRollo());
        produccion.put("CantidadPistasCortadas", produccion_Lista_seleccionada.getCantidadPistasCortadas());
        produccion.put("PistasTroquelUsadas", produccion_Lista_seleccionada.getPistasTroquelUsadas());
        produccion.put("RollosEmpaquetados", produccion_Lista_seleccionada.getRollosEmpaquetados());
        produccion.put("UsuarioId", produccion_Lista_seleccionada.getUsuarioId());


        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                ActualizarTarea();
            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo Bobina Reintentar",Toast.LENGTH_SHORT).show();

            }
        });







    }

    @Override
    public void ResultadoScrapDialogo(float cantidad) {

        AccionClasses.PrintMesajeLog("ProduccionActivity","Scrap: " + cantidad);

    }



    private void ActualizarTarea() {

        String params = "/"+2+"/"+2;

        httpLayer.getTareaEspecifica(params, new HttpLayer.HttpLayerResponses<Tareas>() {
            @Override
            public void onSuccess(Tareas response) {

                AccionClasses.PrintMesajeLog("ProduccionActivity","actualzandoTarea");

              TareaSingleton.SingletonInstance().setTarea(response);
              CargarReciclerViews();

            }

            @Override
            public void onError(Exception e) {

                AccionClasses.PrintMesajeLog("ProduccionActivity","Error Actualziar Tarea");

            }
        });
    }


    private void cargarBobina() {


        Intent intent = new Intent(Produccion_Activity.this, BobinaActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        /*

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

*/
    }

}