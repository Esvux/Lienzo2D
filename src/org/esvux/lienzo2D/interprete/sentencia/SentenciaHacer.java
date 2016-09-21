package org.esvux.lienzo2D.interprete.sentencia;

import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Resultado;

/**
 *
 * @author esvux
 */
public class SentenciaHacer extends Sentencia {

    public SentenciaHacer(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        //-------------------------------- PRIMERA ITERACIÓN, LIBRE DE CONDICIÓN
        Nodo hijoCuerpo = sentencia.getHijo(1);
        SentenciaCuerpo cuerpoHacer = new SentenciaCuerpo(hijoCuerpo, true);
        Resultado resultado = cuerpoHacer.ejecutar(ctx, nivel + 1);
        
        //---------------------------------------- OCURRIÓ UN BREAK EN EL CUERPO
        if (resultado.esSalir()) {
            return Resultado.ejecucionCorrecta();
        }

        //------------------------------------------ HUBO UN RETURN EN EL CUERPO
        if (resultado.esRetorno()) {
            return resultado;
        }

        //------------------------------------------- EJECUCIÓN DEL WHILE NORMAL
        SentenciaMientras mientras = new SentenciaMientras(sentencia, true);
        resultado = mientras.ejecutar(ctx, nivel);

        //------------------------------------------------ LIMPIEZA DEL CONTEXTO
        ctx.limpiarContexto(nivel);
        return resultado;
    }

}
