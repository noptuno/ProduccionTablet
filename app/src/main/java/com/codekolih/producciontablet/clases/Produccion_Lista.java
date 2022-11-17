package com.codekolih.producciontablet.clases;

import java.io.Serializable;

public class Produccion_Lista implements Serializable  {

    private String Fecha;
    private String UserNameId;
    private int ProduccionId;
    private int PedidoId;
    private int TareaId;
    private float MetrosImpresos;
    private float AnchoFinalRolloYGap;
    private float CantidadPistasImpresas;
    private float CantidadTintas;
    private float AnchoBobinaUsadoCm;
    private float ScrapAjusteInicial;
    private String ScrapAjusteInicial_Unidades;
    private float ScrapAjusteProduccion;
    private String ScrapAjusteProduccion_Unidades;
    private String ObservacionesCierre;
    private float RollosFabricdos;
    private float AnchoFinalRollo;
    private float CantidadPistasCortadas;
    private float PistasTroquelUsadas;
    private float RollosEmpaquetados;
    private int UsuarioId;


    public Produccion_Lista() {


    }



// Getter Methods

    public String getFecha() {
        return Fecha;
    }

    public String getUserNameId() {
        return UserNameId;
    }

    public int getProduccionId() {
        return ProduccionId;
    }

    public int getPedidoId() {
        return PedidoId;
    }

    public int getTareaId() {
        return TareaId;
    }

    public float getMetrosImpresos() {
        return MetrosImpresos;
    }

    public float getAnchoFinalRolloYGap() {
        return AnchoFinalRolloYGap;
    }

    public float getCantidadPistasImpresas() {
        return CantidadPistasImpresas;
    }

    public float getCantidadTintas() {
        return CantidadTintas;
    }

    public float getAnchoBobinaUsadoCm() {
        return AnchoBobinaUsadoCm;
    }

    public float getScrapAjusteInicial() {
        return ScrapAjusteInicial;
    }

    public String getScrapAjusteInicial_Unidades() {
        return ScrapAjusteInicial_Unidades;
    }

    public float getScrapAjusteProduccion() {
        return ScrapAjusteProduccion;
    }

    public String getScrapAjusteProduccion_Unidades() {
        return ScrapAjusteProduccion_Unidades;
    }

    public String getObservacionesCierre() {
        return ObservacionesCierre;
    }

    public float getRollosFabricdos() {
        return RollosFabricdos;
    }

    public float getAnchoFinalRollo() {
        return AnchoFinalRollo;
    }

    public float getCantidadPistasCortadas() {
        return CantidadPistasCortadas;
    }

    public float getPistasTroquelUsadas() {
        return PistasTroquelUsadas;
    }

    public float getRollosEmpaquetados() {
        return RollosEmpaquetados;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    // Setter Methods

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public void setUserNameId(String UserNameId) {
        this.UserNameId = UserNameId;
    }

    public void setProduccionId(int ProduccionId) {
        this.ProduccionId = ProduccionId;
    }

    public void setPedidoId(int PedidoId) {
        this.PedidoId = PedidoId;
    }

    public void setTareaId(int TareaId) {
        this.TareaId = TareaId;
    }

    public void setMetrosImpresos(float MetrosImpresos) {
        this.MetrosImpresos = MetrosImpresos;
    }

    public void setAnchoFinalRolloYGap(float AnchoFinalRolloYGap) {
        this.AnchoFinalRolloYGap = AnchoFinalRolloYGap;
    }

    public void setCantidadPistasImpresas(float CantidadPistasImpresas) {
        this.CantidadPistasImpresas = CantidadPistasImpresas;
    }

    public void setCantidadTintas(float CantidadTintas) {
        this.CantidadTintas = CantidadTintas;
    }

    public void setAnchoBobinaUsadoCm(float AnchoBobinaUsadoCm) {
        this.AnchoBobinaUsadoCm = AnchoBobinaUsadoCm;
    }

    public void setScrapAjusteInicial(float ScrapAjusteInicial) {
        this.ScrapAjusteInicial = ScrapAjusteInicial;
    }

    public void setScrapAjusteInicial_Unidades(String ScrapAjusteInicial_Unidades) {
        this.ScrapAjusteInicial_Unidades = ScrapAjusteInicial_Unidades;
    }

    public void setScrapAjusteProduccion(float ScrapAjusteProduccion) {
        this.ScrapAjusteProduccion = ScrapAjusteProduccion;
    }

    public void setScrapAjusteProduccion_Unidades(String ScrapAjusteProduccion_Unidades) {
        this.ScrapAjusteProduccion_Unidades = ScrapAjusteProduccion_Unidades;
    }

    public void setObservacionesCierre(String ObservacionesCierre) {
        this.ObservacionesCierre = ObservacionesCierre;
    }

    public void setRollosFabricdos(float RollosFabricdos) {
        this.RollosFabricdos = RollosFabricdos;
    }

    public void setAnchoFinalRollo(float AnchoFinalRollo) {
        this.AnchoFinalRollo = AnchoFinalRollo;
    }

    public void setCantidadPistasCortadas(float CantidadPistasCortadas) {
        this.CantidadPistasCortadas = CantidadPistasCortadas;
    }

    public void setPistasTroquelUsadas(float PistasTroquelUsadas) {
        this.PistasTroquelUsadas = PistasTroquelUsadas;
    }

    public void setRollosEmpaquetados(float RollosEmpaquetados) {
        this.RollosEmpaquetados = RollosEmpaquetados;
    }

    public void setUsuarioId(int UsuarioId) {
        this.UsuarioId = UsuarioId;
    }
}
