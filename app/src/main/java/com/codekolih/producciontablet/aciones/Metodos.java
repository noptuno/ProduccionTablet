package com.codekolih.producciontablet.aciones;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Metodos {

    private ProgressDialog progressDialog;
    private Context context;

    public Metodos(Context a) {
        this.context = a;
    }

    public void DialogDismiss(){
        progressDialog.dismiss();
    }

    public void setProgressDialog() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }


}
