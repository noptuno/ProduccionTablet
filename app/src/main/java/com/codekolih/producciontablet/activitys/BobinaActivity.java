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
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;

public class BobinaActivity extends AppCompatActivity {


    private RequestQueue requestQueue;
    Tareas tarea_Seleccionada;

    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobina);

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

        JSONObject jsonObject = GsonUtils.toJSON(bobinacargar);
        JsonObjectRequest request = new JsonObjectRequest(
                com.android.volley.Request.Method.POST,
                Urls.agregarbobinas,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), "Se cargo", Toast.LENGTH_LONG).show();
                     finish();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                       // cargarBobina();
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
}