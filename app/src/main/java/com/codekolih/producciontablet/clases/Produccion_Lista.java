package com.codekolih.producciontablet.clases;

import java.io.Serializable;

public class Produccion_Lista implements Serializable  {
    private String Fecha;
    private String UserNameId;
    private float ProduccionId;
    private float PedidoId;
    private float TareaId;
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
    private float UsuarioId;

    public Produccion_Lista(String fecha, String userNameId, float produccionId, float pedidoId, float tareaId, float metrosImpresos, float anchoFinalRolloYGap, float cantidadPistasImpresas, float cantidadTintas, float anchoBobinaUsadoCm, float scrapAjusteInicial, String scrapAjusteInicial_Unidades, float scrapAjusteProduccion, String scrapAjusteProduccion_Unidades, String observacionesCierre, float rollosFabricdos, float anchoFinalRollo, float cantidadPistasCortadas, float pistasTroquelUsadas, float rollosEmpaquetados, float usuarioId) {
        Fecha = fecha;
        UserNameId = userNameId;
        ProduccionId = produccionId;
        PedidoId = pedidoId;
        TareaId = tareaId;
        MetrosImpresos = metrosImpresos;
        AnchoFinalRolloYGap = anchoFinalRolloYGap;
        CantidadPistasImpresas = cantidadPistasImpresas;
        CantidadTintas = cantidadTintas;
        AnchoBobinaUsadoCm = anchoBobinaUsadoCm;
        ScrapAjusteInicial = scrapAjusteInicial;
        ScrapAjusteInicial_Unidades = scrapAjusteInicial_Unidades;
        ScrapAjusteProduccion = scrapAjusteProduccion;
        ScrapAjusteProduccion_Unidades = scrapAjusteProduccion_Unidades;
        ObservacionesCierre = observacionesCierre;
        RollosFabricdos = rollosFabricdos;
        AnchoFinalRollo = anchoFinalRollo;
        CantidadPistasCortadas = cantidadPistasCortadas;
        PistasTroquelUsadas = pistasTroquelUsadas;
        RollosEmpaquetados = rollosEmpaquetados;
        UsuarioId = usuarioId;
    }

// Getter Methods

    public String getFecha() {
        return Fecha;
    }

    public String getUserNameId() {
        return UserNameId;
    }

    public float getProduccionId() {
        return ProduccionId;
    }

    public float getPedidoId() {
        return PedidoId;
    }

    public float getTareaId() {
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

    public float getUsuarioId() {
        return UsuarioId;
    }

    // Setter Methods

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public void setUserNameId(String UserNameId) {
        this.UserNameId = UserNameId;
    }

    public void setProduccionId(float ProduccionId) {
        this.ProduccionId = ProduccionId;
    }

    public void setPedidoId(float PedidoId) {
        this.PedidoId = PedidoId;
    }

    public void setTareaId(float TareaId) {
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

    public void setUsuarioId(float UsuarioId) {
        this.UsuarioId = UsuarioId;
    }
}
