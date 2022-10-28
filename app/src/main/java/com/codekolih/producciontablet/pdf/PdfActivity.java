package com.codekolih.producciontablet.pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class PdfActivity extends AppCompatActivity {

    private static final String RECIBIRFOLDER = "F";
    private static final String RECIBIRINTENT = "NN";
    private PDFView pdfView;
    private File myFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdfView);

    }

    private void recibir_pdf_mainactivity() {

        Intent intentrecibir = getIntent();

        if (intentrecibir != null) {

            if (intentrecibir.getAction().equals(RECIBIRFOLDER)) {

                try {
                    String path = intentrecibir.getExtras().getString("PATH");
                    myFile = new File(path);
                    AbriPdf(myFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (intentrecibir.getAction().equals(RECIBIRINTENT)) {
                myFile = (File) intentrecibir.getSerializableExtra("MY_FILE");
                AbriPdf(myFile);
            } else {
                Toast.makeText(PdfActivity.this, "No se de donde recibe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void AbriPdf(File bFileeee) {

        if (bFileeee.length() > 0) {
            pdfView.fromFile(bFileeee).defaultPage(0).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {

                }
            }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();
        } else {
            Toast.makeText(PdfActivity.this, "Arhivo Dañado", Toast.LENGTH_SHORT).show();
        }
    }
}