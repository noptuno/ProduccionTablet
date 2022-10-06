package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.Metodos;
import com.codekolih.producciontablet.clases.Imprentas;

public class Login_Activity extends AppCompatActivity {

    private Button btn_inicioSesion, btn_cargarImprenta;
    private ProgressDialog progressDialog;
    private TextView txt_nombreImprenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Referencias

        btn_inicioSesion = findViewById(R.id.login_btn_IniciarSesion);
        txt_nombreImprenta = findViewById(R.id.login_txt_nombreimprenta);
        btn_cargarImprenta = findViewById(R.id.login_btn_cargarImprenta);


        //Inicializar
        Metodos metodos = new Metodos(getApplicationContext());


        //cargar

        String NombreMaquina = getIntent().getStringExtra("NombreMaquina");
        txt_nombreImprenta.setText(NombreMaquina);


        btn_cargarImprenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this, Imprentas_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

            }
        });



        btn_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setProgressDialog();

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intent = new Intent(Login_Activity.this, Tarea_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        progressDialog.dismiss();
                    }

                }, 2000);

            }
        });
    }


    public void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }

}