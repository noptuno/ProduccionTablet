package com.codekolih.producciontablet.clases;

public class Imprentas {

    int MaquinaId;
    int MaquinaTipoId;
    String NombreMaquina;
    Boolean PermiteCambioPrioridad;
    Boolean Habilitada;

    public Imprentas() {
    }

    public Imprentas(int maquinaId, int maquinaTipoId, String nombreMaquina, Boolean permiteCambioPrioridad, Boolean habilitada) {
        MaquinaId = maquinaId;
        MaquinaTipoId = maquinaTipoId;
        NombreMaquina = nombreMaquina;
        PermiteCambioPrioridad = permiteCambioPrioridad;
        Habilitada = habilitada;
    }

    public int getMaquinaId() {
        return MaquinaId;
    }

    public void setMaquinaId(int maquinaId) {
        MaquinaId = maquinaId;
    }

    public int getMaquinaTipoId() {
        return MaquinaTipoId;
    }

    public void setMaquinaTipoId(int maquinaTipoId) {
        MaquinaTipoId = maquinaTipoId;
    }

    public String getNombreMaquina() {
        return NombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        NombreMaquina = nombreMaquina;
    }

    public Boolean getPermiteCambioPrioridad() {
        return PermiteCambioPrioridad;
    }

    public void setPermiteCambioPrioridad(Boolean permiteCambioPrioridad) {
        PermiteCambioPrioridad = permiteCambioPrioridad;
    }

    public Boolean getHabilitada() {
        return Habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        Habilitada = habilitada;
    }
}
