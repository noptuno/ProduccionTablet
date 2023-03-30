package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.R.id.constrain_produccion;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_USUARIO;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterBobinas;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.EstadosOp;
import com.codekolih.producciontablet.clases.Pedido;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.BobinaDialogo;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.codekolih.producciontablet.dialogs.CancelarDialog;
import com.codekolih.producciontablet.dialogs.FinTrabajoDialog;
import com.codekolih.producciontablet.dialogs.FinTurnoDialog;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Produccion_Activity extends OcultarTeclado implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog, CancelarDialog.finalizarMotivo, FinTurnoDialog.finalizarTurno, FinTrabajoDialog.finalizarTarea {


    Tareas tarea_Seleccionada;
    Pedido pedido_seleccionado;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;

    private TextView cantidadtotal;
    private float totaldadscrap;
    private TextView txt_imprenta, txt_usuario, txt_fecha, txt_hora;
    private TextView txt_SerieYNro, txt_ArticuloId, txt_Cantidad, txt_Concepto;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private Button btn_cantidad, btn_bobina, btn_scrap, btn_finalizar, btn_cancelar, btn_finturno;
    private int BOBINA_ACTIVITY = 1;
    RecyclerView recyclerViewCantidad, recyclerViewBobinas;
    private int produccionId;
    private int pedidoId;
    private int tareaId;
    private String USUARIO;
    private SharedPreferences pref;
    private int MAQUINAID = 0;

    private boolean cargobobina = false;
    private boolean cargocantidad = false;
    private boolean cargascrap = false;

    private static final int CODIGO_PARA_LA_ACTIVIDAD_2 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        Log.e("ProduccionActivity", "INICIO");

        findrid();


        requestQueue = Volley.newRequestQueue(this);
        httpLayer = new HttpLayer(this);
        recyclerViewCantidad = findViewById(R.id.produccion_cantidad_recycler);
        recyclerViewBobinas = findViewById(R.id.produccion_bobina_recycler);


        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        txt_imprenta.setText(String.format("%s Tipo: %s", pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"), pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "0")));
        txt_usuario.setText(pref.getString(PREF_PRODUCCION_USUARIO, "NO"));
        MAQUINAID = Integer.parseInt(pref.getString(PREF_PRODUCCION_MAQUINAID, "0"));

        //VALIDAR
        produccionId = TareaSingleton.SingletonInstance().getProduccionId();
        pedidoId = TareaSingleton.SingletonInstance().getTarea().getPedidoId();
        tareaId = TareaSingleton.SingletonInstance().getTarea().getTareaId();
        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();
        txt_usuario.setText(USUARIO);


        Log.e("IdProduccionSelec", "" + produccionId);

        ConstraintLayout constraintLayout = findViewById(R.id.constrain_produccion);

        addKeyboardHideListener(constraintLayout);

        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cargascrap) {
                    if (cargocantidad) {
                        if (cargobobina) {
                            new FinTrabajoDialog(Produccion_Activity.this, Produccion_Activity.this);
                        } else {
                            Toast.makeText(getApplicationContext(), "Hay que cargar Bobina", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Hay que cargar Produccion", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Hay que cargar Scrap", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_finturno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (cargascrap) {
                        if (cargocantidad) {
                            if (cargobobina) {
                                finalizar();
                            } else {
                                Toast.makeText(getApplicationContext(), "Hay que cargar Bobina", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Hay que cargar Produccion", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Hay que cargar Scrap", Toast.LENGTH_SHORT).show();
                    }





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

                new CantidadDialog(Produccion_Activity.this, Produccion_Activity.this);

            }
        });

        btn_bobina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   new BobinaDialogo(Produccion_Activity.this, Produccion_Activity.this);


                Intent intent = new Intent(Produccion_Activity.this, Bobina_Activity.class);
               // intent.putExtra("tareaId", tareaid);
               // intent.putExtra("produccionId", produccionid);
                startActivityForResult(intent, CODIGO_PARA_LA_ACTIVIDAD_2);


                /*
                Intent i = new Intent(Produccion_Activity.this, BobinaActivity.class);
                startActivityForResult(i, BOBINA_ACTIVITY);
                */


            }
        });

        btn_scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ScrapDialogo(Produccion_Activity.this, Produccion_Activity.this);

            }
        });

        recyclerViewCantidad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCantidad.setAdapter(adapterProduccion);

        recyclerViewBobinas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewBobinas.setAdapter(adapterBobina);


        cargarEstadoProduccion();
        ocultarVariables();
        cargarfecha();





    }

    private void cargartodoslosestados(Tareas a) {

        List<EstadosOp> response = a.getEstadosOp();

        for (EstadosOp tareatemp : response) {

            Log.e("Estados","Tarea: "+ tareatemp.getTareaId() + "\n"+
                    "id estado: "+tareatemp.getEstadoId() + "\n"+
                    "nombreEstado: " +tareatemp.getNombreEstado() + "\n"+
                    "fecha: " +tareatemp.getFechaInicio() + "\n"

            );

        }

    }

    private void cargarEstadoProduccion() {

        Map<String, Object> estado = new HashMap<>();
        estado.put("TareaId", tareaId);
        estado.put("EstadoId", "P1");
        estado.put("TipoEstadoId", "I");

        //sin finish

        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("Produccion_Activity", "Cargo Estado Produccion P1 en I " + tareaId);

                cargarTareaHttp(); // tarea y produccion_actual y bobina actual

            }

            @Override
            public void onError(Exception e) {

                dialogError("No cargo el estado inicial");


            }
        }, USUARIO);

    }

    private void finalizar() {


        new FinTurnoDialog(Produccion_Activity.this, Produccion_Activity.this);

    }

    private void cancelar() {


        new CancelarDialog(Produccion_Activity.this, Produccion_Activity.this);


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


            if ("EtiquetasPorRollo".equals(entry.getKey())) {
                lyp_EtiquetasPorRollo.setVisibility(parseInt(entry.getValue()));
            } else if ("EtiquetasEnBanda".equals(entry.getKey())) {
                lyp_EtiquetasEnBanda.setVisibility(parseInt(entry.getValue()));
            } else if ("Pistas".equals(entry.getKey())) {
                lyp_Pistas.setVisibility(parseInt(entry.getValue()));
            } else if ("Observaciones".equals(entry.getKey())) {
                lyp_Observaciones.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosAImprimir".equals(entry.getKey())) {
                lyp_MetrosAImprimir.setVisibility(parseInt(entry.getValue()));
            } else if ("NroDeSobre".equals(entry.getKey())) {
                lyp_NroDeSobre.setVisibility(parseInt(entry.getValue()));
            } else if ("Descripcion".equals(entry.getKey())) {
                lyp_Descripcion.setVisibility(parseInt(entry.getValue()));
            } else if ("Cilindro".equals(entry.getKey())) {
                lyp_Cilindro.setVisibility(parseInt(entry.getValue()));
            } else if ("Z_AltoMasGap".equals(entry.getKey())) {
                lyp_Z_AltoMasGap.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosPorRollo".equals(entry.getKey())) {
                lyp_MetrosPorRollo.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosMatTroquelar".equals(entry.getKey())) {
                lyp_MetrosMatTroquelar.setVisibility(parseInt(entry.getValue()));
            } else if ("TroquelId".equals(entry.getKey())) {
                lyp_TroquelId.setVisibility(parseInt(entry.getValue()));
            }

        }
    }

    private void findrid() {

        txt_imprenta = findViewById(R.id.produccion_txt_imprenta);
        txt_usuario = findViewById(R.id.produccion_txt_usuario);
        txt_fecha = findViewById(R.id.produccion_txt_fecha);
        txt_hora = findViewById(R.id.produccion_txt_hora);

        txt_SerieYNro = findViewById(R.id.pro_txt_SerieYNro);
        txt_ArticuloId = findViewById(R.id.pro_txt_ArticuloId);
        txt_Cantidad = findViewById(R.id.pro_txt_Cantidad);
        txt_Concepto = findViewById(R.id.pro_txt_Concepto);

        btn_cantidad = findViewById(R.id.produccion_btn_cantidad);
        btn_bobina = findViewById(R.id.produccion_btn_bobina);
        btn_scrap = findViewById(R.id.produccion_btn_scrap);
        btn_finalizar = findViewById(R.id.produccion_btn_finalziar);
        btn_cancelar = findViewById(R.id.produccion_btn_cancelar);
        btn_finturno = findViewById(R.id.produccion_btn_finalziar2);
        cantidadtotal = findViewById(R.id.txt_produccion_cantidadtotal);


        //datos tarea
        lyp_EtiquetasPorRollo = findViewById(R.id.lyp_EtiquetasPorRollo);
        lyp_EtiquetasEnBanda = findViewById(R.id.lyp_EtiquetasEnBanda);
        lyp_Pistas = findViewById(R.id.lyp_Pistas);
        lyp_Observaciones = findViewById(R.id.lyp_Observaciones);
        lyp_MetrosAImprimir = findViewById(R.id.lyp_MetrosAImprimir);
        lyp_NroDeSobre = findViewById(R.id.lyp_NroDeSobre);
        lyp_Descripcion = findViewById(R.id.lyp_Descripcion);
        lyp_Cilindro = findViewById(R.id.lyp_Cilindro);
        lyp_Z_AltoMasGap = findViewById(R.id.lyp_Z_AltoMasGap);
        lyp_MetrosPorRollo = findViewById(R.id.lyp_MetrosPorRollo);
        lyp_MetrosMatTroquelar = findViewById(R.id.lyp_MetrosMatTroquelar);
        lyp_TroquelId = findViewById(R.id.lyp_TroquelId);


        txt_produccion_txt_NroDeSobre = findViewById(R.id.produccion_txt_NroDeSobre);
        txt_produccion_txt_Descripcion = findViewById(R.id.produccion_txt_Descripcion);
        txt_produccion_txt_MetrosAImprimir = findViewById(R.id.produccion_txt_MetrosAImprimir);
        txt_produccion_txt_MetrosPorRollo = findViewById(R.id.produccion_txt_MetrosPorRollo);
        txt_produccion_txt_Z_AltoMasGap = findViewById(R.id.produccion_txt_Z_AltoMasGap);
        txt_produccion_txt_Cilindro = findViewById(R.id.produccion_txt_Cilindro);
        txt_produccion_txt_Pistas = findViewById(R.id.produccion_txt_Pistas);
        txt_produccion_txt_EtiquetasEnBanda = findViewById(R.id.produccion_txt_EtiquetasEnBanda);
        txt_produccion_txt_EtiquetasPorRollo = findViewById(R.id.produccion_txt_EtiquetasPorRollo);
        txt_produccion_txt_TroquelId = findViewById(R.id.produccion_txt_TroquelId);
        txt_produccion_txt_MetrosMatTroquelar = findViewById(R.id.produccion_txt_MetrosMatTroquelar);
        txt_produccion_txt_Observaciones = findViewById(R.id.produccion_txt_Observaciones);

    }

    private void cambioEstado(Map<String, Object> estado) {

        Log.e("Resultado", "cambio estado ");
        dialogProgress = ProgressHUD.show(Produccion_Activity.this);


        Log.e("EstadoInsert", ""+ GsonUtils.toJSON(estado).toString());

        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("onSuccess CE", response.toString());
                dialogProgress.dismiss();
                finish();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Reintentar", Toast.LENGTH_SHORT).show();
                dialogProgress.dismiss();
            }
        }, USUARIO);

    }


    void cargarTareaHttp() {

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);

        httpLayer.getTareas(MAQUINAID+ "/O", new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                for (Tareas tareatemp : response) {

                    Log.e("MSG","" + response.toString());

                    if (tareatemp.getPedidoId() == pedidoId && tareatemp.getTareaId() == tareaId) {
                        TareaSingleton.SingletonInstance().setTarea(tareatemp);
                        cargartodoslosestados(tareatemp);
                        break;
                    }
                }

                dialogProgress.dismiss();
                cargarTareaSeleccionada();
            }

            @Override
            public void onError(Exception e) {

                dialogError("No Cargo Tarea");
                Log.e("error_produccion", e.toString());
                dialogProgress.dismiss();
            }
        });
    }

    void cargarTareaSeleccionada() {

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea()) == null) {

            Toast.makeText(getApplicationContext(), "Hubo un problema critico con la carga de Tarea Seleecionada", Toast.LENGTH_SHORT).show();

            finish();
        }

        float contcantidad = 0;
        float contscrap = 0;

        if (tarea_Seleccionada.getProduccion_Lista().size() > 0) {
            for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
                Log.e("BDproduccion", lg.toString());

                for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {
                    if ("SumMetrosImpresos".equals(entry.getKey())) {
                        if (entry.getValue().equals("0")) {
                            contcantidad += lg.getMetrosImpresos();

                        }
                    } else if ("SumRollosFabricados".equals(entry.getKey())) {
                        if (entry.getValue().equals("0")) {
                            contcantidad += lg.getRollosFabricdos();

                        }
                    } else if ("SumRollosEmpaquedatos".equals(entry.getKey())) {
                        if (entry.getValue().equals("0")) {
                            contcantidad += lg.getRollosEmpaquetados();
                        }
                    }
                }

                if (lg.getProduccionId() == produccionId) {
                    produccion_actual = lg;
                }

                contscrap += lg.getScrapAjusteProduccion();
            }

            totaldadscrap = contscrap;
            cantidadtotal.setText(String.format("%s", contcantidad));

            adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
            adapterProduccion.notifyDataSetChanged();

        } else {
            Log.e("MSG", "no hay produccion para mostrar");

        }

        if (tarea_Seleccionada.getBobinas().size() > 0) {
            for (Bobinas lg : tarea_Seleccionada.getBobinas()) {
                Log.e("Producción_Bobin", lg.toString());
                bobinas_actual = lg;
            }

            adapterBobina.setNotes(tarea_Seleccionada.getBobinas());
            adapterBobina.notifyDataSetChanged();
        }

        if (tarea_Seleccionada != null) {
            //datos tarea


            txt_SerieYNro.setText(tarea_Seleccionada.getSerieYNro());
            txt_ArticuloId.setText(tarea_Seleccionada.getArticuloId());
            txt_Cantidad.setText(String.format("%s", tarea_Seleccionada.getCantidad()));
            txt_Concepto.setText(tarea_Seleccionada.getConcepto());

            txt_produccion_txt_NroDeSobre.setText(String.format("%s", tarea_Seleccionada.getNroDeSobre()));
            txt_produccion_txt_Descripcion.setText(tarea_Seleccionada.getDescripcion());
            txt_produccion_txt_MetrosAImprimir.setText(String.format("%s", tarea_Seleccionada.getMetrosAImprimir()));
            txt_produccion_txt_MetrosPorRollo.setText(String.format("%s", tarea_Seleccionada.getMetrosPorRollo()));
            txt_produccion_txt_Z_AltoMasGap.setText(String.format("%s", tarea_Seleccionada.getZ_AltoMasGap()));
            txt_produccion_txt_Cilindro.setText(String.format("%s", tarea_Seleccionada.getCilindro()));
            txt_produccion_txt_Pistas.setText(String.format("%s", tarea_Seleccionada.getPistas()));
            txt_produccion_txt_EtiquetasEnBanda.setText(String.format("%s", tarea_Seleccionada.getEtiquetasEnBanda()));
            txt_produccion_txt_EtiquetasPorRollo.setText(String.format("%s", tarea_Seleccionada.getEtiquetasPorRollo()));
            txt_produccion_txt_TroquelId.setText(String.format("%s", tarea_Seleccionada.getTroquelId()));
            txt_produccion_txt_MetrosMatTroquelar.setText(String.format("%s", tarea_Seleccionada.getMetrosMatTroquelar()));
            txt_produccion_txt_Observaciones.setText(tarea_Seleccionada.getObservaciones());

        }



    }

    @Override
    public void ResultadoCantidadDialogo(float valorFloat) {

        Log.e("ProduccionActivity", "Cantidad: " + valorFloat);

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            if ("SumMetrosImpresos".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    produccion_actual.setMetrosImpresos(valorFloat);
                }
            } else if ("SumRollosFabricados".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    produccion_actual.setRollosFabricdos(valorFloat);
                }
            } else if ("SumRollosEmpaquedatos".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
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

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);
        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                dialogProgress.dismiss();
                cargocantidad = true;
                Log.e("onSuccess AP", response.toString());
                cargarTareaHttp();

                btn_cantidad.setText("MODIFICAR CANTIDAD");
            }

            @Override
            public void onError(Exception e) {

                dialogProgress.dismiss();
                dialogErrorPrintet("No Cargo Cantidad");
                Toast.makeText(getApplicationContext(), "No Cargo Cantidad Reintentar", Toast.LENGTH_SHORT).show();

            }
        }, USUARIO);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_PARA_LA_ACTIVIDAD_2 && resultCode == Bobina_Activity.RESULT_OK) {
            //Obtener el string enviado desde la actividad 2 utilizando la clave correspondiente


            String onSuccessRecibido = data.getStringExtra("onSuccess");


            Log.e("recibo",""+onSuccessRecibido);

            if (onSuccessRecibido.equals("ok")){
                cargobobina = true;
                cargarTareaHttp();
            }else{
                Toast.makeText(getApplicationContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void dialogErrorPrintet(String mensaje) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Produccion_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.alerdialogerror, null);
        final TextView mPassword = mView.findViewById(R.id.txtmensajeerror);
        Button mLogin = mView.findViewById(R.id.btnReintentar);
        mPassword.setText(mensaje);
        mBuilder.setView(mView);
        final AlertDialog dialogg = mBuilder.create();
        dialogg.show();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.dismiss();

            }
        });
    }


    @Override
    public void onBackPressed() {
        cancelar();
    }


    private void actualziarmotivoyestado(String motivo, Map<String, Object> estado) {

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
        produccion.put("ObservacionesCierre", motivo);
        produccion.put("RollosFabricdos", produccion_actual.getRollosFabricdos());
        produccion.put("AnchoFinalRollo", produccion_actual.getAnchoFinalRollo());
        produccion.put("CantidadPistasCortadas", produccion_actual.getCantidadPistasCortadas());
        produccion.put("PistasTroquelUsadas", produccion_actual.getPistasTroquelUsadas());
        produccion.put("RollosEmpaquetados", produccion_actual.getRollosEmpaquetados());
        produccion.put("UsuarioId", produccion_actual.getUsuarioId());

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);

        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                dialogProgress.dismiss();
                Log.e("onSuccess AP", response.toString());
                cambioEstado(estado);

            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();

                Toast.makeText(getApplicationContext(), "No Cargo Actualizo la Tarea Reintentar", Toast.LENGTH_SHORT).show();
                dialogErrorPrintet("No Cargo Motivo");

            }
        }, USUARIO);
    }


    @Override
    public void ResultadoMotivoDialogo(String motivo, String tipocierre) {

        Log.e("Resultado", "recibido" + motivo + " " + tipocierre);
        Map<String, Object> estado = new HashMap<>();
        estado.put("TareaId", tareaId);
        estado.put("EstadoId", tipocierre);
        estado.put("TipoEstadoId", "I");

        actualziarmotivoyestado(motivo, estado);
    }

    @Override
    public void ResultadoFinTurnoDialogo(String motivo, String tipocierre) {

        Log.e("Resultado", "recibido" + motivo + " " + tipocierre);
        Map<String, Object> estado = new HashMap<>();
        estado.put("TareaId", tareaId);
        estado.put("EstadoId", tipocierre);
        estado.put("TipoEstadoId", "I");

        actualziarmotivoyestado(motivo, estado);

    }

    @Override
    public void ResultadoFinTrabajoDialogo(String motivo, String tipocierre) {


        Map<String, Object> estado = new HashMap<>();
        estado.put("TareaId", tareaId);
        estado.put("EstadoId", tipocierre);
        estado.put("TipoEstadoId", "I");

        actualziarmotivoyestado(motivo, estado);

    }


    @Override
    public void ResultadoScrapDialogo(float cantidad, String unidad) {

        Log.e("ProduccionActivity", "Scrap: " + cantidad);


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


        dialogProgress = ProgressHUD.show(Produccion_Activity.this);

        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                dialogProgress.dismiss();
                cargarTareaHttp();
                btn_scrap.setText("MODIFICAR SCRAP");

                 cargascrap = true;

            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();
                Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar", Toast.LENGTH_SHORT).show();
                dialogErrorPrintet("No Cargo Scrap");

            }
        }, USUARIO);
    }


    private void dialogError(String mensaje) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Produccion_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.alerdialogerror, null);
        final TextView mPassword = mView.findViewById(R.id.txtmensajeerror);
        Button mLogin = mView.findViewById(R.id.btnReintentar);
        mPassword.setText(mensaje);
        mBuilder.setView(mView);
        final AlertDialog dialogg = mBuilder.create();
        dialogg.show();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.dismiss();


                if (mensaje.equals("No cargo el estado inicial")){

                    cargarEstadoProduccion();

                }else if(mensaje.equals("No Cargo Tarea")){

                    cargarTareaHttp();
                }

            }
        });
    }

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
            lyp_TroquelId;


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