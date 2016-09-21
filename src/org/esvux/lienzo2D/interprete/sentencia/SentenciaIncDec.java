package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Elemento;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Aritmetica;
import org.esvux.lienzo2D.interprete.expresion.Check;

/**
 *
 * @author esvux
 */
public class SentenciaIncDec extends Sentencia {

    public SentenciaIncDec(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        Nodo target = sentencia.getHijo(0);
        if (Check.EsTipo(target.getRol(), Tipos.ID)) {
            return incDecVariable(ctx, sentencia);
        } else if (Check.EsTipo(target.getRol(), Tipos.ARREGLO)) {
            incDecArreglo(ctx, sentencia);
        }
        return Resultado.ejecucionCorrecta();
    }

    private Resultado incDecVariable(Contexto ctx, Nodo sentencia) {
        Nodo target = sentencia.getHijo(0);
        Elemento elemento = ctx.getVariable(target.getNombre());
        if (elemento == null) {
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "La variable '" + target.getNombre() + "' no existe en el contexto acutal.");
            return Resultado.ejecucionErronea();
        }
        if (elemento.getValor() == null) {
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "Variable '" + target.getNombre() + "' sin valor asignado previamente imposible incrementar/decrementar.");
            return Resultado.ejecucionErronea();
        }
        if (elemento.getEsArreglo()){
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "La variable "+ target.getNombre() +", es un arreglo, se debe especificar una posición para poder operar ++ o --");
            return Resultado.ejecucionErronea();
        }
        Resultado resultado = new Resultado(elemento.getTipo(), elemento.getValor());
        if(sentencia.getNombre().equalsIgnoreCase("++")){
            resultado = Aritmetica.aumento(resultado);
        }else{
            resultado = Aritmetica.decremento(resultado);
        }
        if(Check.EsTipo(resultado.getTipo(), Tipos.T_ERROR)){
            return Resultado.ejecucionErronea();
        }
        elemento.setValor(resultado.getValor());
        return Resultado.ejecucionCorrecta();
    }

    private Resultado incDecArreglo(Contexto ctx, Nodo sentencia) {
        Nodo target = sentencia.getHijo(0);
        Elemento elemento = ctx.getVariable(target.getNombre());
        if (elemento == null) {
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "La variable '" + target.getNombre() + "' no existe en el contexto acutal.");
            return Resultado.ejecucionErronea();
        }
        if (elemento.getValores().isEmpty()) {
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "Arreglo '" + target.getNombre() + "' sin valores asignados previamente imposible incrementar/decrementar.");
            return Resultado.ejecucionErronea();
        }
        Nodo dimension = target.getHijo(0);
        ArrayList<Resultado> detallePosicion=Sentencia.resolverDimensiones(dimension, ctx);
        Integer posicion = elemento.obtenerPosicion(detallePosicion);
        if(posicion<0 || posicion >=elemento.getCapacidad()){
            manager.addErrorSemantico(target.getFila(), target.getColumna(), "La posición '" + posicion + "' está fuera de contexto.");
            return Resultado.ejecucionErronea();
        }
        
        Resultado resultado = elemento.obtenerValorArrelgo(posicion);
        if(sentencia.getNombre().equalsIgnoreCase("++")){
            resultado = Aritmetica.aumento(resultado);
        }else{
            resultado = Aritmetica.decremento(resultado);
        }
        if(Check.EsTipo(resultado.getTipo(), Tipos.T_ERROR)){
            return Resultado.ejecucionErronea();
        }
        elemento.realizarAsignacionArreglo(resultado, posicion);
        return Resultado.ejecucionCorrecta();    
    }

}
