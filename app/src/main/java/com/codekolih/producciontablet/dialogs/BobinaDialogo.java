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
import android.widget.Switch;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Proveedor;

import java.util.ArrayList;
import java.util.Map;

public class BobinaDialogo {

    private finalizarBobinaDialog interfaz_scrap;
    String idproveedorSeleccionado = "0";


    String unidad = "Seleccionar";
    Spinner  spi_ProveedorNombre,spi_EsAbiertaoCerrada;
    String proveedorSeleccionado= "NO";
    String abiertaocerrada = "Seleccionar";

    EditText edt_Lote;
    EditText edt_Ancho;
    EditText edt_DefectuosaKg;


    LinearLayout lyb_ProveedorNombre,lyb_EsAbiertaoCerrada, lyb_Lote, lyb_Ancho,lyb_DefectuosaKg;
    LinearLayout ly_once, ly_doce, ly_trece, ly_catorce, ly_quince, ly_dieciseis;
    Switch once, doce, trece, catorce, quince, dieciseis;
    Button btn_guardar;
    Button btn_cancelar;


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

        ly_once = dialogo.findViewById(R.id.ly_once);
        ly_doce = dialogo.findViewById(R.id.ly_doce);
        ly_trece = dialogo.findViewById(R.id.ly_trece);
        ly_catorce= dialogo.findViewById(R.id.ly_catorce);
        ly_quince= dialogo.findViewById(R.id.ly_quince);
        ly_dieciseis= dialogo.findViewById(R.id.ly_dieciseis);

        once = dialogo.findViewById(R.id.once);
        doce = dialogo.findViewById(R.id.doce);
        trece = dialogo.findViewById(R.id.trece);
        catorce= dialogo.findViewById(R.id.catorce);
        quince= dialogo.findViewById(R.id.quince);
        dieciseis= dialogo.findViewById(R.id.dieciseis);


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
                    abiertaocerrada = "Seleccionar";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idproveedorSeleccionado.length()>0 && proveedorSeleccionado.length()>0 && edt_Lote.getText().length()>0){

                   if (validarVariables()){

                       int idproveedor = parseInt(idproveedorSeleccionado);
                       String ProveedorNombre = proveedorSeleccionado;
                       String Lote = edt_Lote.getText().toString();
                       float Ancho = parseFloat(edt_Ancho.getText().toString());
                       String EsAbiertaoCerrada = abiertaocerrada;
                       float DefectuosaKg = parseFloat(edt_DefectuosaKg.getText().toString());

                       try{
                           interfaz_scrap.ResultadoBobinaDialogo(idproveedor,ProveedorNombre,Lote,Ancho,EsAbiertaoCerrada,DefectuosaKg);
                           dialogo.dismiss();
                       }catch (Exception e){

                           Log.e("ErrorBobina",e.toString());
                           //  interfaz_scrap.ResultadoScrapDialogo(0,"KG");

                       }
                   }else{
                    System.out.println("Error faltan datos Bobina");
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

    private boolean validarVariables(){

        boolean validado = true;

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());

            if ("Ancho".equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    String a = edt_Ancho.getText().toString();
                    if(a.equals("0")){
                        validado = false;
                        break;
                    }
                }

            }
            else if ("EsAbiertaoCerrada".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if(abiertaocerrada.equals("Seleccionar")){
                        validado = false;
                        break;
                    }
                }else{
                    abiertaocerrada = "C";
                }


            }
            else if ("DefectuosaKg".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    String a = edt_DefectuosaKg.getText().toString();
                    if(a.equals("0")){
                        validado = false;
                        break;
                    }
                }



            }else if ("11".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (entry.getValue().equals("0")){
                        if (!once.isChecked()){
                            validado = false;
                            break;
                        }
                    }
                }



            }else if ("12".toString().equals(entry.getKey())){

                    if (entry.getValue().equals("0")){
                        if (!doce.isChecked()){
                            validado = false;
                            break;
                        }
                    }




            }else if ("13".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!trece.isChecked()){
                        validado = false;
                        break;
                    }
                }

            }else if ("14".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!catorce.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }else if ("15".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!quince.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }else if ("16".toString().equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    if (!dieciseis.isChecked()){
                        validado = false;
                        break;
                    }
                }
            }
        }

        return validado;

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

            }else if ("11".equals(entry.getKey())){
                ly_once.setVisibility(parseInt(entry.getValue()));
            }else if ("12".toString().equals(entry.getKey())){
                ly_doce.setVisibility(parseInt(entry.getValue()));
            }else if ("13".toString().equals(entry.getKey())){
                ly_trece.setVisibility(parseInt(entry.getValue()));
            }else if ("14".toString().equals(entry.getKey())){
                ly_catorce.setVisibility(parseInt(entry.getValue()));
            }else if ("15".toString().equals(entry.getKey())){
                ly_quince.setVisibility(parseInt(entry.getValue()));
            }else if ("16".toString().equals(entry.getKey())){
                ly_dieciseis.setVisibility(parseInt(entry.getValue()));
            }

        }
    }

}
