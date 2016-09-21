package org.esvux.lienzo2D.AST;

import java.util.Comparator;
import org.esvux.lienzo2D.compilador.Tipos;

/**
 *
 * @author esvux
 */
public class Metodo implements Comparable<Metodo>, Comparator<Metodo> {

    private String nombre;
    private String detalleParametros;
    private Integer tipo;
    private Integer visibilidad;
    private Nodo parametros;
    private Nodo acciones;
    private Boolean conservar;
    private Integer padre;
    private Integer nivel;
    private Integer correlativo;
    private String clase;
    private Integer fila;
    private Integer columna;
    private String dimensiones;

    public Metodo(String nombre, String detalleParametros, Nodo parametros, Integer tipo, Integer visibilidad, Nodo acciones, Boolean conservar, Integer padre, Integer nivel, Integer correlativo, String clase, Integer fila, Integer columna) {
        this.nombre = nombre;
        this.detalleParametros = detalleParametros;
        this.parametros = parametros;
        this.tipo = tipo;
        this.visibilidad = visibilidad;
        this.acciones = acciones;
        this.conservar = conservar;
        this.padre = padre;
        this.nivel = nivel;
        this.correlativo = correlativo;
        this.clase = clase;
        this.fila = fila;
        this.columna = columna;
    }

    public static Metodo crearMetodo(Nodo nodo) {
        Metodo metodo = new Metodo(nodo.getNombre(), parametrosToString(nodo.getHijo(0)), nodo.getHijo(0), nodo.getTipo(), nodo.getVisibilidad(),
                nodo.getHijo(1), nodo.getConservar(), nodo.getPadre(), nodo.getNivel(), nodo.getCorrelativo(), "",
                nodo.getFila(), nodo.getColumna());
        metodo.setDimensiones(nodo.getValor());
        return metodo;
    }

    public static Metodo crearPrincipal(Nodo nodo){
        Metodo metodo=new Metodo(nodo.getNombre(),"", nodo.getHijo(0), nodo.getTipo(), nodo.getVisibilidad(), 
                nodo.getHijo(1), nodo.getConservar(), nodo.getPadre(), nodo.getNivel(), nodo.getCorrelativo(), "",
                nodo.getFila(), nodo.getColumna());
        metodo.setDimensiones("0");
        return metodo;
    }

    public static String parametrosToString(Nodo nodo) {
        String temp = "";
        String parametros = "";
        for (Nodo param : nodo.getHijos()) {
            parametros = temp + Tipos.getTipoAsString(param.getTipo());
            temp = parametros + ",";
        }
        return parametros;
    }

    public String getLLave() {
        return this.nombre + ":" + this.detalleParametros;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }


    @Override
    public int compareTo(Metodo o) {
        int compNombre = this.getNombre().compareTo(o.getNombre());
        if (compNombre != 0) {
            return compNombre;
        }
        return this.getDetalleParametros().compareTo(o.getDetalleParametros());
    }

    @Override
    public int compare(Metodo o1, Metodo o2) {
        return o1.compareTo(o2);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nodo getParametros() {
        return parametros;
    }

    public void setParametros(Nodo parametros) {
        this.parametros = parametros;
    }

    public String getDetalleParametros() {
        return detalleParametros;
    }

    public void setDetalleParametros(String detalleParametros) {
        this.detalleParametros = detalleParametros;
    }

    /**
     * @return the tipo
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the visibilidad
     */
    public Integer getVisibilidad() {
        return visibilidad;
    }

    /**
     * @param visibilidad the visibilidad to set
     */
    public void setVisibilidad(Integer visibilidad) {
        this.visibilidad = visibilidad;
    }

    /**
     * @return the acciones
     */
    public Nodo getAcciones() {
        return acciones;
    }

    /**
     * @param acciones the acciones to set
     */
    public void setAcciones(Nodo acciones) {
        this.acciones = acciones;
    }

    /**
     * @return the conservar
     */
    public Boolean getConservar() {
        return conservar;
    }

    /**
     * @param conservar the conservar to set
     */
    public void setConservar(Boolean conservar) {
        this.conservar = conservar;
    }

    /**
     * @return the padre
     */
    public Integer getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Integer padre) {
        this.padre = padre;
    }

    /**
     * @return the nivel
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the correlativo
     */
    public Integer getCorrelativo() {
        return correlativo;
    }

    /**
     * @param correlativo the correlativo to set
     */
    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }
    
    

    public boolean retornoEsArreglo(){
        if(this.dimensiones==null || this.dimensiones =="0"){
            return false;
        }
        return true;
    }
    
}
