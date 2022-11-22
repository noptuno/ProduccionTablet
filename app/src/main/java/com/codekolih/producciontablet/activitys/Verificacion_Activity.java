package com.codekolih.producciontablet.activitys;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.PdfActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Verificacion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private ArrayList<Produccion_Lista> listImprentas = new ArrayList<>();
    Produccion_Lista produccion_actual;
    private ProgressHUD dialogProgress;
    private Boolean pdfAbierto = false;
    private HttpLayer httpLayer;
    private EditText
            edt_verificacion_AnchoFinalRolloYGap,
            edt_verificacion_CantidadPistasImpresas,
            edt_verificacion_CantidadTintas,
            edt_verificacion_ScrapAjusteInicial,

            edt_verificacion_AnchoFinalRollo,
            edt_verificacion_CantidadPistasCortadas,
            edt_verificacion_PistasTroquelUsadas;

    private Spinner spi_verificacion_UnidadIdScrapInicial;

    private Switch uno,dos,tres,cuatro,cinco,seis;

    private TextView
            txt_verificacion_txt_NroDeSobre,
            txt_verificacion_txt_Descripcion,
            txt_verificacion_txt_MetrosAImprimir,
            txt_verificacion_txt_MetrosPorRollo;
    private TextView
            txt_verificacion_txt_Z_AltoMasGap,
            txt_verificacion_txt_Cilindro,
            txt_verificacion_txt_Pistas,
            txt_verificacion_txt_EtiquetasEnBanda,
            txt_verificacion_txt_EtiquetasPorRollo;

    Button btn_guardar, btn_verpdf, btn_cancelar;

    String UnidadIdScrapInicial= "KG";

    private boolean permisosaceptados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        variablesFindRid();
        httpLayer = new HttpLayer(this);

       if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Instancia Creada",Toast.LENGTH_LONG).show();
       }

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

        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {
            produccion_actual = lg;
            Log.e("DATOS_PRODUCCION",produccion_actual.toString());
        }


        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



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


                httpLayer.altaproduccion(GsonUtils.toJSON(newproduccion), new HttpLayer.HttpLayerResponses<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {

                        Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        Toast.makeText(getApplicationContext(), "Se dio de alta",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Exception e) {

                        Toast.makeText(getApplicationContext(), "No Cargo Scrap Reintentar",Toast.LENGTH_SHORT).show();

                    }
                });

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
        //todo lo desactive por ahora
       // cambioEstado();

    }

    private void cambioEstado() {

        Map<String, Object> estado = new HashMap<>();
        estado.put("TareaId", tarea_Seleccionada.getTareaId());
        estado.put("EstadoId", "A1");
        estado.put("TipoEstadoId","I" );


        dialogProgress = ProgressHUD.show(Verificacion_Activity.this);

        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Verificacion_Activity","Cargo Estado" + tarea_Seleccionada.getTareaId());
                dialogProgress.dismiss();
            }

            @Override
            public void onError(Exception e) {
                Log.e("Verificacion_Activity","Error al cargar Estado" + tarea_Seleccionada.getTareaId());
                dialogProgress.dismiss();
            }
        });

    }

    private void variablesFindRid() {

        btn_verpdf =findViewById(R.id.verificacion_btn_verpdf);
        btn_guardar = findViewById(R.id.verificacion_btn_guardar);
        btn_cancelar = findViewById(R.id.verificacion_btn_cancelar);

        uno= findViewById(R.id.switch1);
        dos= findViewById(R.id.switch2);
        tres= findViewById(R.id.switch3);
        cuatro= findViewById(R.id.switch4);
        cinco= findViewById(R.id.switch5);
        seis= findViewById(R.id.switch6);

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
            if ("edt_verificacion_AnchoFinalRolloYGap".equals(entry.getKey())){
                edt_verificacion_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_CantidadPistasImpresas".equals(entry.getKey())){
                edt_verificacion_CantidadPistasImpresas.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_CantidadTintas".equals(entry.getKey())){
                edt_verificacion_CantidadTintas.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_ScrapAjusteInicial".equals(entry.getKey())){
                edt_verificacion_ScrapAjusteInicial.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_UnidadIdScrapInicial".equals(entry.getKey())){
                spi_verificacion_UnidadIdScrapInicial.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_AnchoFinalRollo".equals(entry.getKey())){
                edt_verificacion_AnchoFinalRollo.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_CantidadPistasCortadas".equals(entry.getKey())){
                edt_verificacion_CantidadPistasCortadas.setVisibility(parseInt(entry.getValue()));
            }
            if ("edt_verificacion_PistasTroquelUsadas".equals(entry.getKey())){
                edt_verificacion_PistasTroquelUsadas.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_NroDeSobre".equals(entry.getKey())){
                txt_verificacion_txt_NroDeSobre.setVisibility(parseInt(entry.getValue()));
            }
            if ("verificacion_txt_Descripcion".equals(entry.getKey())){
                txt_verificacion_txt_Descripcion.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_MetrosAImprimir".equals(entry.getKey())){
                txt_verificacion_txt_MetrosAImprimir.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_MetrosPorRollo".equals(entry.getKey())){
                txt_verificacion_txt_MetrosPorRollo.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_Z_AltoMasGap".equals(entry.getKey())){
                txt_verificacion_txt_Z_AltoMasGap.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_Cilindro".equals(entry.getKey())){
                txt_verificacion_txt_Cilindro.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_Pistas".equals(entry.getKey())){
                txt_verificacion_txt_Pistas.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_EtiquetasEnBanda".equals(entry.getKey())){
                txt_verificacion_txt_EtiquetasEnBanda.setVisibility(parseInt(entry.getValue()));
            }
            if ("txt_verificacion_txt_EtiquetasPorRollo".equals(entry.getKey())){
                txt_verificacion_txt_EtiquetasPorRollo.setVisibility(parseInt(entry.getValue()));
            }
        }
    }



    private void toastPersonalziado(String msg){

    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

}

}