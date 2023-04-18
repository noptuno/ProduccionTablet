package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Material;
import com.codekolih.producciontablet.clases.Proveedor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Bobina_Activity extends OcultarTeclado {

    String idproveedorSeleccionado = "0";
    String TipoMaterialId = "1";

    String unidad = "Seleccionar";
    Spinner spi_ProveedorNombre,spi_EsAbiertaoCerrada,spi_NombreTipoMaterial;
    String proveedorSeleccionado= "NO";
    String NombreTipoMaterial= "NO";
    String abiertaocerrada = "Seleccionar";

    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;
    LinearLayout lyb_ProveedorNombre,lyb_EsAbiertaoCerrada, lyb_Lote, lyb_Ancho,lyb_DefectuosaKg;
    LinearLayout ly_once, ly_doce, ly_trece, ly_catorce, ly_quince, ly_dieciseis;
    Switch once, doce, trece, catorce, quince, dieciseis;
    Button btn_guardar;
    Button btn_cancelar;
    private SharedPreferences pref;
    private String MAQUINATIPOID = "0";

    private RequestQueue requestQueue;
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;

    private String USUARIO;
    int tareaid = 0;
    int produccionid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobina);

        btn_guardar = findViewById(R.id.bobina_btn_guardar);
        btn_cancelar = findViewById(R.id.bobina_btn_cancelar);

        spi_ProveedorNombre = findViewById(R.id.dialogbobina_spi_ProveedorNombre);
        spi_EsAbiertaoCerrada = findViewById(R.id.dialogbobina_spi_EsAbiertaoCerrada);
        edt_Lote = findViewById(R.id.dialogbobina_edt_Lote);
        edt_Ancho = findViewById(R.id.dialogbobina_edt_Ancho);
        edt_DefectuosaKg = findViewById(R.id.dialogbobina_edt_DefectuosaKg);

        spi_NombreTipoMaterial = findViewById(R.id.dialogbobina_spi_NombreTipoMaterial);

        lyb_ProveedorNombre= findViewById(R.id.lyb_ProveedorNombre);
        lyb_EsAbiertaoCerrada= findViewById(R.id.lyb_EsAbiertaoCerrada);
        lyb_Lote= findViewById(R.id.lyb_Lote);
        lyb_Ancho= findViewById(R.id.lyb_Ancho);
        lyb_DefectuosaKg= findViewById(R.id.lyb_DefectuosaKg);

        ly_once = findViewById(R.id.ly_once);
        ly_doce = findViewById(R.id.ly_doce);
        ly_trece = findViewById(R.id.ly_trece);
        ly_catorce= findViewById(R.id.ly_catorce);
        ly_quince= findViewById(R.id.ly_quince);
        ly_dieciseis= findViewById(R.id.ly_dieciseis);

        once = findViewById(R.id.once);
        doce = findViewById(R.id.doce);
        trece = findViewById(R.id.trece);
        catorce= findViewById(R.id.catorce);
        quince= findViewById(R.id.quince);
        dieciseis= findViewById(R.id.dieciseis);


        requestQueue = Volley.newRequestQueue(this);
        httpLayer = new HttpLayer(this);


        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        MAQUINATIPOID = pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "0");

