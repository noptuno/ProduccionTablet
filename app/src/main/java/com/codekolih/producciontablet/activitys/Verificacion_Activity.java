package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_USUARIO;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
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
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Pedido;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.PdfActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Verificacion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    //Produccion_Lista produccion_actual;
    private ProgressHUD dialogProgress;
    private Boolean pdfAbierto = false;
    private HttpLayer httpLayer;
    private TextView  txt_imprenta,txt_usuario,txt_fecha,txt_hora;
    private EditText
            edt_verificacion_AnchoFinalRolloYGap,
            edt_verificacion_CantidadPistasImpresas,
            edt_verificacion_CantidadTintas,
            edt_verificacion_ScrapAjusteInicial,
            edt_verificacion_AnchoFinalRollo,
            edt_verificacion_CantidadPistasCortadas,
            edt_verificacion_PistasTroquelUsadas;
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
            ly_UnidadIdScrapInicial;



    private Switch uno,dos,tres,cuatro,cinco,seis;
    private TextView
            txt_verificacion_txt_NroDeSobre,
            txt_verificacion_txt_Descripcion,
            txt_verificacion_txt_MetrosAImprimir,
            txt_verificacion_txt_MetrosPorRollo,
            txt_verificacion_txt_Z_AltoMasGap,
            txt_verificacion_txt_Cilindro,
            txt_verificacion_txt_Pistas,
            txt_verificacion_txt_EtiquetasEnBanda,
            txt_verificacion_txt_EtiquetasPorRollo;

    Button btn_guardar, btn_verpdf, btn_cancelar;
    String UnidadIdScrapInicial= "KG";
    private SharedPreferences pref;
    private boolean permisosaceptados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        Log.e("VrerivicacionActivity","INICIO");
        variablesFind();

        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        txt_imprenta.setText(pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"));
        txt_usuario.setText(pref.getString(PREF_PRODUCCION_USUARIO, "NO"));

        httpLayer = new HttpLayer(this);

        cargarTareaSeleccionada(); // tarea y produccion_actual

        txt_verificacion_txt_NroDeSobre.setText(""+tarea_Seleccionada.getNroDeSobre());
        txt_verificacion_txt_Descripcion.setText(""+tarea_Seleccionada.getDescripcion());
        txt_verificacion_txt_MetrosAImprimir.setText(""+tarea_Seleccionada.getMetrosAImprimir());
        txt_verificacion_txt_MetrosPorRollo.setText(""+tarea_Seleccionada.getMetrosPorRollo());
        txt_verificacion_txt_Z_AltoMasGap.setText(""+tarea_Seleccionada.getZ_AltoMasGap());
        txt_verificacion_txt_Cilindro.setText(""+tarea_Seleccionada.getCilindro());
        txt_verificacion_txt_Pistas.setText(""+tarea_Seleccionada.getPistas());
        txt_verificacion_txt_EtiquetasEnBanda.setText(""+tarea_Seleccionada.getEtiquetasEnBanda());
        txt_verificacion_txt_EtiquetasPorRollo.setText(""+tarea_Seleccionada.getEtiquetasPorRollo());

        spi_verificacion_UnidadIdScrapInicial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                UnidadIdScrapInicial =  adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder build4 = new AlertDialog.Builder(Verificacion_Activity.this);
                build4.setMessage("¿Desea cancelar el trabajo? ").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Map<String, Object> estado = new HashMap<>();
                        estado.put("TareaId", tarea_Seleccionada.getTareaId());
                        estado.put("EstadoId", "A1");
                        estado.put("TipoEstadoId","F" );

                        cambioEstado(estado);
                        finish();
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

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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


/*
                Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
*/



            }

        });

        btn_verpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Verificacion_Activity.this, PdfActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });

        OcultarVariables();
        cargarfecha();

       // CargarPedido();
        //todo lo desactive por ahora
       // cambioEstado();

    }

    private void CargarPedido() {

    String params = "/"+tarea_Seleccionada.getPedidoId();

            httpLayer.getPedido(params, new HttpLayer.HttpLayerResponses<Pedido>() {
                @Override
                public void onSuccess(Pedido response) {
                    Log.e("http",response.toString());


                }

                @Override
                public void onError(Exception e) {
                    Log.e("http",e.toString());
                }
            });

    }



    private void cargarVerificacion() {

        String AnchoFinalRolloYGap = edt_verificacion_AnchoFinalRolloYGap.getText().toString();
        String CantidadPistasImpresas = edt_verificacion_CantidadPistasImpresas.getText().toString();
        String CantidadTintas = edt_verificacion_CantidadTintas.getText().toString();
        String ScrapAjusteInicial = edt_verificacion_ScrapAjusteInicial.getText().toString();
        String AnchoFinalRollo = edt_verificacion_AnchoFinalRollo.getText().toString();
        String CantidadPistasCortadas = edt_verificacion_CantidadPistasCortadas.getText().toString();
        String PistasTroquelUsadas = edt_verificacion_PistasTroquelUsadas.getText().toString();


        Map<String, Object> newproduccion = new HashMap<>();
        newproduccion.put("ProduccionId", 0);
        newproduccion.put("PedidoId", tarea_Seleccionada.getPedidoId());
        newproduccion.put("TareaId", tarea_Seleccionada.getTareaId());
        newproduccion.put("MetrosImpresos", 0);
        newproduccion.put("AnchoFinalRolloYGap", AnchoFinalRolloYGap);
        newproduccion.put("CantidadPistasImpresas", CantidadPistasImpresas);
        newproduccion.put("CantidadTintas",CantidadTintas );
        newproduccion.put("AnchoBobinaUsadoCm", 0);
        newproduccion.put("ScrapAjusteInicial",  ScrapAjusteInicial );
        newproduccion.put("ScrapAjusteInicial_Unidades", UnidadIdScrapInicial);
        newproduccion.put("ScrapAjusteProduccion", 0);
        newproduccion.put("ScrapAjusteProduccion_Unidades", "KG");
        newproduccion.put("ObservacionesCierre", "Sin cierre");
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

                Log.e("http",response.toString());

                try {
                    String esthttp = response.getString("RespuestaMensaje");

                    if (esthttp.equals("OK")){

                        String RespuestaDato = response.getString("RespuestaDato");
                        if (RespuestaDato.length()>0){

                            String[] valirIdProduccion = RespuestaDato.split(":");
                            String id = valirIdProduccion[1];
                            TareaSingleton.SingletonInstance().setProduccionId(Integer.parseInt(id));
                        }


                        Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        dialogProgress.dismiss();

                        Map<String, Object> estado = new HashMap<>();
                        estado.put("TareaId", tarea_Seleccionada.getTareaId());
                        estado.put("EstadoId", "P1");
                        estado.put("TipoEstadoId","I" );
                        cambioEstado(estado);
                        finish();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }





             //   actualziarTarea(tarea_Seleccionada.getPedidoId(),tarea_Seleccionada.getTareaId());
            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();

                Log.e("http_altaproduccion","Fallo");
            }
        });
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

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Instancia Creada",Toast.LENGTH_LONG).show();
        }
