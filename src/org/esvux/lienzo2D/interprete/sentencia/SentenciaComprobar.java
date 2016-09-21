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
public class SentenciaComprobar extends Sentencia {

    public SentenciaComprobar(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        Resultado resultado = Resultado.ejecucionCorrecta();
        Object todosLosCasos[] = sentencia.getHijo(0).getHijos().toArray();
        Nodo hijoValor = new Nodo(Tipos.ID, Tipos.NULL, sentencia.getNombre(), "");
        int i = 0;
        boolean seCumplioAlguno = false;
        for (; i < todosLosCasos.length - 1; i++) {
            Nodo hijoCaso = (Nodo)todosLosCasos[i];
            Nodo hijoExpresion = hijoCaso.getHijo(0).getHijo(0);
            Nodo hijoRelacional = new Nodo(Tipos.RELACIONAL, Tipos.NULL, "==", "");
            Nodo hijoCondicion = new Nodo(Tipos.EXPRESION, Tipos.NULL, "expresion", "");
            hijoRelacional.addHijo(hijoValor);
            hijoRelacional.addHijo(hijoExpresion);
            hijoCondicion.addHijo(hijoRelacional);

            Resultado condicion = new Expresion(hijoCondicion, ctx).resolverExpresion();
            if (!Check.EsTipo(condicion.getTipo(), Tipos.T_BOOLEAN)) {
                manager.addErrorSemantico(hijoCondicion.getFila(), hijoCondicion.getColumna(),
                        "Se esperaba un valor booleano para la condición del CASO " + (i + 1) + " en la instrucción COMPROBAR.");
                return Resultado.ejecucionErronea();
            }

            if (seCumplioAlguno || Check.ToBooleano(condicion.getValor())) {
                seCumplioAlguno = true;
                SentenciaCuerpo cuerpo = new SentenciaCuerpo(hijoCaso.getHijo(1), true);
                resultado = cuerpo.ejecutar(ctx, nivel + 1);
                if (resultado.esSalir()) {
                    resultado = Resultado.ejecucionCorrecta();
                    break;
                }
                if (resultado.esRetorno()) {
                    ctx.limpiarContexto(nivel);
                    return resultado;
                }
                if (resultado.esContinuar()) {
                    manager.addErrorSemantico(hijoExpresion.getFila(), hijoExpresion.getColumna(),
                            "Sentencia 'Continuar$' fuera de lugar.");
                }
            }
        }
        if (!seCumplioAlguno) {
            Nodo hijoDefalut = (Nodo)todosLosCasos[i];
            Nodo cuerpoDefault = hijoDefalut.getHijo(0);
            SentenciaCuerpo cuerpo = new SentenciaCuerpo(cuerpoDefault, false);
            resultado = cuerpo.ejecutar(ctx, nivel + 1);
        }

        ctx.limpiarContexto(nivel);
        return resultado;
    }

}
