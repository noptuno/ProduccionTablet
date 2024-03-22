package com.codekolih.producciontablet.activitys;

import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_CONFIGURACION;
import static com.codekolih.producciontablet.aciones.Variables.PREF_PRODUCCION_IP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.example.tscdll.TscWifiActivity;

import honeywell.connection.ConnectionBase;

public class Configuracion extends OcultarTeclado {
    private SharedPreferences pref;
    private Button guardar, cancelar, imprimir;
    private EditText text_ip;
    private Context activity_context_actual;
    private  ConnectionBase conn = null;
    private TscWifiActivity TscEthernetDll = new TscWifiActivity();
    private String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        guardar = findViewById(R.id.btn_save);
        text_ip = findViewById(R.id.edt_ip);
        cancelar = findViewById(R.id.btn_conf_cancelar);
        imprimir = findViewById(R.id.btn_testprint);

        activity_context_actual = getApplicationContext();
        pref = getSharedPreferences(PREF_PRODUCCION_CONFIGURACION, Context.MODE_PRIVATE);
        ip = pref.getString(PREF_PRODUCCION_IP, "0");
        text_ip.setText(ip);



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipInput = text_ip.getText().toString().trim();
                if (isValidIP(ipInput)) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(PREF_PRODUCCION_IP, ipInput);
                    editor.apply();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Dirección IP inválida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ipInput = text_ip.getText().toString().trim();

                if (isValidIP(ipInput)) {


                    String prn =  "SIZE 101.2 mm, 100.1 mm\n" +
                            "GAP 3 mm, 0 mm\n" +
                            "CLS\n" +
                            "TEXT 447,762,\"ROMAN.TTF\",180,1,22,\"Example\"\n" +
                            "PRINT 1\n";
                    print(prn,ipInput);





                }

            }
        });


    }

    private void print(String prn,String ip) {

        TscEthernetDll.openport(ip, 9100);
        TscEthernetDll.sendcommand("CODEPAGE UTF-8\n");
        TscEthernetDll.sendcommand(prn);
        TscEthernetDll.closeport(5000);

    }

    // Método para validar una dirección IP utilizando expresiones regulares
    private boolean isValidIP (String ip){
        String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(ipPattern);
    }


}