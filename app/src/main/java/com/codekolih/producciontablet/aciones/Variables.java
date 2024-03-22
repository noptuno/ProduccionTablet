package com.codekolih.producciontablet.aciones;

public class Variables {


    public static final String PREF_PRODUCCION_CONFIGURACION= "CONFIGURARPRODUCCION_1";
    public static final String PREF_PRODUCCION_MAQUINAID= "MAQUINAID";
    public static final String PREF_PRODUCCION_NOMBREMAQUINA= "NOMBREMAQUINA";
    public static final String PREF_PRODUCCION_MAQUINATIPOID= "MAQUINATIPOID";
    public static final String PREF_PRODUCCION_ELEGIRTAREA= "ELEGIRTAREA";
    public static final String PREF_PRODUCCION_USUARIO= "USUARUIO";
    public static final String PREF_SETSTRING= "SETSTRING";
    public static final String PREF_PRODUCCION_CAMBIOPRIORIDAD= "CAMBIOPRIORIDAD";


    public static final String PREF_PRODUCCION_IP= "IP";

    public static final String prnEtiquetas ="SIZE 101.2 mm, 70.1 mm\n" +
            "GAP 3 mm, 0 mm\n" +
            "DIRECTION 0,0\n" +
            "REFERENCE 0,0\n" +
            "OFFSET 0 mm\n" +
            "SET REWIND OFF\n" +
            "SET PEEL OFF\n" +
            "SET CUTTER OFF\n" +
            "SET PARTIAL_CUTTER OFF\n" +
            "SET TEAR ON\n" +
            "CLS\n" +
            "CODEPAGE 1252\n" +
            "TEXT 775,126,\"ROMAN.TTF\",90,1,24,\"~cod~\"\n" +
            "TEXT 546,26,\"ROMAN.TTF\",90,1,10,\"~des1~\"\n" +
            "TEXT 513,26,\"ROMAN.TTF\",90,1,10,\"~des2~\"\n" +
            "TEXT 480,26,\"ROMAN.TTF\",90,1,10,\"~des3~\"\n" +
            "TEXT 76,39,\"ROMAN.TTF\",90,1,10,\"~fec~\"\n" +
            "TEXT 77,177,\"ROMAN.TTF\",90,1,10,\"~hor~\"\n" +
            "TEXT 77,299,\"ROMAN.TTF\",90,1,10,\"~usu~\"\n" +
            "BARCODE 685,46,\"128M\",102,0,90,3,6,\"!104~cod~\"\n" +
            "BOX 32,9,98,533,3\n" +
            "BOX 432,9,569,533,3\n" +
            "TEXT 385,97,\"ROMAN.TTF\",90,1,10,\"Etiquetas por Rollo\"\n" +
            "TEXT 284,390,\"ROMAN.TTF\",90,1,18,\"~rxc~\"\n" +
            "TEXT 274,156,\"ROMAN.TTF\",90,1,10,\"Rollo por Caja\"\n" +
            "BOX 228,360,310,535,3\n" +
            "TEXT 395,390,\"ROMAN.TTF\",90,1,18,\"~exr~\"\n" +
            "BOX 332,360,413,535,3\n" +
            "PRINT ~cant~\n";


