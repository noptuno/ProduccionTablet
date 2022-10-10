package com.codekolih.producciontablet.clases;

public class Usuario {

        private float UsuarioId;
        private String Nombre;
        private String Apellido;
        private String UserNameId;
        private String Email;
        private boolean Habilitado;
        private float RolId;
        private String RolNombre;


        // Getter Methods

        public float getUsuarioId() {
            return UsuarioId;
        }

        public String getNombre() {
            return Nombre;
        }

        public String getApellido() {
            return Apellido;
        }

        public String getUserNameId() {
            return UserNameId;
        }

        public String getEmail() {
            return Email;
        }

        public boolean getHabilitado() {
            return Habilitado;
        }

        public float getRolId() {
            return RolId;
        }

        public String getRolNombre() {
            return RolNombre;
        }

        // Setter Methods

        public void setUsuarioId(float UsuarioId) {
            this.UsuarioId = UsuarioId;
        }

        public void setNombre(String Nombre) {
            this.Nombre = Nombre;
        }

        public void setApellido(String Apellido) {
            this.Apellido = Apellido;
        }

        public void setUserNameId(String UserNameId) {
            this.UserNameId = UserNameId;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public void setHabilitado(boolean Habilitado) {
            this.Habilitado = Habilitado;
        }

        public void setRolId(float RolId) {
            this.RolId = RolId;
        }

        public void setRolNombre(String RolNombre) {
            this.RolNombre = RolNombre;
        }

}
