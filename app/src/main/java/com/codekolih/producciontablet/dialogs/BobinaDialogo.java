package com.codekolih.producciontablet.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codekolih.producciontablet.R;


public class BobinaDialogo {

    private finalizarBobinaDialog interfaz_scrap;


    String unidad = "KG";
    Spinner  spi_ProveedorNombre,spi_EsAbiertaoCerrada;
    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;
    Button btn_guardar;
    Button btn_cancelar;


    public interface finalizarBobinaDialog{


        void ResultadoBobinaDialogo(int ProveedorId, String ProveedorNombre, String Lote, double Ancho, String EsAbiertaoCerrada, double DefectuosaKg);

    }

    public BobinaDialogo(Context contexcto , finalizarBobinaDialog actividad){

        interfaz_scrap = actividad;

        int numeros= 0;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_bobina);

        btn_guardar = dialogo.findViewById(R.id.dialogbobina_btn_guardar2);
        btn_cancelar = dialogo.findViewById(R.id.dialogbobina_btn_cancelar2);
        spi_ProveedorNombre = dialogo.findViewById(R.id.dialogbobina_spi_ProveedorNombre);
        spi_EsAbiertaoCerrada = dialogo.findViewById(R.id.dialogbobina_spi_EsAbiertaoCerrada);
        edt_Lote = dialogo.findViewById(R.id.dialogbobina_edt_Lote);
        edt_Ancho = dialogo.findViewById(R.id.dialogbobina_edt_Ancho);
        edt_DefectuosaKg = dialogo.findViewById(R.id.dialogbobina_edt_DefectuosaKg);

/*
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
*/


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ProveedorId = 0;
                String ProveedorNombre = "";
                String Lote = "";
                double Ancho = 0.0 ;
                String EsAbiertaoCerrada = "";
                double DefectuosaKg = 0.0;



                try{




                    interfaz_scrap.ResultadoBobinaDialogo(ProveedorId,ProveedorNombre,Lote,Ancho,EsAbiertaoCerrada,DefectuosaKg);
                    dialogo.dismiss();
                }catch (Exception e){
                  //  interfaz_scrap.ResultadoScrapDialogo(0,"KG");
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
