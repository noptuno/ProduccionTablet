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
import com.codekolih.producciontablet.dialogs.MotivoCierreDialog;
import com.codekolih.producciontablet.dialogs.MotivoFinTurnoDialog;
import com.codekolih.producciontablet.dialogs.ScrapDialogo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Produccion_Activity extends AppCompatActivity implements CantidadDialog.finalizarCuadro, ScrapDialogo.finalizarScrapDialog, BobinaDialogo.finalizarBobinaDialog, MotivoCierreDialog.finalizarMotivo,MotivoFinTurnoDialog.finalizarMotivo {

    Tareas tarea_Seleccionada;
    Pedido pedido_seleccionado;
    Produccion_Lista produccion_actual;
    Bobinas bobinas_actual;

    private TextView  cantidadtotal;
    private float totaldadscrap;
    private TextView  txt_imprenta,txt_usuario,txt_fecha,txt_hora;
    private TextView  txt_SerieYNro,txt_ArticuloId,txt_Cantidad,txt_Concepto;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private AdapterBobinas adapterBobina = new AdapterBobinas();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private Button btn_cantidad, btn_bobina,btn_scrap, btn_finalizar, btn_cancelar, btn_finturno;
    private int BOBINA_ACTIVITY = 1;
    RecyclerView recyclerViewCantidad,recyclerViewBobinas;
    private int produccionId;
    private int pedidoId;
    private int tareaId;
    private String USUARIO;
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
        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();
        txt_usuario.setText(USUARIO);


        Log.e("IdProduccionSelec",""+produccionId);


        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (totaldadscrap>0){
                 if (parseFloat(cantidadtotal.getText().toString())>0){

        if (tarea_Seleccionada.getBobinas().size()>0){
            AlertDialog.Builder build4 = new AlertDialog.Builder(Produccion_Activity.this);
            build4.setMessage("¿Desea Finalizar la Produccion? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Map<String, Object> estado = new HashMap<>();
                    estado.put("TareaId", tareaId);
                    estado.put("EstadoId", "C1");
                    estado.put("TipoEstadoId","I" );
                    cambioEstado(estado);

                }

            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            AlertDialog alertDialog4 = build4.create();
            alertDialog4.show();

        }else{
            Toast.makeText(getApplicationContext(), "Hay que cargar Bobina", Toast.LENGTH_SHORT).show();
        }

    }else{
        Toast.makeText(getApplicationContext(), "Hay que cargar Produccion", Toast.LENGTH_SHORT).show();
    }
}else{

    Toast.makeText(getApplicationContext(), "Hay que cargar Scrap", Toast.LENGTH_SHORT).show();
}



            }
        });


        btn_finturno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finalizar();

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

    private void finalizar() {


        new MotivoFinTurnoDialog(Produccion_Activity.this,Produccion_Activity.this);

    }

    private void cancelar() {


        new MotivoCierreDialog(Produccion_Activity.this,Produccion_Activity.this);



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
                    lyp_EtiquetasPorRollo.setVisibility(parseInt(entry.getValue()));
                }  else if ("EtiquetasEnBanda".equals(entry.getKey())) {
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
        btn_finturno = findViewById(R.id.produccion_btn_finalziar2);
        cantidadtotal = findViewById(R.id.txt_produccion_cantidadtotal);


        //datos tarea
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

        Log.e("Resultado","cambio estado ");
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
                    Toast.makeText(getApplicationContext(),"Reintentar",Toast.LENGTH_SHORT).show();
                    dialogProgress.dismiss();
                }
            },USUARIO);

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

                dialogErrorPrintet("No Cargo Tarea");
                Log.e("error_produccion",e.toString());
                dialogProgress.dismiss();
            }
        });
    }

    void cargarTareaSeleccionada(){

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Log.e("ERROR","NO TAREA");
            finish();
        }

        float contcantidad = 0;
        float contscrap = 0;

        if (tarea_Seleccionada.getProduccion_Lista().size()>0){
            for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
                Log.e("BDproduccion",lg.toString());

                for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {
                    if ("SumMetrosImpresos".equals(entry.getKey())){
                        if (entry.getValue().equals("0")){
                            contcantidad+= lg.getMetrosImpresos();

                        }
                    }else if ("SumRollosFabricados".equals(entry.getKey())){
                        if (entry.getValue().equals("0")){
                            contcantidad+= lg.getRollosFabricdos();

                        }
                    } else if ("SumRollosEmpaquedatos".equals(entry.getKey())){
                        if (entry.getValue().equals("0")){
                            contcantidad+= lg.getRollosEmpaquetados();
                        }
                    }
                }

                if (lg.getProduccionId()==produccionId){
                    produccion_actual = lg;
                }

                contscrap+= lg.getScrapAjusteProduccion();
            }

            totaldadscrap = contscrap;
            cantidadtotal.setText(String.format("%s", contcantidad));

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


            txt_SerieYNro.setText(tarea_Seleccionada.getSerieYNro());
            txt_ArticuloId.setText(tarea_Seleccionada.getArticuloId());
            txt_Cantidad.setText(String.format("%s", tarea_Seleccionada.getCantidad()));
            txt_Concepto.setText(tarea_Seleccionada.getConcepto());

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

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);
        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
            dialogProgress.dismiss();
                cargarTareaHttp();

                btn_cantidad.setText("MODIFICAR CANTIDAD");
            }

            @Override
            public void onError(Exception e) {

            dialogProgress.dismiss();
                dialogErrorPrintet("No Cargo Cantidad");
                Toast.makeText(getApplicationContext(), "No Cargo Cantidad Reintentar",Toast.LENGTH_SHORT).show();

            }
        },USUARIO);

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

        dialogProgress = ProgressHUD.show(Produccion_Activity.this);
        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Bobina_activit",response.toString());
        dialogProgress.dismiss();
                cargarTareaHttp();

            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();
                dialogErrorPrintet("No Cargo Bobina");
            }
        },USUARIO);

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




    @Override
    public void ResultadoMotivoDialogo(String motivo, String tipocierre) {

            Log.e("Resultado","recibido" + motivo + " " + tipocierre);


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

                    Log.e("Resultado","recibido" + motivo + " " + tipocierre);


                    Map<String, Object> estado = new HashMap<>();
                    estado.put("TareaId", tareaId);
                    estado.put("EstadoId", tipocierre);
                    estado.put("TipoEstadoId","I" );
                    cambioEstado(estado);

                }

                @Override
                public void onError(Exception e) {
                    dialogProgress.dismiss();

                    Toast.makeText(getApplicationContext(), "No Cargo Actualizo la Tarea Reintentar",Toast.LENGTH_SHORT).show();
                    dialogErrorPrintet("No Cargo Motivo");

                }
            },USUARIO);
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


        dialogProgress = ProgressHUD.show(Produccion_Activity.this);

        httpLayer.actualizarProduccion(GsonUtils.toJSON(produccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                dialogProgress.dismiss();
                cargarTareaHttp();
                btn_scrap.setText("MODIFICAR SCRAP");

            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();
                Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar",Toast.LENGTH_SHORT).show();
                dialogErrorPrintet("No Cargo Scrap");

            }
        },USUARIO);
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