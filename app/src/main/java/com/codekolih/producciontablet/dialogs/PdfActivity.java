package com.codekolih.producciontablet.dialogs;

import static java.lang.System.in;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.Util;
import com.shockwave.pdfium.PdfDocument;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class PdfActivity extends AppCompatActivity {

    private PDFView pdfView;
    private long downloadID;
    private Button regresar;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdf_view_pdf);
        regresar = findViewById(R.id.btncancelar);

        //AbriPdf();
       // otro();

        pedir_permiso_escritura();

    //abrirpdf1();
       abrirconSMB();



/*
        try {
            URL url = new URL("file://192.168.234.9/Public/PDF/logoejemplo.pdf");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();

            File file = new File(Environment.getExternalStorageDirectory(), "logoejemplo.pdf");
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = connection.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }

            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


/*
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
        */
/*
        File pdfFile = new File(myFile.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        Uri uri = FileProvider.getUriForFile(Pdf_view.this, "com.gpp.dmr_print.provider", pdfFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        */

    }

    private void abrirconSMB() {

        new Thread(new Runnable() {
            public void run() {

                try {

                    String path = "smb://192.168.234.9/Public/PDF/logoejemplo.pdf";
                    SmbFile smbFile = new SmbFile(path);
                    SmbFileInputStream inputStream = new SmbFileInputStream(smbFile);

                    File file = new File(Environment.getExternalStorageDirectory(), "logoejemplo.pdf");
                    FileOutputStream fileOutput = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;

                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                    }

                    fileOutput.close();



                }catch (Exception e){
                    Log.e("Error",e.toString());

                }

            }
        }).start();

    }


    private void abrirpdf1() {

        new Thread(new Runnable() {
            public void run() {

                try {

                    String fileUrl = "file://192.168.234.9/Public/PDF/logoejemplo.pdf";
                    URL url = new URL(fileUrl);
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Accept", "application/pdf");
                    int fileSize = connection.getContentLength();
                    InputStream input = new BufferedInputStream(connection.getInputStream());

                    File outputFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + "/pdftemp.pdf");
                    OutputStream output = new FileOutputStream(outputFile);
                    byte[] data = new byte[1024];
                    int total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }
                    input.close();
                    output.close();



                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Archivo descargado con éxito", Toast.LENGTH_SHORT).show();
                        }
                    });


                }catch (Exception e){
                    Log.e("Error",e.toString());

                }

            }
        }).start();
    }

    public void AbriPdf() {
        /*
        pdfView.fromUri(pdfUri).defaultPage(0).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {

            }
        }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();
*/
    }

    private void pedir_permiso_escritura() {

        int readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED || readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(PdfActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(PdfActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);

                return;
            }
        }

    }
}