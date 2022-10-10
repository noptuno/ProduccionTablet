package com.codekolih.producciontablet.clases;

public class EstadosOp {

    int LogId;
    int TareaId;
    String EstadoId;
    String NombreEstado;
    String FechaInicio;
    String FechaFin;
    int UsuarioId;
    String NombreUsuario;


    public EstadosOp(int logId, int tareaId, String estadoId, String nombreEstado, String fechaInicio, String fechaFin, int usuarioId, String nombreUsuario) {
        LogId = logId;
        TareaId = tareaId;
        EstadoId = estadoId;
        NombreEstado = nombreEstado;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        UsuarioId = usuarioId;
        NombreUsuario = nombreUsuario;
    }

    public int getLogId() {
        return LogId;
    }

    public void setLogId(int logId) {
        LogId = logId;
    }

    public int getTareaId() {
        return TareaId;
    }

    public void setTareaId(int tareaId) {
        TareaId = tareaId;
    }

    public String getEstadoId() {
        return EstadoId;
    }

    public void setEstadoId(String estadoId) {
        EstadoId = estadoId;
    }

    public String getNombreEstado() {
        return NombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        NombreEstado = nombreEstado;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }
}
