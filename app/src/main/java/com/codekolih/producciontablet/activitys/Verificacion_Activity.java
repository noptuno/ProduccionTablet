package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;
import static java.lang.Integer.parseInt;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Utils;
import com.codekolih.producciontablet.aciones.Validarinternet;
import com.codekolih.producciontablet.clases.Pedido;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.PdfActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Verificacion_Activity extends OcultarTeclado {

    Tareas tarea_Seleccionada;
    private String USUARIO;
    private String nombrepdf;
    private int tipoimprenta;
    private static final int CODIGOABRIRPDF = 1;
    Pedido pedido_Cargado;
    //Produccion_Lista produccion_actual;
    private ProgressHUD dialogProgress;
    private Boolean pdfAbierto = false;
    private HttpLayer httpLayer;
    private TextView txt_imprenta, txt_usuario, txt_fecha, txt_hora;
    //pedido
    private TextView txt_SerieYNro, txt_ArticuloId, txt_Cantidad, txt_Concepto,txt_pistas;
    private EditText
            edt_verificacion_AnchoFinalRolloYGap,
            edt_verificacion_CantidadPistasImpresas,
            edt_verificacion_CantidadTintas,
            edt_verificacion_ScrapAjusteInicial,
            edt_verificacion_AnchoFinalRollo,
            edt_verificacion_CantidadPistasCortadas,
            edt_verificacion_PistasTroquelUsadas,

            edt_verificacion_AnchoBobinaUsadoCm;

    private Spinner spi_verificacion_UnidadIdScrapInicial;
    private LinearLayout
            ly_EtiquetasPorRollo,
            ly_EtiquetasEnBanda,
            ly_Pistas,
            ly_Observaciones,
            ly_MetrosAImprimir,
            ly_NroDeSobre,
            ly_Descripcion,
            ly_Cilindro,
            ly_Z_AltoMasGap,
            ly_MetrosPorRollo,
            ly_MetrosMatTroquelar,
            ly_TroquelId,
            ly_AnchoFinalRolloYGap,
            ly_CantidadPistasImpresas,
            ly_CantidadTintas,
            ly_ScrapAjusteInicial,
            ly_AnchoFinalRollo,
            ly_CantidadPistasCortadas,
            ly_PistasTroquelUsadas,
            ly_UnidadIdScrapInicial,
            ly_AnchoBobinaUsadoCm;
    private LinearLayout ly_uno, ly_dos, ly_tres, ly_cuatro, ly_cinco, ly_seis, ly_siete, ly_ocho, ly_nueve, ly_dies;

    private Switch uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, dies;

    private TextView

            txt_verificacion_txt_Observaciones,
            txt_verificacion_txt_NroDeSobre,
            txt_verificacion_txt_Descripcion,
            txt_verificacion_txt_MetrosAImprimir,
            txt_verificacion_txt_MetrosPorRollo,
            txt_verificacion_txt_Z_AltoMasGap,
            txt_verificacion_txt_Cilindro,
            txt_verificacion_txt_Pistas,
            txt_verificacion_txt_EtiquetasEnBanda,
            txt_verificacion_txt_EtiquetasPorRollo,
             txt_verificacion_txt_MetrosMatTroquelar;


    Button btn_guardar, btn_verpdf, btn_cancelar;
    String UnidadIdScrapInicial = "Seleccionar";
    private SharedPreferences pref;
    private boolean permisosaceptados = false;

    private int tareaId;
    private int SessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        Log.e("VrerivicacionActivity", "INICIO");
        variablesFind();

        //verificacion_edt_AnchoFinalRolloYGap no registra
        //verificacion_edt_AnchoBobinaUsadoCm error

        httpLayer = new HttpLayer(this);

        try {
            pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
            String nombreMaquina = pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO");
            String tipomaquinaid = pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "NO");

            if (tipomaquinaid.equals("2") || tipomaquinaid.equals("3")){
                txt_pistas.setText("Pistas");
            }

            txt_imprenta.setText(String.format("%s Tipo: %s", nombreMaquina, tipomaquinaid));
            tareaId = TareaSingleton.SingletonInstance().getTarea().getTareaId();
            SessionId = Integer.parseInt(TareaSingleton.SingletonInstance().getRespuestaDato());

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Hubo un problema en los datos de Preference", Toast.LENGTH_SHORT).show();
            finish();
        }




        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();
        txt_usuario.setText(USUARIO);

        cargarTareaSeleccionada(); // tarea y produccion_actual // usuario

        ConstraintLayout constraintLayout = findViewById(R.id.constrain_verificacion);

        addKeyboardHideListener(constraintLayout);

        spi_verificacion_UnidadIdScrapInicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String unidad = "Seleccionar";
                if (adapterView.getItemAtPosition(i).toString().equals("Kilos")) {
                    unidad = "KG";
                } else if (adapterView.getItemAtPosition(i).toString().equals("Gramos")) {
                    unidad = "G";
                } else if (adapterView.getItemAtPosition(i).toString().equals("Metros")) {
                    unidad = "M";

                }

                UnidadIdScrapInicial = unidad;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelar();

            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(constraintLayout.getRootView());



                if (validarVariables()) {

                    AlertDialog.Builder build4 = new AlertDialog.Builder(Verificacion_Activity.this);
                    build4.setMessage("¿Desea avanzar a produccion? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarVerificacion();

                        }

                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    AlertDialog alertDialog4 = build4.create();
                    alertDialog4.show();


            } else {
                Toast.makeText(getApplicationContext(), "Faltan Datos", Toast.LENGTH_SHORT).show();
            }

        }
        });

        btn_verpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pdfAbierto = true;

                Intent intent = new Intent(Verificacion_Activity.this, PdfActivity.class);
                intent.putExtra("nombrepdf", nombrepdf);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivityForResult(intent, CODIGOABRIRPDF);


            }
        });

        OcultarVariables();
        cargarfecha();

        //CargarPedido();


        // validar el 0
        configureEditText(edt_verificacion_AnchoFinalRolloYGap);
        configureEditText(edt_verificacion_CantidadPistasImpresas);
        configureEditText(edt_verificacion_CantidadTintas);
        configureEditText(edt_verificacion_ScrapAjusteInicial);
        configureEditText(edt_verificacion_AnchoFinalRollo);
        configureEditText(edt_verificacion_CantidadPistasCortadas);
        configureEditText(edt_verificacion_PistasTroquelUsadas);
        configureEditText(edt_verificacion_AnchoBobinaUsadoCm);
        establecerlimitesnumericos();


    }


    @Override
    protected void onPause() {
        super.onPause();

        Utils.stopHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();

       Validarinternet.validarConexionInternet(this);

        Utils.startHandler(Utils.getRunnable(this, "Debe avanzar a Produccion"));
    }





    private void establecerlimitesnumericos() {

        if (tarea_Seleccionada.getTipoMaquinaId()== 4){

            edt_verificacion_ScrapAjusteInicial.setMaxEms(6);
            edt_verificacion_ScrapAjusteInicial.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_ScrapAjusteInicial.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

        }else{


            edt_verificacion_ScrapAjusteInicial.setMaxEms(6);
            edt_verificacion_ScrapAjusteInicial.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_ScrapAjusteInicial.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

            edt_verificacion_AnchoFinalRolloYGap.setMaxEms(4);
            edt_verificacion_AnchoFinalRolloYGap.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_AnchoFinalRolloYGap.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            edt_verificacion_CantidadPistasImpresas.setMaxEms(2);
            edt_verificacion_CantidadPistasImpresas.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_CantidadPistasImpresas.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            edt_verificacion_CantidadTintas.setMaxEms(1);
            edt_verificacion_CantidadTintas.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_CantidadTintas.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            edt_verificacion_AnchoFinalRollo.setMaxEms(5);
            edt_verificacion_AnchoFinalRollo.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_AnchoFinalRollo.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

            edt_verificacion_CantidadPistasCortadas.setMaxEms(2);
            edt_verificacion_CantidadPistasCortadas.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_CantidadPistasCortadas.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            edt_verificacion_PistasTroquelUsadas.setMaxEms(2);
            edt_verificacion_PistasTroquelUsadas.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_PistasTroquelUsadas.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        }

        if (tarea_Seleccionada.getTipoMaquinaId()== 3){

            edt_verificacion_ScrapAjusteInicial.setMaxEms(6);
            edt_verificacion_ScrapAjusteInicial.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_ScrapAjusteInicial.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

            edt_verificacion_AnchoBobinaUsadoCm.setMaxEms(6);
            edt_verificacion_AnchoBobinaUsadoCm.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_verificacion_AnchoBobinaUsadoCm.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

        }

    }

    private void cancelar() {

        AlertDialog.Builder build4 = new AlertDialog.Builder(Verificacion_Activity.this);
        build4.setMessage("¿Desea cancelar el trabajo? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Map<String, Object> estado = new HashMap<>();
                estado.put("TareaId", tareaId);
                estado.put("EstadoId", "C2");
                estado.put("ProduccionId", 0);
                estado.put("SessionId", SessionId);

                finish();

               // cargarEstadoCancelarProduccion(estado);


            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog4 = build4.create();
        alertDialog4.show();

    }

    @Override
    public void onBackPressed() {
        cancelar();
    }


    private void cargarVerificacion() {

        if (Validarinternet.validarConexionInternet(Verificacion_Activity.this)){

            String AnchoFinalRolloYGap = edt_verificacion_AnchoFinalRolloYGap.getText().toString();
            String CantidadPistasImpresas = edt_verificacion_CantidadPistasImpresas.getText().toString();
            String CantidadTintas = edt_verificacion_CantidadTintas.getText().toString();
            String ScrapAjusteInicial = edt_verificacion_ScrapAjusteInicial.getText().toString();
            String AnchoFinalRollo = edt_verificacion_AnchoFinalRollo.getText().toString();
            String CantidadPistasCortadas = edt_verificacion_CantidadPistasCortadas.getText().toString();
            String PistasTroquelUsadas = edt_verificacion_PistasTroquelUsadas.getText().toString();
            String AnchoBobinaUsadoCm = edt_verificacion_AnchoBobinaUsadoCm.getText().toString();

            Map<String, Object> newproduccion = new HashMap<>();
            newproduccion.put("ProduccionId", 0);
            newproduccion.put("PedidoId", tarea_Seleccionada.getPedidoId());
            newproduccion.put("TareaId", tarea_Seleccionada.getTareaId());
            newproduccion.put("MetrosImpresos", 0);
            newproduccion.put("AnchoFinalRolloYGap", AnchoFinalRolloYGap);
            newproduccion.put("CantidadPistasImpresas", CantidadPistasImpresas);
            newproduccion.put("CantidadTintas", CantidadTintas);
            newproduccion.put("AnchoBobinaUsadoCm", AnchoBobinaUsadoCm);
            newproduccion.put("ScrapAjusteInicial", ScrapAjusteInicial);
            newproduccion.put("ScrapAjusteInicial_Unidades", UnidadIdScrapInicial);
            newproduccion.put("ScrapAjusteProduccion", 0);
            newproduccion.put("ScrapAjusteProduccion_Unidades", "KG");
            newproduccion.put("ObservacionesCierre", "");
            newproduccion.put("RollosFabricdos", 0);
            newproduccion.put("AnchoFinalRollo", AnchoFinalRollo);
            newproduccion.put("CantidadPistasCortadas", CantidadPistasCortadas);
            newproduccion.put("PistasTroquelUsadas", PistasTroquelUsadas);
            newproduccion.put("RollosEmpaquetados", 0);
            newproduccion.put("UsuarioId", tarea_Seleccionada.getUsuarioId());
            dialogProgress = ProgressHUD.show(Verificacion_Activity.this);


            httpLayer.altaproduccion(GsonUtils.toJSON(newproduccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.e("http", response.toString());

                    try {
                        String esthttp = response.getString("RespuestaMensaje");

                        if (esthttp.equals("OK")) {

                            String RespuestaDato = response.getString("RespuestaDato");

                            if (RespuestaDato.length() > 0) {

                                String[] valirIdProduccion = RespuestaDato.split(":");
                                String id = valirIdProduccion[1];
                                TareaSingleton.SingletonInstance().setProduccionId(Integer.parseInt(id));

                                Map<String, Object> estado = new HashMap<>();
                                estado.put("TareaId", tareaId);
                                estado.put("EstadoId", "P1");
                                estado.put("ProduccionId", TareaSingleton.SingletonInstance().getProduccionId());
                                estado.put("SessionId", SessionId);

                                cargarEstadoProduccion(estado);


                            }else{
                              Toast.makeText(Verificacion_Activity.this, "Hubo un problema con el id de produccion", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        dialogProgress.dismiss();
                    }

                    dialogProgress.dismiss();
                }

                @Override
                public void onError(Exception e) {
                    dialogProgress.dismiss();

                    dialogError("No cargo Datos Verificación");

                }
            }, USUARIO);
        }
        }


    private void cargarEstadoProduccion(Map<String, Object> estado) {

        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("Verificacion_Activity", "Cargo Estado Produccion P1 " + tareaId);

                try {

                    String comando = response.getString("Comando");
                    String RespuestaMensaje = response.getString("RespuestaMensaje");
                    String CodigoError = response.getString("CodigoError");
                    String RespuestaDato = response.getString("RespuestaDato");

                    if (RespuestaMensaje.equals("200")){

                        Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }

                } catch (JSONException e) {
                    Log.e("Verificacion_Activity", "Dio error");
                    throw new RuntimeException(e);
                }




            }

            @Override
            public void onError(Exception e) {

                dialogError("No cargo el estado inicial a produccion reintentar");


            }
        }, USUARIO);

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

    private void cargarTareaSeleccionada() {

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea()) == null) {
            Toast.makeText(getApplicationContext(), "Instancia NO Creada", Toast.LENGTH_LONG).show();
            finish();
        } else {

            txt_SerieYNro.setText(tarea_Seleccionada.getSerieYNro());
            txt_ArticuloId.setText(tarea_Seleccionada.getArticuloId());
            txt_Cantidad.setText(String.format("%s", tarea_Seleccionada.getCantidad()));
            txt_Concepto.setText(tarea_Seleccionada.getConcepto());

            txt_verificacion_txt_Observaciones.setText(String.format("%s", tarea_Seleccionada.getObservaciones()));
            txt_verificacion_txt_NroDeSobre.setText(String.format("%s", tarea_Seleccionada.getNroDeSobre()));
            txt_verificacion_txt_Descripcion.setText(String.format("%s", tarea_Seleccionada.getDescripcion()));
            txt_verificacion_txt_MetrosAImprimir.setText(String.format("%s", tarea_Seleccionada.getMetrosAImprimir()));
            txt_verificacion_txt_MetrosPorRollo.setText(String.format("%s", tarea_Seleccionada.getMetrosPorRollo()));
            txt_verificacion_txt_Z_AltoMasGap.setText(String.format("%s", tarea_Seleccionada.getZ_AltoMasGap()));
            txt_verificacion_txt_Cilindro.setText(String.format("%s", tarea_Seleccionada.getCilindro()));
            txt_verificacion_txt_Pistas.setText(String.format("%s", tarea_Seleccionada.getPistas()));
            txt_verificacion_txt_EtiquetasEnBanda.setText(String.format("%s", tarea_Seleccionada.getEtiquetasEnBanda()));
            txt_verificacion_txt_EtiquetasPorRollo.setText(String.format("%s", tarea_Seleccionada.getEtiquetasPorRollo()));
            txt_verificacion_txt_MetrosMatTroquelar.setText(String.format("%s", tarea_Seleccionada.getMetrosMatTroquelar()));
            nombrepdf = tarea_Seleccionada.getArchivoEspecificacion();
        }

    }


    private void variablesFind() {

        txt_imprenta = findViewById(R.id.verificacion_txt_imprenta);
        txt_usuario = findViewById(R.id.verificacion_txt_usuario);
        txt_fecha = findViewById(R.id.verificacion_txt_fecha);
        txt_hora = findViewById(R.id.verificacion_txt_hora);

        txt_SerieYNro = findViewById(R.id.ver_txt_SerieYNro);
        txt_ArticuloId = findViewById(R.id.ver_txt_ArticuloId);
        txt_Cantidad = findViewById(R.id.ver_txt_Cantidad);
        txt_Concepto = findViewById(R.id.ver_txt_Concepto);

        btn_verpdf = findViewById(R.id.verificacion_btn_verpdf);
        btn_guardar = findViewById(R.id.verificacion_btn_guardar);
        btn_cancelar = findViewById(R.id.verificacion_btn_cancelar);

        txt_pistas = findViewById(R.id.txt_ppistas);

        ly_uno = findViewById(R.id.ly_uno);
        ly_dos = findViewById(R.id.ly_dos);
        ly_tres = findViewById(R.id.ly_tres);
        ly_cuatro = findViewById(R.id.ly_cuatro);
        ly_cinco = findViewById(R.id.ly_cinco);
        ly_seis = findViewById(R.id.ly_seis);
        ly_siete = findViewById(R.id.ly_siete);
        ly_ocho = findViewById(R.id.ly_ocho);
        ly_nueve = findViewById(R.id.ly_nueve);
        ly_dies = findViewById(R.id.ly_dies);

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);
        cuatro = findViewById(R.id.cuatro);
        cinco = findViewById(R.id.cinco);
        seis = findViewById(R.id.seis);
        siete = findViewById(R.id.siete);
        ocho = findViewById(R.id.ocho);
        nueve = findViewById(R.id.nueve);
        dies = findViewById(R.id.dies);

        ly_EtiquetasPorRollo = findViewById(R.id.ly_EtiquetasPorRollo);
        ly_EtiquetasEnBanda = findViewById(R.id.ly_EtiquetasEnBanda);
        ly_Pistas = findViewById(R.id.ly_Pistas);
        ly_Observaciones = findViewById(R.id.ly_Observaciones);
        ly_MetrosAImprimir = findViewById(R.id.ly_MetrosAImprimir);
        ly_NroDeSobre = findViewById(R.id.ly_NroDeSobre);
        ly_Descripcion = findViewById(R.id.ly_Descripcion);
        ly_Cilindro = findViewById(R.id.ly_Cilindro);
        ly_Z_AltoMasGap = findViewById(R.id.ly_Z_AltoMasGap);
        ly_MetrosPorRollo = findViewById(R.id.ly_MetrosPorRollo);
        ly_MetrosMatTroquelar = findViewById(R.id.ly_MetrosMatTroquelar);
        ly_TroquelId = findViewById(R.id.ly_TroquelId);


        ly_AnchoFinalRolloYGap = findViewById(R.id.ly_AnchoFinalRolloYGap);
        ly_CantidadPistasImpresas = findViewById(R.id.ly_CantidadPistasImpresas);
        ly_CantidadTintas = findViewById(R.id.ly_CantidadTintas);
        ly_ScrapAjusteInicial = findViewById(R.id.ly_ScrapAjusteInicial);
        ly_AnchoFinalRollo = findViewById(R.id.ly_AnchoFinalRollo);
        ly_CantidadPistasCortadas = findViewById(R.id.ly_CantidadPistasCortadas);
        ly_PistasTroquelUsadas = findViewById(R.id.ly_PistasTroquelUsadas);
        ly_UnidadIdScrapInicial = findViewById(R.id.ly_UnidadIdScrapInicial);
        ly_AnchoBobinaUsadoCm= findViewById(R.id.ly_AnchoBobinaUsadoCm);


        edt_verificacion_AnchoFinalRolloYGap = findViewById(R.id.verificacion_edt_AnchoFinalRolloYGap);
        edt_verificacion_CantidadPistasImpresas = findViewById(R.id.verificacion_edt_CantidadPistasImpresas);
        edt_verificacion_CantidadTintas = findViewById(R.id.verificacion_edt_CantidadTintas);
        edt_verificacion_ScrapAjusteInicial = findViewById(R.id.verificacion_edt_ScrapAjusteInicial);
        spi_verificacion_UnidadIdScrapInicial = findViewById(R.id.verificacion_spi_UnidadIdScrapInicial);
        edt_verificacion_AnchoFinalRollo = findViewById(R.id.verificacion_edt_AnchoFinalRollo);
        edt_verificacion_CantidadPistasCortadas = findViewById(R.id.verificacion_edt_CantidadPistasCortadas);
        edt_verificacion_PistasTroquelUsadas = findViewById(R.id.verificacion_edt_PistasTroquelUsadas);
        edt_verificacion_AnchoBobinaUsadoCm= findViewById(R.id.verificacion_edt_AnchoBobinaUsadoCm);

        txt_verificacion_txt_Observaciones = findViewById(R.id.verificacion_txt_Observaciones);
        txt_verificacion_txt_NroDeSobre = findViewById(R.id.verificacion_txt_NroDeSobre);
        txt_verificacion_txt_Descripcion = findViewById(R.id.verificacion_txt_Descripcion);
        txt_verificacion_txt_MetrosAImprimir = findViewById(R.id.verificacion_txt_MetrosAImprimir);
        txt_verificacion_txt_MetrosPorRollo = findViewById(R.id.verificacion_txt_MetrosPorRollo);

        txt_verificacion_txt_Z_AltoMasGap = findViewById(R.id.verificacion_txt_Z_AltoMasGap);
        txt_verificacion_txt_Cilindro = findViewById(R.id.verificacion_txt_Cilindro);
        txt_verificacion_txt_Pistas = findViewById(R.id.verificacion_txt_Pistas);
        txt_verificacion_txt_EtiquetasEnBanda = findViewById(R.id.verificacion_txt_EtiquetasEnBanda);
        txt_verificacion_txt_EtiquetasPorRollo = findViewById(R.id.verificacion_txt_EtiquetasPorRollo);
        txt_verificacion_txt_MetrosMatTroquelar = findViewById(R.id.verificacion_txt_MetrosMatTroquelar);
    }

    private void OcultarVariables() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("1".equals(entry.getKey())) {
                ly_uno.setVisibility(parseInt(entry.getValue()));
            } else if ("2".toString().equals(entry.getKey())) {
                ly_dos.setVisibility(parseInt(entry.getValue()));
            } else if ("3".toString().equals(entry.getKey())) {
                ly_tres.setVisibility(parseInt(entry.getValue()));
            } else if ("4".toString().equals(entry.getKey())) {
                ly_cuatro.setVisibility(parseInt(entry.getValue()));
            } else if ("5".toString().equals(entry.getKey())) {
                ly_cinco.setVisibility(parseInt(entry.getValue()));
            } else if ("6".toString().equals(entry.getKey())) {
                ly_seis.setVisibility(parseInt(entry.getValue()));
            } else if ("7".toString().equals(entry.getKey())) {
                ly_siete.setVisibility(parseInt(entry.getValue()));
            } else if ("8".toString().equals(entry.getKey())) {
                ly_ocho.setVisibility(parseInt(entry.getValue()));
            } else if ("9".toString().equals(entry.getKey())) {
                ly_nueve.setVisibility(parseInt(entry.getValue()));
            } else if ("10".toString().equals(entry.getKey())) {
                ly_dies.setVisibility(parseInt(entry.getValue()));
            }

            if ("EtiquetasPorRollo".equals(entry.getKey())) {
                ly_EtiquetasPorRollo.setVisibility(parseInt(entry.getValue()));
            } else if ("EtiquetasEnBanda".equals(entry.getKey())) {
                ly_EtiquetasEnBanda.setVisibility(parseInt(entry.getValue()));
            } else if ("Pistas".equals(entry.getKey())) {
                ly_Pistas.setVisibility(parseInt(entry.getValue()));
            } else if ("Observaciones".equals(entry.getKey())) {
                ly_Observaciones.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosAImprimir".equals(entry.getKey())) {
                ly_MetrosAImprimir.setVisibility(parseInt(entry.getValue()));
            } else if ("NroDeSobre".equals(entry.getKey())) {
                ly_NroDeSobre.setVisibility(parseInt(entry.getValue()));
            } else if ("Descripcion".equals(entry.getKey())) {
                ly_Descripcion.setVisibility(parseInt(entry.getValue()));
            } else if ("Cilindro".equals(entry.getKey())) {
                ly_Cilindro.setVisibility(parseInt(entry.getValue()));
            } else if ("Z_AltoMasGap".equals(entry.getKey())) {
                ly_Z_AltoMasGap.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosPorRollo".equals(entry.getKey())) {
                ly_MetrosPorRollo.setVisibility(parseInt(entry.getValue()));
            } else if ("MetrosMatTroquelar".equals(entry.getKey())) {
                ly_MetrosMatTroquelar.setVisibility(parseInt(entry.getValue()));
            } else if ("TroquelId".equals(entry.getKey())) {
                ly_TroquelId.setVisibility(parseInt(entry.getValue()));
            }


            if ("AnchoFinalRolloYGap".equals(entry.getKey())) {
                ly_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
            } else if ("CantidadPistasImpresas".equals(entry.getKey())) {
                ly_CantidadPistasImpresas.setVisibility(parseInt(entry.getValue()));
            } else if ("CantidadTintas".equals(entry.getKey())) {
                ly_CantidadTintas.setVisibility(parseInt(entry.getValue()));
            } else if ("ScrapAjusteInicial".equals(entry.getKey())) {

                ly_ScrapAjusteInicial.setVisibility(parseInt(entry.getValue()));

                if (tarea_Seleccionada.getTipoMaquinaId() == 4) {

                    edt_verificacion_ScrapAjusteInicial.setMaxEms(6);

                } else if (tarea_Seleccionada.getTipoMaquinaId() == 5) {

                    edt_verificacion_ScrapAjusteInicial.setMaxEms(5);

                } else if (tarea_Seleccionada.getTipoMaquinaId() == 1) {

                    edt_verificacion_ScrapAjusteInicial.setMaxEms(6);
                } else if (tarea_Seleccionada.getTipoMaquinaId() == 2) {

                    edt_verificacion_ScrapAjusteInicial.setMaxEms(5);
                }

            } else if ("UnidadIdScrapInicial".equals(entry.getKey())) {
                ly_UnidadIdScrapInicial.setVisibility(parseInt(entry.getValue()));
            } else if ("AnchoFinalRollo".equals(entry.getKey())) {
                ly_AnchoFinalRollo.setVisibility(parseInt(entry.getValue()));
            } else if ("CantidadPistasCortadas".equals(entry.getKey())) {
                ly_CantidadPistasCortadas.setVisibility(parseInt(entry.getValue()));
            } else if ("PistasTroquelUsadas".equals(entry.getKey())) {
                ly_PistasTroquelUsadas.setVisibility(parseInt(entry.getValue()));
            }else if("AnchoBobinaUsadoCm".equals(entry.getKey())){
                ly_AnchoBobinaUsadoCm.setVisibility(parseInt(entry.getValue()));
            }
        }
    }

    private boolean validarVariables() {

        boolean validado = true;

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());


            //switch
            if ("1".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    if (!uno.isChecked()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("2".toString().equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    if (!dos.isChecked()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("3".toString().equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    if (!tres.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("4".toString().equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    if (!cuatro.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("5".toString().equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {

                    if (!cinco.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("6".toString().equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    if (!seis.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("7".toString().equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    if (!siete.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("8".toString().equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    if (!ocho.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("9".toString().equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    if (!nueve.isChecked()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("10".toString().equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    if (!dies.isChecked()) {
                        validado = false;
                        break;
                    }
                }
            }

            //valdiar datos
            else if ("AnchoFinalRolloYGap".equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {

                    String a = edt_verificacion_AnchoFinalRolloYGap.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("CantidadPistasImpresas".equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_CantidadPistasImpresas.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("CantidadTintas".equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_CantidadTintas.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("ScrapAjusteInicial".equals(entry.getKey())) {

                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_ScrapAjusteInicial.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }

            } else if ("UnidadIdScrapInicial".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    if (UnidadIdScrapInicial.equals("Seleccionar")) {
                        validado = false;
                        break;
                    }
                }
            } else if ("AnchoFinalRollo".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_AnchoFinalRollo.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("CantidadPistasCortadas".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_CantidadPistasCortadas.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            } else if ("PistasTroquelUsadas".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_PistasTroquelUsadas.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            }else if ("AnchoBobinaUsadoCm".equals(entry.getKey())) {
                if (entry.getValue().equals("0")) {
                    String a = edt_verificacion_AnchoBobinaUsadoCm.getText().toString();
                    if (a.isEmpty()) {
                        validado = false;
                        break;
                    }
                }
            }
            else if(!pdfAbierto) {
                validado = false;
                toastPersonalziado("Debe abrir las especificaciones");
                break;
            }


        }


        return validado;
    }

    private void toastPersonalziado(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    private void dialogError(String mensaje) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Verificacion_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.alerdialogerror, null);
        final TextView mPassword = mView.findViewById(R.id.txtmensajeerror);
        Button mLogin = mView.findViewById(R.id.btnReintentar);
        mPassword.setText(mensaje + " Problema API");
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
}