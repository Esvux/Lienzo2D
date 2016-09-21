package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Metodo;
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
public class SentenciaLlamada extends Sentencia {

    public SentenciaLlamada(Nodo sentencia, boolean debug) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        ArrayList<Resultado> parametrosEvaluados = evaluarParametros(sentencia.getHijo(0), ctx);
        String params = getParametrosComoString(parametrosEvaluados);
        Metodo metodo = principal.getMetodo(sentencia.getNombre(), params);
        if (metodo == null) {
            manager.addErrorSemantico(sentencia.getFila(), sentencia.getColumna(),
                    "No existe, o no se tiene acceso, al método " + sentencia.getNombre() + "(" + params + ").");
            return Resultado.ejecucionErronea();
        }
        Nodo declaraciones = crearDeclaraciones(metodo.getParametros(), parametrosEvaluados);
        Contexto nuevo = new Contexto();
        Resultado decs = new SentenciaDeclaraciones(declaraciones).ejecutar(nuevo, nivel);
        if (!decs.esOk()) {
            manager.addErrorSemantico(sentencia.getFila(), sentencia.getColumna(),
                    "Error en la asignación de parámetros en la llamada al método '" + metodo.getNombre() + "'");
            return Resultado.ejecucionErronea();
        }
        SentenciaCuerpo cuerpoDelMetodo = new SentenciaCuerpo(metodo.getAcciones(), false);
        Resultado retorno = cuerpoDelMetodo.ejecutar(nuevo, nivel);

        if(!retorno.esRetorno()){
            return retorno;
        }
        
        if (retorno.esArreglo() != metodo.retornoEsArreglo()) {
            manager.addErrorSemantico(sentencia.getFila(), sentencia.getColumna(),
                    "La declaración del método " + metodo.getNombre() + "(" + metodo.getDetalleParametros() + ") no coincide "
                    + "con su retorno, unos es arreglo y el otro un valor puntual.");
            return new Resultado();
        }
        int tipo = (retorno.esArreglo()) ? retorno.getSoluciones().get(0).getTipo() : retorno.getTipo();
        if (! Check.EsTipo(metodo.getTipo(), tipo)){
            manager.addErrorSemantico(sentencia.getFila(), sentencia.getColumna(),
                    "El tipo del método " + metodo.getNombre() + "(" + metodo.getDetalleParametros() + ") no coincide con su retorno.");
            return new Resultado();
        }
        return retorno;
    }

    private Nodo crearDeclaraciones(Nodo parametros, ArrayList<Resultado> valores) {
        Nodo declaraciones = new Nodo(Tipos.DECLARACIONES, Tipos.NULL, "declaraciones", "");
        Object[] arrayParametros = parametros.getHijos().toArray();
        for (int i = 0; i < arrayParametros.length; i++) {
            Nodo id = (Nodo) arrayParametros[i];
            Nodo temp = new Nodo(Tipos.DECLARACION, Tipos.VARIABLE, "declaracion", "");
            Nodo expresion = new Nodo(Tipos.EXPRESION, Tipos.NULL, "expresion", "");
            expresion.addHijo(valores.get(i).getAsNodo());
            temp.addHijo(id);
            temp.addHijo(expresion);
            declaraciones.addHijo(temp);
        }
        return declaraciones;
    }

    private String getParametrosComoString(ArrayList<Resultado> parametros) {
        String respuesta = "", temporal = "";
        for (Resultado param : parametros) {
            respuesta = temporal + Tipos.getTipoAsString(param.getTipo());
            temporal += Tipos.getTipoAsString(param.getTipo()) + ",";
        }
        return respuesta;
    }

    private ArrayList<Resultado> evaluarParametros(Nodo paramLlamada, Contexto ctx) {
        ArrayList<Resultado> evaluados = new ArrayList<>();
        for (Nodo param : paramLlamada.getHijos()) {
            Resultado solucion = new Expresion(param, ctx).resolverExpresion();
            evaluados.add(solucion);
        }
        return evaluados;
    }

}
