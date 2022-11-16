package com.codekolih.producciontablet.dialogs;


import android.app.Activity;
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

import com.codekolih.producciontablet.R;


public class ScrapDialogo {

    private finalizarScrapDialog interfaz_scrap;
    String unidad = "KG";

    public interface finalizarScrapDialog{

        void ResultadoScrapDialogo(float cantidad, String unidad);

    }


    public ScrapDialogo(Context contexcto , finalizarScrapDialog actividad){

        interfaz_scrap = actividad;
        int numeros= 0;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_scrap);
        Spinner spinner = (Spinner) dialogo.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                unidad =  adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Button aceptar = dialogo.findViewById(R.id.scrap_btn_confirmar);
        Button cancelar = dialogo.findViewById(R.id.scrap_btn_cancelar);
        EditText numero = dialogo.findViewById(R.id.et_numero);
        numero.setText("1");

        numero.setSelectAllOnFocus(true);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    interfaz_scrap.ResultadoScrapDialogo(Float.parseFloat(numero.getText().toString()), unidad);
                    dialogo.dismiss();
                }catch (Exception e){
                    interfaz_scrap.ResultadoScrapDialogo(0,"KG");
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