    public static final String prnRollos ="SIZE 101.2 mm, 70.1 mm\n" +
            "GAP 3 mm, 0 mm\n" +
            "DIRECTION 0,0\n" +
            "REFERENCE 0,0\n" +
            "OFFSET 0 mm\n" +
            "SET REWIND OFF\n" +
            "SET PEEL OFF\n" +
            "SET CUTTER OFF\n" +
            "SET PARTIAL_CUTTER OFF\n" +
            "SET TEAR ON\n" +
            "CLS\n" +
            "CODEPAGE 1252\n" +
            "TEXT 775,126,\"ROMAN.TTF\",90,1,24,\"~cod~\"\n" +
            "TEXT 546,26,\"ROMAN.TTF\",90,1,10,\"~des1~\"\n" +
            "TEXT 513,26,\"ROMAN.TTF\",90,1,10,\"~des2~\"\n" +
            "TEXT 480,26,\"ROMAN.TTF\",90,1,10,\"~des3~\"\n" +
            "TEXT 76,39,\"ROMAN.TTF\",90,1,10,\"~fec~\"\n" +
            "TEXT 77,177,\"ROMAN.TTF\",90,1,10,\"~hor~\"\n" +
            "TEXT 77,299,\"ROMAN.TTF\",90,1,10,\"~usu~\"\n" +
            "BARCODE 685,46,\"128M\",102,0,90,3,6,\"!104~cod~\"\n" +
            "BOX 32,9,98,533,3\n" +
            "BOX 432,9,569,533,3\n" +
            "TEXT 284,390,\"ROMAN.TTF\",90,1,18,\"~rxc~\"\n" +
            "TEXT 274,156,\"ROMAN.TTF\",90,1,10,\"Rollo por Caja\"\n" +
            "BOX 228,360,310,535,3\n" +
            "PRINT ~cant~\n";
    public static final String prnNumerados = "SIZE 101.2 mm, 70.1 mm\n" +
            "GAP 3 mm, 0 mm\n" +
            "DIRECTION 0,0\n" +
            "REFERENCE 0,0\n" +
            "OFFSET 0 mm\n" +
            "SET REWIND OFF\n" +
            "SET PEEL OFF\n" +
            "SET CUTTER OFF\n" +
            "SET PARTIAL_CUTTER OFF\n" +
            "SET TEAR ON\n" +
            "CLS\n" +
            "CODEPAGE 1252\n" +
            "TEXT 773,134,\"ROMAN.TTF\",90,1,24,\"~cod~\"\n" +
            "TEXT 544,34,\"ROMAN.TTF\",90,1,10,\"~des1~\"\n" +
            "TEXT 511,34,\"ROMAN.TTF\",90,1,10,\"~des2~\"\n" +
            "TEXT 478,34,\"ROMAN.TTF\",90,1,10,\"~des3~\"\n" +
            "TEXT 74,47,\"ROMAN.TTF\",90,1,10,\"~fec~\"\n" +
            "TEXT 75,185,\"ROMAN.TTF\",90,1,10,\"~hor~\"\n" +
            "TEXT 75,307,\"ROMAN.TTF\",90,1,10,\"~usu~\"\n" +
            "BARCODE 683,54,\"128M\",102,0,90,3,6,\"!104~cod~\"\n" +
            "BOX 30,17,96,541,3\n" +
            "BOX 430,17,567,541,3\n" +
            "TEXT 409,325,\"ROMAN.TTF\",90,1,10,\"Cantidad de rollos\"\n" +
            "BOX 275,17,412,272,3\n" +
            "TEXT 390,34,\"ROMAN.TTF\",90,1,10,\"De Nro\"\n" +
            "TEXT 346,376,\"ROMAN.TTF\",90,1,18,\"~can~\"\n" +
            "BOX 283,329,364,541,3\n" +
            "TEXT 390,182,\"ROMAN.TTF\",90,1,10,\"~den~\"\n" +
            "TEXT 330,34,\"ROMAN.TTF\",90,1,10,\"Al Nro\"\n" +
            "TEXT 330,182,\"ROMAN.TTF\",90,1,10,\"~aln~\"\n" +
            "TEXT 236,34,\"ROMAN.TTF\",90,1,10,\"Caja Nro\"\n" +
            "TEXT 173,34,\"ROMAN.TTF\",90,1,10,\"De\"\n" +
            "TEXT 236,182,\"ROMAN.TTF\",90,1,10,\"~caj~\"\n" +
            "TEXT 173,182,\"ROMAN.TTF\",90,1,10,\"~de~\"\n" +
            "BOX 118,17,255,272,3\n" +
            "TEXT 243,325,\"ROMAN.TTF\",90,1,10,\"Serie\"\n" +
            "BOX 120,326,201,541,3\n" +
            "TEXT 183,404,\"ROMAN.TTF\",90,1,18,\"~ser~\"\n" +
            "PRINT ~cant~\n";

}
