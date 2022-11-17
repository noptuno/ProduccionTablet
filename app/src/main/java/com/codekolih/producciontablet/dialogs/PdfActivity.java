package com.codekolih.producciontablet.dialogs;

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

    private PDFView pdfView;
    private File myFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdf_view_pdf);

        AbriPdf();
    }

    public void AbriPdf() {


            pdfView.fromAsset("ejemplo.pdf").defaultPage(0).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {

                }
            }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();

    }
}