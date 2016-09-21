package org.esvux.lienzo2D.interprete;

import java.util.Iterator;
import java.util.TreeSet;
import org.esvux.lienzo2D.AST.Lienzo;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.sentencia.SentenciaDeclaraciones;

/**
 *
 * @author esvux
 */
public class Contexto {
    
    private TreeSet<Elemento> variables;

    public Contexto() {
        this.variables = new TreeSet<>();
    }
    
    public TreeSet<Elemento> getVariables() {
        return variables;
    }

    public void setVariables(TreeSet<Elemento> variables) {
        this.variables = variables;
    }

    
    /**
     * Método que agrega una variable al contexto, 
     * la variable pertenece al cuerpo del contexto actual.
     * @param variable 
     */
    public void agregarVariable(Elemento variable){
        System.out.println("Agregando al contexto: " + variable.getNombre());
        System.out.println("              De tipo: "+Tipos.getTipoAsString(variable.getTipo()));
        System.out.println("         Con valor de: " + variable.getValor() + "\n");
        this.variables.add(variable);
    }

    public Elemento getVariable(String nombre) {
        Elemento global = null, local = null;
        for (Elemento var : this.variables) {
            if (var.getNombre().compareTo(nombre) == 0) {
                if (var.isGlobal()) {
                    global = var;
                } else {
                    local = var;
                }
            }
        }
        return (local == null) ? global : local;
    }

    
    public boolean existencia(Elemento elemento){
        for(Elemento var:this.getVariables()){
            if (var.compareTo(elemento)==0){
                return true;
            }
        }
        return false;
    }
    
    public void limpiarContexto(int nivel){
        Iterator<Elemento> it = variables.iterator();
        TreeSet<Elemento> limpias = new TreeSet<>();
        while (it.hasNext()) {
            Elemento next = it.next();
            if(next.getNivel().compareTo(nivel) != 0){
                limpias.add(next);
            }
        }
        variables.clear();
        variables.addAll(limpias);
    }

    public void agregarAlContexto(Lienzo lienzo, boolean permisoPrivado){
        if(Check.EsTipo(lienzo.getVisibilidad(), Tipos.PRIVADO) && !permisoPrivado){
            return;
        }

        Nodo declaraciones = new Nodo(Tipos.DECLARACIONES, Tipos.NULL, "declaraciones", "");
        declaraciones.setHijos(lienzo.getDeclaraciones());
        SentenciaDeclaraciones declarar = new SentenciaDeclaraciones(declaraciones);
        declarar.setPermisoPrivado(permisoPrivado);
        declarar.ejecutar(this, 0);
    }
    
    

}
