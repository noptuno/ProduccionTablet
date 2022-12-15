package com.codekolih.producciontablet.clases;

import androidx.navigation.NavType;

import java.io.Serializable;
import java.util.ArrayList;

public class Tareas implements Serializable {

    private int TroquelId;
    private String Descripcion;
    private float Z_AltoMasGap;
    private float Cilindro;
    private float NroDeSobre;
    private int PedidoId;
    private int TareaId;
    private String ArchivoEspecificacion;
    private float TipoMaquinaId;
    private String NombreTipoMaquina;
    private float MaquinaId;
    private String NombreMaquina;
    private String EstadoAcceso;
    private float Pistas;
    private float EtiquetasEnBanda;
    private String FechaEntrega;
    private float EtiquetasPorRollo;
    private String Observaciones;
    private String FechaCierre;
    private float OrdenPrioridad;
    private float UsuarioId;
    private String UltimoEstadoIdAvance;
    private String PermiteCambioPrioridad;
    private String FechaAlta;
    private float MetrosAImprimir;//IE,IR
    private float MetrosPorRollo;
    private float MetrosMatTroquelar;
    private String Estado;
    private boolean EsPedidoFinal;

    private ArrayList<Bobinas> Bobinas = new ArrayList<Bobinas>();
    private ArrayList<EstadosOp> EstadosOp = new ArrayList<EstadosOp>();
    private ArrayList<Produccion_Lista> Produccion_Lista = new ArrayList<Produccion_Lista>();

    public ArrayList<Produccion_Lista> getProduccion_Lista() {
        return Produccion_Lista;
    }

    public void setProduccion_Lista(ArrayList<Produccion_Lista> produccion_Lista) {
        Produccion_Lista = produccion_Lista;
    }

    public ArrayList<Bobinas> getBobinas() {
        return Bobinas;
    }

    public void setBobinas(ArrayList<Bobinas> bobinas) {
        Bobinas = bobinas;
    }

    public ArrayList<EstadosOp> getEstadosOp() {
        return EstadosOp;
    }

    public void setEstadosOp(ArrayList<EstadosOp> estadosOp) {
        EstadosOp = estadosOp;
    }



    public String toString(){

        String a = "CodigoTarea: " + getTareaId() + "\n"+
                "Pedido: " + getPedidoId() + "\n"+
                "Usuario: " + getUsuarioId() + "\n"+
                "Sobre: " + getNroDeSobre() + "\n"+
                "Descripcion: " + getDescripcion() + "\n"+
                "MaquinaId: " + getMaquinaId() + "\n"+
                "EstadoAcceso: " + getEstadoAcceso() + "\n"+
                "Observaciones: " + getObservaciones() + "\n"+
                "Estado: " + getEstado() + "\n";


        return a;
    }
    // Getter Methods

