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
public class SentenciaMientras extends Sentencia {

    public SentenciaMientras(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        //------------------------------------------ CUERPO DEL CICLO 'MIENTRAS'
        Resultado resultado = Resultado.ejecucionCorrecta();
        while (true) {
            //--------------------------------------- EVALUACION DE LA CONDICION
            Nodo hijoCondicion = sentencia.getHijo(0);
            Resultado condicion = new Expresion(hijoCondicion, ctx).resolverExpresion();
            if (!Check.EsTipo(condicion.getTipo(), Tipos.T_BOOLEAN)) {
                manager.addErrorSemantico(hijoCondicion.getFila(), hijoCondicion.getColumna(),
                        "Se esperaba un valor booleano para la condición de la instrucción SI.");
                return Resultado.ejecucionErronea();
            }
            boolean seCumple = Check.ToBooleano(condicion.getValor());
            
            //------------------------------------------------- SALIDA DEL CICLO
            if (!seCumple) {
                break;
            }

            //----------------------------------- EJECUCION DEL CUERPO DEL CICLO
            Nodo hijoCuerpo = sentencia.getHijo(1);
            SentenciaCuerpo cuerpoWhile = new SentenciaCuerpo(hijoCuerpo, true);
            resultado = cuerpoWhile.ejecutar(ctx, nivel + 1);

            //------------------------------------ OCURRIÓ UN BREAK EN EL CUERPO
            if (resultado.esSalir()) {
                resultado = Resultado.ejecucionCorrecta();
                break;
            }

            //-------------------------------------- HUBO UN RETURN EN EL CUERPO
            if (resultado.esRetorno()) {
                break;
            }
        }
        
        //------------------------------------------------ LIMPIEZA DEL CONTEXTO
        ctx.limpiarContexto(nivel);
        return resultado;
    }

}
