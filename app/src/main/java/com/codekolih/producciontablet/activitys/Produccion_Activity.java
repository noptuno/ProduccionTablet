package com.codekolih.producciontablet.activitys;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.codekolih.producciontablet.dialogs.BobinaDialogo;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Produccion_Activity extends AppCompatActivity implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog, BobinaDialogo.finalizarBobinaDialog {

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

        findrid();

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

                new BobinaDialogo(Produccion_Activity.this, Produccion_Activity.this);

                /*
                Intent i = new Intent(Produccion_Activity.this, BobinaActivity.class);
                startActivityForResult(i, BOBINA_ACTIVITY);
                */


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

    private void findrid() {

        btn_cantidad = findViewById(R.id.produccion_btn_cantidad);
        btn_bobina = findViewById(R.id.produccion_btn_bobina);
        btn_scrap = findViewById(R.id.produccion_btn_scrap);
        btn_finalizar = findViewById(R.id.produccion_btn_finalziar);
        btn_cancelar= findViewById(R.id.produccion_btn_cancelar);

                txt_produccion_AnchoFinalRolloYGap = findViewById(R.id.produccion_txt_AnchoFinalRolloYGap);
                txt_produccion_CantidadPistasImpresas = findViewById(R.id.produccion_txt_CantidadPistasImpresas);
                txt_produccion_CantidadTintas = findViewById(R.id.produccion_txt_CantidadTintas);
                txt_produccion_ScrapAjusteInicial = findViewById(R.id.produccion_txt_ScrapAjusteInicial);
                txt_produccion_AnchoFinalRollo = findViewById(R.id.produccion_txt_AnchoFinalRollo);
                txt_produccion_CantidadPistasCortadas = findViewById(R.id.produccion_txt_CantidadPistasCortadas);
                txt_produccion_PistasTroquelUsadas = findViewById(R.id.produccion_txt_PistasTroquelUsadas);
                txt_produccion_UnidadIdScrapInicial = findViewById(R.id.produccion_txt_UnidadIdScrapInicial);


                lyp_AnchoFinalRolloYGap = findViewById(R.id.lyp_AnchoFinalRolloYGap);
                lyp_CantidadPistasImpresas = findViewById(R.id.lyp_CantidadPistasImpresas);
                lyp_CantidadTintas = findViewById(R.id.lyp_CantidadTintas);
                lyp_ScrapAjusteInicial = findViewById(R.id.lyp_ScrapAjusteInicial);
                lyp_AnchoFinalRollo = findViewById(R.id.lyp_AnchoFinalRollo);
                lyp_CantidadPistasCortadas = findViewById(R.id.lyp_CantidadPistasCortadas);
                lyp_PistasTroquelUsadas = findViewById(R.id.lyp_PistasTroquelUsadas);
                lyp_UnidadIdScrapInicial = findViewById(R.id.lyp_UnidadIdScrapInicial);


                txt_produccion_txt_NroDeSobre = findViewById(R.id.produccion_txt_NroDeSobre);
                txt_produccion_txt_Descripcion = findViewById(R.id.produccion_txt_Descripcion);
                txt_produccion_txt_MetrosAImprimir = findViewById(R.id.produccion_txt_MetrosAImprimir);
                txt_produccion_txt_MetrosPorRollo = findViewById(R.id.produccion_txt_MetrosPorRollo);
                txt_produccion_txt_Z_AltoMasGap = findViewById(R.id.produccion_txt_Z_AltoMasGap);
                txt_produccion_txt_Cilindro = findViewById(R.id.produccion_txt_Cilindro);
                txt_produccion_txt_Pistas = findViewById(R.id.produccion_txt_Pistas);
                txt_produccion_txt_EtiquetasEnBanda = findViewById(R.id.produccion_txt_EtiquetasEnBanda);
                txt_produccion_txt_EtiquetasPorRollo = findViewById(R.id.produccion_txt_EtiquetasPorRollo);
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

        if (tarea_Seleccionada!=null){
            //datos tarea
            txt_produccion_txt_NroDeSobre.setText(""+tarea_Seleccionada.getNroDeSobre());
            txt_produccion_txt_Descripcion.setText(""+tarea_Seleccionada.getDescripcion());
            txt_produccion_txt_MetrosAImprimir.setText(""+tarea_Seleccionada.getMetrosAImprimir());
            txt_produccion_txt_MetrosPorRollo.setText(""+tarea_Seleccionada.getMetrosPorRollo());
            txt_produccion_txt_Z_AltoMasGap.setText(""+tarea_Seleccionada.getZ_AltoMasGap());
            txt_produccion_txt_Cilindro.setText(""+tarea_Seleccionada.getCilindro());
            txt_produccion_txt_Pistas.setText(""+tarea_Seleccionada.getPistas());
            txt_produccion_txt_EtiquetasEnBanda.setText(""+tarea_Seleccionada.getEtiquetasEnBanda());
            txt_produccion_txt_EtiquetasPorRollo.setText(""+tarea_Seleccionada.getEtiquetasPorRollo());
        }

        if (produccion_actual!=null){
            //datos cargados
            txt_produccion_AnchoFinalRolloYGap.setText(""+produccion_actual.getAnchoFinalRolloYGap());
            txt_produccion_CantidadPistasImpresas.setText(""+produccion_actual.getCantidadPistasImpresas());
            txt_produccion_CantidadTintas.setText(""+produccion_actual.getCantidadTintas());
            txt_produccion_ScrapAjusteInicial.setText(""+produccion_actual.getScrapAjusteInicial());
            txt_produccion_AnchoFinalRollo.setText(""+produccion_actual.getAnchoFinalRollo());
            txt_produccion_CantidadPistasCortadas.setText(""+produccion_actual.getCantidadPistasCortadas());
            txt_produccion_PistasTroquelUsadas.setText(""+produccion_actual.getPistasTroquelUsadas());
            txt_produccion_UnidadIdScrapInicial.setText(""+produccion_actual.getScrapAjusteInicial_Unidades());

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
    public void ResultadoBobinaDialogo(int ProveedorId, String ProveedorNombre, String Lote, float Ancho, String EsAbiertaoCerrada, float DefectuosaKg) {


        int tareaId = tarea_Seleccionada.getTareaId();
        int ProduccionId = produccion_actual.getProduccionId();
        int TipoMaterialId = 1;
        String  DeNombreTipoMaterial = "0";
        Bobinas bobinacargar = new Bobinas();

        bobinacargar.setBobinaId(0);
        bobinacargar.setTareaId(tareaId);
        bobinacargar.setProduccionId(ProduccionId);
        bobinacargar.setProveedorId(ProveedorId);
        bobinacargar.setProveedorNombre(ProveedorNombre);
        bobinacargar.setLote(Lote);
        bobinacargar.setAncho(Ancho);
        bobinacargar.setTipoMaterialId(TipoMaterialId);
        bobinacargar.setEsAbiertaoCerrada(EsAbiertaoCerrada);
        bobinacargar.setDefectuosaKg(DefectuosaKg);
        bobinacargar.setNombreTipoMaterial(DeNombreTipoMaterial);

        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Bobina_activit",response.toString());

                actualziarTarea();


            }

            @Override
            public void onError(Exception e) {

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


    private TextView
            txt_produccion_AnchoFinalRolloYGap,
            txt_produccion_CantidadPistasImpresas,
            txt_produccion_CantidadTintas,
            txt_produccion_ScrapAjusteInicial,
            txt_produccion_AnchoFinalRollo,
            txt_produccion_CantidadPistasCortadas,
            txt_produccion_PistasTroquelUsadas,
            txt_produccion_UnidadIdScrapInicial;

    private LinearLayout
            lyp_AnchoFinalRolloYGap,
            lyp_CantidadPistasImpresas,
            lyp_CantidadTintas,
            lyp_ScrapAjusteInicial,
            lyp_AnchoFinalRollo,
            lyp_CantidadPistasCortadas,
            lyp_PistasTroquelUsadas,
            lyp_UnidadIdScrapInicial;

    private TextView
            txt_produccion_txt_NroDeSobre,
            txt_produccion_txt_Descripcion,
            txt_produccion_txt_MetrosAImprimir,
            txt_produccion_txt_MetrosPorRollo,
            txt_produccion_txt_Z_AltoMasGap,
            txt_produccion_txt_Cilindro,
            txt_produccion_txt_Pistas,
            txt_produccion_txt_EtiquetasEnBanda,
            txt_produccion_txt_EtiquetasPorRollo;



}