package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekolih.producciontablet.activitys.Tarea_Activity;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.List;

public class RequestVolleySingleton {
        private static RequestVolleySingleton instance;
        private RequestQueue requestQueue;
        private ImageLoader imageLoader;
        private static Context ctx;

        private RequestVolleySingleton(Context context) {
            ctx = context;
            requestQueue = getRequestQueue();

/*
            imageLoader = new ImageLoader(requestQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
            */

        }

        public static synchronized RequestVolleySingleton getInstance(Context context) {
            if (instance == null) {
                instance = new RequestVolleySingleton(context);
            }
            return instance;
        }

        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }

        public ImageLoader getImageLoader() {
            return imageLoader;
        }

        /*
             int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
         */

        private ProgressHUD dialogProgress;

        public void request(String url,int tipo) {

            dialogProgress = ProgressHUD.show(ctx);

            StringRequest request = new StringRequest(
                    tipo,
                    url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Tarea Response",response);

                            List<Tareas> lista = GsonUtils.parseList(response, Tareas[].class);


                           // dialogProgress.dismiss();

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ctx, "Fallo", Toast.LENGTH_LONG).show();

                          //  dialogProgress.dismiss();

                        }
                    });
            request.setRetryPolicy(new DefaultRetryPolicy(1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            instance.addToRequestQueue(request);


        }

    }

