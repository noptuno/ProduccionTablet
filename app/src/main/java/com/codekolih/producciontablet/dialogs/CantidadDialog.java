package com.codekolih.producciontablet.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;


public class CantidadDialog {

    private finalizarCuadro interfaz;
    private EditText edt_numero;
    Button btn_cancelar, btn_aceptar;
    private HttpLayer httpLayer;
    public interface finalizarCuadro{

        void ResultadoCantidadDialogo(float cantidad);

    }


    public CantidadDialog(Context contexcto , finalizarCuadro actividad){


        httpLayer = new HttpLayer(contexcto);
        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_num);

        btn_aceptar = dialogo.findViewById(R.id.cantidad_btn_confirmar);
        btn_cancelar= dialogo.findViewById(R.id.cantidad_btn_cancelar);
        edt_numero = dialogo.findViewById(R.id.et_numero);


        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{


                    String num = edt_numero.getText().toString();

                    if (num.length()>0){

                        float numfloat = Float.parseFloat(num);
                        interfaz.ResultadoCantidadDialogo(numfloat);
                        dialogo.dismiss();

                    }


                }catch (Exception e){

                    interfaz.ResultadoCantidadDialogo(0);
                    dialogo.dismiss();

                }


            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogo.dismiss();

            }
        });

        dialogo.show();
    }

}
