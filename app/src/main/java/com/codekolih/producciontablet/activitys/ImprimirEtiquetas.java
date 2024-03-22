package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_IP;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_MAQUINATIPOID;
import static com.codekolih.producciontablet.aciones.Variables.prnEtiquetas;
import static com.codekolih.producciontablet.aciones.Variables.prnNumerados;
import static com.codekolih.producciontablet.aciones.Variables.prnRollos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Validarinternet;
import com.codekolih.producciontablet.clases.Tareas;
import com.codekolih.producciontablet.dialogs.CantidadDialog;
import com.codekolih.producciontablet.dialogs.CantidadPrintDialog;
import com.example.tscdll.TscWifiActivity;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import honeywell.connection.ConnectionBase;
import honeywell.connection.Connection_TCP;

public class ImprimirEtiquetas extends AppCompatActivity implements CantidadPrintDialog.finalizarCuadro {

    private SharedPreferences pref;
    private  String ipImpresora;

    Tareas tarea_Seleccionada;
    int valorSeleccionado = 1;
    private Spinner spinnerEtiquetas;
    private LinearLayout linearRollo,  layoutetiqcaja, layoutrollocaja,layoutetiqrollo;
    private EditText e_etixrollo,e_rolloxcaja,e_num_inicial,e_num_final,e_serie,e_cant_rollos_x_caja,e_num_x_rollos;
    Button btn_cancelar, btn_aceptar;
    TscWifiActivity TscEthernetDll;
    int etiquetasxrollo=0;
    float rollosxcajas=0;
    int num_inicial=0;
    int num_final=0;
    String serie ="A";
    int cant_rollos_x_caja=0;
    int num_x_rollos =0;
    private Handler mHandler = new Handler();
     private LinearLayout linear_filtro;
     private EditText filtro;
     private Button btn_filtro,btn_limpiar;
     private TextView descripcion;
     private String USUARIO= "";
     private String descripcion_producto= "";
     private String CodigoID= "";
     private RequestQueue requestQueue;
     private HttpLayer httpLayer;
     private String fechaFormateada="";
     private String horaFormateada= "";
    ConnectionBase conn = null;
     private Boolean poderImprimir=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprimir_etiquetas);

        spinnerEtiquetas = findViewById(R.id.spinnerEtiquetas);

        linearRollo= findViewById(R.id.linearRollo);

        layoutetiqrollo= findViewById(R.id.layoutetqxrollos);
        layoutetiqcaja= findViewById(R.id.layoutetiqcaja);
        layoutrollocaja= findViewById(R.id.layoutrollocaja);

        e_etixrollo= findViewById(R.id.etixrollo);
        e_rolloxcaja= findViewById(R.id.rolloxrcaja);
        //e_etixcaja= findViewById(R.id.etixcaja);

        e_num_inicial= findViewById(R.id.num_inicial);
        e_num_final= findViewById(R.id.num_final);
        e_serie= findViewById(R.id.serie);

        e_cant_rollos_x_caja= findViewById(R.id.cant_rollos_x_caja);
        e_num_x_rollos= findViewById(R.id.num_x_rollos);

        btn_cancelar = findViewById(R.id.eti_btn_cancel);
        btn_aceptar = findViewById(R.id.eti_btn_confirmar);
        btn_limpiar = findViewById(R.id.eti_btn_limp);

        linear_filtro = findViewById(R.id.eti_linear_filtro);
        filtro = findViewById(R.id.eti_filtro);
        descripcion = findViewById(R.id.eti_descripcion);
        btn_filtro = findViewById(R.id.eti_btn_filtro);

        requestQueue = Volley.newRequestQueue(this);

        httpLayer = new HttpLayer(this);

        tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea();

        TscEthernetDll = new TscWifiActivity();


        btn_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigofiltro = filtro.getText().toString().trim();

                if (!codigofiltro.equals("") && codigofiltro.length()>0 ){

                    if (Validarinternet.validarConexionInternet(ImprimirEtiquetas.this)) {


                        descripcion.setText("TareaFiltradaEjemplodemasde90caracteresaraverlaimpresionenfisicoycorroborrarquenosesuperelaslineasymasdecaracteresdelonormal");

                        descripcion_producto = descripcion.getText().toString();
                        CodigoID = codigofiltro;

                        btn_filtro.setEnabled(false);
                        btn_aceptar.setEnabled(true);
                        filtro.setEnabled(false);

                    }

                }

            }

        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validar()){

                    if(!(valorSeleccionado == 3)){

                        new CantidadPrintDialog(ImprimirEtiquetas.this, ImprimirEtiquetas.this);
                       // prepararNumeros(1);
                    }else{
                        prepararNumeros(0);
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpiar();
                finish();

            }
        });

        btn_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpiar();

            }
        });

        cargarnombrevariable();
        configurarSpinner();
        obtenerfechayhora();
        cargarTareaSeleccionada();

    }


    private void cargarTareaSeleccionada() {

        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);

        ipImpresora = pref.getString(PREF_PRODUCCION_IP, "0").trim();
        USUARIO = TareaSingleton.SingletonInstance().getUsuarioIniciado();

        String origen = getIntent().getStringExtra("origen");
        if (origen != null) {

        } else {

        }

        if ((tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea()) == null) {
            linear_filtro.setVisibility(View.VISIBLE);
            descripcion_producto = "Debe buscar el producto";
            CodigoID = "";
            filtro.requestFocus();
        } else {

            btn_aceptar.setEnabled(true);
            linear_filtro.setVisibility(View.VISIBLE);
            btn_filtro.setVisibility(View.GONE);
            filtro.setEnabled(false);
            String temcod = String.format("%s", tarea_Seleccionada.getArticuloId());
            String[] partes = temcod.split("\\*"); // Dividir la cadena en partes usando el asterisco como delimitador
            CodigoID = partes[0].trim();
            filtro.setText(CodigoID);
            descripcion_producto = String.format("%s", tarea_Seleccionada.getConcepto());
            descripcion.setText(descripcion_producto);
            e_etixrollo.requestFocus();

        }

    }

    private void obtenerfechayhora() {

        Date fechaHoraActual = new Date();

        // Formatear la fecha en el formato "dd/MM/yyyy"
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        fechaFormateada = formatoFecha.format(fechaHoraActual);

        // Formatear la hora en el formato "HH:mm"
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
        horaFormateada = formatoHora.format(fechaHoraActual);

    }

    @Override
    public void onBackPressed() {
        // Realiza aquí la acción que deseas, por ejemplo, minimizar la actividad
        moveTaskToBack(false); // Minimiza la actividad
        // O muestra un diálogo de confirmación al usuario
        // showDialogConfirmation();
    }


    private void senddatoswifiTCP(String prn) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                byte[] msgBuffer = prn.getBytes(StandardCharsets.UTF_8);

                try {
                    conn = null;
                    conn = Connection_TCP.createClient(ipImpresora, 9100, false);
                    if (!conn.getIsOpen()) {
                        conn.open();
                    }

                    int bytesWritten = 0;
                    int bytesToWrite = 1024;
                    int totalBytes = msgBuffer.length;
                    int remainingBytes = totalBytes;
                    while (bytesWritten < totalBytes) {
                        if (remainingBytes < bytesToWrite)
                            bytesToWrite = remainingBytes;

                        // Send data, 1024 bytes at a time until all data sent
                        conn.write(msgBuffer, bytesWritten, bytesToWrite);
                        bytesWritten += bytesToWrite;
                        remainingBytes = remainingBytes - bytesToWrite;
                        Thread.sleep(100);
                    }

                    conn.close();

                } catch (Exception e) {
                    if (conn != null)
                        conn.close();
                    e.printStackTrace();

                }
            }
        }).start();

    }


    private void prepararNumeros(float c) {

        String[] stringsDistribuidos = distribuirString(descripcion_producto);

        // Mostrar el resultado
        for (String str : stringsDistribuidos) {
            System.out.println(str);
        }

        int cant = (int) c;

        switch (valorSeleccionado) {
            case 1:

               String prn = prnEtiquetas;

                prn = prn.replace("~usu~",USUARIO);
                prn = prn.replace("~fec~",fechaFormateada);
                prn = prn.replace("~hor~",horaFormateada);
                prn = prn.replace("~exr~",""+etiquetasxrollo);
                prn = prn.replace("~rxc~",""+rollosxcajas);
                prn = prn.replace("~cod~",CodigoID);
                prn = prn.replace("~des1~",stringsDistribuidos[0]);
                prn = prn.replace("~des2~",stringsDistribuidos[1]);
                prn = prn.replace("~des3~",stringsDistribuidos[2]);
                prn = prn.replace("~cant~",""+cant);

                System.out.println("PRN" + prn);

                senddatoswifiTCP(prn);

                break;

            case 2:

                String prn2 = prnRollos;

                prn2 = prn2.replace("~usu~",USUARIO);
                prn2 = prn2.replace("~fec~",fechaFormateada);
                prn2 = prn2.replace("~hor~",horaFormateada);
                prn2 = prn2.replace("~rxc~",""+rollosxcajas);
                prn2 = prn2.replace("~cod~",CodigoID);
                prn2 = prn2.replace("~des1~",stringsDistribuidos[0]);
                prn2 = prn2.replace("~des2~",stringsDistribuidos[1]);
                prn2 = prn2.replace("~des3~",stringsDistribuidos[2]);
                prn2 = prn2.replace("~cant~",""+cant);
                System.out.println("PRN" + prn2);

                senddatoswifiTCP(prn2);

                break;

            case 3:

                Log.e("Datos", "num_final: " + num_final + "num_inicial: " + num_inicial + "num_x_rollos: "+num_x_rollos + "cantidad rollos por caja: "+ cant_rollos_x_caja);

                int numero_ini = num_inicial;
                int numero_fin = num_final;
                int cant_numeros_por_cada_rollo = num_x_rollos;
                int cant_rollos_por_cajas = cant_rollos_x_caja;
                float cantidad_de_rollos_totales = (float) (numero_fin - numero_ini) / cant_numeros_por_cada_rollo; // 10.13
                double cant_cajas_necesarias = Math.ceil(cantidad_de_rollos_totales / cant_rollos_por_cajas); // 1.44 -> 2

                if (cant_cajas_necesarias>500){
                    Toast.makeText(getApplicationContext(),"ERROR: Se deben imprimir una cantidad de etiqeutas superior a 500",Toast.LENGTH_SHORT).show();
                    break;
                }
                int cantidad_numeros_por_cajas = cant_rollos_por_cajas * cant_numeros_por_cada_rollo;
                int numeros_restantes = numero_fin - numero_ini + 1;
                int residuo = numeros_restantes % cantidad_numeros_por_cajas;
                int rollos_adicionales = residuo > 0 ? (int) Math.ceil(residuo / (float) cant_numeros_por_cada_rollo) : 0;
                DecimalFormat df2 = new DecimalFormat("#.###");
                float rollos_adicionales2 =Float.parseFloat(df2.format(residuo > 0 ? residuo / (float) cant_numeros_por_cada_rollo : 0));


                // Imprimir resultados
                DecimalFormat df = new DecimalFormat("#.####");
                String etiquetas = Math.ceil(cant_cajas_necesarias) == cant_cajas_necesarias ? Integer.toString((int) cant_cajas_necesarias) : df.format(Math.ceil(cant_cajas_necesarias));

                int numeroInicial = numero_ini;

                String prn3 = prnNumerados;

                for (int i = 1; i <= cant_cajas_necesarias; i++) {

                    int numeroFinal = Math.min(numeroInicial + cantidad_numeros_por_cajas - 1, numero_fin);

                    int cantidadderollos = i < cant_cajas_necesarias || rollos_adicionales == 0 ? cant_rollos_por_cajas : (int) rollos_adicionales2;

                    System.out.println("numeroInicial: " + numeroInicial + " hasta: " + numeroFinal);
                    System.out.println("Caja n: " + i + " de: " + etiquetas);
                    System.out.println("cantidad rollos: " + cantidadderollos + " - ");

                    prn3 = prn3.replace("~usu~",USUARIO);
                    prn3 = prn3.replace("~fec~",fechaFormateada);
                    prn3 = prn3.replace("~hor~",horaFormateada);
                    prn3 = prn3.replace("~cod~",CodigoID);
                    prn3 = prn3.replace("~des1~",stringsDistribuidos[0]);
                    prn3 = prn3.replace("~des2~",stringsDistribuidos[1]);
                    prn3 = prn3.replace("~des3~",stringsDistribuidos[2]);
                    prn3 = prn3.replace("~den~",""+numeroInicial);
                    prn3 = prn3.replace("~aln~",""+numeroFinal);
                    prn3 = prn3.replace("~caj~",""+i);
                    prn3 = prn3.replace("~de~",""+etiquetas);
                    prn3 = prn3.replace("~ser~",""+"A");
                    prn3 = prn3.replace("~cant~",""+1 + "\n");

                    if (i==cant_cajas_necesarias){
                        prn3 = prn3.replace("~can~",""+rollos_adicionales2);
                    }else{
                        prn3 = prn3.replace("~can~",""+cantidadderollos);
                    }


                    numeroInicial = numeroFinal + 1;

                    if (i<cant_cajas_necesarias){
                        prn3 = prn3 + "\n" + prnNumerados;
                    }

                }
                System.out.println("PRN" + prn3);
                senddatoswifiTCP(prn3);

                break;
        }

    }

    public static String[] distribuirString(String inputString) {
        String[] stringsDistribuidos = new String[3];

        // Verificar si el string principal tiene más de 40 caracteres
        if (inputString.length() > 40) {
            // Buscar el índice del último espacio antes del límite de 45 caracteres
            int index = 40;
            boolean foundSpace = false;
            while (index > 0 && index < inputString.length()) {
                if (inputString.charAt(index) == ' ') {
                    foundSpace = true;
                    break;
                }
                index--;
            }

            // Si no se encontró un espacio antes del límite de 45 caracteres, o no se encontró ningún espacio en absoluto, cortar en 40 caracteres
            if (!foundSpace) {
                index = 40;
            }

            // Distribuir el string principal
            stringsDistribuidos[0] = inputString.substring(0, index);

            int remainingChars = inputString.length() - index;
            if (remainingChars > 0) {
                int endIndex = Math.min(index + 40, inputString.length());
                stringsDistribuidos[1] = inputString.substring(index, endIndex);

                remainingChars = inputString.length() - endIndex;
                if (remainingChars > 0) {
                    stringsDistribuidos[2] = inputString.substring(endIndex, Math.min(endIndex + 40, inputString.length()));
                } else {
                    stringsDistribuidos[2] = "";
                }
            } else {
                stringsDistribuidos[1] = "";
                stringsDistribuidos[2] = "";
            }
        } else {
            // Si el string principal tiene 40 caracteres o menos, dejar las otras posiciones vacías
            stringsDistribuidos[0] = inputString;
            stringsDistribuidos[1] = "";
            stringsDistribuidos[2] = "";
        }

        return stringsDistribuidos;
    }

    Boolean validar(){

        boolean validado = false;
        if (valorSeleccionado==1){
            if (!e_etixrollo.getText().toString().isEmpty() && !e_rolloxcaja.getText().toString().isEmpty()) {
                etiquetasxrollo = Integer.parseInt(e_etixrollo.getText().toString());
                rollosxcajas =  Float.parseFloat(e_rolloxcaja.getText().toString());

                if (etiquetasxrollo>0 && rollosxcajas>0){
                    validado=true;
                }

            }
        }else if(valorSeleccionado==2){

            if (!e_rolloxcaja.getText().toString().isEmpty()) {

                rollosxcajas =  Float.parseFloat(e_rolloxcaja.getText().toString());

                if (rollosxcajas>0){
                    validado=true;
                }

            }
        }else{
            if (!e_etixrollo.getText().toString().isEmpty() && !e_num_inicial.getText().toString().isEmpty() && !e_num_final.getText().toString().isEmpty()
                    && !e_serie.getText().toString().isEmpty() && !e_cant_rollos_x_caja.getText().toString().isEmpty() && !e_num_x_rollos.getText().toString().isEmpty()) {

                num_inicial=  Integer.parseInt(e_num_inicial.getText().toString());
                num_final=  Integer.parseInt(e_num_final.getText().toString());
                serie =  e_serie.getText().toString();
                cant_rollos_x_caja=  Integer.parseInt(e_cant_rollos_x_caja.getText().toString());
                num_x_rollos =  Integer.parseInt(e_num_x_rollos.getText().toString());

                if (num_inicial>0 && num_final>0 && num_final>num_inicial && cant_rollos_x_caja>0 && num_x_rollos>0 ){
                    validado=true;
                }

            }
        }
        return validado;
    }

    void limpiar(){

        e_etixrollo.setText("0");
        e_rolloxcaja.setText("0");
        e_num_inicial.setText("0");
        e_num_final.setText("0");
        e_serie.setText("A");
        e_cant_rollos_x_caja.setText("0");
        e_num_x_rollos.setText("0");

       // Toast.makeText(getApplicationContext(),"Se limpiaron los datos",Toast.LENGTH_SHORT).show();

    }

    private void configurarSpinner() {

        spinnerEtiquetas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Asignar el valor correspondiente a la variable según la opción seleccionada
                switch (position) {
                    case 0:
                        valorSeleccionado = 1;
                        Log.e("MS","1");
                        ordenarylimpiar(valorSeleccionado);
                        limpiar();
                        e_etixrollo.requestFocus();
                        e_etixrollo.selectAll();

                        break;
                    case 1:

                        valorSeleccionado = 2;
                        ordenarylimpiar(valorSeleccionado);
                        Log.e("MS","2");
                        limpiar();
                        e_rolloxcaja.requestFocus();
                        e_rolloxcaja.selectAll();

                        break;
                    case 2:
                        valorSeleccionado = 3;
                        ordenarylimpiar(valorSeleccionado);
                        Log.e("MS","3");
                        limpiar();
                        e_num_inicial.requestFocus();
                        e_num_inicial.selectAll();

                        break;
                    default:
                        valorSeleccionado = 1;
                        ordenarylimpiar(valorSeleccionado);
                        Log.e("MS","0");
                        limpiar();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Hacer algo si no se selecciona nada, si es necesario
            }
        });

        spinnerEtiquetas.setSelection(0);

        // Seleccionar siempre la primera opción por defecto

    }

    private void ordenarylimpiar(int valorSeleccionado) {

        switch (valorSeleccionado) {
            case 1:

                layoutetiqrollo.setVisibility(View.VISIBLE);
                layoutetiqcaja.setVisibility(View.VISIBLE);
                layoutrollocaja.setVisibility(View.VISIBLE);
                linearRollo.setVisibility(View.INVISIBLE);

                break;
            case 2:

                layoutetiqrollo.setVisibility(View.INVISIBLE);
                layoutetiqcaja.setVisibility(View.INVISIBLE);
                layoutrollocaja.setVisibility(View.VISIBLE);
                linearRollo.setVisibility(View.INVISIBLE);

                break;
            case 3:

                layoutetiqrollo.setVisibility(View.INVISIBLE);
                layoutetiqcaja.setVisibility(View.INVISIBLE);
                layoutrollocaja.setVisibility(View.INVISIBLE);
                linearRollo.setVisibility(View.VISIBLE);

                break;

        }
    }

    private void cargarnombrevariable() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            if ("SumMetrosImpresos".equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    //  nombrevariable.setText("Metros Impresos");
                }

            } else if ("SumRollosFabricados".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    // nombrevariable.setText("Rollos Fabricados");
                }
            }
            else if ("SumRollosEmpaquedatos".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    //  nombrevariable.setText("Rollos Producidos");
                }
            }
        }
    }

    @Override
    public void ResultadoCantidadDialogo(float cantidad) {

        prepararNumeros(cantidad);

        }

}