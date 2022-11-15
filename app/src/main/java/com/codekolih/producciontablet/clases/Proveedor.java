package com.codekolih.producciontablet.clases;

public class Proveedor {

     int ProveedorlId;
     String CodigoProveedor;
     String Nombre;
     Boolean Habilitado;

    public Proveedor() {
    }

    public int getProveedorlId() {
        return ProveedorlId;
    }

    public void setProveedorlId(int proveedorlId) {
        ProveedorlId = proveedorlId;
    }

    public String getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        CodigoProveedor = codigoProveedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Boolean getHabilitado() {
        return Habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        Habilitado = habilitado;
    }
}
