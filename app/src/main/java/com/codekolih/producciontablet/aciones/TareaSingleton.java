package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.clases.Pedido;
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
    private Pedido pedidoInstanciada;
    private int produccionId;
    private Map<String, String> tipoMaquina;
    private ArrayList<Proveedor> proveedores;


    public Pedido getPedidoInstanciada() {
        return pedidoInstanciada;
    }

    public void setPedidoInstanciada(Pedido pedidoInstanciada) {
        this.pedidoInstanciada = pedidoInstanciada;
    }

    public Map<String, String> getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(String idtipomaquina) {

        Map<String, String> variablesGenrales = new HashMap<>();

        if (idtipomaquina.equals("1")){

            Log.e("VariablesOcultar",idtipomaquina);

            //pregutnas
            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "8");

            //tarea unicavez
            variablesGenrales.put("SumCantidad", "8"); //FR,FE,E
            variablesGenrales.put("EtiquetasPorRollo", "8");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "0");//IE,IR

            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("MetrosPorRollo", "8");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "0");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "0");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "0");//unica
            variablesGenrales.put("CantidadPistasImpresas", "0");//unica
            variablesGenrales.put("CantidadTintas", "0");//unica
            variablesGenrales.put("ScrapAjusteInicial", "0");//unica
            variablesGenrales.put("UnidadIdScrapInicial", "0");//unica
            variablesGenrales.put("SumRollosFabricados", "8");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "8");
            variablesGenrales.put("CantidadPistasCortadas", "8");
            variablesGenrales.put("PistasTroquelUsadas", "8");
            variablesGenrales.put("SumRollosEmpaquedatos", "8");//E

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "8");
            variablesGenrales.put("DefectuosaKg", "0");

        }else if (idtipomaquina.equals("2")){

            Log.e("VariablesOcultar",idtipomaquina);

            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "8");
            variablesGenrales.put("seis", "8");

            variablesGenrales.put("SumCantidad", "0");
            variablesGenrales.put("EtiquetasPorRollo", "8");
            variablesGenrales.put("EtiquetasEnBanda", "8");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "0");
            variablesGenrales.put("NroDeSobre", "8");
            variablesGenrales.put("Descripcion", "8");
            variablesGenrales.put("Cilindro", "8");
            variablesGenrales.put("Z_AltoMasGap", "8");
            variablesGenrales.put("MetrosPorRollo", "8");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "0");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "0");
            variablesGenrales.put("CantidadTintas", "0");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("SumRollosFabricados", "8");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "8");
            variablesGenrales.put("CantidadPistasCortadas", "8");
            variablesGenrales.put("PistasTroquelUsadas", "8");
            variablesGenrales.put("SumRollosEmpaquedatos", "8");//e

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "8");
            variablesGenrales.put("DefectuosaKg", "0");


        }else if (idtipomaquina.equals("3")){

            Log.e("VariablesOcultar",idtipomaquina);

            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "8");
            variablesGenrales.put("cuatro", "8");
            variablesGenrales.put("cinco", "8");
            variablesGenrales.put("seis", "8");

            variablesGenrales.put("SumCantidad", "0");
            variablesGenrales.put("EtiquetasPorRollo", "8");
            variablesGenrales.put("EtiquetasEnBanda", "8");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "8");
            variablesGenrales.put("NroDeSobre", "8");
            variablesGenrales.put("Descripcion", "8");
            variablesGenrales.put("Cilindro", "8");
            variablesGenrales.put("Z_AltoMasGap", "8");
            variablesGenrales.put("MetrosPorRollo", "8");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("SumRollosFabricados", "0");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", ".0");
            variablesGenrales.put("PistasTroquelUsadas", "8");
            variablesGenrales.put("SumRollosEmpaquedatos", "8");//e

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "0");
            variablesGenrales.put("DefectuosaKg", "0");
        }else if (idtipomaquina.equals("4")){

            Log.e("VariablesOcultar",idtipomaquina);

            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "8");
            variablesGenrales.put("tres", "8");
            variablesGenrales.put("cuatro", "8");
            variablesGenrales.put("cinco", "8");
            variablesGenrales.put("seis", "8");

            variablesGenrales.put("SumCantidad", "0");
            variablesGenrales.put("EtiquetasPorRollo", "0");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "0");
            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "0");
            variablesGenrales.put("TroquelId", "0");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "0");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("SumRollosFabricados", "8");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "8");
            variablesGenrales.put("CantidadPistasCortadas", ("8"));
            variablesGenrales.put("PistasTroquelUsadas", "0");
            variablesGenrales.put("SumRollosEmpaquedatos", "0");//e

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "8");
            variablesGenrales.put("DefectuosaKg", "0");

        }else if (idtipomaquina.equals("5")){

            Log.e("VariablesOcultar",idtipomaquina);

            variablesGenrales.put("uno", "0");
            variablesGenrales.put("dos", "0");
            variablesGenrales.put("tres", "0");
            variablesGenrales.put("cuatro", "0");
            variablesGenrales.put("cinco", "0");
            variablesGenrales.put("seis", "0");


            variablesGenrales.put("SumCantidad", "0");
            variablesGenrales.put("EtiquetasPorRollo", "8");
            variablesGenrales.put("EtiquetasEnBanda", "8");
            variablesGenrales.put("Pistas", "8");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "8");
            variablesGenrales.put("NroDeSobre", "8");
            variablesGenrales.put("Descripcion", "8");
            variablesGenrales.put("Cilindro", "8");
            variablesGenrales.put("Z_AltoMasGap", "8");
            variablesGenrales.put("MetrosPorRollo", "8");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "8");
            variablesGenrales.put("UnidadIdScrapInicial", "8");
            variablesGenrales.put("SumRollosFabricados", "8");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", ".8");
            variablesGenrales.put("PistasTroquelUsadas", "8");
            variablesGenrales.put("SumRollosEmpaquedatos", "0");//e

            //bobina
            variablesGenrales.put("Ancho", "8");
            variablesGenrales.put("EsAbiertaoCerrada", "8");
            variablesGenrales.put("DefectuosaKg", "8");
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
