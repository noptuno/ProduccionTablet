package com.codekolih.producciontablet.aciones;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressHUD extends ProgressDialog {

    public ProgressHUD(Context context) {
        super(context);

    }

    public static ProgressHUD show(Context context) {

        ProgressHUD dialog = new ProgressHUD(context);
        dialog.setMessage("Loading..."); // Setting Message
        dialog.setTitle("Cargarndo Datos ..."); // Setting Title
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner// Display Progress Dialog
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

}
