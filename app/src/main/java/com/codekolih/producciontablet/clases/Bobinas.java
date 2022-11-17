package com.codekolih.producciontablet.clases;

import java.io.Serializable;

public class Bobinas implements Serializable {

        private int BobinaId;
        private int TareaId;
        private int ProduccionId;
        private int ProveedorId;
        private String ProveedorNombre;
        private String Lote;
        private float Ancho;
        private float TipoMaterialId;
        private String EsAbiertaoCerrada;
        private float DefectuosaKg;
        private String NombreTipoMaterial;


        // Getter Methods

        public int getBobinaId() {
            return BobinaId;
        }

        public int getTareaId() {
            return TareaId;
        }

        public int getProduccionId() {
            return ProduccionId;
        }

        public int getProveedorId() {
            return ProveedorId;
        }

        public String getProveedorNombre() {
            return ProveedorNombre;
        }

        public String getLote() {
            return Lote;
        }

        public float getAncho() {
            return Ancho;
        }

        public float getTipoMaterialId() {
            return TipoMaterialId;
        }

        public String getEsAbiertaoCerrada() {
            return EsAbiertaoCerrada;
        }

        public float getDefectuosaKg() {
            return DefectuosaKg;
        }

        public String getNombreTipoMaterial() {
            return NombreTipoMaterial;
        }

        // Setter Methods

        public void setBobinaId( int BobinaId ) {
            this.BobinaId = BobinaId;
        }

        public void setTareaId( int TareaId ) {
            this.TareaId = TareaId;
        }

        public void setProduccionId( int ProduccionId ) {
            this.ProduccionId = ProduccionId;
        }

        public void setProveedorId( int ProveedorId ) {
            this.ProveedorId = ProveedorId;
        }

        public void setProveedorNombre( String ProveedorNombre ) {
            this.ProveedorNombre = ProveedorNombre;
        }

        public void setLote( String Lote ) {
            this.Lote = Lote;
        }

        public void setAncho( float Ancho ) {
            this.Ancho = Ancho;
        }

        public void setTipoMaterialId( float TipoMaterialId ) {
            this.TipoMaterialId = TipoMaterialId;
        }

        public void setEsAbiertaoCerrada( String EsAbiertaoCerrada ) {
            this.EsAbiertaoCerrada = EsAbiertaoCerrada;
        }

        public void setDefectuosaKg( float DefectuosaKg ) {
            this.DefectuosaKg = DefectuosaKg;
        }

        public void setNombreTipoMaterial( String NombreTipoMaterial ) {
            this.NombreTipoMaterial = NombreTipoMaterial;
        }
}
