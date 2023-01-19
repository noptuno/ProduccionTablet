package com.codekolih.producciontablet.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.TareaSingleton;

import java.util.Map;


public class MotivoCierreDialog {

    private finalizarMotivo interfaz;

    private Spinner spinner_opcion_motivo;
    private EditText edit_motivo;
    Button cierre_btn_cancelar, cierre_btn_confirmar;
    private String opcionSpinnerSeleccionada = "Cancelar y cerrar la tarea";

    private HttpLayer httpLayer;
    public interface finalizarMotivo{

        void ResultadoMotivoDialogo(String motivo,String tipocierre);

    }


    public MotivoCierreDialog(Context contexcto , finalizarMotivo actividad){


        httpLayer = new HttpLayer(contexcto);
        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexcto);



        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_motivo_cierre);

        spinner_opcion_motivo = dialogo.findViewById(R.id.spinner_opcion);
        cierre_btn_cancelar = dialogo.findViewById(R.id.cierre_btn_confirmar);
        cierre_btn_confirmar= dialogo.findViewById(R.id.cierre_btn_cancelar);
        edit_motivo = dialogo.findViewById(R.id.edit_motivo);

        edit_motivo.requestFocus();


        spinner_opcion_motivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                opcionSpinnerSeleccionada = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        cierre_btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String motivo = edit_motivo.getText().toString();
                    if(motivo.length() > 1){

                        if (opcionSpinnerSeleccionada.equals("Cancelar y cerrar la tarea")){

                            interfaz.ResultadoMotivoDialogo(motivo,"F1");
                            dialogo.dismiss();

                        }else{
                            interfaz.ResultadoMotivoDialogo(motivo,"C2");
                            dialogo.dismiss();
                        }

                    }else{
                        Toast.makeText(contexcto,"Escriba Motivo",Toast.LENGTH_SHORT).show();
                    }
            }
        });

        cierre_btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();

            }
        });

        dialogo.show();

    }

}
