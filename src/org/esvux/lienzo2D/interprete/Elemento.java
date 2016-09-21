package org.esvux.lienzo2D.interprete;

import java.util.ArrayList;
import java.util.Comparator;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.expresion.Check;

/**
 *
 * @author esvux
 */
public class Elemento implements Comparable<Elemento>, Comparator<Elemento>{

    public static Elemento createVariable(String nombre, Integer tipo, Integer nivel, Integer visibilidad) {
        Elemento var = new Elemento(nombre, tipo, nivel, visibilidad);
        var.setValor(null);
        return var;
    }
    
    public static Elemento createArreglo(String nombre, Integer tipo, Integer nivel, Integer visibilidad) {
        Elemento arr= new Elemento(nombre, tipo, nivel, visibilidad, true);
        return arr;
    }
    
    private String nombre;
    private String valor;
    private Integer tipo;
    private Integer nivel;
    private Integer visibilidad;
    // Campos utiles para el manejo de arreglos 
    private Boolean esArreglo;
    private ArrayList<Integer> dimensiones;
    private ArrayList<String> valores;

    /**
     * Constructor para declaracion de variables
     * @param nombre
     * @param tipo
     * @param nivel
     * @param visibilidad 
     */
    private Elemento(String nombre, Integer tipo, Integer nivel, Integer visibilidad) {
        this.nombre = nombre;
        this.valor = "";
        this.tipo = tipo;
        this.nivel = nivel;
        this.visibilidad = visibilidad;
        this.esArreglo = false;
    }

    /**
     * Constructor para declaracion de arreglos
     * @param nombre
     * @param tipo
     * @param nivel
     * @param visibilidad
     * @param esArreglo 
     */
    private Elemento(String nombre, Integer tipo, Integer nivel, Integer visibilidad, boolean esArreglo) {
        this.nombre = nombre;
        this.valor = "";
        this.tipo = tipo;
        this.nivel = nivel;
        this.visibilidad = visibilidad;
        this.esArreglo = esArreglo;
        this.dimensiones = new ArrayList<>();
        this.valores = new ArrayList<>();
    }

    public void agregarDimension(Integer dimension){
        this.dimensiones.add(dimension);
    }
    
    public void generarValores(Integer capacidad){
        for(int i=0; i<capacidad; i++){
           this.valores.add(null);
        }
    }
    
    private void agregarValor(String valor){
        this.valores.add(valor);
    }
    
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(Integer visibilidad) {
        this.visibilidad = visibilidad;
    }

    public boolean isGlobal() {
        return this.nivel.compareTo(0) == 0;
    }

    public ArrayList<String> getValores() {
        return valores;
    }
    
    public Integer gerSizeValores(){
        return this.valores.size();
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }

    public ArrayList<Integer> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(ArrayList<Integer> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Integer getCapacidad(){
        return this.getValores().size();
    }

    public Boolean getEsArreglo() {
        return esArreglo;
    }

    public void setEsArreglo(Boolean esArreglo) {
        this.esArreglo = esArreglo;
    }
   
    @Override
    public int compareTo(Elemento o) {
        int compNombre = this.nombre.compareTo(o.nombre);
        if (compNombre != 0) {
            return compNombre;
        }
        return (this.isGlobal() == o.isGlobal()) ? 0 : compNombre;
    }

    @Override
    public int compare(Elemento o1, Elemento o2) {
        return o1.compareTo(o2);
    }
    
    public boolean realizarAsignacion(Resultado solucion){
        if(Check.EsTipo(this.getTipo(),Tipos.T_ENTERO)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.setValor(solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.setValor(Check.DobleToEntero(Check.ToDoble(solucion.getValor())));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.setValor(""+Check.BooleanToEntero(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.setValor(""+Check.CaracterToEntero(solucion.getValor()));
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_DOBLE)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.setValor(""+Check.ToDoble(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.setValor(solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_BOOLEAN)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.setValor(solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_CARACTER)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.setValor(""+(char)Check.ToEntero(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.setValor(solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_CADENA)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.setValor(solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.setValor(solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.setValor(solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.setValor(solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CADENA)){
                this.setValor(solucion.getValor());
                return true;
            }                        
        }
        return false;
    }
    
    public boolean realizarAsignacionArreglo(Resultado solucion, Integer posicion){
        if(Check.EsTipo(this.getTipo(),Tipos.T_ENTERO)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.valores.set(posicion, Check.DobleToEntero(Check.ToDoble(solucion.getValor())));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.valores.set(posicion, ""+Check.BooleanToEntero(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.valores.set(posicion, ""+Check.CaracterToEntero(solucion.getValor()));
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_DOBLE)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.valores.set(posicion, ""+Check.ToDoble(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_BOOLEAN)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_CARACTER)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.valores.set(posicion, ""+(char)Check.ToEntero(solucion.getValor()));
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
        }
        if(Check.EsTipo(this.getTipo(),Tipos.T_CADENA)){
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_ENTERO)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_DOBLE)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_BOOLEAN)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CARACTER)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }
            if(Check.EsTipo(solucion.getTipo(),Tipos.T_CADENA)){
                this.valores.set(posicion, solucion.getValor());
                return true;
            }                        
        }
        return false;
    }
    
    public Resultado obtenerValorArrelgo(Integer posicion){
        Resultado solucion = new Resultado(this.tipo, this.valores.get(posicion));
        return solucion;                
    }

    public Resultado obtenerValor(){
        Resultado solucion = new Resultado(this.getTipo(), this.valor);
        return solucion;                
    }
    
    public Integer obtenerPosicion(ArrayList<Resultado> soluciones){
        for(int i=0; i<soluciones.size(); i++){
            if(!Check.EsTipo(soluciones.get(i).getTipo(), Tipos.T_ENTERO)){
                // Nota: El error ya se ha contemplado al resolverDimensiones 
                return -1;
            }
        }
        Integer capacidad = getCapacidad();
        Integer posicion = 0;
        Integer tempPosicion = 0;
        for (int j=0; j<soluciones.size()-1; j++){
            capacidad = capacidad / this.getDimensiones().get(j);
            posicion = tempPosicion + Check.ToEntero(soluciones.get(j).getValor())*capacidad + Check.ToEntero(soluciones.get(j+1).getValor());  
            tempPosicion = posicion - Check.ToEntero(soluciones.get(j+1).getValor());
        }
        if(soluciones.size()==1){
            return Check.ToEntero(soluciones.get(0).getValor());
        }
        return posicion;
    }
    
}
