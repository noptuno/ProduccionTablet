package com.codekolih.producciontablet.aciones;


import android.content.Context;
import android.text.TextUtils;

import com.codekolih.producciontablet.clases.Tareas;

import java.io.Serializable;

public class TareaSingleton {

    private static TareaSingleton tareaSingle;
    private Tareas tareaInstanciada;
    private float produccionId;

    private TareaSingleton() {
    }

    public static TareaSingleton SingletonInstance() {
        if (tareaSingle == null){
            tareaSingle = new TareaSingleton();
        }
        else{
            System.out.println("No se puede crear el objeto ");
        }
        return tareaSingle;

    }

    public float getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(float produccionId) {
        this.produccionId = produccionId;
    }



    public Tareas setTarea(Tareas tarea) {
        if (this.tareaInstanciada == null) {
            this.tareaInstanciada = tarea;
        }
        return this.tareaInstanciada;
    }

    public Tareas getTarea(){

        if (tareaInstanciada==null){
            tareaInstanciada = null;
        }

        return this.tareaInstanciada;
    }



}
