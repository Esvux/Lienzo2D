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
public class SentenciaSi extends Sentencia {

    public SentenciaSi(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        //------------------------------------------- EVALUACIÓN DE LA CONDICIÓN
        Nodo hijoCondicion = sentencia.getHijo(0);
        Resultado condicion = new Expresion(hijoCondicion, ctx).resolverExpresion();
        if (!Check.EsTipo(condicion.getTipo(), Tipos.T_BOOLEAN)) {
            manager.addErrorSemantico(hijoCondicion.getFila(), hijoCondicion.getColumna(),
                    "Se esperaba un valor booleano para la condición de la instrucción SI.");
            return Resultado.ejecucionErronea();
        }

        Resultado resultado;
        //--------------------------------------------------- EJECUCIÓN DEL THEN
        if (Check.ToBooleano(condicion.getValor())) {
            Nodo hijoThen = sentencia.getHijo(1);
            SentenciaCuerpo cuerpoThen = new SentenciaCuerpo(hijoThen, this.permiteInterrupciones);
            resultado = cuerpoThen.ejecutar(ctx, nivel + 1);
        } //------------------------------------------------- EJECUCIÓN DEL ELSE
        else {
            Nodo hijoElse = sentencia.getHijo(2);
            SentenciaCuerpo cuerpoElse = new SentenciaCuerpo(hijoElse, this.permiteInterrupciones);
            resultado = cuerpoElse.ejecutar(ctx, nivel + 1);
        }

        //------------------------------------------------ LIMPIEZA DEL CONTEXTO
        ctx.limpiarContexto(nivel);
        return resultado;
    }

}
