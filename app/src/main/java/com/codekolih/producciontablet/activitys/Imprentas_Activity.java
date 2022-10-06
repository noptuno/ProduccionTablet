package com.codekolih.producciontablet.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.adapter.AdapterImprentas;
import com.codekolih.producciontablet.clases.Imprentas;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Imprentas_Activity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ArrayList<Imprentas> listImprentas = new ArrayList<>();
    private AdapterImprentas adapterImprentas = new AdapterImprentas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprentas);

        //declaraciones
        requestQueue = Volley.newRequestQueue(this);


        RecyclerView recyclerView = findViewById(R.id.config_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterImprentas);

        adapterImprentas.setOnNoteSelectedListener(new AdapterImprentas.OnNoteSelectedListener(){
            @Override
            public void onClick(Imprentas note) {

                Intent intent = new Intent(Imprentas_Activity.this, Login_Activity.class);
                intent.putExtra("NombreMaquina", note.getNombreMaquina());
                intent.putExtra("TipoMaquina", note.getMaquinaTipoId());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

        });

        cargarConfiguracion();

        cargarDatos();
       // Request();


    }
    private RequestQueue requestQueue;

void cargarDatos(){


        final ProgressDialog dialog = ProgressDialog.show(this, "Cargando...", "Espere por favor", true);

        String url = Urls.Imprentas;

        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //updateLista(response);

                            List<Imprentas> lista = GsonUtils.parseList(response, Imprentas[].class);
                             List<Imprentas> aux = new ArrayList<>();

                                for (Imprentas lg : lista) {
                                    if (lg.getHabilitada()){
                                        aux.add(lg);
                                    }
                                }

                            adapterImprentas.setNotes(aux);
                            adapterImprentas.notifyDataSetChanged();
                            dialog.dismiss();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Imprentas_Activity.this, "Siempre tengo hambre =)", Toast.LENGTH_LONG).show();
                         dialog.dismiss();
                    }
                });
        requestQueue.add(request);

}


    private void cargarConfiguracion() {


    }

    void Request(){

       setProgressDialog();

       OkHttpClient client = new OkHttpClient.Builder()
               .connectTimeout(10, TimeUnit.SECONDS)
               .writeTimeout(10, TimeUnit.SECONDS)
               .readTimeout(30, TimeUnit.SECONDS)
               .build();

       Request request = new Request.Builder()
               .url("https://apidmr.azurewebsites.net/api/v1/maquina/F03601A1-FD60-47E1-B30F-6B2C3BACDE3B")
               .method("GET", null)
               .build();

       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               call.cancel();
               actualizarReciclerView(false);
               progressDialog.dismiss();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {

               final String myResponse = response.body().string();

               if (response.isSuccessful()) {
                   try {

                       JSONArray jsonArray = new JSONArray(myResponse);
                       JSONObject jsonObject = new JSONObject();
                       listImprentas.clear();

                       for(int i=0; i<jsonArray.length(); i++){
                           jsonObject = (JSONObject) jsonArray.get(i);
                           Gson gson = new Gson();
                           Imprentas imprenta = gson.fromJson(jsonObject.toString(), Imprentas.class);
                           listImprentas.add(imprenta);

                       }

                       progressDialog.dismiss();
                       actualizarReciclerView(true);


/*
                       JSONArray names = json.names();
                       JSONArray values = json.toJSONArray(names);
                       for(int i=0; i<values.length(); i++){
                           if (names.getString(i).equals("description")){
                               setDescription(values.getString(i));
                           }
                           else if (names.getString(i).equals("expiryDate")){
                               String dateString = values.getString(i);
                               setExpiryDate(stringToDateHelper(dateString));
                           }
                           else if (names.getString(i).equals("id")){
                               setId(values.getLong(i));
                           }
                           else if (names.getString(i).equals("offerCode")){
                               setOfferCode(values.getString(i));
                           }
                           else if (names.getString(i).equals("startDate")){
                               String dateString = values.getString(i);
                               setStartDate(stringToDateHelper(dateString));
                           }
                           else if (names.getString(i).equals("title")){
                               setTitle(values.getString(i));
                           }
                       }
                       */

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
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

    public void actualizarReciclerView(boolean a) {
        runOnUiThread(() -> {
            if (a){
                adapterImprentas.setNotes(listImprentas);
                adapterImprentas.notifyDataSetChanged();
                Toast.makeText(Imprentas_Activity.this,"Actualizo",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Imprentas_Activity.this,"Error Api",Toast.LENGTH_SHORT).show();
            }

        });

    }

}

/*
private void Registrar(Login login) {
    final ProgressDialog dialog = ProgressDialog.show(this, "Cargando...", "Espere por favor", true);

    String url = URLs.REGISTER;
    JSONObject jsonObject = GsonUtils.toJSON(login);
    JsonObjectRequest request = new JsonObjectRequest(
            com.android.volley.Request.Method.POST,
            url,
            jsonObject,
            new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    dialog.dismiss();
                    Toast.makeText(RegistroUsuario.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    finish();
                }
            },
            new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    error.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(RegistroUsuario.this, "Reintentelo de conexion", Toast.LENGTH_LONG).show();
                }
            });

    requestQueue.add(request);
}
*/