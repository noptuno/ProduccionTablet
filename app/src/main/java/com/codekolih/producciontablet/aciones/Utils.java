package com.codekolih.producciontablet.aciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codekolih.producciontablet.R;

public class Utils {

    private static Handler mHandler;


    public static void startHandler(Runnable runnable) {
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 600000);
    }

    public static void stopHandler() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public static AlertDialog dialogaviso(Context context, String mensaje) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.alerdialogerror, null);
        final TextView mPassword = mView.findViewById(R.id.txtmensajeerror);
        Button mLogin = mView.findViewById(R.id.btnReintentar);
        mLogin.setText("Continuar");
        mPassword.setText(mensaje);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        final int[] colors = {android.R.color.white, android.R.color.holo_red_light, android.R.color.holo_orange_light};
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                mView.setBackgroundColor(context.getResources().getColor(colors[i]));
                mPassword.setTextColor(context.getResources().getColor(android.R.color.black));
                i++;
                if (i == colors.length) {
                    i = 0;
                }
                handler.postDelayed(this, 2000); // Cambiar los colores cada 2 segundos (2000 milisegundos)
            }
        };
        handler.post(runnable);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        return dialog;
    }

    private static boolean mIsHandlerWaiting = false;
    public static Runnable getRunnable(final Activity activity, final String mensaje) {

        return new Runnable() {
            @Override
            public void run() {
                if (!mIsHandlerWaiting) {
                    final AlertDialog dialog = Utils.dialogaviso(activity, mensaje);
                    mIsHandlerWaiting = true;
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            // Establecer la variable booleana como false para permitir que el Handler vuelva a ejecutarse
                            mIsHandlerWaiting = false;
                            Utils.startHandler(getRunnable(activity, mensaje));
                        }
                    });
                    Utils.stopHandler();
                }
            }
        };
    }
}

