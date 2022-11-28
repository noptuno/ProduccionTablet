package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.codekolih.producciontablet.R;
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

        if (idtipomaquina.equals("2")){

            Log.e("VariablesOcultar",idtipomaquina);
            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "0");


            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("AnchoFinalRollo", "8");
            variablesGenrales.put("CantidadPistasCortadas", "8");
            variablesGenrales.put("PistasTroquelUsadas", "8");

            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("MetrosAImprimir", "0");
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("EtiquetasPorRollo", "0");
        }else if (idtipomaquina.equals("4")){
            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "0");
            variablesGenrales.put("AnchoFinalRolloYGap", "0");
            variablesGenrales.put("CantidadPistasImpresas", "0");
            variablesGenrales.put("CantidadTintas", "0");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", "0");
            variablesGenrales.put("PistasTroquelUsadas", "0");
            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("MetrosAImprimir", "0");
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("EtiquetasPorRollo", "0");
        }else{
            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "0");
            variablesGenrales.put("AnchoFinalRolloYGap", "0");
            variablesGenrales.put("CantidadPistasImpresas", "0");
            variablesGenrales.put("CantidadTintas", "0");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", "0");
            variablesGenrales.put("PistasTroquelUsadas", "0");
            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("MetrosAImprimir", "0");
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("EtiquetasPorRollo", "0");
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
