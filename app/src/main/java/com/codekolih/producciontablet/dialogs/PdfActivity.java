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
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ProgressHUD;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.activitys.Login_Activity;
import com.codekolih.producciontablet.activitys.Verificacion_Activity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.Util;
import com.shockwave.pdfium.PdfDocument;


import org.apache.commons.net.ftp.FTPClient;

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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PdfActivity extends AppCompatActivity {

    private PDFView pdfView;
    private long downloadID;
    private Button regresar,continuar;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private ProgressHUD dialogProgress;
    private String nombrepdf = "null";
    private boolean abriopdf = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdf_view_pdf);
        regresar = findViewById(R.id.btncancelar);
        continuar = findViewById(R.id.btncontinuar);

        continuar.setVisibility(View.GONE);


        nombrepdf = TareaSingleton.SingletonInstance().getNombrepdf();


        dialogProgress = ProgressHUD.show(PdfActivity.this);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogProgress.dismiss();
                finish();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogProgress.dismiss();
                finish();
            }
        });





        //ClientCredential clientCredential = new ClientCredential("client_id", "client_secret");

        Toast.makeText(getApplicationContext(),nombrepdf,Toast.LENGTH_SHORT).show();

            Log.e("nombre",nombrepdf);

        int index = nombrepdf.lastIndexOf("\\");
        String fileNameWithExt = nombrepdf.substring(index + 1);

        if(fileNameWithExt.endsWith(".pdf") || fileNameWithExt.endsWith(".PDF")) {

            String direccionServidor = "192.168.234.9";
            int puerto = 21;
            String usuario = "Usuario1";
            String contrasena = "123456789";
            String rutaArchivo = fileNameWithExt;
            String nombreArchivo = fileNameWithExt;

            DownloadTask downloadTask = new DownloadTask(direccionServidor, puerto, usuario, contrasena, rutaArchivo, nombreArchivo);
            downloadTask.execute();

        }




    }

    private class DownloadTask extends AsyncTask<Void, Void, File> {
        private final String direccionServidor;
        private final int puerto;
        private final String usuario;
        private final String contrasena;
        private final String rutaArchivo;
        private final String nombreArchivo;

        public DownloadTask(String direccionServidor, int puerto, String usuario, String contrasena, String rutaArchivo, String nombreArchivo) {
            this.direccionServidor = direccionServidor;
            this.puerto = puerto;
            this.usuario = usuario;
            this.contrasena = contrasena;
            this.rutaArchivo = rutaArchivo;
            this.nombreArchivo = nombreArchivo;
        }

        @Override
        protected File doInBackground(Void... voids) {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(direccionServidor, puerto);
                ftpClient.login(usuario, contrasena);
                ftpClient.enterLocalPassiveMode();
                FileOutputStream outputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
                boolean exitoDescarga = ftpClient.retrieveFile(rutaArchivo, outputStream);

                outputStream.close();
                ftpClient.logout();
                ftpClient.disconnect();

                if (exitoDescarga){

                    return new File(getFilesDir(), nombreArchivo);

                }else{

                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                dialogProgress.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(File exitoDescarga) {

            dialogProgress.dismiss();

            if (exitoDescarga!=null) {

                    pdfView.fromFile(exitoDescarga).defaultPage(0).onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
/*
                            pageCount = nbPages;
                            abriopdf = true;
                            if (pdfView.getCurrentPage() == pageCount - 1) {

                                // El usuario ha leído todo el PDF, habilitar el botón de finalizar
                                continuar.setVisibility(View.VISIBLE);
                            }
*/

                        }
                    }).scrollHandle(new DefaultScrollHandle(PdfActivity.this)).load();


            } else{
                abriopdf = false;
            Toast.makeText(getApplicationContext(),"No se encontro documento",Toast.LENGTH_SHORT).show();
            }
        }

    }
    int currentPage = 0;
    int pageCount = 0;
}