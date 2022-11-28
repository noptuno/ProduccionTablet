package com.codekolih.producciontablet.clases;

import java.util.ArrayList;

public class Pedido {
    private float PedidoId;
    private String SerieYNro;
    private String ArticuloId;
    private String Concepto;
    private float Cantidad;
    private String FechaIngreso;
    private String FechaFin;
    ArrayList< Object > Tareas = new ArrayList < Object > ();
    private boolean TareasCreadas;


    // Getter Methods

    public float getPedidoId() {
        return PedidoId;
    }

    public String getSerieYNro() {
        return SerieYNro;
    }

    public String getArticuloId() {
        return ArticuloId;
    }

    public String getConcepto() {
        return Concepto;
    }

    public float getCantidad() {
        return Cantidad;
    }

    public String getFechaIngreso() {
        return FechaIngreso;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public boolean getTareasCreadas() {
        return TareasCreadas;
    }

    // Setter Methods

    public void setPedidoId(float PedidoId) {
        this.PedidoId = PedidoId;
    }

    public void setSerieYNro(String SerieYNro) {
        this.SerieYNro = SerieYNro;
    }

    public void setArticuloId(String ArticuloId) {
        this.ArticuloId = ArticuloId;
    }

    public void setConcepto(String Concepto) {
        this.Concepto = Concepto;
    }

    public void setCantidad(float Cantidad) {
        this.Cantidad = Cantidad;
    }

    public void setFechaIngreso(String FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public void setFechaFin(String FechaFin) {
        this.FechaFin = FechaFin;
    }

    public void setTareasCreadas(boolean TareasCreadas) {
        this.TareasCreadas = TareasCreadas;
    }
}
