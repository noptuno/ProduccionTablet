package com.codekolih.producciontablet.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.activitys.Produccion_Activity;


public class FinTurnoDialog {

    private finalizarTurno interfaz;

    private EditText edit_motivo;
    Button cierre_btn_cancelar,cierre_btn_fin_de_turno_sin_terminar,cierre_btn_fin_de_turno_terminado;


    private HttpLayer httpLayer;
    public interface finalizarTurno{

        void ResultadoFinTurnoDialogo(String motivo,String tipocierre);

    }

    public FinTurnoDialog(Context contexcto , finalizarTurno actividad){

        httpLayer = new HttpLayer(contexcto);
        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
       // dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_fin_turno);

        cierre_btn_fin_de_turno_sin_terminar= dialogo.findViewById(R.id.cierre_btn_findeturnosinterminar);
        cierre_btn_fin_de_turno_terminado= dialogo.findViewById(R.id.cierre_btn_findeturnoterminado);

        cierre_btn_cancelar= dialogo.findViewById(R.id.cierre_btn_cancelar);
        edit_motivo = dialogo.findViewById(R.id.edit_motivo);

        edit_motivo.requestFocus();

        cierre_btn_fin_de_turno_sin_terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String motivo = edit_motivo.getText().toString();
                if(motivo.length() > 0){

                    interfaz.ResultadoFinTurnoDialogo(motivo,"C1");
                    dialogo.dismiss();

                }else{
                    Toast.makeText(contexcto,"Escriba Motivo",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cierre_btn_fin_de_turno_terminado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String motivo = edit_motivo.getText().toString();
                    if(motivo.length() > 0){

                     interfaz.ResultadoFinTurnoDialogo(motivo,"F1");
                            dialogo.dismiss();

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
