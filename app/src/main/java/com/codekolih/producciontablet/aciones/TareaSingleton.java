package com.codekolih.producciontablet.aciones;


import com.codekolih.producciontablet.clases.Tareas;

import java.io.Serializable;

public class TareaSingleton implements Serializable {

    private Tareas tareaInstanciada;
    private static TareaSingleton tareaSingle;

    // El constructor es privado, no permite que se genere un constructor por defecto.
    public TareaSingleton(Tareas tarea) {
        this.tareaInstanciada = tarea;
        System.out.println("Mi nombre es: " + this.tareaInstanciada);
    }

    public static TareaSingleton SingletonInstance(Tareas tareaInstanciada) {
        if (tareaSingle == null){
            tareaSingle = new TareaSingleton(tareaInstanciada);
        }
        else{
            System.out.println("No se puede crear el objeto "+ tareaInstanciada.getDescripcion() + " porque ya existe un objeto de la clase");
        }

        return tareaSingle;
    }


    public Tareas getTareaInstanciada() {
        return tareaInstanciada;
    }

    public void setTareaInstanciada(Tareas tareaInstanciada) {
        this.tareaInstanciada = tareaInstanciada;
    }
}
