package com.codekolih.producciontablet.aciones;

import android.util.Log;

import com.codekolih.producciontablet.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AccionClasses {

    private static AccionClasses AccionesSingleton;
    private Map<String, String> maquina = new HashMap<>();

    public AccionClasses() {


    }

    public static AccionClasses SingletonInstance() {
        if (AccionesSingleton == null){
            AccionesSingleton = new AccionClasses();
        }
        else{
            System.out.println("No se puede crear otro AcconClase ");
        }
        return AccionesSingleton;

    }

    public static void PrintMesajeLog(String a, String b){
        Log.e(a,b);
    }


}