    public int getTroquelId() {
        return TroquelId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public float getZ_AltoMasGap() {
        return Z_AltoMasGap;
    }

    public float getCilindro() {
        return Cilindro;
    }

    public float getNroDeSobre() {
        return NroDeSobre;
    }

    public int getPedidoId() {
        return PedidoId;
    }

    public int getTareaId() {
        return TareaId;
    }

    public String getArchivoEspecificacion() {
        return ArchivoEspecificacion;
    }

    public float getTipoMaquinaId() {
        return TipoMaquinaId;
    }

    public String getNombreTipoMaquina() {
        return NombreTipoMaquina;
    }

    public float getMaquinaId() {
        return MaquinaId;
    }

    public String getNombreMaquina() {
        return NombreMaquina;
    }

    public String getEstadoAcceso() {
        return EstadoAcceso;
    }

    public float getPistas() {
        return Pistas;
    }

    public float getEtiquetasEnBanda() {
        return EtiquetasEnBanda;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public float getEtiquetasPorRollo() {
        return EtiquetasPorRollo;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public String getFechaCierre() {
        return FechaCierre;
    }

    public float getOrdenPrioridad() {
        return OrdenPrioridad;
    }

    public float getUsuarioId() {
        return UsuarioId;
    }

    public String getUltimoEstadoIdAvance() {
        return UltimoEstadoIdAvance;
    }

    public String getPermiteCambioPrioridad() {
        return PermiteCambioPrioridad;
    }

    public String getFechaAlta() {
        return FechaAlta;
    }

    public float getMetrosAImprimir() {
        return MetrosAImprimir;
    }

    public float getMetrosPorRollo() {
        return MetrosPorRollo;
    }

    public float getMetrosMatTroquelar() {
        return MetrosMatTroquelar;
    }

    public String getEstado() {
        return Estado;
    }

    public boolean getEsPedidoFinal() {
        return EsPedidoFinal;
    }

    // Setter Methods

    public void setTroquelId( int TroquelId ) {
        this.TroquelId = TroquelId;
    }

    public void setDescripcion( String Descripcion ) {
        this.Descripcion = Descripcion;
    }

    public void setZ_AltoMasGap( float Z_AltoMasGap ) {
        this.Z_AltoMasGap = Z_AltoMasGap;
    }

    public void setCilindro( float Cilindro ) {
        this.Cilindro = Cilindro;
    }

    public void setNroDeSobre( float NroDeSobre ) {
        this.NroDeSobre = NroDeSobre;
    }

    public void setPedidoId( int PedidoId ) {
        this.PedidoId = PedidoId;
    }

    public void setTareaId( int TareaId ) {
        this.TareaId = TareaId;
    }

    public void setArchivoEspecificacion( String ArchivoEspecificacion ) {
        this.ArchivoEspecificacion = ArchivoEspecificacion;
    }

    public void setTipoMaquinaId( float TipoMaquinaId ) {
        this.TipoMaquinaId = TipoMaquinaId;
    }

    public void setNombreTipoMaquina( String NombreTipoMaquina ) {
        this.NombreTipoMaquina = NombreTipoMaquina;
    }

    public void setMaquinaId( float MaquinaId ) {
        this.MaquinaId = MaquinaId;
    }

    public void setNombreMaquina( String NombreMaquina ) {
        this.NombreMaquina = NombreMaquina;
    }

    public void setEstadoAcceso( String EstadoAcceso ) {
        this.EstadoAcceso = EstadoAcceso;
    }

    public void setPistas( float Pistas ) {
        this.Pistas = Pistas;
    }

    public void setEtiquetasEnBanda( float EtiquetasEnBanda ) {
        this.EtiquetasEnBanda = EtiquetasEnBanda;
    }

    public void setFechaEntrega( String FechaEntrega ) {
        this.FechaEntrega = FechaEntrega;
    }

    public void setEtiquetasPorRollo( float EtiquetasPorRollo ) {
        this.EtiquetasPorRollo = EtiquetasPorRollo;
    }

    public void setObservaciones( String Observaciones ) {
        this.Observaciones = Observaciones;
    }

    public void setFechaCierre( String FechaCierre ) {
        this.FechaCierre = FechaCierre;
    }

    public void setOrdenPrioridad( float OrdenPrioridad ) {
        this.OrdenPrioridad = OrdenPrioridad;
    }

    public void setUsuarioId( float UsuarioId ) {
        this.UsuarioId = UsuarioId;
    }

    public void setUltimoEstadoIdAvance( String UltimoEstadoIdAvance ) {
        this.UltimoEstadoIdAvance = UltimoEstadoIdAvance;
    }

    public void setPermiteCambioPrioridad( String PermiteCambioPrioridad ) {
        this.PermiteCambioPrioridad = PermiteCambioPrioridad;
    }

    public void setFechaAlta( String FechaAlta ) {
        this.FechaAlta = FechaAlta;
    }

    public void setMetrosAImprimir( float MetrosAImprimir ) {
        this.MetrosAImprimir = MetrosAImprimir;
    }

    public void setMetrosPorRollo( float MetrosPorRollo ) {
        this.MetrosPorRollo = MetrosPorRollo;
    }

    public void setMetrosMatTroquelar( float MetrosMatTroquelar ) {
        this.MetrosMatTroquelar = MetrosMatTroquelar;
    }

    public void setEstado( String Estado ) {
        this.Estado = Estado;
    }

    public void setEsPedidoFinal( boolean EsPedidoFinal ) {
        this.EsPedidoFinal = EsPedidoFinal;
    }
}
