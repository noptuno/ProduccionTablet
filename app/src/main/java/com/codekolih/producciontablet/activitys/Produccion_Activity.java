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
import java.util.List;
import java.util.Map;

public class Produccion_Activity extends AppCompatActivity implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog {

    Tareas tarea_Seleccionada;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;

    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private Button btn_cantidad, btn_bobina,btn_scrap, btn_finalizar, btn_cancelar;
    private int BOBINA_ACTIVITY = 1;
    RecyclerView recyclerViewCantidad,recyclerViewBobinas;

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
        recyclerViewCantidad = findViewById(R.id.produccion_cantidad_recycler);
        recyclerViewBobinas = findViewById(R.id.produccion_bobina_recycler);


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
                startActivityForResult(i, BOBINA_ACTIVITY);

            }
        });

        btn_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ScrapDialogo(Produccion_Activity.this,Produccion_Activity.this);

            }
        });


        recyclerViewCantidad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCantidad.setAdapter(adapterProduccion);

        recyclerViewBobinas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewBobinas.setAdapter(adapterBobina);


        cargarTareaSeleccionada(); // tarea y produccion_actual y bobina actual


      //  cambioEstado();

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

        if (requestCode == BOBINA_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

                actualziarTarea();
            }

            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    } //onAct


    void cargarTareaSeleccionada(){

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Log.e("ERROR","NO TAREA");
        }

        if (tarea_Seleccionada.getProduccion_Lista().size()>0){
            for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
                Log.e("Producción_producion",lg.toString());
                produccion_actual = lg;
            }

