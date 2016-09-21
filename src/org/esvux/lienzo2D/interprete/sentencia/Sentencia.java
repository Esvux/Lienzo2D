package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Lienzo;
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
public abstract class Sentencia {

    protected Nodo sentencia;
    protected ManejadorErrores manager;
    protected boolean permiteInterrupciones;
    protected boolean permisoPrivado;
    protected static boolean debug;
    protected static Contexto contextoGlobal;
    protected static Lienzo principal;
    protected static boolean pausa = false;
    protected static int tiempoPausa = 1000;
    
    public Sentencia (Nodo sentencia, boolean permiteInterrupciones) {
        this.sentencia = sentencia;
        this.manager = ManejadorErrores.getInstance();
        this.permiteInterrupciones = permiteInterrupciones;
        this.permisoPrivado = true;
    }
    
    public abstract Resultado ejecutar(Contexto ctx, int nivel);
    
    public static final void setContextoGlobal(Contexto ctxGlobal){
        contextoGlobal = ctxGlobal;
    }
    
    public static final Contexto getContextoGlobal(){
        return contextoGlobal;
    }
    
    public static final void triggerRunPause(){
        pausa =! pausa;
    }
    
    public static final void debugRun(){
        pausa = false;
    }
    
    public static final void setTiempoPausa(int pausa){
        tiempoPausa = pausa;
    }
    
    public static final void noDebug(){
        debug = false;
    }
    
    public static final void debug(){
        debug = true;
    }
    
