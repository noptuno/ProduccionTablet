package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;

import java.util.List;

public class BobinaActivity extends AppCompatActivity {


    private RequestQueue requestQueue;
    Tareas tarea_Seleccionada;
    private HttpLayer httpLayer;
    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobina);

        httpLayer = new HttpLayer(this);


        requestQueue = Volley.newRequestQueue(this);
        btn_guardar = findViewById(R.id.bobina_btn_guardar);

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
    }


    void registrarBobinas(){



        Bobinas bobinacargar = new Bobinas();

        bobinacargar.setBobinaId(0);
        bobinacargar.setTareaId(tarea_Seleccionada.getTareaId());
        bobinacargar.setProduccionId(1);
        bobinacargar.setProveedorId(2);
        bobinacargar.setProveedorNombre("");
        bobinacargar.setLote("lote123");
        bobinacargar.setAncho(5);
        bobinacargar.setTipoMaterialId(1);
        bobinacargar.setEsAbiertaoCerrada("A");
        bobinacargar.setDefectuosaKg(5);
        bobinacargar.setNombreTipoMaterial("A");


        httpLayer.cargarBobinas(bobinacargar, new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {


            }

            @Override
            public void onError(Exception e) {


            }
        });

    }
}