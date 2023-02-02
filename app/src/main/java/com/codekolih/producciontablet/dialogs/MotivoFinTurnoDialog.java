package com.codekolih.producciontablet.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;

import java.util.HashMap;
import java.util.Map;


public class MotivoFinTurnoDialog {

    private finalizarMotivo interfaz;

    private EditText edit_motivo;
    Button cierre_btn_cancelar,
            cierre_btn_finincompleto,cierre_btn_finterminado;


    private HttpLayer httpLayer;
    public interface finalizarMotivo{

        void ResultadoMotivoDialogo(String motivo,String tipocierre);

    }


    public MotivoFinTurnoDialog(Context contexcto , finalizarMotivo actividad){


        httpLayer = new HttpLayer(contexcto);
        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_motivo_cierre);

        cierre_btn_finincompleto= dialogo.findViewById(R.id.cierre_btn_finincompleto);
        cierre_btn_finterminado= dialogo.findViewById(R.id.cierre_btn_finterminado);

        cierre_btn_cancelar= dialogo.findViewById(R.id.cierre_btn_cancelar);
        edit_motivo = dialogo.findViewById(R.id.edit_motivo);

        edit_motivo.requestFocus();

        cierre_btn_finincompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String motivo = edit_motivo.getText().toString();
                if(motivo.length() > 1){

                    interfaz.ResultadoMotivoDialogo(motivo,"C1");
                    dialogo.dismiss();

                }else{
                    Toast.makeText(contexcto,"Escriba Motivo",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cierre_btn_finterminado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String motivo = edit_motivo.getText().toString();
                    if(motivo.length() > 1){

                     interfaz.ResultadoMotivoDialogo(motivo,"C1");
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