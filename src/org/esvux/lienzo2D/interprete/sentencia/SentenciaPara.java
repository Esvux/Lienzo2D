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
public class SentenciaPara extends Sentencia {

    public SentenciaPara(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        //------------------------------------- ASIGNACION O DECLARACION INICIAL
        Nodo hijoDecAsigna = sentencia.getHijo(0);
        //Se realiza la declaración inicial
        if (Check.EsTipo(hijoDecAsigna.getRol(), Tipos.DECLARACION)) {
            Nodo tempDeclaraciones = new Nodo(Tipos.DECLARACIONES, Tipos.NULL, "declaraciones", "");
            tempDeclaraciones.addHijo(hijoDecAsigna);
            new SentenciaDeclaraciones(tempDeclaraciones).ejecutar(ctx, nivel);
        } //O se realiza la asignación inicial
        else if (Check.EsTipo(hijoDecAsigna.getRol(), Tipos.ASIGNACION)) {
            new SentenciaAsignacion(hijoDecAsigna).ejecutar(ctx, nivel);
        }

        //---------------------------------------------- CUERPO DEL CICLO 'PARA'
        Resultado resultado = Resultado.ejecucionCorrecta();
        while (true) {
            //--------------------------------------- EVALUACION DE LA CONDICION
            Nodo hijoCondicion = sentencia.getHijo(1);
            Resultado condicion = new Expresion(hijoCondicion, ctx).resolverExpresion();
            if (!Check.EsTipo(condicion.getTipo(), Tipos.T_BOOLEAN)) {
                manager.addErrorSemantico(hijoCondicion.getFila(), hijoCondicion.getColumna(),
                        "Se esperaba un valor booleano para la condición de la instrucción PARA.");
                return Resultado.ejecucionErronea();
            }
            boolean seCumple = Check.ToBooleano(condicion.getValor());
            if (!seCumple) {
                break;
            }

            //----------------------------------- EJECUCION DEL CUERPO DEL CICLO
            Nodo hijoCuerpo = sentencia.getHijo(3);
            SentenciaCuerpo cuerpoPara = new SentenciaCuerpo(hijoCuerpo, true);
            resultado = cuerpoPara.ejecutar(ctx, nivel + 1);

            //------------------------------------ OCURRIÓ UN BREAK EN EL CUERPO
            if (resultado.esSalir()) {
                resultado = Resultado.ejecucionCorrecta();
                break;
            }

            //-------------------------------------- HUBO UN RETURN EN EL CUERPO
            if (resultado.esRetorno()) {
                break;
            }

            //-------------------------- SENTENCIA PREVIA A LA PROXIMA ITERACION
            Nodo hijoExtra = sentencia.getHijo(2);
            if (Check.EsTipo(hijoExtra.getHijo(0).getRol(), Tipos.ARITMETICA)) {
                new SentenciaIncDec(hijoExtra.getHijo(0)).ejecutar(ctx, nivel);
            } else if (Check.EsTipo(hijoExtra.getHijo(0).getRol(), Tipos.ASIGNACION)) {
                new SentenciaAsignacion(hijoExtra.getHijo(0)).ejecutar(ctx, nivel);
            }
            ctx.limpiarContexto(nivel + 1);
        }

        //------------------------------------------------ LIMPIEZA DEL CONTEXTO
        ctx.limpiarContexto(nivel);
        return resultado;
    }

}
