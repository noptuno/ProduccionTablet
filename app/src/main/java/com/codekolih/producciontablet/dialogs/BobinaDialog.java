package com.codekolih.producciontablet.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.codekolih.producciontablet.R;

public class BobinaDialog {

    private finalizarBobina interfazBobina;


    public interface finalizarBobina{

        void ResultadoBobinaDialogo(float cantidad);

    }

    public BobinaDialog(Context contexcto , finalizarBobina actividad){

        interfazBobina = actividad;
        int numeros= 0;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_bobina);

        Button aceptar = dialogo.findViewById(R.id.bobina_btn_guardar);
        Button cancelar = dialogo.findViewById(R.id.bobina_btn_cancelar);

        EditText bobina_edt_Lote = dialogo.findViewById(R.id.bobina_edt_Lote);
        bobina_edt_Lote.setText("1");

        bobina_edt_Lote.setSelectAllOnFocus(true);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    interfazBobina.ResultadoBobinaDialogo(Float.parseFloat(bobina_edt_Lote.getText().toString()));
                    dialogo.dismiss();
                }catch (Exception e){
                    interfazBobina.ResultadoBobinaDialogo(0);
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
