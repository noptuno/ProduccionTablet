package com.codekolih.producciontablet.activitys;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    ArrayList<Proveedor> contacts;


    String proveedorSeleccionado;
    int idproveedorSeleccionado;
    String abiertaocerrada = "A";
    int produccionId;


    Spinner  spi_ProveedorNombre,spi_EsAbiertaoCerrada;
    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;


    ArrayAdapter<String> adapterProveedor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobina);

        listProveedores = new ArrayList<>();
        contacts = TareaSingleton.SingletonInstance().getProveedores();
        produccionId = TareaSingleton.SingletonInstance().getProduccionId();
        requestQueue = Volley.newRequestQueue(this);

        httpLayer = new HttpLayer(this);
        btn_guardar = findViewById(R.id.bobina_btn_guardar);


        spi_ProveedorNombre = (Spinner) findViewById(R.id.bobina_spi_ProveedorNombre);
        spi_EsAbiertaoCerrada = findViewById(R.id.bobina_spi_EsAbiertaoCerrada);
        edt_Lote= findViewById(R.id.bobina_edt_Lote);
        edt_Ancho= findViewById(R.id.bobina_edt_Ancho);
        edt_DefectuosaKg= findViewById(R.id.bobina_edt_DefectuosaKg);


        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea())==null){
            Toast.makeText(getApplicationContext(),"Error Instacia",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"IDPRODUCCON: " + TareaSingleton.SingletonInstance().getProduccionId(),Toast.LENGTH_LONG).show();
        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarBobinas();

            }

        });


        cargarProveedor();


        spi_ProveedorNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                for (Proveedor proveeedores : contacts){
                    if(proveeedores.getNombre().equals(adapterView.getItemAtPosition(i).toString())){
                        proveedorSeleccionado = proveeedores.getNombre();
                        idproveedorSeleccionado = proveeedores.getProveedorlId();
                        break;
                    }
                }
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


    }

    private void cargarProveedor() {


        listProveedores.clear();

        for (Proveedor proveeedores : contacts){

            listProveedores.add(proveeedores.getNombre());
        }

        adapterProveedor = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listProveedores);
        adapterProveedor.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spi_ProveedorNombre.setAdapter(adapterProveedor);
    }



    void registrarBobinas(){


        Bobinas bobinacargar = new Bobinas();

        bobinacargar.setBobinaId(0);
        bobinacargar.setTareaId((int) tarea_Seleccionada.getTareaId());
        bobinacargar.setProduccionId(produccionId);
        bobinacargar.setProveedorId(idproveedorSeleccionado);
        bobinacargar.setProveedorNombre(proveedorSeleccionado);
        bobinacargar.setLote(edt_Lote.getText().toString());
        bobinacargar.setAncho(parseFloat(edt_Ancho.getText().toString()));
        bobinacargar.setTipoMaterialId(1);
        bobinacargar.setEsAbiertaoCerrada(abiertaocerrada);
        bobinacargar.setDefectuosaKg(parseFloat(edt_DefectuosaKg.getText().toString()));
        bobinacargar.setNombreTipoMaterial("");

        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","true");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();



            }

            @Override
            public void onError(Exception e) {

                Toast.makeText(getApplicationContext(), "No Cargo Bobina Reintentar",Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }
}