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
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Tareas;

import org.json.JSONObject;


public class CantidadDialog {

    private finalizarCuadro interfaz;
    private HttpLayer httpLayer;
    public interface finalizarCuadro{

        void ResultadoCantidadDialogo(float cantidad);

    }


    public CantidadDialog(Context contexcto , finalizarCuadro actividad){


        httpLayer = new HttpLayer(contexcto);
        interfaz = actividad;
        int numeros= 0;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_num);

        Button aceptar = dialogo.findViewById(R.id.cantidad_btn_confirmar);
        Button cancelar = dialogo.findViewById(R.id.cantidad_btn_cancelar);
        EditText numero = dialogo.findViewById(R.id.et_numero);
        numero.setText("1");

        numero.setSelectAllOnFocus(true);


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    interfaz.ResultadoCantidadDialogo(Float.parseFloat(numero.getText().toString()));
                    dialogo.dismiss();

                }catch (Exception e){

                    interfaz.ResultadoCantidadDialogo(0);
                    dialogo.dismiss();

                }


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogo.dismiss();

            }
        });

        dialogo.show();
    }

}
