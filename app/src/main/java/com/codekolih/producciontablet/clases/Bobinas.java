package com.codekolih.producciontablet.clases;

public class Bobinas {

        private float BobinaId;
        private float TareaId;
        private float ProduccionId;
        private float ProveedorId;
        private String ProveedorNombre;
        private String Lote;
        private float Ancho;
        private float TipoMaterialId;
        private String EsAbiertaoCerrada;
        private float DefectuosaKg;
        private String NombreTipoMaterial;


        // Getter Methods

        public float getBobinaId() {
            return BobinaId;
        }

        public float getTareaId() {
            return TareaId;
        }

        public float getProduccionId() {
            return ProduccionId;
        }

        public float getProveedorId() {
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

        public void setBobinaId( float BobinaId ) {
            this.BobinaId = BobinaId;
        }

        public void setTareaId( float TareaId ) {
            this.TareaId = TareaId;
        }

        public void setProduccionId( float ProduccionId ) {
            this.ProduccionId = ProduccionId;
        }

        public void setProveedorId( float ProveedorId ) {
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
