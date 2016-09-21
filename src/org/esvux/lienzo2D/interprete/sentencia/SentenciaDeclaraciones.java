package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Dimension;
import org.esvux.lienzo2D.interprete.Elemento;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.expresion.Expresion;

/**
 *
 * @author esvux
 */
public class SentenciaDeclaraciones extends Sentencia {

    public SentenciaDeclaraciones(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        if (sentencia.getHijos().isEmpty()) {
            return Resultado.ejecucionCorrecta();
        }
        if (Check.EsTipo(sentencia.getHijo(0).getTipo(), Tipos.ARREGLO)) {
            declaracionDeArreglos(ctx, nivel);
        } else {
            declaracionDeVariables(ctx, nivel);
        }
        return Resultado.ejecucionCorrecta();
    }

    public void setPermisoPrivado(boolean permiso) {
        this.permisoPrivado = permiso;
    }

    private void declaracionDeVariables(Contexto ctx, int nivel) {
        for (Nodo declaracion : sentencia.getHijos()) {
            //Resolviendo la expresión de la asignación
            Nodo expresion = declaracion.getHijo(declaracion.getHijos().size() - 1);
            Resultado valor = new Resultado();
            if (Check.EsTipo(expresion.getRol(), Tipos.EXPRESION)) {
                valor = new Expresion(expresion, ctx).resolverExpresion();
            }
            for (Nodo id : declaracion.getHijos()) {
                if (!Check.EsTipo(id.getRol(), Tipos.ID)) {
                    continue;
                }
                if (Check.EsTipo(id.getVisibilidad(), Tipos.PRIVADO) && !this.permisoPrivado) {
                    continue;
                }
                Elemento elemento = Elemento.createVariable(id.getNombre(), id.getTipo(), nivel, id.getVisibilidad());
                //El elemento ya existe, genera error
                if (ctx.existencia(elemento)) {
                    ManejadorErrores.getInstance().addErrorSemantico(id.getFila(), id.getColumna(), "Duplicación en declaración de la variable '" + elemento.getNombre() + "' para el contexto actual.");
                    continue;
                }
                //Se agrega el elemento con null en el atributo valor
                if (Check.EsTipo(valor.getTipo(), Tipos.T_ERROR)) {
                    ctx.agregarVariable(elemento);
                    continue;
                }
                //No se puede realizar la asignación porque no corresponden los tipos
                if (!elemento.realizarAsignacion(valor)) {
                    manager.addErrorSemantico(id.getFila(), id.getColumna(), "Imposible asignar un valor de tipo " + Tipos.getTipoAsString(valor.getTipo()).toLowerCase() + " a la variable '" + id.getNombre() + "'.");
                    continue;
                }
                //Se agrega el elemento con un valor ya asignado
                ctx.agregarVariable(elemento);
            }
        }
    }

    private void declaracionDeArreglos(Contexto ctx, int nivel) {
        Dimension dimensiones = new Dimension();
        Elemento elemento;
        ArrayList<Resultado> valores = new ArrayList<>();
        // Creando las dimensiones para el arreglo 
        Nodo declaracion = this.sentencia.getHijo(0);
        Nodo dims = declaracion.getHijo(declaracion.getHijos().size() - 2);
        if (Check.EsTipo(dims.getRol(), Tipos.DIMENSION)) {
            dimensiones = Sentencia.resolverDimension(dims, ctx);
        }
        // Existe una asignacion 
        Nodo asignacion = declaracion.getHijo(declaracion.getHijos().size() - 1);
        if (Check.EsTipo(asignacion.getRol(), Tipos.ASIGNACION)) {
            // generar valores 
            valores = Sentencia.resolverAsignacion(asignacion, ctx);
        }else if(Check.EsTipo(asignacion.getRol(), Tipos.EXPRESION)){
            Resultado resultado = new Expresion(asignacion, ctx).resolverExpresion();
            if(resultado.esArreglo()){
                valores = resultado.getSoluciones();
            }   
        }
        // Recorriendo los ID para crear el arreglo 
        for (Nodo temp : declaracion.getHijos()) {
            if (!Check.EsTipo(temp.getRol(), Tipos.ID)) {
                continue;
            }
            if (Check.EsTipo(temp.getVisibilidad(), Tipos.PRIVADO) && !this.permisoPrivado) {
                continue;
            }
            elemento = Elemento.createArreglo(temp.getNombre(), temp.getTipo(), nivel, temp.getVisibilidad());
            // Agregando las dimensiones al arreglo 
            for (Integer dimension : dimensiones.getDetalle()) {
                elemento.agregarDimension(dimension);
            }
            // Creando el espacio para colocar los valores del arreglo 
            elemento.generarValores(dimensiones.getCapacidad());
            if (ctx.existencia(elemento)) {
                ManejadorErrores.getInstance().addErrorSemantico(temp.getFila(), temp.getColumna(), "Duplicación en declaración de arreglo para el contexto actual");
                continue;
            }
            // Comprobar si se debe realizar una asignacion de valores 
            if (valores.size() > 0) {
                // Comprobar que el tamaño de los valores corresponda a los que se esperaban 
                if (valores.size() != elemento.gerSizeValores()) {
                    ManejadorErrores.getInstance().addErrorSemantico(temp.getFila(), temp.getColumna(), "La asignación para el arreglo no corresponde al número de posiciones");
                    ctx.agregarVariable(elemento);
                    continue;
                }
                Sentencia.asignarValores(temp.getTipo(), valores, elemento.getValores(), temp.getFila(), temp.getColumna());
            }
            ctx.agregarVariable(elemento);
        }
    }

    
}
