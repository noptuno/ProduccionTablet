package com.codekolih.producciontablet.dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class PdfActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File myFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdf_view_pdf);


        URI uri = new File("File://192.168.234.144/pdf/fact.pdf").toURI();




        /*
        try {
        URL url1 =new URL("File://192.168.234.144/pdf/fact.pdf");
        byte[] ba1 = new byte[1024];
        int baLength;
        FileOutputStream fos1 = new FileOutputStream("fact.pdf");

                try {
                    // Read the PDF from the URL and save to a local file
                    InputStream is1 = url1.openStream();
                    while ((baLength = is1.read(ba1)) != -1) {
                        fos1.write(ba1, 0, baLength);
                    }
                    fos1.flush();
                    fos1.close();

                    // Load the PDF document and display its page count
                    System.out.print("DONE.\nProcessing the PDF ... ");

                    pdfView.fromStream(is1).defaultPage(0).onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {

                        }
                    }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();

                    is1.close();

                } catch (ConnectException ce) {
                    System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
*/


      //  AbriPdf();
    }




    public void AbriPdf() {


            pdfView.fromAsset("ejemplo.pdf").defaultPage(0).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {

                }
            }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();

    }
}