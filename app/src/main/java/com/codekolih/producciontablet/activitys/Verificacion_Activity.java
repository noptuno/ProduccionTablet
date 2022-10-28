package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterProduccion;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.PdfActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Verificacion_Activity extends AppCompatActivity {

    Tareas tarea_Seleccionada;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private ArrayList<Produccion_Lista> listImprentas = new ArrayList<>();
    Produccion_Lista produccion_actual;
    private AdapterProduccion adapterProduccion = new AdapterProduccion();
    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;

    private Boolean pdfAbierto = false;

    private EditText
            edt_AnchoFinalRolloYGap,
            edt_CantidadPistasImpresas,
            edt_CantidadTintas,
            edt_ScrapAjusteInicial,
            edt_UnidadIdScrapInicial,
            edt_AnchoFinalRollo,
            edt_CantidadPistasCortadas,edt_PistasTroquelUsadas;

    private Switch uno,dos,tres,cuatro,cinco,seis;


    private TextView
            txt_verificacion_txt_NroDeSobre,
            txt_verificacion_txt_Descripcion,
            txt_verificacion_txt_MetrosAImprimir,
            txt_verificacion_txt_MetrosPorRollo,
            txt_verificacion_txt_MetrosMatTroquelar,
            txt_verificacion_txt_Observaciones;

    private TextView
            txt_verificacion_txt_Z_AltoMasGap,
            txt_verificacion_txt_Cilindro,
            txt_verificacion_txt_Pistas,
            txt_verificacion_txt_EtiquetasEnBanda,
            txt_verificacion_txt_EtiquetasPorRollo;

    Button btn_guardar, btn_verpdf;
    private boolean permisosaceptados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        btn_verpdf =findViewById(R.id.verificacion_btn_verpdf);
        btn_guardar = findViewById(R.id.verificacion_btn_guardar);

        uno= findViewById(R.id.switch1);
        dos= findViewById(R.id.switch2);
        tres= findViewById(R.id.switch3);
        cuatro= findViewById(R.id.switch4);
        cinco= findViewById(R.id.switch5);
        seis= findViewById(R.id.switch6);



        edt_AnchoFinalRolloYGap = findViewById(R.id.verificacion_edt_AnchoFinalRolloYGap);
        edt_CantidadPistasImpresas= findViewById(R.id.verificacion_edt_CantidadPistasImpresas);
        edt_CantidadTintas= findViewById(R.id.verificacion_edt_CantidadTintas);
        edt_ScrapAjusteInicial= findViewById(R.id.verificacion_edt_ScrapAjusteInicial);
        edt_UnidadIdScrapInicial= findViewById(R.id.verificacion_edt_UnidadIdScrapInicial);
        edt_AnchoFinalRollo= findViewById(R.id.verificacion_edt_AnchoFinalRollo);
        edt_CantidadPistasCortadas= findViewById(R.id.verificacion_edt_CantidadPistasCortadas);
        edt_PistasTroquelUsadas= findViewById(R.id.verificacion_edt_PistasTroquelUsadas);


        txt_verificacion_txt_NroDeSobre = findViewById(R.id.verificacion_txt_NroDeSobre);
        txt_verificacion_txt_Descripcion = findViewById(R.id.verificacion_txt_Descripcion);
        txt_verificacion_txt_MetrosAImprimir = findViewById(R.id.verificacion_txt_MetrosAImprimir);
        txt_verificacion_txt_MetrosPorRollo = findViewById(R.id.verificacion_txt_MetrosPorRollo);
        txt_verificacion_txt_MetrosMatTroquelar = findViewById(R.id.verificacion_txt_MetrosMatTroquelar);
        txt_verificacion_txt_Observaciones = findViewById(R.id.verificacion_txt_Observaciones);

        txt_verificacion_txt_Z_AltoMasGap= findViewById(R.id.verificacion_txt_Z_AltoMasGap);
        txt_verificacion_txt_Cilindro= findViewById(R.id.verificacion_txt_Cilindro);
        txt_verificacion_txt_Pistas= findViewById(R.id.verificacion_txt_Pistas);
        txt_verificacion_txt_EtiquetasEnBanda= findViewById(R.id.verificacion_txt_EtiquetasEnBanda);
        txt_verificacion_txt_EtiquetasPorRollo= findViewById(R.id.verificacion_txt_EtiquetasPorRollo);


        //tarea_Seleccionada = TareaInstance.Get();

//        Log.e("Mensaje",tarea_Seleccionada.getDescripcion());

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Error Instacia",Toast.LENGTH_LONG).show();
        }

        txt_verificacion_txt_NroDeSobre.setText(""+tarea_Seleccionada.getNroDeSobre());
        txt_verificacion_txt_Descripcion.setText(""+tarea_Seleccionada.getDescripcion());
        txt_verificacion_txt_MetrosAImprimir.setText(""+tarea_Seleccionada.getMetrosAImprimir());
        txt_verificacion_txt_MetrosPorRollo.setText(""+tarea_Seleccionada.getMetrosPorRollo());
        txt_verificacion_txt_MetrosMatTroquelar.setText(""+tarea_Seleccionada.getMetrosMatTroquelar());
        txt_verificacion_txt_Observaciones.setText(""+tarea_Seleccionada.getObservaciones());

        txt_verificacion_txt_Z_AltoMasGap.setText(""+tarea_Seleccionada.getZ_AltoMasGap());
        txt_verificacion_txt_Cilindro.setText(""+tarea_Seleccionada.getCilindro());
        txt_verificacion_txt_Pistas.setText(""+tarea_Seleccionada.getPistas());
        txt_verificacion_txt_EtiquetasEnBanda.setText(""+tarea_Seleccionada.getEtiquetasEnBanda());
        txt_verificacion_txt_EtiquetasPorRollo.setText(""+tarea_Seleccionada.getEtiquetasPorRollo());


         requestQueue = Volley.newRequestQueue(this);


        RecyclerView recyclerView = findViewById(R.id.verificacion_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterProduccion);


        for (Produccion_Lista lg : tarea_Seleccionada.getProduccion_Lista()) {

            produccion_actual = lg;

        }

        adapterProduccion.setNotes(tarea_Seleccionada.getProduccion_Lista());
        adapterProduccion.notifyDataSetChanged();


        adapterProduccion.setOnNoteSelectedListener(new AdapterProduccion.OnNoteSelectedListener() {
            @Override
            public void onClick(Produccion_Lista note) {
                produccion_actual = note;

                edt_AnchoFinalRolloYGap.setText(""+ produccion_actual.getAnchoFinalRolloYGap());
                edt_CantidadPistasImpresas.setText(""+ produccion_actual.getCantidadPistasImpresas());
                edt_CantidadTintas.setText(""+ produccion_actual.getCantidadTintas());
                edt_ScrapAjusteInicial.setText(""+ produccion_actual.getScrapAjusteInicial());
                edt_UnidadIdScrapInicial.setText(""+ produccion_actual.getScrapAjusteInicial_Unidades());
                edt_AnchoFinalRollo.setText(""+ produccion_actual.getAnchoFinalRollo());
                edt_CantidadPistasCortadas.setText(""+ produccion_actual.getCantidadPistasCortadas());
                edt_PistasTroquelUsadas.setText(""+ produccion_actual.getPistasTroquelUsadas());

            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

/*
                if (pdfAbierto && uno.isChecked() && dos.isChecked() && tres.isChecked() && cuatro.isChecked() && cinco.isChecked() && seis.isChecked()){


                   Produccion_Lista produccion =  cargarProduccion();

                    if (produccion!=null){

                        cargarVolley(produccion);
                    }



                }else{

                    toastPersonalziado("Verificar Especificacion");

                }
*/

            }
        });

        btn_verpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                     File TEMPfILE = new File("");

                    if (!permisosaceptados) {
                        toastPersonalziado("Debe aceptar los permisos para continuar");
                        pedir_permiso_escritura();

                    } else {

                        Intent I = new Intent(Verificacion_Activity.this, PdfActivity.class);
                        Bundle b = new Bundle();
                        I.setAction("ENVIANDO_INTENT");
                        b.putSerializable("MY_FILE", TEMPfILE);
                        I.putExtras(b);
                        startActivity(I);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }

                pdfAbierto = true;

            }
        });
    }


    private void pedir_permiso_escritura() {

        int readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED || readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            permisosaceptados = true;
        }

        if (ContextCompat.checkSelfPermission(Verificacion_Activity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(Verificacion_Activity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);

                return;
            }
        }

    }


    private void cargarVolley(Produccion_Lista produccion) {

        dialogProgress = ProgressHUD.show(Verificacion_Activity.this);


                JSONObject jsonObject = GsonUtils.toJSON(produccion);

                JsonObjectRequest request = new JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        Urls.agregarProduccion,
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext(), "Se cargo", Toast.LENGTH_LONG).show();

                                dialogProgress.dismiss();
                                Intent intent = new Intent(Verificacion_Activity.this, Produccion_Activity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                dialogProgress.dismiss();

                                //cargarVolley(produccion);
                            }
                        });
                request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);


    }

    private Produccion_Lista cargarProduccion() {


        //todo hay que crearlo sin fecha y sin usuario ID
        Produccion_Lista produccion = new Produccion_Lista();

        produccion.setProduccionId(0);
        produccion.setPedidoId(tarea_Seleccionada.getPedidoId());
        produccion.setTareaId(tarea_Seleccionada.getTareaId());
        produccion.setMetrosImpresos(0);
        produccion.setAnchoFinalRolloYGap(Float.parseFloat(edt_AnchoFinalRolloYGap.getText().toString()));
        produccion.setCantidadPistasImpresas(Float.parseFloat(edt_CantidadPistasImpresas.getText().toString()));
        produccion.setCantidadTintas(Float.parseFloat(edt_CantidadTintas.getText().toString()));
        produccion.setAnchoBobinaUsadoCm(0);
        produccion.setScrapAjusteInicial(Float.parseFloat(edt_ScrapAjusteInicial.getText().toString()));
        produccion.setScrapAjusteInicial_Unidades(edt_UnidadIdScrapInicial.getText().toString());
        produccion.setScrapAjusteProduccion(0);
        produccion.setScrapAjusteProduccion_Unidades("KG");
        produccion.setObservacionesCierre("SIN OBSERVACIONES");
        produccion.setRollosFabricdos(0);
        produccion.setAnchoFinalRollo(Float.parseFloat(edt_AnchoFinalRollo.getText().toString()));
        produccion.setCantidadPistasCortadas(Float.parseFloat(edt_CantidadPistasCortadas.getText().toString()));
        produccion.setPistasTroquelUsadas(Float.parseFloat(edt_PistasTroquelUsadas.getText().toString()));
        produccion.setRollosEmpaquetados(0);
        produccion.setUsuarioId(tarea_Seleccionada.getUsuarioId());

        return produccion;
    }

    private void verificarSwicht() {



    }

    private void toastPersonalziado(String msg){

    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

}
}