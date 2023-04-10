package com.codekolih.producciontablet.aciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Validarinternet {

    public static boolean validarConexionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            mostrarDialogoError(context);
            return false;
        }
        return true;
}

    private static void mostrarDialogoError(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("No hay conexión a Internet. Por favor, inténtalo de nuevo más tarde.")
                .setCancelable(false)
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        validarConexionInternet(context);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}