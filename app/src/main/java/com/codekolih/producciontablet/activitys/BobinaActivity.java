package com.codekolih.producciontablet.activitys;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BobinaActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    Tareas tarea_Seleccionada;
    private HttpLayer httpLayer;
    Button btn_guardar;
    ArrayList<String> listProveedores;
    ArrayList<Proveedor> proveedores = new ArrayList<>();
    String proveedorSeleccionado;
    int idproveedorSeleccionado = 1;
    String abiertaocerrada = "A";
    Spinner  spi_ProveedorNombre,spi_EsAbiertaoCerrada;
    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;
    int produccionid;
    ArrayAdapter<String> adapterProveedor;
    private ProgressHUD dialogProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobina);

        requestQueue = Volley.newRequestQueue(this);
        httpLayer = new HttpLayer(this);

        btn_guardar = findViewById(R.id.bobina_btn_guardar);
        spi_ProveedorNombre = (Spinner) findViewById(R.id.bobina_spi_ProveedorNombre);
        spi_EsAbiertaoCerrada = findViewById(R.id.bobina_spi_EsAbiertaoCerrada);
        edt_Lote= findViewById(R.id.bobina_edt_Lote);
        edt_Ancho= findViewById(R.id.bobina_edt_Ancho);
        edt_DefectuosaKg= findViewById(R.id.bobina_edt_DefectuosaKg);



        produccionid =  TareaSingleton.SingletonInstance().getProduccionId();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarBobinas();

            }

        });

        spi_ProveedorNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                proveedorSeleccionado = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spi_EsAbiertaoCerrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                abiertaocerrada = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cargarDatos();
    }

    private void cargarDatos() {

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Error Instacia",Toast.LENGTH_LONG).show();
        }


        Log.e("Bobina_ac_produccionid",produccionid+"");
        Log.e("Bobina_ac_tareaid",tarea_Seleccionada.getTareaId()+"");
        cargarProveedor();
    }


    void cargarProveedor() {

        dialogProgress = ProgressHUD.show(BobinaActivity.this);

        httpLayer.listaProveedor(new HttpLayer.HttpLayerResponses<ArrayList<Proveedor>>() {
            @Override
            public void onSuccess(ArrayList<Proveedor> response) {

                listProveedores = new ArrayList<>();

                for (Proveedor proveeedores :  response){
                    listProveedores.add(proveeedores.getNombre());
                }
                adapterProveedor = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listProveedores);
                adapterProveedor.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spi_ProveedorNombre.setAdapter(adapterProveedor);
                dialogProgress.dismiss();
            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo proveedores Reintentar",Toast.LENGTH_SHORT).show();
                dialogProgress.dismiss();
            }
        });

    }



    void registrarBobinas(){


        int tareaId = tarea_Seleccionada.getTareaId();
        int ProduccionId = produccionid;
        int ProveedorId = idproveedorSeleccionado;
        String ProveedorNombre = proveedorSeleccionado;
        String Lote = edt_Lote.getText().toString();
        Float Ancho = parseFloat(edt_Ancho.getText().toString());
        int TipoMaterialId = 1;
        String EsAbiertaoCerrada = abiertaocerrada;
        Float DefectuosaKg = parseFloat(edt_DefectuosaKg.getText().toString());
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

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","true");
                setResult(Activity.RESULT_OK,returnIntent);

                Log.e("Bobina_activit","Registro bobina");
                finish();

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
}