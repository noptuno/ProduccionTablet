package com.codekolih.producciontablet;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class HttpLayer {

    private final RequestQueue requestQueue;

    public HttpLayer(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }


    public void getTareas(HttpLayerResponses<List<Tareas>> listener) {

        String url = Urls.Tareas;

        Type listOfMyClassObject = new TypeToken<List<Tareas>>() {}.getType();

        StringRequest request = new StringRequest(
                GET,
                url,
                response -> listener.onSuccess(mapObject(response, listOfMyClassObject)),
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }



    public void getTareaEspecifica(String params, HttpLayerResponses<Tareas> listener) {

        String url = Urls.obtenertarea + params;

        Type objectType = new TypeToken<Tareas>() {}.getType();

        StringRequest request = new StringRequest(
                GET,
                url,
                response -> {
                    Tareas t = mapObject(response, objectType);
                    listener.onSuccess(t);
                },
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void cargarBobinas(Bobinas bobinas, HttpLayerResponses<JSONObject> listener) {

        JSONObject jsonObject = GsonUtils.toJSON(bobinas);
        JsonObjectRequest request = new JsonObjectRequest(
                POST,
                Urls.agregarbobinas,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void actualizarProduccion(JSONObject jsonObject, HttpLayerResponses<JSONObject> listener) {

        JsonObjectRequest request = new JsonObjectRequest(
                PUT,
                Urls.UpdateProduccion,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


    private <T> T mapObject(String response, Type clazz) {
        return new Gson().fromJson(response, clazz);
    }

    public interface HttpLayerResponses<T> {
        void onSuccess(T response);
        void onError(Exception e);
    }





}
