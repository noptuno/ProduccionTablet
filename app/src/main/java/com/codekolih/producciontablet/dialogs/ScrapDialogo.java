package com.codekolih.producciontablet.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Tareas;


public class ScrapDialogo {

    private finalizarScrapDialog interfaz_scrap;
    String unidad = "KG";
    Tareas tarea_Seleccionada;
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

        Button aceptar = dialogo.findViewById(R.id.scrap_btn_confirmar);
        Button cancelar = dialogo.findViewById(R.id.scrap_btn_cancelar);
        EditText numero = dialogo.findViewById(R.id.et_numero);
        numero.setText("0");

        tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea();

        if (tarea_Seleccionada.getTipoMaquinaId()== 3){
            numero.setMaxEms(6);
          numero.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (tarea_Seleccionada.getTipoMaquinaId()== 1){
            numero.setMaxEms(6);
        }else if (tarea_Seleccionada.getTipoMaquinaId()== 5){
            numero.setMaxEms(5);
        }else if (tarea_Seleccionada.getTipoMaquinaId()== 2){
            numero.setMaxEms(5);

        }
/*
        final String DECIMAL_PATTERN = "^\\d+\\.\\d{1}$";
        numero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Obtener el valor actual del EditText
                String input = charSequence.toString();

                // Validar si el valor ingresado es un decimal válido
                if (input.matches(DECIMAL_PATTERN)) {
                    Log.e("mensaje","valido");
                } else {
                    Log.e("mensaje","no es ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        */



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String unidadd = "Seleccionar";
                if (adapterView.getItemAtPosition(i).toString().equals("Kilos")){
                    unidadd = "KG";
                }else if (adapterView.getItemAtPosition(i).toString().equals("Gramos")){
                    unidadd = "G";
                }else if(adapterView.getItemAtPosition(i).toString().equals("Metros")){
                    unidadd = "M";
                }

                unidad =  unidadd;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        numero.setSelectAllOnFocus(true);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String a = numero.getText().toString();
                    if(a.equals("") || unidad.equals("Seleccionar")){
                        Toast.makeText(contexcto,"Faltan Datos",Toast.LENGTH_SHORT).show();
                    }else{
                        interfaz_scrap.ResultadoScrapDialogo(Float.parseFloat(numero.getText().toString()), unidad);
                        dialogo.dismiss();
                    }
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