//este hay que sacarlo de aca
            TareaSingleton.SingletonInstance().setProduccionId(produccion_actual.getProduccionId());
            adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
            adapterProduccion.notifyDataSetChanged();

        }else{
            Log.e("ERROR","NO PRODUCCION");

        }

        if (tarea_Seleccionada.getBobinas().size()>0){
            for (Bobinas lg : tarea_Seleccionada.getBobinas()) {
                Log.e("Producción_Bobin",lg.toString());
                bobinas_actual = lg;
            }

            adapterBobina.setNotes(tarea_Seleccionada.getBobinas());
            adapterBobina.notifyDataSetChanged();
        }

    }

    @Override
    public void ResultadoCantidadDialogo(float valorFloat) {

        Log.e("ProduccionActivity","Cantidad: " + valorFloat);

        produccion_actual.setRollosEmpaquetados(valorFloat);
        produccion_actual.setRollosFabricdos(valorFloat);
        produccion_actual.setMetrosImpresos(valorFloat);

        Map<String, Object> produccion = new HashMap<>();
        produccion.put("ProduccionId", produccion_actual.getProduccionId());
        produccion.put("PedidoId", produccion_actual.getPedidoId());
        produccion.put("TareaId", produccion_actual.getTareaId());
        produccion.put("MetrosImpresos", produccion_actual.getMetrosImpresos());
        produccion.put("AnchoFinalRolloYGap", produccion_actual.getAnchoFinalRollo());
        produccion.put("CantidadPistasImpresas", produccion_actual.getCantidadPistasImpresas());
        produccion.put("CantidadTintas", produccion_actual.getCantidadTintas());
        produccion.put("AnchoBobinaUsadoCm", produccion_actual.getAnchoBobinaUsadoCm());
        produccion.put("ScrapAjusteInicial", produccion_actual.getScrapAjusteInicial());
        produccion.put("ScrapAjusteInicial_Unidades", produccion_actual.getScrapAjusteInicial_Unidades());
        produccion.put("ScrapAjusteProduccion", produccion_actual.getScrapAjusteProduccion());
        produccion.put("ScrapAjusteProduccion_Unidades", produccion_actual.getScrapAjusteProduccion_Unidades());
        produccion.put("ObservacionesCierre", produccion_actual.getObservacionesCierre());
        produccion.put("RollosFabricdos", produccion_actual.getRollosFabricdos());
        produccion.put("AnchoFinalRollo", produccion_actual.getAnchoFinalRollo());
        produccion.put("CantidadPistasCortadas", produccion_actual.getCantidadPistasCortadas());
        produccion.put("PistasTroquelUsadas", produccion_actual.getPistasTroquelUsadas());
        produccion.put("RollosEmpaquetados", produccion_actual.getRollosEmpaquetados());
        produccion.put("UsuarioId", produccion_actual.getUsuarioId());


        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                actualziarTarea();
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


        produccion_actual.setScrapAjusteProduccion(cantidad);
        produccion_actual.setScrapAjusteProduccion_Unidades(unidad);


        Map<String, Object> produccion = new HashMap<>();
        produccion.put("ProduccionId", produccion_actual.getProduccionId());
        produccion.put("PedidoId", produccion_actual.getPedidoId());
        produccion.put("TareaId", produccion_actual.getTareaId());
        produccion.put("MetrosImpresos", produccion_actual.getMetrosImpresos());
        produccion.put("AnchoFinalRolloYGap", produccion_actual.getAnchoFinalRollo());
        produccion.put("CantidadPistasImpresas", produccion_actual.getCantidadPistasImpresas());
        produccion.put("CantidadTintas", produccion_actual.getCantidadTintas());
        produccion.put("AnchoBobinaUsadoCm", produccion_actual.getAnchoBobinaUsadoCm());
        produccion.put("ScrapAjusteInicial", produccion_actual.getScrapAjusteInicial());
        produccion.put("ScrapAjusteInicial_Unidades", produccion_actual.getScrapAjusteInicial_Unidades());
        produccion.put("ScrapAjusteProduccion", produccion_actual.getScrapAjusteProduccion());
        produccion.put("ScrapAjusteProduccion_Unidades", produccion_actual.getScrapAjusteProduccion_Unidades());
        produccion.put("ObservacionesCierre", produccion_actual.getObservacionesCierre());
        produccion.put("RollosFabricdos", produccion_actual.getRollosFabricdos());
        produccion.put("AnchoFinalRollo", produccion_actual.getAnchoFinalRollo());
        produccion.put("CantidadPistasCortadas", produccion_actual.getCantidadPistasCortadas());
        produccion.put("PistasTroquelUsadas", produccion_actual.getPistasTroquelUsadas());
        produccion.put("RollosEmpaquetados", produccion_actual.getRollosEmpaquetados());
        produccion.put("UsuarioId", produccion_actual.getUsuarioId());

        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                actualziarTarea();

            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void actualziarTarea() {

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);
        int p = tarea_Seleccionada.getPedidoId();
        int t = tarea_Seleccionada.getTareaId();

        httpLayer.getTareas("0/0",new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                for (Tareas tareatemp : response) {

                    if (tareatemp.getPedidoId()==p && tareatemp.getTareaId()==t){


                        TareaSingleton.SingletonInstance().setTarea(tareatemp);

                        break;
                    }

                }
                cargarTareaSeleccionada();

                dialogProgress.dismiss();

            }
            @Override
            public void onError(Exception e) {

                Log.e("error_verificarActvity",e.toString());
                dialogProgress.dismiss();
            }
        });
    }

    /*
    private void actualziarTarea2() {

        //todo registrar id pedido y tarea id

        String params = "/"+tarea_Seleccionada.getPedidoId()+"/"+tarea_Seleccionada.getTareaId();

        Log.e("Produccion_parametros",params);

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);
        httpLayer.getTareaEspecifica(params, new HttpLayer.HttpLayerResponses<Tareas>() {
            @Override
            public void onSuccess(Tareas response) {

                Log.e("ProduccionActivity","actualzandoTarea");

                TareaSingleton.SingletonInstance().setTarea(response);
                cargarTareaSeleccionada();
                dialogProgress.dismiss();

            }

            @Override
            public void onError(Exception e) {

                Log.e("ProduccionActivity","Error Actualziar Tarea");
                dialogProgress.dismiss();
            }
        });
    }
*/

}