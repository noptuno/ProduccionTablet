package com.codekolih.producciontablet.dialogs;


import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Proveedor;

import java.util.ArrayList;
import java.util.Map;

public class BobinaDialogo {

    private finalizarBobinaDialog interfaz_scrap;


    String unidad = "KG";
    Spinner  spi_ProveedorNombre,spi_EsAbiertaoCerrada;
    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;


    LinearLayout lyb_ProveedorNombre,lyb_EsAbiertaoCerrada, lyb_Lote, lyb_Ancho,lyb_DefectuosaKg;

    Button btn_guardar;
    Button btn_cancelar;

    String idproveedorSeleccionado = "0";
    String proveedorSeleccionado= "NO";
    String abiertaocerrada = "N";

    public interface finalizarBobinaDialog{


        void ResultadoBobinaDialogo(int ProveedorId, String ProveedorNombre, String Lote, float Ancho, String EsAbiertaoCerrada, float DefectuosaKg);

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

        lyb_ProveedorNombre= dialogo.findViewById(R.id.lyb_ProveedorNombre);
        lyb_EsAbiertaoCerrada= dialogo.findViewById(R.id.lyb_EsAbiertaoCerrada);
        lyb_Lote= dialogo.findViewById(R.id.lyb_Lote);
        lyb_Ancho= dialogo.findViewById(R.id.lyb_Ancho);
        lyb_DefectuosaKg= dialogo.findViewById(R.id.lyb_DefectuosaKg);


        spi_ProveedorNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               String nombreProveedor = adapterView.getItemAtPosition(i).toString();

               String[] parts = nombreProveedor.split("-");
               idproveedorSeleccionado = parts[0];
               proveedorSeleccionado = parts[1];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        spi_EsAbiertaoCerrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Abierta")){

                    abiertaocerrada = "A";
                }else if (adapterView.getItemAtPosition(i).toString().equals("Cerrada")){
                    abiertaocerrada = "B";
                }else{
                    abiertaocerrada = "N";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idproveedorSeleccionado.length()>0 && proveedorSeleccionado.length()>0 && edt_Lote.getText().length()>0 && edt_Ancho.getText().length()>0&& edt_DefectuosaKg.getText().length()>0){

                    int idproveedor = parseInt(idproveedorSeleccionado);
                    String ProveedorNombre = proveedorSeleccionado;
                    String Lote = edt_Lote.getText().toString();
                    Float Ancho = parseFloat(edt_Ancho.getText().toString());
                    String EsAbiertaoCerrada = abiertaocerrada;
                    Float DefectuosaKg = parseFloat(edt_DefectuosaKg.getText().toString());

                    try{
                        interfaz_scrap.ResultadoBobinaDialogo(idproveedor,ProveedorNombre,Lote,Ancho,EsAbiertaoCerrada,DefectuosaKg);
                        dialogo.dismiss();
                    }catch (Exception e){

                        Log.e("ErrorBobina",e.toString());
                        //  interfaz_scrap.ResultadoScrapDialogo(0,"KG");

                    }
                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogo.dismiss();

            }
        });

        cargarProveedor(contexcto);
        OcultarVariables();
        dialogo.show();
    }

    ArrayList<String> listProveedores;
    ArrayAdapter<String> adapterProveedor;

    private void cargarProveedor(Context c) {

                listProveedores = new ArrayList<>();
                for (Proveedor proveeedores : TareaSingleton.SingletonInstance().getProveedores()){
                    listProveedores.add( proveeedores.getProveedorlId()+"-" +proveeedores.getNombre()  );
                }
                adapterProveedor = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, listProveedores);
                adapterProveedor.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spi_ProveedorNombre.setAdapter(adapterProveedor);

            }


    private void OcultarVariables() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("Ancho".equals(entry.getKey())){
                lyb_Ancho.setVisibility(parseInt(entry.getValue()));
            }
            else if ("EsAbiertaoCerrada".equals(entry.getKey())){
                lyb_EsAbiertaoCerrada.setVisibility(parseInt(entry.getValue()));
            }
            else if ("DefectuosaKg".equals(entry.getKey())){
                lyb_DefectuosaKg.setVisibility(parseInt(entry.getValue()));
            }

        }
    }

}
