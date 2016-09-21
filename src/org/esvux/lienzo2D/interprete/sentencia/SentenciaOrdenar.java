package org.esvux.lienzo2D.interprete.sentencia;

import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.expresion.Expresion;

/**
 *
 * @author esvux
 */
public class SentenciaOrdenar extends Sentencia {

    public SentenciaOrdenar(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        if(!Check.EsTipo(sentencia.getRol(), Tipos.ORDENAR)){
            return Resultado.ejecucionErronea();
        }
        Resultado resultado = new Expresion(sentencia, ctx).resolverExpresion();
        return resultado;
    }
    
}
