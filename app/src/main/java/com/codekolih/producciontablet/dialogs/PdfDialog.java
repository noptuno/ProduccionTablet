package com.codekolih.producciontablet.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PdfDialog {

    private PDFView pdfView;
    private Context c;

    public PdfDialog(Context contexcto ){
        c = contexcto;
        final Dialog dialogo = new Dialog(contexcto);

        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C808E")));
        dialogo.setContentView(R.layout.dialog_pdf);
        pdfView = dialogo.findViewById(R.id.dialog_view_pdf);

        Button cancelar = dialogo.findViewById(R.id.btn_cerra_pdf);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogo.dismiss();

            }
        });

        try{

            File f = new File(contexcto.getCacheDir() + "/temp.pdf");
            if (!f.exists()) {

                InputStream is = contexcto.getAssets().open("ejemplo.pdf");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();

                if (f.length() > 0) {
                    pdfView.fromFile(f).defaultPage(0).onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {

                        }
                    }).scrollHandle(new DefaultScrollHandle(c)).load();
                } else {
                    Toast.makeText(c, "Arhivo Dañado", Toast.LENGTH_SHORT).show();
                }
            }

        }catch (Exception e){
            Log.e("Error",e.toString());
        }

        dialogo.show();

    }



}
