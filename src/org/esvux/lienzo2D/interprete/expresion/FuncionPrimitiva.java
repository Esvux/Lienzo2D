/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esvux.lienzo2D.interprete.expresion;

import java.util.ArrayList;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Resultado;

/**
 *
* @autor esvux
 */
public class FuncionPrimitiva {
    
    public static Resultado ordenarPares(Integer tipo, ArrayList<String> valores){
        ArrayList<String> pares = new ArrayList<>();
        ArrayList<String> impares = new ArrayList<>();
        for(int i=0; i<valores.size(); i++){
            if(Check.EsPar(tipo, valores.get(i))){
                pares.add(valores.get(i));
            }else{
                impares.add(valores.get(i));
            }
        }
        valores.clear();
        valores.addAll(pares);
        valores.addAll(impares);
        return new Resultado(Tipos.T_ENTERO, "1");
    }

    public static Resultado ordenarImpares(Integer tipo, ArrayList<String> valores){
        ArrayList<String> pares = new ArrayList<>();
        ArrayList<String> impares = new ArrayList<>();
        for(int i=0; i<valores.size(); i++){
            if(Check.EsPar(tipo, valores.get(i))){
                pares.add(valores.get(i));
            }else{
                impares.add(valores.get(i));
            }
        }
        valores.clear();
        valores.addAll(impares);
        valores.addAll(pares);
        return new Resultado(Tipos.T_ENTERO, "1");
    }

    public static Resultado ordenarPrimos(Integer tipo, ArrayList<String> valores){
        ArrayList<String> primos = new ArrayList<>();
        ArrayList<String> compuestos = new ArrayList<>();
        for(int i=0; i<valores.size(); i++){
            if(Check.EsPrimo(tipo, valores.get(i))){
                primos.add(valores.get(i));
            }else{
                compuestos.add(valores.get(i));
            }
        }
        valores.clear();
        valores.addAll(primos);
        valores.addAll(compuestos);
        return new Resultado(Tipos.T_ENTERO, "1");
    }
  
    public static Resultado sumarizar(ArrayList<Resultado> Resultadoes){
        Resultado respuesta = new Resultado();
        if(Resultadoes.isEmpty()){
            return respuesta;
        }
        respuesta = Resultadoes.get(0);
        for(int i = 1; i<Resultadoes.size(); i++){
            respuesta = Aritmetica.suma(respuesta, Resultadoes.get(i));
        }
        return respuesta;
    }
}
