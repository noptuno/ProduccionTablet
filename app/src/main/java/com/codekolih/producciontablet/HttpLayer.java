package com.codekolih.producciontablet;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.aciones.GsonUtils;
import com.codekolih.producciontablet.aciones.Urls;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Material;
import com.codekolih.producciontablet.clases.Pedido;
import com.codekolih.producciontablet.clases.Proveedor;
import com.codekolih.producciontablet.clases.Tareas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpLayer {

    private final RequestQueue requestQueue;

    public HttpLayer(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getTareas(String params, HttpLayerResponses<List<Tareas>> listener) {

        String url = Urls.Tareas + params;

        Type listOfMyClassObject = new TypeToken<List<Tareas>>() {}.getType();

        StringRequest request = new StringRequest(
                GET,
                url,
                response -> listener.onSuccess(mapObject(response, listOfMyClassObject)),
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void getPedido(String params, HttpLayerResponses<Pedido> listener) {

        String url = Urls.obtenerpedido + params;

        StringRequest request = new StringRequest(
                GET,
                url,
                response -> listener.onSuccess(mapObject(response, Pedido.class)),
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


    public void cargarBobinas(Bobinas bobinas, HttpLayerResponses<JSONObject> listener,String USUARIO) {

        JSONObject jsonObject = GsonUtils.toJSON(bobinas);

        Log.e("enviobobina",""+jsonObject.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                POST,
                Urls.agregarbobinas + USUARIO,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

        public void listaProveedor(HttpLayerResponses<ArrayList<Proveedor>> listener) {

            String url = Urls.proveedor;

            Type listOfMyClassObject = new TypeToken<ArrayList<Proveedor>>() {}.getType();

            StringRequest request = new StringRequest(
                    GET,
                    url,
                    response -> listener.onSuccess(mapObject(response, listOfMyClassObject)),
                    listener::onError
            );

            request.setRetryPolicy(new DefaultRetryPolicy(6000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);

        }

    public void listaMateriales(HttpLayerResponses<ArrayList<Material>> listener) {

        String url = Urls.materiales;

        Type listOfMyClassObject = new TypeToken<ArrayList<Material>>() {}.getType();

        StringRequest request = new StringRequest(
                GET,
                url,
                response -> listener.onSuccess(mapObject(response, listOfMyClassObject)),
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void actualizarProduccion(JSONObject jsonObject, HttpLayerResponses<JSONObject> listener,String USUARIO) {

        JsonObjectRequest request = new JsonObjectRequest(
                PUT,
                Urls.UpdateProduccion+USUARIO,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void cargarEstado(JSONObject jsonObject, HttpLayerResponses<JSONObject> listener,String USUARIO) {

        JsonObjectRequest request = new JsonObjectRequest(
                POST,
                Urls.estadoOperativo+USUARIO,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


    public void altaproduccion(JSONObject jsonObject, HttpLayerResponses<JSONObject> listener,String USUARIO) {

        JsonObjectRequest request = new JsonObjectRequest(
                POST,
                Urls.altaproduccion + USUARIO,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    public void login(JSONObject jsonObject, HttpLayerResponses<JSONObject> listener) {


        JsonObjectRequest request = new JsonObjectRequest(
                POST,
                Urls.login,
                jsonObject,
                listener::onSuccess,
                listener::onError
        );

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
