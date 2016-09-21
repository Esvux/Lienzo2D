package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import java.util.Iterator;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Elemento;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.expresion.Expresion;

/**
 *
 * @author esvux
 */
public class SentenciaRetorno extends Sentencia {

    public SentenciaRetorno(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        Nodo hijoExpresion = sentencia.getHijo(0);
        Nodo raiz = hijoExpresion.getHijo(0);
        if (Check.EsTipo(raiz.getTipo(), Tipos.ID)) {
            Elemento elemento = ctx.getVariable(raiz.getNombre());
            if (elemento == null) {
                elemento = Sentencia.contextoGlobal.getVariable(raiz.getNombre());
            }
            if (elemento == null) {
                manager.addErrorSemantico(hijoExpresion.getFila(), hijoExpresion.getColumna(),
                        "No existe la variable '" + raiz.getNombre() + "' que desea retornar.");
                return new Resultado();
            }
            //Se asume que el return va a ser de una sola variable
            Integer tipo = elemento.getTipo();
            String valor = elemento.getValor();
            Resultado resultado = new Resultado(tipo, valor);
            resultado.marcarComoRetorno();
            if (!elemento.getEsArreglo()) {
                return resultado;
            }
            //De lo contrario, el return es de un arreglo
            resultado.marcarComoArreglo();
            ArrayList<Resultado> soluciones = new ArrayList<>();
            Iterator<String> valores = elemento.getValores().iterator();
            while (valores.hasNext()) {
                String next = valores.next();
                Resultado temp = new Resultado(tipo, next);
                soluciones.add(temp);
            }
            resultado.setSoluciones(soluciones);
            resultado.setDimensiones(elemento.getDimensiones().size());
            return resultado;
        }
        
        //Cuando el resultado es una expresión
        Resultado solucion = new Expresion(hijoExpresion, ctx).resolverExpresion();
        solucion.marcarComoRetorno();
        return solucion;
    }

}
