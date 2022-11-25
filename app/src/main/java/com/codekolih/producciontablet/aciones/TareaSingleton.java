package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaSingleton {

    private static TareaSingleton tareaSingle;
    private Tareas tareaInstanciada;
    private int produccionId;
    private Map<String, String> tipoMaquina;
    private ArrayList<Proveedor> proveedores;

    public Map<String, String> getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(String idtipomaquina) {

        Map<String, String> variablesGenrales = new HashMap<>();

        if (idtipomaquina.equals("1")){
            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "0");
            variablesGenrales.put("edt_verificacion_AnchoFinalRolloYGap", "0");
            variablesGenrales.put("edt_verificacion_CantidadPistasImpresas", "0");
            variablesGenrales.put("edt_verificacion_CantidadTintas", "0");
            variablesGenrales.put("edt_verificacion_ScrapAjusteInicial", "0");
            variablesGenrales.put("edt_verificacion_UnidadIdScrapInicial", "0");
            variablesGenrales.put("edt_verificacion_AnchoFinalRollo", "0");
            variablesGenrales.put("edt_verificacion_CantidadPistasCortadas", "0");
            variablesGenrales.put("edt_verificacion_PistasTroquelUsadas", "0");
            variablesGenrales.put("txt_verificacion_txt_NroDeSobre", "0");
            variablesGenrales.put("txt_verificacion_txt_Descripcion", "0");
            variablesGenrales.put("txt_verificacion_txt_MetrosAImprimir", "0");
            variablesGenrales.put("txt_verificacion_txt_MetrosPorRollo", "0");
            variablesGenrales.put("txt_verificacion_txt_MetrosMatTroquelar", "0");
            variablesGenrales.put("txt_verificacion_txt_Observaciones", "0");
            variablesGenrales.put("txt_verificacion_txt_Z_AltoMasGap", "0");
            variablesGenrales.put("txt_verificacion_txt_Cilindro", "0");
            variablesGenrales.put("txt_verificacion_txt_Pistas", "0");
            variablesGenrales.put("txt_verificacion_txt_EtiquetasEnBanda", "0");
            variablesGenrales.put("txt_verificacion_txt_EtiquetasPorRollo", "0");
        }

        this.tipoMaquina = variablesGenrales;
    }

    public ArrayList<Proveedor> getProveedores() {
        Log.e("Class","GetProveedores : " + proveedores.size());
        return proveedores;
    }

    public void setProveedores(ArrayList<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    private TareaSingleton() {
    }

    public static TareaSingleton SingletonInstance() {
        if (tareaSingle == null){
            tareaSingle = new TareaSingleton();
        }
        return tareaSingle;

    }

    public int getProduccionId() {
        Log.e("Class","GetProduccionid : " + produccionId);
        return produccionId;
    }

    public void setProduccionId(int produccionId) {
        this.produccionId = produccionId;

        Log.e("Class","SetProduccionid : " + produccionId);
    }

    public Tareas setTarea(Tareas tarea) {
        this.tareaInstanciada = tarea;
        Log.e("Class","SetTarea : " + tarea.getTareaId());

        return this.tareaInstanciada;
    }

    public Tareas getTarea(){

        if (tareaInstanciada==null){
            tareaInstanciada = null;
        }
        Log.e("Class","GetTarea : " + tareaInstanciada.getTareaId());
        return this.tareaInstanciada;
    }






}
