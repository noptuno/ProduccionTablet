package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_USUARIO;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.adapter.AdapterTareas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tarea_Activity extends AppCompatActivity {

    private TextView txt_imprenta, txt_usuario, txt_fecha,txt_hora;
    private String MAQUINATIPOID = "";
    private String MAQUINAID = "";
    private RequestQueue requestQueue;
    private ArrayList<Tareas> listImprentas = new ArrayList<>();
    private AdapterTareas adapterTareas = new AdapterTareas();
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private SharedPreferences pref;
    private RecyclerView recyclerViewTareas;
    private Button btn_cerrar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        Log.e("TareaActivity","INICIO");
        //declaraciones

        requestQueue = Volley.newRequestQueue(this);

        httpLayer = new HttpLayer(this);

        //recicler

        txt_imprenta = findViewById(R.id.tarea_txt_imprenta);
        txt_usuario= findViewById(R.id.tarea_txt_usuario);
        txt_fecha= findViewById(R.id.tarea_txt_fecha);
        txt_hora= findViewById(R.id.txt_tarea_hora);
        btn_cerrar_sesion = findViewById(R.id.tarea_btn_cerraSesion);

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder build4 = new AlertDialog.Builder(Tarea_Activity.this);
                build4.setMessage("¿Desea Cerrar Sesion").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        recyclerViewTareas = findViewById(R.id.tarea_recycler);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewTareas.setAdapter(adapterTareas);

        //pref
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        MAQUINATIPOID = pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "NO");
        MAQUINAID = pref.getString(PREF_PRODUCCION_MAQUINAID, "NO");
        txt_imprenta.setText(pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"));
        txt_usuario.setText(pref.getString(PREF_PRODUCCION_USUARIO, "NO"));

        //TODO Validar maquinatipoid y aquinaid

        adapterTareas.setOnNoteSelectedListener(new AdapterTareas.OnNoteSelectedListener() {
            @Override
            public void onClick(Tareas note) {


                AlertDialog.Builder build4 = new AlertDialog.Builder(Tarea_Activity.this);
                build4.setMessage("¿Desea Seleccionar la Tarea : " + note.getDescripcion()).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TareaSingleton.SingletonInstance().setTarea(note);
                        Intent intent = new Intent(Tarea_Activity.this, Verificacion_Activity.class);
                        intent.putExtra("tarea", note);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

        //todo valdiar internet
        cargarTarea();
        cargarfecha();

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

    void cargarTarea(){

        dialogProgress = ProgressHUD.show(Tarea_Activity.this);
        httpLayer.getTareas("0/0",new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                adapterTareas.setNotes(response);
                adapterTareas.notifyDataSetChanged();
                dialogProgress.dismiss();

                for (Tareas lg : response) {
                   // Log.e("Datos_tareas",lg.toString());
                    Log.e("ListTareas","Cod: " + lg.getTareaId()+" Cant produccion: "+ lg.getProduccion_Lista().size() + " cantbobinas: "+ lg.getBobinas().size());

                }

            }
            @Override
            public void onError(Exception e) {

                Log.e("TareaActivity",e.toString());
                dialogProgress.dismiss();
            }
        });
    }


}