/*
        if (tarea_Seleccionada.getProduccion_Lista().size()>0){
            for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
                Log.e("Activity",lg.toString());
                produccion_actual = lg;
            }
        }
        */


    }

    private void cambioEstado( Map<String, Object> estado ) {


        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Verificacion_Activity","Cargo Estado" + tarea_Seleccionada.getTareaId());

            }

            @Override
            public void onError(Exception e) {
                Log.e("Verificacion_Activity","Error al cargar Estado" + tarea_Seleccionada.getTareaId());

            }
        });

    }

    private void variablesFind() {

        txt_imprenta = findViewById(R.id.verificacion_txt_imprenta);
        txt_usuario= findViewById(R.id.verificacion_txt_usuario);
        txt_fecha= findViewById(R.id.verificacion_txt_fecha);
        txt_hora= findViewById(R.id.verificacion_txt_hora);


        btn_verpdf =findViewById(R.id.verificacion_btn_verpdf);
        btn_guardar = findViewById(R.id.verificacion_btn_guardar);
        btn_cancelar = findViewById(R.id.verificacion_btn_cancelar);

        uno= findViewById(R.id.switch1);
        dos= findViewById(R.id.switch2);
        tres= findViewById(R.id.switch3);
        cuatro= findViewById(R.id.switch4);
        cinco= findViewById(R.id.switch5);
        seis= findViewById(R.id.switch6);



        ly_EtiquetasPorRollo= findViewById(R.id.ly_EtiquetasPorRollo);
        ly_EtiquetasEnBanda= findViewById(R.id.ly_EtiquetasEnBanda);
        ly_Pistas= findViewById(R.id.ly_Pistas);
        ly_Observaciones= findViewById(R.id.ly_Observaciones);
        ly_MetrosAImprimir= findViewById(R.id.ly_MetrosAImprimir);
        ly_NroDeSobre= findViewById(R.id.ly_NroDeSobre);
        ly_Descripcion= findViewById(R.id.ly_Descripcion);
        ly_Cilindro= findViewById(R.id.ly_Cilindro);
        ly_Z_AltoMasGap= findViewById(R.id.ly_Z_AltoMasGap);
        ly_MetrosPorRollo= findViewById(R.id.ly_MetrosPorRollo);
        ly_MetrosMatTroquelar= findViewById(R.id.ly_MetrosMatTroquelar);
        ly_TroquelId= findViewById(R.id.ly_TroquelId);


        ly_AnchoFinalRolloYGap= findViewById(R.id.ly_AnchoFinalRolloYGap);
        ly_CantidadPistasImpresas= findViewById(R.id.ly_CantidadPistasImpresas);
        ly_CantidadTintas= findViewById(R.id.ly_CantidadTintas);
        ly_ScrapAjusteInicial= findViewById(R.id.ly_ScrapAjusteInicial);
        ly_AnchoFinalRollo= findViewById(R.id.ly_AnchoFinalRollo);
        ly_CantidadPistasCortadas= findViewById(R.id.ly_CantidadPistasCortadas);
        ly_PistasTroquelUsadas= findViewById(R.id.ly_PistasTroquelUsadas);
        ly_UnidadIdScrapInicial= findViewById(R.id.ly_UnidadIdScrapInicial);



        edt_verificacion_AnchoFinalRolloYGap = findViewById(R.id.verificacion_edt_AnchoFinalRolloYGap);
        edt_verificacion_CantidadPistasImpresas= findViewById(R.id.verificacion_edt_CantidadPistasImpresas);
        edt_verificacion_CantidadTintas= findViewById(R.id.verificacion_edt_CantidadTintas);
        edt_verificacion_ScrapAjusteInicial= findViewById(R.id.verificacion_edt_ScrapAjusteInicial);
        spi_verificacion_UnidadIdScrapInicial= findViewById(R.id.verificacion_spi_UnidadIdScrapInicial);
        edt_verificacion_AnchoFinalRollo= findViewById(R.id.verificacion_edt_AnchoFinalRollo);
        edt_verificacion_CantidadPistasCortadas= findViewById(R.id.verificacion_edt_CantidadPistasCortadas);
        edt_verificacion_PistasTroquelUsadas= findViewById(R.id.verificacion_edt_PistasTroquelUsadas);

        txt_verificacion_txt_NroDeSobre = findViewById(R.id.verificacion_txt_NroDeSobre);
        txt_verificacion_txt_Descripcion = findViewById(R.id.verificacion_txt_Descripcion);
        txt_verificacion_txt_MetrosAImprimir = findViewById(R.id.verificacion_txt_MetrosAImprimir);
        txt_verificacion_txt_MetrosPorRollo = findViewById(R.id.verificacion_txt_MetrosPorRollo);

        txt_verificacion_txt_Z_AltoMasGap= findViewById(R.id.verificacion_txt_Z_AltoMasGap);
        txt_verificacion_txt_Cilindro= findViewById(R.id.verificacion_txt_Cilindro);
        txt_verificacion_txt_Pistas= findViewById(R.id.verificacion_txt_Pistas);
        txt_verificacion_txt_EtiquetasEnBanda= findViewById(R.id.verificacion_txt_EtiquetasEnBanda);
        txt_verificacion_txt_EtiquetasPorRollo= findViewById(R.id.verificacion_txt_EtiquetasPorRollo);

    }
    private void OcultarVariables() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("uno".equals(entry.getKey())){
                uno.setVisibility(parseInt(entry.getValue()));
            }
            if ("dos".toString().equals(entry.getKey())){
                dos.setVisibility(parseInt(entry.getValue()));
            }
            if ("tres".toString().equals(entry.getKey())){
                tres.setVisibility(parseInt(entry.getValue()));
            }
            if ("cuatro".toString().equals(entry.getKey())){
                cuatro.setVisibility(parseInt(entry.getValue()));
            }
            if ("cinco".toString().equals(entry.getKey())){
                cinco.setVisibility(parseInt(entry.getValue()));
            }
            if ("seis".toString().equals(entry.getKey())){
                seis.setVisibility(parseInt(entry.getValue()));
            }

            if ("EtiquetasPorRollo".equals(entry.getKey())){
                ly_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
            }  else if ("EtiquetasEnBanda".equals(entry.getKey())){
                ly_EtiquetasEnBanda.setVisibility(parseInt(entry.getValue()));
            } else if ("Pistas".equals(entry.getKey())){
                ly_Pistas.setVisibility(parseInt(entry.getValue()));
            }else if ("Observaciones".equals(entry.getKey())){
                ly_Observaciones.setVisibility(parseInt(entry.getValue()));
            }else if ("MetrosAImprimir".equals(entry.getKey())){
                ly_MetrosAImprimir.setVisibility(parseInt(entry.getValue()));
            }else if ("NroDeSobre".equals(entry.getKey())){
                ly_NroDeSobre.setVisibility(parseInt(entry.getValue()));
            }else if ("Descripcion".equals(entry.getKey())){
                ly_Descripcion.setVisibility(parseInt(entry.getValue()));
            }else if ("Cilindro".equals(entry.getKey())){
                ly_Cilindro.setVisibility(parseInt(entry.getValue()));
            }else if ("Z_AltoMasGap".equals(entry.getKey())){
                ly_Z_AltoMasGap.setVisibility(parseInt(entry.getValue()));
            }else if ("MetrosPorRollo".equals(entry.getKey())){
                ly_MetrosPorRollo.setVisibility(parseInt(entry.getValue()));
            }else if ("MetrosMatTroquelar".equals(entry.getKey())){
                ly_MetrosMatTroquelar.setVisibility(parseInt(entry.getValue()));
            }else if ("TroquelId".equals(entry.getKey())){
                ly_TroquelId.setVisibility(parseInt(entry.getValue()));
            }


            if ("AnchoFinalRolloYGap".equals(entry.getKey())){
                ly_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
            }
            else if ("CantidadPistasImpresas".equals(entry.getKey())){
                ly_CantidadPistasImpresas.setVisibility(parseInt(entry.getValue()));
            }
            else if ("CantidadTintas".equals(entry.getKey())){
                ly_CantidadTintas.setVisibility(parseInt(entry.getValue()));
            }
            else if ("ScrapAjusteInicial".equals(entry.getKey())){
                ly_ScrapAjusteInicial.setVisibility(parseInt(entry.getValue()));
            }
            else if ("UnidadIdScrapInicial".equals(entry.getKey())){
                ly_UnidadIdScrapInicial.setVisibility(parseInt(entry.getValue()));
            }
            else if ("AnchoFinalRollo".equals(entry.getKey())){
                ly_AnchoFinalRollo.setVisibility(parseInt(entry.getValue()));
            }
            else if ("CantidadPistasCortadas".equals(entry.getKey())){
                ly_CantidadPistasCortadas.setVisibility(parseInt(entry.getValue()));
            }
            else if ("PistasTroquelUsadas".equals(entry.getKey())){
                ly_PistasTroquelUsadas.setVisibility(parseInt(entry.getValue()));
            }
        }
    }



    private void toastPersonalziado(String msg){

    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

}

}