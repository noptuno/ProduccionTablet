package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.clases.Material;
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
    private String usuarioIniciado;
    private String nombrepdf;

    private String RespuestaDato;

    private int tipomaquinaid;

    public int getTipomaquinaid() {
        return tipomaquinaid;
    }

    public void setTipomaquinaid(int tipomaquinaid) {
        this.tipomaquinaid = tipomaquinaid;
    }

    public String getRespuestaDato() {
        return RespuestaDato;
    }

    public void setRespuestaDato(String respuestaDato) {
        RespuestaDato = respuestaDato;
    }

    public String getNombrepdf() {
        return nombrepdf;
    }

    public void setNombrepdf(String nombrepdf) {
        this.nombrepdf = nombrepdf;
    }

    private int produccionId;
    private Map<String, String> tipoMaquina;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Material> materiales;


    public String getUsuarioIniciado() {
        return usuarioIniciado;
    }

    public void setUsuarioIniciado(String usuarioIniciado) {
        this.usuarioIniciado = usuarioIniciado;
    }

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<Material> materiales) {
        this.materiales = materiales;
    }

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


        // 8 OCULTAR
        // 0 MOSTRAR
        Map<String, String> variablesGenrales = new HashMap<>();

        if (idtipomaquina.equals("1")){
//IE
            Log.e("VariablesOcultar",idtipomaquina);

            variablesGenrales.put("1", "0"); //control del boceto o muestra contra la impresión de largada
            variablesGenrales.put("2", "0"); //control de tipo de papel correcto
            variablesGenrales.put("3", "0"); //control que el trabajo cumpla con la especificación
            variablesGenrales.put("4", "0"); //control del troquelado y del estado del line
            variablesGenrales.put("5", "0"); // control del sentido de bobinado.
            variablesGenrales.put("6", "8"); // control para numerados (datos de pie, dirección y numeración)
            variablesGenrales.put("7", "8"); //control para numerados (armado y control de numeración)
            variablesGenrales.put("8", "8"); //control para largo fijo (largo de ticket)
            variablesGenrales.put("9", "8"); //control tipo de papel y buje
            variablesGenrales.put("10", "8");// Control del armado de la medida

            //Bobina
            variablesGenrales.put("11", "0");// control de calidad de la impresión
            variablesGenrales.put("12", "0");//control de calidad del curado UV
            variablesGenrales.put("13", "0");//control de calidad del troquelado y del estado del liner
            variablesGenrales.put("14", "8"); //control de numeración
            variablesGenrales.put("15", "8");// control del sentido de bobinado
            variablesGenrales.put("16", "8");// control de la calidad del final del rollo

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
            variablesGenrales.put("AnchoBobinaUsadoCm", "8");

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
//IR
            Log.e("VariablesOcultar",idtipomaquina);

            //valdiar
            variablesGenrales.put("1", "0"); //control del boceto o muestra contra la impresión de largada
            variablesGenrales.put("2", "0"); //control de tipo de papel correcto
            variablesGenrales.put("3", "0"); //control que el trabajo cumpla con la especificación
            variablesGenrales.put("4", "8"); //control del troquelado y del estado del line
            variablesGenrales.put("5", "0"); // control del sentido de bobinado.
            variablesGenrales.put("6", "0"); // control para numerados (datos de pie, dirección y numeración)
            variablesGenrales.put("7", "0"); //control para numerados (armado y control de numeración)
            variablesGenrales.put("8", "0"); //control para largo fijo (largo de ticket)
            variablesGenrales.put("9", "8"); //control tipo de papel y buje
            variablesGenrales.put("10", "8");// Control del armado de la medida

            //Bobina
            variablesGenrales.put("11", "0");// control de calidad de la impresión
            variablesGenrales.put("12", "8");//control de calidad del curado UV
            variablesGenrales.put("13", "8");//control de calidad del troquelado y del estado del liner
            variablesGenrales.put("14", "0"); //control de numeración
            variablesGenrales.put("15", "8");// control del sentido de bobinado
            variablesGenrales.put("16", "8");// control de la calidad del final del rollo

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
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");
            variablesGenrales.put("AnchoBobinaUsadoCm", "8");

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
//FR
            Log.e("VariablesOcultar",idtipomaquina);

            //valdiar
            variablesGenrales.put("1", "8"); //control del boceto o muestra contra la impresión de largada
            variablesGenrales.put("2", "8"); //control de tipo de papel correcto
            variablesGenrales.put("3", "0"); //control que el trabajo cumpla con la especificación
            variablesGenrales.put("4", "8"); //control del troquelado y del estado del line
            variablesGenrales.put("5", "0"); // control del sentido de bobinado.
            variablesGenrales.put("6", "8"); // control para numerados (datos de pie, dirección y numeración)
            variablesGenrales.put("7", "8"); //control para numerados (armado y control de numeración)
            variablesGenrales.put("8", "8"); //control para largo fijo (largo de ticket)
            variablesGenrales.put("9", "0"); //control tipo de papel y buje
            variablesGenrales.put("10", "0");// Control del armado de la medida

            //Bobina
            variablesGenrales.put("11", "8");// control de calidad de la impresión
            variablesGenrales.put("12", "8");//control de calidad del curado UV
            variablesGenrales.put("13", "8");//control de calidad del troquelado y del estado del liner
            variablesGenrales.put("14", "8"); //control de numeración
            variablesGenrales.put("15", "0");// control del sentido de bobinado
            variablesGenrales.put("16", "0");// control de la calidad del final del rollo


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
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");
            variablesGenrales.put("AnchoBobinaUsadoCm", "0");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("SumRollosFabricados", "0");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", "0");
            variablesGenrales.put("PistasTroquelUsadas", "8");
            variablesGenrales.put("SumRollosEmpaquedatos", "8");//e

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "0");
            variablesGenrales.put("DefectuosaKg", "0");

        }else if (idtipomaquina.equals("4")){
                //FE
            Log.e("VariablesOcultar",idtipomaquina);


            //valdiar
            variablesGenrales.put("1", "8"); //control del boceto o muestra contra la impresión de largada
            variablesGenrales.put("2", "8"); //control de tipo de papel correcto
            variablesGenrales.put("3", "0"); //control que el trabajo cumpla con la especificación
            variablesGenrales.put("4", "0"); //control del troquelado y del estado del line
            variablesGenrales.put("5", "8"); // control del sentido de bobinado.
            variablesGenrales.put("6", "8"); // control para numerados (datos de pie, dirección y numeración)
            variablesGenrales.put("7", "8"); //control para numerados (armado y control de numeración)
            variablesGenrales.put("8", "8"); //control para largo fijo (largo de ticket)
            variablesGenrales.put("9", "0"); //control tipo de papel y buje
            variablesGenrales.put("10", "8");// Control del armado de la medida

            //Bobina
            variablesGenrales.put("11", "0");// control de calidad de la impresión
            variablesGenrales.put("12", "0");//control de calidad del curado UV
            variablesGenrales.put("13", "0");//control de calidad del troquelado y del estado del liner
            variablesGenrales.put("14", "8"); //control de numeración
            variablesGenrales.put("15", "0");// control del sentido de bobinado
            variablesGenrales.put("16", "8");// control de la calidad del final del rollo


            variablesGenrales.put("SumCantidad", "0");
            variablesGenrales.put("EtiquetasPorRollo", "0");
            variablesGenrales.put("EtiquetasEnBanda", "0");
            variablesGenrales.put("Pistas", "0");
            variablesGenrales.put("Observaciones", "0");
            variablesGenrales.put("MetrosAImprimir", "8");
            variablesGenrales.put("NroDeSobre", "0");
            variablesGenrales.put("Descripcion", "0");
            variablesGenrales.put("Cilindro", "0");
            variablesGenrales.put("Z_AltoMasGap", "0");
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "0");
            variablesGenrales.put("TroquelId", "0");
            variablesGenrales.put("AnchoBobinaUsadoCm", "8");
            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "0");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "0");
            variablesGenrales.put("UnidadIdScrapInicial", "0");
            variablesGenrales.put("SumRollosFabricados", "0");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "8");
            variablesGenrales.put("CantidadPistasCortadas", ("8"));
            variablesGenrales.put("PistasTroquelUsadas", "0");
            variablesGenrales.put("SumRollosEmpaquedatos", "8");//e

            //bobina
            variablesGenrales.put("Ancho", "0");
            variablesGenrales.put("EsAbiertaoCerrada", "8");
            variablesGenrales.put("DefectuosaKg", "0");

        }else if (idtipomaquina.equals("5")){
//E
            Log.e("VariablesOcultar",idtipomaquina);


            //valdiar
            variablesGenrales.put("1", "8"); //control del boceto o muestra contra la impresión de largada
            variablesGenrales.put("2", "8"); //control de tipo de papel correcto
            variablesGenrales.put("3", "0"); //control que el trabajo cumpla con la especificación
            variablesGenrales.put("4", "8"); //control del troquelado y del estado del line
            variablesGenrales.put("5", "0"); // control del sentido de bobinado.
            variablesGenrales.put("6", "8"); // control para numerados (datos de pie, dirección y numeración)
            variablesGenrales.put("7", "8"); //control para numerados (armado y control de numeración)
            variablesGenrales.put("8", "8"); //control para largo fijo (largo de ticket)
            variablesGenrales.put("9", "0"); //control tipo de papel y buje
            variablesGenrales.put("10", "0");// Control del armado de la medida


            //Bobina
            variablesGenrales.put("11", "8");// control de calidad de la impresión
            variablesGenrales.put("12", "8");//control de calidad del curado UV
            variablesGenrales.put("13", "8");//control de calidad del troquelado y del estado del liner
            variablesGenrales.put("14", "8"); //control de numeración
            variablesGenrales.put("15", "8");// control del sentido de bobinado
            variablesGenrales.put("16", "8");// control de la calidad del final del rollo


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
            variablesGenrales.put("MetrosPorRollo", "0");
            variablesGenrales.put("MetrosMatTroquelar", "8");
            variablesGenrales.put("TroquelId", "8");
            variablesGenrales.put("AnchoBobinaUsadoCm", "0");

            //produccion
            variablesGenrales.put("SumMetrosImpresos", "8");//IE,IR
            variablesGenrales.put("AnchoFinalRolloYGap", "8");
            variablesGenrales.put("CantidadPistasImpresas", "8");
            variablesGenrales.put("CantidadTintas", "8");
            variablesGenrales.put("ScrapAjusteInicial", "8");
            variablesGenrales.put("UnidadIdScrapInicial", "8");
            variablesGenrales.put("SumRollosFabricados", "8");//FE,FR
            variablesGenrales.put("AnchoFinalRollo", "0");
            variablesGenrales.put("CantidadPistasCortadas", "8");
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
        return this.tareaInstanciada;
    }


    @Override
    public String toString() {
        return "TareaSingleton{" +
                "tareaInstanciada=" + tareaInstanciada +
                ", pedidoInstanciada=" + pedidoInstanciada +
                ", usuarioIniciado='" + usuarioIniciado + '\'' +
                ", nombrepdf='" + nombrepdf + '\'' +
                ", RespuestaDato='" + RespuestaDato + '\'' +
                ", tipomaquinaid=" + tipomaquinaid +
                ", produccionId=" + produccionId +
                ", tipoMaquina=" + tipoMaquina +
                ", proveedores=" + proveedores +
                ", materiales=" + materiales +
                '}';
    }
}
