/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esvux.lienzo2D.interprete.expresion;

import org.esvux.lienzo2D.compilador.Tipos;

/**
 *
* @autor esvux
 */
public class Check {
    
    public static boolean EsTipo(Integer tipo, Integer requerido){
        if(tipo.compareTo(requerido)==0){
            return true;
        }
        return false;    
    }

    public static boolean EsNegativo (Integer tipo, String valor){
        if (EsTipo(tipo, Tipos.T_ENTERO) && ((Integer)(ToEntero(valor))).compareTo(0)<0){
            return true;
        }
        if(EsTipo(tipo, Tipos.T_DOBLE) && ((Double) ToDoble(valor)).compareTo(0.0)<0){
            return true;
        }
        return false;
    }
    
    public static boolean EsError(Integer tipo){
        // Comprobando los tipos, si es Error o Void no se puede operar 
        if(EsTipo(tipo, Tipos.T_ERROR)){
           return true; 
        }
        if(EsTipo(tipo, Tipos.T_VOID)){
           return true;
        }
        return false;
    }
    
    public static String BooleanToEntero(String valor){
        if(valor.equals("false")){
            return "0";
        }
        return "1";
    }

    public static String CaracterToEntero(String valor){
        char caracter=valor.charAt(0);
        return ""+(int)caracter;
    }

    public static boolean EsCero(Integer tipo, String valor){
        if(EsTipo(tipo, Tipos.T_ENTERO) && valor.equals("0")){
            return true;
        }
        if(EsTipo(tipo, Tipos.T_DOBLE) && valor.equals("0.0")){
            return true;
        }
        if(EsTipo(tipo, Tipos.T_BOOLEAN) && BooleanToEntero(valor).equals("0")){
            return true;
        }
        if(EsTipo(tipo, Tipos.T_CARACTER) && CaracterToEntero(valor).equals("0")){
            return true;
        }
        return false;
    }
    
    public static boolean ToBooleano(String valor){
        if(valor.equals("false") || valor.equals("0")){
            return false;
        }else{
            return true;
        }
    }
    
    public static String DobleToEntero(Double valor){
        int resultado = valor.intValue();
        if (resultado >= 2147483647 || resultado <= -2147483647){
            return ""+resultado;
        }
        resultado = (int)Math.round(valor);
        return ""+resultado;        
    }
    
    public static int ToEntero(String valor){
        if(valor.contains(".")){
            Double doble = Double.parseDouble(valor);
            return doble.intValue();
        }
        return Integer.parseInt(valor);        
    }
    
    public static double ToDoble (String valor){
        if(valor.equals("true")||valor.equals("false")){
           return Double.parseDouble(BooleanToEntero(valor));
        }
        return Double.parseDouble(valor);
    }
    
    public static String EsMayor(Double val1, Double val2){
        if(val1.compareTo(val2)>0){
            return "true";
        }
        return "false";
    }

    public static String EsMenor(Double val1, Double val2){
        if(val1.compareTo(val2)<0){
            return "true";
        }
        return "false";
    }

    public static String EsMayorIgual(Double val1, Double val2){
        if(val1.compareTo(val2)>=0){
            return "true";
        }
        return "false";
    }
    
    public static String EsMenorIgual(Double val1, Double val2){
        if(val1.compareTo(val2)<=0){
            return "true";
        }
        return "false";
    }
    
    public static boolean EsPar(Integer tipo, String valor){
        if(EsTipo(tipo, Tipos.T_ENTERO)){
            if(ToEntero(valor)%2==0){
                return true;
            }
        }
        if(EsTipo(tipo, Tipos.T_DOBLE)){
            if(ToEntero(DobleToEntero(ToDoble(valor)))%2==0){
                return true;
            }            
        }
        if(EsTipo(tipo, Tipos.T_CARACTER)){
            if(ToEntero(CaracterToEntero(valor))%2==0){
                return true;
            }            
        }
        return false;
    }
    
    public static boolean EsImpar(Integer tipo, String valor){
        return !EsPar(tipo, valor);
    }

    public static boolean EsPrimo(Integer tipo, String valor){
        if(EsPar(tipo, valor)){
            return false;
        }
        Integer numero=null;
        if(EsTipo(tipo, Tipos.T_ENTERO)){
            numero = ToEntero(valor);
        }
        if(EsTipo(tipo, Tipos.T_DOBLE)){
            numero = ToEntero(DobleToEntero(ToDoble(valor)));        
        }
        if(EsTipo(tipo, Tipos.T_CARACTER)){
            numero =  ToEntero(CaracterToEntero(valor));
        }
        if(numero <= 1){
            return false;
        }
        for (int divisor=3; divisor<numero; divisor+=2){
            if(numero % divisor == 0){
                return false;
            }
        }      
        return true;
    }
    
}
















