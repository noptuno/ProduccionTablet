package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_ELEGIRTAREA;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINAID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_NOMBREMAQUINA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Utils;
import com.codekolih.producciontablet.aciones.Validarinternet;
import com.codekolih.producciontablet.adapter.AdapterTareas;
import com.codekolih.producciontablet.clases.Material;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Tarea_Activity extends AppCompatActivity {

    private TextView txt_imprenta, txt_usuario, txt_fecha, txt_hora;
    private int MAQUINATIPOID = 0;
    private int MAQUINAID = 0;
    private String USUARIO;
    private RequestQueue requestQueue;
    private ArrayList<Tareas> listImprentas = new ArrayList<>();
    private AdapterTareas adapterTareas = new AdapterTareas();
    private ProgressHUD dialogProgress;
    private HttpLayer httpLayer;
    private SharedPreferences pref;
    private RecyclerView recyclerViewTareas;
    private Button btn_cerrar_sesion;
    private String PermiteCambioPrioridad = "false";

    private ImageButton btnactualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        Log.e("TareaActivity", "INICIO");
        //declaraciones

        requestQueue = Volley.newRequestQueue(this);

        httpLayer = new HttpLayer(this);

        //recicler

        txt_imprenta = findViewById(R.id.tarea_txt_imprenta);
        txt_usuario = findViewById(R.id.tarea_txt_usuario);
        txt_fecha = findViewById(R.id.tarea_txt_fecha);
        txt_hora = findViewById(R.id.txt_tarea_hora);
        btn_cerrar_sesion = findViewById(R.id.tarea_btn_cerraSesion);
        btnactualizar = findViewById(R.id.btnactualziartarea);

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarTarea();


            }
        });

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

        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();
        txt_usuario.setText(USUARIO);

        try {
            pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
            MAQUINAID = pref.getInt(PREF_PRODUCCION_MAQUINAID,0);

            if (MAQUINAID==0){
                Toast.makeText(getApplicationContext(), "No hay imprenta seleccionada", Toast.LENGTH_SHORT).show();
                finish();
            }

            txt_imprenta.setText(String.format("%s Tipo: %s", pref.getString(PREF_PRODUCCION_NOMBREMAQUINA, "NO"), pref.getString(PREF_PRODUCCION_MAQUINATIPOID, "0")));

            PermiteCambioPrioridad = pref.getString(PREF_PRODUCCION_ELEGIRTAREA, "false");
            Log.e("ElegirTarea: ", PermiteCambioPrioridad);

        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Hubo un problema en los datos de Preference", Toast.LENGTH_SHORT).show();
            finish();
        }




        //TODO Validar maquinatipoid y aquinaid

        adapterTareas.setOnNoteSelectedListener(new AdapterTareas.OnNoteSelectedListener() {
            @Override
            public void onClick(Tareas note, int a) {

                if (PermiteCambioPrioridad.equals("false")) {
                    if (a == 0) {
                        elegirTarea(note);
                    } else {
                        Toast.makeText(getApplicationContext(), "No puede elegir esa Tarea", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    elegirTarea(note);
                }
            }
        });

        //todo valdiar internet


        cargarfecha();

    }

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                finish();
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        if (Validarinternet.validarConexionInternet(this)) {
            cargarTarea();
            mHandler.postDelayed(mRunnable, 300000);
        }


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 300000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacks(mRunnable);
    }


    private void elegirTarea(Tareas note) {

        AlertDialog.Builder build4 = new AlertDialog.Builder(Tarea_Activity.this);
        build4.setMessage("¿Desea Seleccionar la Tarea : " + note.getArticuloId()).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                TareaSingleton.SingletonInstance().setTarea(note);
                TareaSingleton.SingletonInstance().setNombrepdf(note.getArchivoEspecificacion());

                Intent intent = new Intent(Tarea_Activity.this, Verificacion_Activity.class);
                intent.putExtra("tarea", note);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                Map<String, Object> estado = new HashMap<>();
                estado.put("TareaId", note.getTareaId());
                estado.put("EstadoId", "A1");
                estado.put("TipoEstadoId", "I");
                cambioEstado(estado);

            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog4 = build4.create();
        alertDialog4.show();
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    private void cambioEstado(Map<String, Object> estado) {


        httpLayer.cargarEstado(GsonUtils.toJSON(estado), new HttpLayer.HttpLayerResponses<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("Tarea_Activity", "Cargo Estado");

                finish();
            }

            @Override
            public void onError(Exception e) {
                Log.e("Tarea_Activity", "Error al cargar Estado");
                Toast.makeText(getApplicationContext(), "No cargo Estado", Toast.LENGTH_SHORT).show();
            }
        }, USUARIO);

    }

    private void cargarProveedor() {

        httpLayer.listaProveedor(new HttpLayer.HttpLayerResponses<ArrayList<Proveedor>>() {
            @Override
            public void onSuccess(ArrayList<Proveedor> response) {

                TareaSingleton.SingletonInstance().setProveedores(response);
                Log.e("TareaActivity", "cargoProveedores");

                cargarMateriales();
            }

            @Override
            public void onError(Exception e) {
                ArrayList<Proveedor> listProveedores = new ArrayList<>();
                TareaSingleton.SingletonInstance().setProveedores(listProveedores);
                dialogErrorPrintet("No cargo Proveedor");
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

    void cargarTarea() {

        dialogProgress = ProgressHUD.show(Tarea_Activity.this);

        httpLayer.getTareas(MAQUINAID + "/O", new HttpLayer.HttpLayerResponses<List<Tareas>>() {
            @Override
            public void onSuccess(List<Tareas> response) {

                List<Tareas> temp = new ArrayList<>();

                    for (Tareas lg : response) {

                        if (lg.getMaquinaId() == MAQUINAID) {

                            temp.add(lg);

                            Log.e("Tareas para filtrar: ", "" + lg.getTareaId());

                       }
                        // Log.e("Datos_tareas",lg.toString());
                        Log.e("ListTareas", "Cod: " + lg.getTareaId() + " Cant produccion: " + lg.getProduccion_Lista().size() + " cantbobinas: " + lg.getBobinas().size());
                    }

                    adapterTareas.setNotes(temp);
                    adapterTareas.notifyDataSetChanged();


                dialogProgress.dismiss();
                cargarProveedor();

            }

            @Override
            public void onError(Exception e) {
                Log.e("TareaActivity", e.toString());
                dialogProgress.dismiss();
                dialogErrorPrintet("No cargo Tareas");
            }
        });
    }

    private void cargarMateriales() {

        httpLayer.listaMateriales(new HttpLayer.HttpLayerResponses<ArrayList<Material>>() {
            @Override
            public void onSuccess(ArrayList<Material> response) {
                TareaSingleton.SingletonInstance().setMateriales(response);
                Log.e("TareaActivity", "cargoMateriales");

            }

            @Override
            public void onError(Exception e) {
                ArrayList<Material> listMateriales = new ArrayList<>();
                TareaSingleton.SingletonInstance().setMateriales(listMateriales);
                dialogErrorPrintet("No cargo Materiales");
            }
        });


    }

    private void dialogErrorPrintet(String mensaje) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Tarea_Activity.this);
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

                if(mensaje.equals("No cargo Tareas")){
                    cargarTarea();
                }else if(mensaje.equals("No cargo Proveedor")){
                    cargarProveedor();
                }else if(mensaje.equals("No cargo Materiales")){
                    cargarMateriales();
                }



            }
        });
    }

}