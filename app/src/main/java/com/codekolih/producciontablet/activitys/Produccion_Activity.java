package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_USUARIO;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.codekolih.producciontablet.clases.Pedido;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.BobinaDialogo;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Produccion_Activity extends AppCompatActivity implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog, BobinaDialogo.finalizarBobinaDialog {

    Tareas tarea_Seleccionada;
    Pedido pedido_seleccionado;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;
    private TextView  txt_imprenta,txt_usuario,txt_fecha,txt_hora;

    private TextView  txt_SerieYNro,txt_ArticuloId,txt_Cantidad,txt_Concepto;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private Button btn_cantidad, btn_bobina,btn_scrap, btn_finalizar, btn_cancelar;
    private int BOBINA_ACTIVITY = 1;
    RecyclerView recyclerViewCantidad,recyclerViewBobinas;
    private int produccionId;
    private int pedidoId = TareaSingleton.SingletonInstance().getTarea().getPedidoId();
    private int tareaId = TareaSingleton.SingletonInstance().getTarea().getTareaId();
    private SharedPreferences pref;
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


        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        txt_imprenta.setText(String.format("%s Tipo: %s", pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"), pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "0")));
        txt_usuario.setText(pref.getString(PREF_PRODUCCION_USUARIO, "NO"));


        //VALIDAR
        produccionId = TareaSingleton.SingletonInstance().getProduccionId();
        pedidoId = TareaSingleton.SingletonInstance().getTarea().getPedidoId();
        tareaId = TareaSingleton.SingletonInstance().getTarea().getTareaId();

        pedido_seleccionado = TareaSingleton.SingletonInstance().getPedidoInstanciada();
        if (pedido_seleccionado!=null){
            txt_SerieYNro.setText(pedido_seleccionado.getSerieYNro());
            txt_ArticuloId.setText(pedido_seleccionado.getArticuloId());
            txt_Cantidad.setText(String.format("%s", pedido_seleccionado.getCantidad()));
            txt_Concepto.setText(pedido_seleccionado.getConcepto());
        }

        Log.e("IdProduccionSelec",""+produccionId);


        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder build4 = new AlertDialog.Builder(Produccion_Activity.this);
                build4.setMessage("¿Desea Finalizar la Produccion? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                AlertDialog alertDialog4 = build4.create();
                alertDialog4.show();


            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               cancelar();



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

        cargarTareaHttp(); // tarea y produccion_actual y bobina actual
        ocultarVariables();
        cargarfecha();

    }

    private void cancelar() {

        AlertDialog.Builder build4 = new AlertDialog.Builder(Produccion_Activity.this);
        build4.setMessage("¿Desea Cancelar? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Map<String, Object> estado = new HashMap<>();
                estado.put("TareaId", tarea_Seleccionada.getTareaId());
                estado.put("EstadoId", "C1");
                estado.put("TipoEstadoId","F" );
                cambioEstado(estado);

            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog4 = build4.create();
        alertDialog4.show();
    }

    private void cargarfecha() {

        SimpleDateFormat dateFormatcorta = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat horaFormatcorta = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String fechaCortaLocal = dateFormatcorta.format(date);
        String horaCortaLocal = horaFormatcorta.format(date);

        txt_fecha.setText(fechaCortaLocal);
        txt_hora.setText(horaCortaLocal);

    }

    private void ocultarVariables() {


            for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

                System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());


                if ("EtiquetasPorRollo".equals(entry.getKey())){
                    lyp_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
                }  else if ("EtiquetasEnBanda".equals(entry.getKey())){
                    lyp_EtiquetasEnBanda.setVisibility(parseInt(entry.getValue()));
                } else if ("Pistas".equals(entry.getKey())){
                    lyp_Pistas.setVisibility(parseInt(entry.getValue()));
                }else if ("Observaciones".equals(entry.getKey())){
                    lyp_Observaciones.setVisibility(parseInt(entry.getValue()));
                }else if ("MetrosAImprimir".equals(entry.getKey())){
                    lyp_MetrosAImprimir.setVisibility(parseInt(entry.getValue()));
                }else if ("NroDeSobre".equals(entry.getKey())){
                    lyp_NroDeSobre.setVisibility(parseInt(entry.getValue()));
                }else if ("Descripcion".equals(entry.getKey())){
                    lyp_Descripcion.setVisibility(parseInt(entry.getValue()));
                }else if ("Cilindro".equals(entry.getKey())){
                    lyp_Cilindro.setVisibility(parseInt(entry.getValue()));
                }else if ("Z_AltoMasGap".equals(entry.getKey())){
                    lyp_Z_AltoMasGap.setVisibility(parseInt(entry.getValue()));
                }else if ("MetrosPorRollo".equals(entry.getKey())){
                    lyp_MetrosPorRollo.setVisibility(parseInt(entry.getValue()));
                }else if ("MetrosMatTroquelar".equals(entry.getKey())){
                    lyp_MetrosMatTroquelar.setVisibility(parseInt(entry.getValue()));
                }else if ("TroquelId".equals(entry.getKey())){
                    lyp_TroquelId.setVisibility(parseInt(entry.getValue()));
                }


                if ("AnchoFinalRolloYGap".equals(entry.getKey())){
                    lyp_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadPistasImpresas".equals(entry.getKey())){
                    lyp_CantidadPistasImpresas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadTintas".equals(entry.getKey())){
                    lyp_CantidadTintas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("ScrapAjusteInicial".equals(entry.getKey())){
                    lyp_ScrapAjusteInicial.setVisibility(parseInt(entry.getValue()));
                }
                else if ("UnidadIdScrapInicial".equals(entry.getKey())){
                    lyp_UnidadIdScrapInicial.setVisibility(parseInt(entry.getValue()));
                }
                else if ("AnchoFinalRollo".equals(entry.getKey())){
                    lyp_AnchoFinalRollo.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadPistasCortadas".equals(entry.getKey())){
                    lyp_CantidadPistasCortadas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("PistasTroquelUsadas".equals(entry.getKey())){
                    lyp_PistasTroquelUsadas.setVisibility(parseInt(entry.getValue()));
                }

            }
    }

    private void findrid() {

        txt_imprenta = findViewById(R.id.produccion_txt_imprenta);
        txt_usuario= findViewById(R.id.produccion_txt_usuario);
        txt_fecha= findViewById(R.id.produccion_txt_fecha);
        txt_hora= findViewById(R.id.produccion_txt_hora);

        txt_SerieYNro= findViewById(R.id.pro_txt_SerieYNro);
        txt_ArticuloId= findViewById(R.id.pro_txt_ArticuloId);
        txt_Cantidad= findViewById(R.id.pro_txt_Cantidad);
        txt_Concepto= findViewById(R.id.pro_txt_Concepto);

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

        lyp_EtiquetasPorRollo= findViewById(R.id.lyp_EtiquetasPorRollo);
        lyp_EtiquetasEnBanda= findViewById(R.id.lyp_EtiquetasEnBanda);
        lyp_Pistas= findViewById(R.id.lyp_Pistas);
        lyp_Observaciones= findViewById(R.id.lyp_Observaciones);
        lyp_MetrosAImprimir= findViewById(R.id.lyp_MetrosAImprimir);
        lyp_NroDeSobre= findViewById(R.id.lyp_NroDeSobre);
        lyp_Descripcion= findViewById(R.id.lyp_Descripcion);
        lyp_Cilindro= findViewById(R.id.lyp_Cilindro);
        lyp_Z_AltoMasGap= findViewById(R.id.lyp_Z_AltoMasGap);
        lyp_MetrosPorRollo= findViewById(R.id.lyp_MetrosPorRollo);
        lyp_MetrosMatTroquelar= findViewById(R.id.lyp_MetrosMatTroquelar);
        lyp_TroquelId= findViewById(R.id.lyp_TroquelId);

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

        txt_produccion_txt_TroquelId= findViewById(R.id.produccion_txt_TroquelId);
        txt_produccion_txt_MetrosMatTroquelar= findViewById(R.id.produccion_txt_MetrosMatTroquelar);
        txt_produccion_txt_Observaciones= findViewById(R.id.produccion_txt_Observaciones);

    }

    private void cambioEstado( Map<String, Object> estado) {


            dialogProgress = ProgressHUD.show(Produccion_Activity.this);

            httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.e("Produccion_Activity","Cargo Estado" + tarea_Seleccionada.getTareaId());
                    dialogProgress.dismiss();
                    finish();
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Produccion_Activity","Error al cargar Estado" + tarea_Seleccionada.getTareaId());
                    dialogProgress.dismiss();
                }
            });

    }


    void cargarTareaHttp(){



        dialogProgress = ProgressHUD.show(Produccion_Activity.this);

        httpLayer.getTareas("0/0",new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                for (Tareas tareatemp : response) {

                    if (tareatemp.getPedidoId()==pedidoId && tareatemp.getTareaId()==tareaId){
                        TareaSingleton.SingletonInstance().setTarea(tareatemp);
                        break;
                    }
                }

                dialogProgress.dismiss();
                cargarTareaSeleccionada();
            }
            @Override
            public void onError(Exception e) {

                Log.e("error_produccion",e.toString());
                dialogProgress.dismiss();
            }
        });
    }

    void cargarTareaSeleccionada(){

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Log.e("ERROR","NO TAREA");
        }

        if (tarea_Seleccionada.getProduccion_Lista().size()>0){
            for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
                Log.e("BDproduccion",lg.toString());

                if (lg.getProduccionId()==produccionId){
                    produccion_actual = lg;
                }
              //
            }
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
            txt_produccion_txt_NroDeSobre.setText(String.format("%s",tarea_Seleccionada.getNroDeSobre()));
            txt_produccion_txt_Descripcion.setText(tarea_Seleccionada.getDescripcion());
            txt_produccion_txt_MetrosAImprimir.setText(String.format("%s",tarea_Seleccionada.getMetrosAImprimir()));
            txt_produccion_txt_MetrosPorRollo.setText(String.format("%s",tarea_Seleccionada.getMetrosPorRollo()));
            txt_produccion_txt_Z_AltoMasGap.setText(String.format("%s",tarea_Seleccionada.getZ_AltoMasGap()));
            txt_produccion_txt_Cilindro.setText(String.format("%s",tarea_Seleccionada.getCilindro()));
            txt_produccion_txt_Pistas.setText(String.format("%s",tarea_Seleccionada.getPistas()));
            txt_produccion_txt_EtiquetasEnBanda.setText(String.format("%s",tarea_Seleccionada.getEtiquetasEnBanda()));
            txt_produccion_txt_EtiquetasPorRollo.setText(String.format("%s",tarea_Seleccionada.getEtiquetasPorRollo()));
            txt_produccion_txt_TroquelId.setText(String.format("%s",tarea_Seleccionada.getTroquelId()));;
            txt_produccion_txt_MetrosMatTroquelar.setText(String.format("%s",tarea_Seleccionada.getMetrosMatTroquelar()));
            txt_produccion_txt_Observaciones.setText(tarea_Seleccionada.getObservaciones());

        }

        if (produccion_actual!=null){
            //datos cargados
            txt_produccion_AnchoFinalRolloYGap.setText(String.format("%s", produccion_actual.getAnchoFinalRolloYGap()));
            txt_produccion_CantidadPistasImpresas.setText(String.format("%s", produccion_actual.getCantidadPistasImpresas()));
            txt_produccion_CantidadTintas.setText(String.format("%s", produccion_actual.getCantidadTintas()));
            txt_produccion_ScrapAjusteInicial.setText(String.format("%s", produccion_actual.getScrapAjusteInicial()));
            txt_produccion_AnchoFinalRollo.setText(String.format("%s", produccion_actual.getAnchoFinalRollo()));
            txt_produccion_CantidadPistasCortadas.setText(String.format("%s", produccion_actual.getCantidadPistasCortadas()));
            txt_produccion_PistasTroquelUsadas.setText(String.format("%s", produccion_actual.getPistasTroquelUsadas()));
            txt_produccion_UnidadIdScrapInicial.setText(String.format("%s", produccion_actual.getScrapAjusteInicial_Unidades()));
        }

    }

    @Override
    public void ResultadoCantidadDialogo(float valorFloat) {

        Log.e("ProduccionActivity","Cantidad: " + valorFloat);

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            if ("SumMetrosImpresos".equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    produccion_actual.setMetrosImpresos(valorFloat);
                }


            } else if ("SumRollosFabricados".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    produccion_actual.setRollosFabricdos(valorFloat);
                }


            }
            else if ("SumRollosEmpaquedatos".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    produccion_actual.setRollosEmpaquetados(valorFloat);
                }


            }
        }

        Map<String, Object> produccion = new HashMap<>();
        produccion.put("ProduccionId", produccionId);
        produccion.put("PedidoId", pedidoId);
        produccion.put("TareaId", tareaId);
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

                cargarTareaHttp();
            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo Cantidad Reintentar",Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void ResultadoBobinaDialogo(int ProveedorId, String ProveedorNombre, String Lote, float Ancho, String EsAbiertaoCerrada, float DefectuosaKg, int idmaterial, String nombreMaterial) {

        Bobinas bobinacargar = new Bobinas();
        bobinacargar.setBobinaId(0);
        bobinacargar.setTareaId(tareaId);
        bobinacargar.setProduccionId(produccionId);
        bobinacargar.setProveedorId(ProveedorId);
        bobinacargar.setProveedorNombre(ProveedorNombre);
        bobinacargar.setLote(Lote);
        bobinacargar.setAncho(Ancho);
        bobinacargar.setTipoMaterialId(idmaterial);
        bobinacargar.setEsAbiertaoCerrada(EsAbiertaoCerrada);
        bobinacargar.setDefectuosaKg(DefectuosaKg);
        bobinacargar.setNombreTipoMaterial(nombreMaterial);

        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Bobina_activit",response.toString());

                cargarTareaHttp();


            }

            @Override
            public void onError(Exception e) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        cancelar();
    }


    @Override
    public void ResultadoScrapDialogo(float cantidad, String unidad) {

        Log.e("ProduccionActivity","Scrap: " + cantidad);


        produccion_actual.setScrapAjusteProduccion(cantidad);
        produccion_actual.setScrapAjusteProduccion_Unidades(unidad);


        Map<String, Object> produccion = new HashMap<>();
        produccion.put("ProduccionId", produccionId);
        produccion.put("PedidoId", pedidoId);
        produccion.put("TareaId", tareaId);
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

                cargarTareaHttp();

            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar",Toast.LENGTH_SHORT).show();

            }
        });
    }


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


            lyp_EtiquetasPorRollo,
            lyp_EtiquetasEnBanda,
            lyp_Pistas,
            lyp_Observaciones,
            lyp_MetrosAImprimir,
            lyp_NroDeSobre,
            lyp_Descripcion,
            lyp_Cilindro,
            lyp_Z_AltoMasGap,
            lyp_MetrosPorRollo,
            lyp_MetrosMatTroquelar,
            lyp_TroquelId,

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
            txt_produccion_txt_EtiquetasPorRollo,
            txt_produccion_txt_TroquelId,
             txt_produccion_txt_MetrosMatTroquelar,
             txt_produccion_txt_Observaciones;



}