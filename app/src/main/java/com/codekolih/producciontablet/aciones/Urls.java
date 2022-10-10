package com.codekolih.producciontablet.aciones;

public class Urls {

    public static final String KEY = "F03601A1-FD60-47E1-B30F-6B2C3BACDE3B";
    public static final String barr = "/";
    public static final String API_BASE_URL = "https://apidmr.azurewebsites.net/api/v1";
    public static final String API_BASE_USUARIO_URL = "https://apidmr.azurewebsites.net/api";
    public static final String MAQUINAS = "maquina";
    public static final String Login_tad = "Cuenta/Login";

    public static final String login = API_BASE_USUARIO_URL + barr + Login_tad + barr + KEY;
    public static final String Imprentas = API_BASE_URL + barr + MAQUINAS + barr + KEY;
    public static final String Tareas = "https://apidmr.azurewebsites.net/api/v1/tarea/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/listaxmaquinaid/43/O";
    public static final String getUsuario = "https://apidmr.azurewebsites.net/api/v1/Usuario/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/DatosUsuario/rubach";
    public static final String agregarProduccion = " http://apidmr.azurewebsites.net/api/v1/produccion/altaproduc/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/rubach";



}