    /**
     * Método que retorna un elemento si lo encuentra en el contexto.
     * @param ctx
     * @param variable
     * @return 
     */
    public static Elemento buscarEnContexto(Contexto ctx, Nodo variable){
        Elemento elemento = ctx.getVariable(variable.getNombre());
        if(elemento!=null){
            return elemento;
        }
        elemento = Sentencia.getContextoGlobal().getVariable(variable.getNombre());
        if(elemento==null){
            ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), variable.getNombre()+" no existe o no es accesible dentro del contexto ");
        }
        return elemento;
    }
    
    
    
    /**
     * Método que convierte un nodo "dimensiones" en un array de soluciones, 
     * útil para la creación de arreglos
     * @param nodo
     * @param ctx
     * @return 
     */
    public static ArrayList<Resultado> resolverDimensiones (Nodo nodo, Contexto ctx){
        ArrayList<Resultado> valores = new ArrayList<>();
        Expresion expresion;
        Resultado solucion;        
        if(Check.EsTipo(nodo.getRol(), Tipos.DIMENSION)){
            for (Nodo hijo: nodo.getHijos()){
                valores.addAll(resolverDimensiones(hijo, ctx));
            }
        }
        if(Check.EsTipo(nodo.getRol(), Tipos.EXPRESION)){
            expresion = new Expresion(nodo, ctx);
            solucion = expresion.resolverExpresion();
            if(!Check.EsTipo(solucion.getTipo(), Tipos.T_ENTERO)){
                ManejadorErrores.getInstance().addErrorSemantico(nodo.getFila(), nodo.getColumna(), "La dimensión requiere ser de tipo entero");
            }    
            valores.add(solucion);
        }
        return valores;
    }

    /**
     * Método que recibe un nodo "dimensiones" y luego retorna un objeto de tipo Dimensión
     * proporciona información importante para saber los detalles de las dimensiones y la
     * capacidad de un arreglo.
     * @param nodo
     * @param ctx
     * @return 
     */
    public static Dimension resolverDimension(Nodo nodo, Contexto ctx){
        Dimension dimensiones=new Dimension();
        Integer capacidad = 1;
        Resultado respuesta;
        Expresion expresion;
        for (Nodo temp : nodo.getHijos()) {
            expresion = new Expresion(temp, ctx);
            respuesta = expresion.resolverExpresion();
            if (!(Check.EsTipo(respuesta.getTipo(), Tipos.T_ENTERO)) || respuesta.getValor() == null) {
                ManejadorErrores.getInstance().addErrorSemantico(temp.getFila(), temp.getColumna(), "La dimension debe ser de tipo entero");
                return new Dimension();
            }
            dimensiones.addDetalle(Check.ToEntero(respuesta.getValor()));
            capacidad = capacidad * Check.ToEntero(respuesta.getValor());
            dimensiones.setCapacidad(capacidad);
        }
        return dimensiones;
    }      
 
    /**
     * Retorna un array de Resultados, recibe como parametros un nodo de tipo "Asignación"
     * y de la forma "{{},{}..}", resuelve cada expresión y retorna los valores listos para una
     * asignación de arreglos en una declaración
     * @param nodo
     * @param ctx
     * @return 
     */
    public static ArrayList<Resultado> resolverAsignacion (Nodo nodo, Contexto ctx){
        ArrayList<Resultado> valores = new ArrayList<>();
        Expresion expresion;
        Resultado solucion;
        if (Check.EsTipo(nodo.getRol(), Tipos.ASIGNACION)) {
            for (Nodo hijo : nodo.getHijos()) {
                valores.addAll(resolverAsignacion(hijo, ctx));
            }
        }
        if (Check.EsTipo(nodo.getRol(), Tipos.EXPRESION)) {
            expresion = new Expresion(nodo, ctx);
            solucion = expresion.resolverExpresion();
            valores.add(solucion);
        }
        return valores;
    }

    /**
     * Dado un arreglo, 
     * @param tipo
     * @param soluciones
     * @param valores
     * @param fila
     * @param columna 
     */
    public static void asignarValores(Integer tipo, ArrayList<Resultado> soluciones, ArrayList<String> valores, Integer fila, Integer columna){     
        if (Check.EsTipo(tipo,Tipos.T_ENTERO)){
            for (int i = 0; i<valores.size(); i++){
                if(Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_ENTERO)){
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_DOBLE)) {
                    valores.set(i, Check.DobleToEntero(Check.ToDoble(soluciones.get(i).getValor())));
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_BOOLEAN)) {
                    valores.set(i, Check.BooleanToEntero(soluciones.get(i).getValor()));
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_CARACTER)) {
                    valores.set(i, Check.CaracterToEntero(soluciones.get(i).getValor()));
                    continue;
                }
                // Reportar error 
                ManejadorErrores.getInstance().addErrorSemantico(fila, columna, "No se puede realizar la asignación al arreglo en la posición " + i + " verifique la expresión ");
            }
            return;
        }

        if (Check.EsTipo(tipo, Tipos.T_DOBLE)) {
            for (int i = 0; i < valores.size(); i++) {
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_ENTERO)) {
                    valores.set(i, "" + Check.ToDoble(soluciones.get(i).getValor()));
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_DOBLE)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                // Reportar error 
                ManejadorErrores.getInstance().addErrorSemantico(fila, columna, "No se puede realizar la asignación al arreglo en la posición " + i + " verifique la expresión ");
            }
            return;
        }

        if (Check.EsTipo(tipo, Tipos.T_BOOLEAN)) {
            for (int i = 0; i < valores.size(); i++) {
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_BOOLEAN)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                // Reportar error 
                ManejadorErrores.getInstance().addErrorSemantico(fila, columna, "No se puede realizar la asignación al arreglo en la posición " + i + " verifique la expresión ");
            }
            return;
        }

        if (Check.EsTipo(tipo, Tipos.T_CARACTER)) {
            for (int i = 0; i < valores.size(); i++) {
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_ENTERO)) {
                    valores.set(i, "" + (char) Check.ToEntero(soluciones.get(i).getValor()));
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_CARACTER)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                // Reportar error 
                ManejadorErrores.getInstance().addErrorSemantico(fila, columna, "No se puede realizar la asignación al arreglo en la posición " + i + " verifique la expresión ");
            }
            return;
        }

        if (Check.EsTipo(tipo, Tipos.T_CADENA)) {
            for (int i = 0; i < valores.size(); i++) {
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_ENTERO)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_DOBLE)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_BOOLEAN)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_CARACTER)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                if (Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_CADENA)) {
                    valores.set(i, soluciones.get(i).getValor());
                    continue;
                }
                // Reportar error 
                ManejadorErrores.getInstance().addErrorSemantico(fila, columna, "No se puede realizar la asignación al arreglo en la posición " + i + " verifique la expresión ");
            }
        }

    }


  
    public static final void setPrincipal(Lienzo lienzo) {
        principal = lienzo;
    }
    
    public static final Lienzo getPrincipal(){
        return principal;
    }
    
}
