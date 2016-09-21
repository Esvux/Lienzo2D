package org.esvux.lienzo2D.compilador;

/**
 *
* @autor esvux
 */
public class Tipos {
    
    public static String RAIZ = "/home/sarina/NetBeansProjects/Lienzos2D_200915291/src/org/sarina20lives/recursos/";

    /**
     * Definición de Visualización
     */
    public static final Integer PUBLICO                             = 0;
    public static final Integer PRIVADO                             = 1;
    public static final Integer PROTEGIDO                           = 2;
    public static final Integer GLOBAL                              = 3;
    public static final Integer LOCAL                               = 4;
    public static final String VISIBILIDAD[] = {"Publico","Privado","Protegido","Global","Local"};
    public static String getVisibilidadAsString(int i){
        return (i<0 || i>4) ? "" : VISIBILIDAD[i];
    }

    /** 
     * Definición de Tipos de datos
     */ 
    public static final Integer NULL                               = -1;
    public static final Integer T_BOOLEAN                          =  0;
    public static final Integer T_ENTERO                           =  1;
    public static final Integer T_DOBLE                            =  2;
    public static final Integer T_CARACTER                         =  3;
    public static final Integer T_CADENA                           =  4;
    public static final Integer T_ERROR                            =  5;
    public static final Integer T_VOID                             =  6;
    public static final Integer T_ARREGLO                          =  7;
    public static final String TIPO[] = {"Boolean","Entero","Doble","Caracter","Cadena","Error","Void", "Array"};
    public static String getTipoAsString(int i){
        return (i<0 || i>7) ? "" : TIPO[i];
    }
    
    
    /**
     * Definición de tokens o valores
     */
    public static final Integer FALSE                              = 0;
    public static final Integer TRUE                               = 1;
    public static final Integer ENTERO                             = 2;
    public static final Integer DOBLE                              = 3;
    public static final Integer CARACTER                           = 4;
    public static final Integer CADENA                             = 5;
    public static final Integer ID                                 = 6;
    
    
    /**
     * Definición de Roles..
     */

    //Operaciones
    public static final Integer EXPRESION                           = 7;
    public static final Integer LOGICA                              = 8;
    public static final Integer RELACIONAL                          = 9; 
    public static final Integer ARITMETICA                          = 10;

    
    // Flujos
    public static final Integer FLUJO_SI                           = 11;
    public static final Integer FLUJO_COMPROBAR                    = 12;
    public static final Integer CASOS                              = 13;
    public static final Integer CASO                               = 14;
    public static final Integer DEFECTO                            = 15;
    
    //Bucles
    public static final Integer BUCLE_PARA                         = 16;
    public static final Integer BUCLE_MIENTRAS                     = 17;
    public static final Integer BUCLE_HACER                        = 18;
    
    //Escapes
    public static final Integer SALIR                              = 19;
    public static final Integer CONTINUAR                          = 20;
        
    //Otros
    public static final Integer PRINCIPAL                           = 21;
    public static final Integer METODO                              = 22;
    public static final Integer VARIABLE                            = 23;
    public static final Integer ARREGLO                             = 24;
    public static final Integer ASIGNACION                          = 25;
    public static final Integer DIMENSION                           = 26;
    public static final Integer PARAM_CREACION                      = 27;
    public static final Integer PARAM_LLAMADA                       = 28;
    public static final Integer RETORNA                             = 29;
    public static final Integer LLAMADA                             = 30;
    public static final Integer ACCIONES                            = 31;
    public static final Integer DECLARACION                         = 32;
    public static final Integer DECLARACIONES                       = 33;
    public static final Integer LIENZO                              = 34;
    
    //Funciones primitivas
    public static final Integer ORDENAR                            = 35;
    public static final Integer SUMARIZAR                          = 36;
    
    //Procedimientos primitivos
    public static final Integer PINTAR_P                           = 37;
    public static final Integer PINTAR_OR                          = 38;
    public static final Integer PINTAR_S                           = 39;
    public static final Integer PAUSA                              = 40;
    public static String ROL[] = {
        "false", "true", "entero", "doble", "caracter", "cadena", "id", 
        "expresion", "logica", "relacional", "aritmetica", 
        "flujo_si", "flujo_comparar", "casos", "caso", "defecto", 
        "bucle_para", "bucle_mientras", "blucle_hacer", "salir", "continuar",
        "principal", "metodo", "variable", "arreglo", "asignacion", "dimension", 
        "param_creacion", "param_llamada", "retorna", "llamada", "acciones",
        "declaracion", "declaraciones", "lienzo", "ordenar", "sumarizar", "pintar_p", 
        "pintar_or", "pintar_s", "pausa"
    };
    public static String getRolAsString(int i){
        return (i<0 || i>40) ? "" : ROL[i];
    }
        
}
