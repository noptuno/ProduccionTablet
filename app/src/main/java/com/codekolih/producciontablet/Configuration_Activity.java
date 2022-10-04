package com.codekolih.producciontablet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Configuration_Activity extends AppCompatActivity {


    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        recyclerView = findViewById(R.id.config_rec_imprenta);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Request();
    }

   void Request(){

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
               Log.e("error",e.toString());

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {

               final String myResponse = response.body().string();

               if (response.isSuccessful()) {
                   try {
                       JSONArray jsonArray = new JSONArray(myResponse);

                       JSONObject jsonObject = new JSONObject(jsonArray.getString(0));

                       Log.e("MOSTRANDO",jsonObject.toString());

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
   }

   }