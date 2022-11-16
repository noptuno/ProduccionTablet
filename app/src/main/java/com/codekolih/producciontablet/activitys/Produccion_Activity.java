package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
    private int produccionId;
    private Button btn_cantidad, btn_bobina,btn_scrap, btn_finalizar, btn_cancelar;

    private Produccion_Lista produccion_Lista_seleccionada;
    private int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        Log.e("ProduccionActivity","INICIO");

        btn_cantidad = findViewById(R.id.produccion_btn_cantidad);
        btn_bobina = findViewById(R.id.produccion_btn_bobina);
        btn_scrap = findViewById(R.id.produccion_btn_scrap);
        btn_finalizar = findViewById(R.id.produccion_btn_finalziar);
        btn_cancelar= findViewById(R.id.produccion_btn_cancelar);


        requestQueue = Volley.newRequestQueue(this);
        httpLayer = new HttpLayer(this);

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){

            Log.e("ProduccionActivity","Error Instacia");

        }
        Log.e("codigosSeleccionados",tarea_Seleccionada.getTareaId() + " "+ tarea_Seleccionada.getPedidoId());


        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> estado = new HashMap<>();
                estado.put("TareaId", tarea_Seleccionada.getTareaId());
                estado.put("EstadoId", "C1");
                estado.put("TipoEstadoId","F" );


                dialogProgress = ProgressHUD.show(Produccion_Activity.this);

                httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {

                        Log.e("Produccion_Cierre","Cargo Estado" + tarea_Seleccionada.getTareaId());
                        dialogProgress.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Produccion_Cierre","Error al cargar Estado" + tarea_Seleccionada.getTareaId());
                        dialogProgress.dismiss();
                    }
                });


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

                new CantidadDialog(Produccion_Activity.this,Produccion_Activity.this);

            }
        });

        btn_bobina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Produccion_Activity.this, BobinaActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);

            }
        });

        btn_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ScrapDialogo(Produccion_Activity.this,Produccion_Activity.this);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.produccion_cantidad_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);
        RecyclerView recyclerBobinas = findViewById(R.id.produccion_bobina_recycler);
        recyclerBobinas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerBobinas.setAdapter(adapterBobina);

        CargarReciclerViews();

        cambioEstado();

    }

    private void cambioEstado() {

            Map<String, Object> estado = new HashMap<>();
            estado.put("TareaId", tarea_Seleccionada.getTareaId());
            estado.put("EstadoId", "P1");
            estado.put("TipoEstadoId","I" );


            dialogProgress = ProgressHUD.show(Produccion_Activity.this);

            httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.e("Produccion_Activity","Cargo Estado" + tarea_Seleccionada.getTareaId());
                    dialogProgress.dismiss();
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Produccion_Activity","Error al cargar Estado" + tarea_Seleccionada.getTareaId());
                    dialogProgress.dismiss();
                }
            });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

                String result=data.getStringExtra("result");
                ActualizarTarea();

            }

            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    } //onAct


    void CargarReciclerViews(){

        Log.e(getApplicationContext().toString(),"CargarRecicler");

        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
            produccion_actual = lg;
            produccionId = lg.getProduccionId();
            produccion_Lista_seleccionada = lg;
        }
        Log.e("ProduccionActivity","ProduccionID: " + produccionId);
        Log.e("ProduccionActivity","ProduccionID seleccionado: " + produccion_Lista_seleccionada.getProduccionId());

        for (Bobinas lg : tarea_Seleccionada.getBobinas()) {
            bobinas_actual = lg;

        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterBobina.setNotes(tarea_Seleccionada.getBobinas());

        adapterProduccion.notifyDataSetChanged();
        adapterBobina.notifyDataSetChanged();
    }

    @Override
    public void ResultadoCantidadDialogo(float valorFloat) {

        Log.e("ProduccionActivity","Cantidad: " + valorFloat);

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

                Toast.makeText(getApplicationContext(), "No Cargo Cantidad Reintentar",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void ResultadoScrapDialogo(float cantidad, String unidad) {

        Log.e("ProduccionActivity","Scrap: " + cantidad);


        produccion_Lista_seleccionada.setScrapAjusteProduccion(cantidad);
        produccion_Lista_seleccionada.setScrapAjusteProduccion_Unidades(unidad);


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

                Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar",Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void ActualizarTarea() {

        String params = "/"+2+"/"+2;
        Log.e("ProduccionValor",params);

        httpLayer.getTareaEspecifica(params, new HttpLayer.HttpLayerResponses<Tareas>() {
            @Override
            public void onSuccess(Tareas response) {

                Log.e("ProduccionActivity","actualzandoTarea");

              TareaSingleton.SingletonInstance().setTarea(response);
              CargarReciclerViews();

            }

            @Override
            public void onError(Exception e) {

                Log.e("ProduccionActivity","Error Actualziar Tarea");

            }
        });
    }



}