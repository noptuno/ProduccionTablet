package com.codekolih.producciontablet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codekolih.producciontablet.adapter.AdapterImprentas;
import com.codekolih.producciontablet.clases.Imprentas;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Configuration_Activity extends AppCompatActivity {

    private ArrayList<Imprentas> listImprentas;
    private RecyclerView recyclerView;
    private AdapterImprentas adapterImprentas = new AdapterImprentas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        recyclerView = findViewById(R.id.config_recycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterImprentas);

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
                       JSONObject jsonObject = new JSONObject();

                       for(int i=0; i<jsonArray.length(); i++){

                           jsonObject = (JSONObject) jsonArray.get(i);

                           Log.e("MOSTRANDO",jsonObject.toString());

                           Gson gson = new Gson();

                           Imprentas imprenta = gson.fromJson(jsonObject.toString(), Imprentas.class);

                           listImprentas.add(imprenta);

                       }

                       Log.e("MOSTRANDO",jsonObject.toString());

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

   }