package com.codekolih.producciontablet.aciones;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Urls {

    public static final String KEY = "F03601A1-FD60-47E1-B30F-6B2C3BACDE3B";
    public static final String barr = "/";
    public static final String API_BASE_URL = "https://apidmr.azurewebsites.net/api/v1";
    public static final String API_BASE_USUARIO_URL = "https://apidmr.azurewebsites.net/api";
    public static final String MAQUINAS = "maquina";
    public static final String Login_tad = "Cuenta/Login";

    public static final String login = "https://apidmr.azurewebsites.net/api/Cuenta/Login/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B";
    public static final String Imprentas = API_BASE_URL + barr + MAQUINAS + barr + KEY;


    public static final String Tareas = "https://apidmr.azurewebsites.net/api/v1/tarea/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/listaxmaquinaid/";
    public static final String getUsuario = "https://apidmr.azurewebsites.net/api/v1/Usuario/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/DatosUsuario";

    public static final String UpdateProduccion="https://apidmr.azurewebsites.net/api/v1/produccion/updateproduc/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/";
    public static final String agregarbobinas = "https://apidmr.azurewebsites.net/api/v1/produccion/altabobina/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/";
    public static final String eliminarBobina = "https://apidmr.azurewebsites.net/api/v1/produccion/deletebobina/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/";

    public static final String proveedor = "https://apidmr.azurewebsites.net/api/v1/proveedor/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/lista";
    public static final String estadoOperativo = "https://apidmr.azurewebsites.net/api/v1/tarea/altaestadoop/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/";
    public static final String altaproduccion = " httpS://apidmr.azurewebsites.net/api/v1/produccion/altaproduc/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/";
    public static final String obtenerpedido = "https://apidmr.azurewebsites.net/api/v1/pedido/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/porpedido/";
    public static final String materiales = "https://apidmr.azurewebsites.net/api/v1/tarea/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B/lista_materialid";






}
