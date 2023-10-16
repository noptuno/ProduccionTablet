package com.codekolih.producciontablet.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codekolih.producciontablet.HttpLayer;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.OcultarTeclado;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Tareas;

import java.text.DecimalFormat;
import java.util.Map;


public class CantidadDialog extends OcultarTeclado {

    private finalizarCuadro interfaz;
    Tareas tarea_Seleccionada;
    private TextView nombrevariable;
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

        nombrevariable = dialogo.findViewById(R.id.item_tarea_txt_nombrecurso);
        btn_aceptar = dialogo.findViewById(R.id.cantidad_btn_confirmar);
        btn_cancelar= dialogo.findViewById(R.id.cantidad_btn_cancelar);
        edt_numero = dialogo.findViewById(R.id.et_numero);

        tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea();

        if (tarea_Seleccionada.getTipoMaquinaId()== 4 || tarea_Seleccionada.getTipoMaquinaId()== 1){

            edt_numero.setMaxEms(5);
            edt_numero.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_numero.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        }else{

            edt_numero.setMaxEms(5);
            edt_numero.setInputType(InputType.TYPE_CLASS_PHONE);
            edt_numero.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

        }

        configureEditText(edt_numero);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    String a = edt_numero.getText().toString();
                    if(a.isEmpty()){
                        Toast.makeText(contexcto,"Faltan Datos",Toast.LENGTH_SHORT).show();
                    }else{
                        interfaz.ResultadoCantidadDialogo(Float.parseFloat(edt_numero.getText().toString()));
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


        cargarnombrevariable();
    }

    private void cargarnombrevariable() {

        for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

            if ("SumMetrosImpresos".equals(entry.getKey())){

                if (entry.getValue().equals("0")){
                    nombrevariable.setText("Metros Impresos");
                }

            } else if ("SumRollosFabricados".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    nombrevariable.setText("Rollos Fabricados");
                }
            }
            else if ("SumRollosEmpaquedatos".equals(entry.getKey())){
                if (entry.getValue().equals("0")){
                    nombrevariable.setText("Rollos Producidos");

                }
            }
        }
    }

}