//todo validar
        produccionid = TareaSingleton.SingletonInstance().getProduccionId();
        tareaid = TareaSingleton.SingletonInstance().getTarea().getTareaId();
        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();

        Log.e("cargado ","" +tareaid + produccionid);

        getSupportActionBar().hide();

        ConstraintLayout constraintLayout = findViewById(R.id.constrain_bobina);

        addKeyboardHideListener(constraintLayout);

        spi_NombreTipoMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String string = adapterView.getItemAtPosition(i).toString();

                String[] parts = string.split("-");
                TipoMaterialId = parts[0];
                NombreTipoMaterial = parts[1];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });



        spi_ProveedorNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String nombreProveedor = adapterView.getItemAtPosition(i).toString();


                if (!nombreProveedor.equals(unidad)){
                    String[] parts = nombreProveedor.split("-");
                    idproveedorSeleccionado = parts[0];
                    proveedorSeleccionado = parts[1];
                }else{
                    proveedorSeleccionado = unidad;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        spi_EsAbiertaoCerrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Abierta")){

                    abiertaocerrada = "A";
                }else if (adapterView.getItemAtPosition(i).toString().equals("Cerrada")){
                    abiertaocerrada = "B";
                }else{
                    abiertaocerrada = unidad;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!proveedorSeleccionado.equals(unidad)&&idproveedorSeleccionado.length()>0 && proveedorSeleccionado.length()>0 && edt_Lote.getText().length()>0 && NombreTipoMaterial.length()>0){

                    if (validarVariables()){

                        int idproveedor = parseInt(idproveedorSeleccionado);
                        String ProveedorNombre = proveedorSeleccionado;
                        String Lote = edt_Lote.getText().toString();
                        float Ancho = parseFloat(edt_Ancho.getText().toString());
                        String EsAbiertaoCerrada = abiertaocerrada;
                        float DefectuosaKg = parseFloat(edt_DefectuosaKg.getText().toString());
                        int tipoMaterialId = parseInt(TipoMaterialId);
                        String ProveedorMaterial = NombreTipoMaterial;

                        try{
                            if (MAQUINATIPOID.equals("5")){

                                if (!Lote.equals("")){

                                  //todo  interfaz_scrap.ResultadoBobinaDialogo(idproveedor,ProveedorNombre,Lote,Ancho,EsAbiertaoCerrada,DefectuosaKg,tipoMaterialId,ProveedorMaterial);
                                   // dialogo.dismiss();

                                    Bobinas bobinacargar = new Bobinas();
                                    bobinacargar.setBobinaId(0);
                                    bobinacargar.setTareaId(tareaid);
                                    bobinacargar.setProduccionId(produccionid);
                                    bobinacargar.setProveedorId(idproveedor);
                                    bobinacargar.setProveedorNombre(ProveedorNombre);
                                    bobinacargar.setLote(Lote);
                                    bobinacargar.setAncho(Ancho);
                                    bobinacargar.setTipoMaterialId(tipoMaterialId);
                                    bobinacargar.setEsAbiertaoCerrada(EsAbiertaoCerrada);
                                    bobinacargar.setDefectuosaKg(DefectuosaKg);
                                    bobinacargar.setNombreTipoMaterial(ProveedorMaterial);


                            cargarBobina(bobinacargar);


                                }else{
                                    Toast.makeText(getApplicationContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();
                                }
                            }else{

                                if (!Lote.equals("")){




                                //todo    interfaz_scrap.ResultadoBobinaDialogo(idproveedor,ProveedorNombre,Lote,Ancho,EsAbiertaoCerrada,DefectuosaKg,tipoMaterialId,ProveedorMaterial);

                                    Bobinas bobinacargar = new Bobinas();
                                    bobinacargar.setBobinaId(0);
                                    bobinacargar.setTareaId(tareaid);
                                    bobinacargar.setProduccionId(produccionid);
                                    bobinacargar.setProveedorId(idproveedor);
                                    bobinacargar.setProveedorNombre(ProveedorNombre);
                                    bobinacargar.setLote(Lote);
                                    bobinacargar.setAncho(Ancho);
                                    bobinacargar.setTipoMaterialId(tipoMaterialId);
                                    bobinacargar.setEsAbiertaoCerrada(EsAbiertaoCerrada);
                                    bobinacargar.setDefectuosaKg(DefectuosaKg);
                                    bobinacargar.setNombreTipoMaterial(ProveedorMaterial);

                                    cargarBobina(bobinacargar);



                                }else{

                                    Toast.makeText(getApplicationContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();
                                }

                            }


                        }catch (Exception e){
                            Log.e("ErrorBobina",e.toString());
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finish();

            }
        });

        cargarProveedor(getApplicationContext());
        cargarMaterial(getApplicationContext());

        OcultarVariables();


    }

    private void cargarBobina(Bobinas bobinacargar) {

        dialogProgress = ProgressHUD.show(Bobina_Activity.this);

        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("onSuccess CB", response.toString());

                dialogProgress.dismiss();

                String onSuccess = "ok";
                Intent intent = new Intent(Bobina_Activity.this, Produccion_Activity.class);
                intent.putExtra("onSuccess", onSuccess);
                setResult(Activity.RESULT_OK, intent);
                finish();


                //  cargarTareaHttp();

            }

            @Override
            public void onError(Exception e) {
                dialogProgress.dismiss();




                String onSuccess = "false";
                Intent intent = new Intent(Bobina_Activity.this, Produccion_Activity.class);
                intent.putExtra("onSuccess", onSuccess);
                setResult(Activity.RESULT_OK, intent);
                finish();



                //dialogErrorPrintet("No Cargo Bobina");
            }
        }, USUARIO);

    }

    ArrayList<String> listProveedores;
    ArrayList<String> listMaterial;
    ArrayAdapter<String> adapterProveedor;
    ArrayAdapter<String> adapterMateriales;

    private void cargarProveedor(Context c) {

        listProveedores = new ArrayList<>();

        listProveedores.add(unidad);
        for (Proveedor proveeedores : TareaSingleton.SingletonInstance().getProveedores()){
            listProveedores.add( proveeedores.getProveedorlId()+"-" +proveeedores.getNombre()  );
        }

        adapterProveedor = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, listProveedores);
        adapterProveedor.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spi_ProveedorNombre.setAdapter(adapterProveedor);
        spi_ProveedorNombre.setSelection(0);

    }

    private void cargarMaterial(Context c) {

        listMaterial = new ArrayList<>();
        listMaterial.add(unidad);
        for (Material materiales : TareaSingleton.SingletonInstance().getMateriales()){
            listMaterial.add( materiales.getTipoMaterialId()+"-" +materiales.getNombre()  );
        }

        adapterMateriales = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, listMaterial);
        adapterMateriales.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spi_NombreTipoMaterial.setAdapter(adapterMateriales);
        spi_NombreTipoMaterial.setSelection(0);

    }


    private boolean validarVariables(){

        boolean validado = true;

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("Ancho".equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    String a = edt_Ancho.getText().toString();
                    if(a.equals("0") || a.equals("") ){
                        validado = false;
                        break;
                    }
                }

            }
            else if ("EsAbiertaoCerrada".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if(abiertaocerrada.equals("Seleccionar")){
                        validado = false;
                        break;
                    }
                }else{
                    abiertaocerrada = "C";
                }


            }
            else if ("DefectuosaKg".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    String a = edt_DefectuosaKg.getText().toString();
                    if(a.equals("")){
                        validado = false;
                        break;
                    }
                }

            }else if ("11".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (entry.getValue().equals("0")){
                        if (!once.isChecked()){
                            validado = false;
                            break;
                        }
                    }
                }



            }else if ("12".toString().equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    if (!doce.isChecked()){
                        validado = false;
                        break;
                    }
                }




            }else if ("13".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!trece.isChecked()){
                        validado = false;
                        break;
                    }
                }

            }else if ("14".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!catorce.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }else if ("15".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!quince.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }else if ("16".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!dieciseis.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }
        }

        return validado;

    }



    private void OcultarVariables() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("Ancho".equals(entry.getKey())){
                lyb_Ancho.setVisibility(parseInt(entry.getValue()));
            }
            else if ("EsAbiertaoCerrada".equals(entry.getKey())){
                lyb_EsAbiertaoCerrada.setVisibility(parseInt(entry.getValue()));
            }
            else if ("DefectuosaKg".equals(entry.getKey())){
                lyb_DefectuosaKg.setVisibility(parseInt(entry.getValue()));

            }else if ("11".equals(entry.getKey())){
                ly_once.setVisibility(parseInt(entry.getValue()));
            }else if ("12".toString().equals(entry.getKey())){
                ly_doce.setVisibility(parseInt(entry.getValue()));
            }else if ("13".toString().equals(entry.getKey())){
                ly_trece.setVisibility(parseInt(entry.getValue()));
            }else if ("14".toString().equals(entry.getKey())){
                ly_catorce.setVisibility(parseInt(entry.getValue()));
            }else if ("15".toString().equals(entry.getKey())){
                ly_quince.setVisibility(parseInt(entry.getValue()));
            }else if ("16".toString().equals(entry.getKey())){
                ly_dieciseis.setVisibility(parseInt(entry.getValue()));
            }

        }
    }


}