/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esvux.lienzo2D.interprete.expresion;

import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Resultado;

/**
 *
* @autor esvux
 */
public class Logica {
    
    public static Resultado not(Resultado izq){
       Resultado respuesta = new Resultado();
       respuesta.setTipo(Tipos.T_BOOLEAN);
       respuesta.setValor("true");
       if(Check.ToBooleano(izq.getValor())){
           respuesta.setValor("false");
       }
       return respuesta;
    }
    
    public static Resultado or(Resultado izq, Resultado der){
      Resultado respuesta = new Resultado();
      respuesta.setTipo(Tipos.T_BOOLEAN);
      respuesta.setValor("false");
      if(Check.ToBooleano(izq.getValor())||Check.ToBooleano(der.getValor())){
          respuesta.setValor("true");
      }
      return respuesta;
    }

    public static Resultado nor(Resultado izq, Resultado der){
      Resultado respuesta = new Resultado();
      respuesta.setTipo(Tipos.T_BOOLEAN);
      respuesta.setValor("true");
      if(Check.ToBooleano(izq.getValor()) || Check.ToBooleano(der.getValor())){
          respuesta.setValor("false");
      }
      return respuesta;       
    }

    public static Resultado and(Resultado izq, Resultado der){
      Resultado respuesta = new Resultado();
      respuesta.setTipo(Tipos.T_BOOLEAN);
      respuesta.setValor("false");
      if(Check.ToBooleano(izq.getValor()) && Check.ToBooleano(der.getValor())){
          respuesta.setValor("true");
      }
      return respuesta;
    }

    public static Resultado nand(Resultado izq, Resultado der){
      Resultado respuesta = new Resultado();
      respuesta.setTipo(Tipos.T_BOOLEAN);
      respuesta.setValor("true");
      if(Check.ToBooleano(izq.getValor()) && Check.ToBooleano(der.getValor())){
          respuesta.setValor("false");
      }
      return respuesta;    
    }
    
    public static Resultado xor(Resultado izq, Resultado der){
      Resultado respuesta = new Resultado();
      respuesta.setTipo(Tipos.T_BOOLEAN);
      respuesta.setValor("false");
      if(Check.ToBooleano(izq.getValor()) != Check.ToBooleano(der.getValor())){
          respuesta.setValor("true");
      }
      return respuesta;
    }
    
}
