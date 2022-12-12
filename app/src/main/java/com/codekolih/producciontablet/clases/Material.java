package com.codekolih.producciontablet.clases;

public class Material {

    int TipoMaterialId;
    String Nombre;

    public Material() {
    }

    public int getTipoMaterialId() {
        return TipoMaterialId;
    }

    public void setTipoMaterialId(int tipoMaterialId) {
        TipoMaterialId = tipoMaterialId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
