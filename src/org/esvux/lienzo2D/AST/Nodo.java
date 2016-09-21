package org.esvux.lienzo2D.AST;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.utilidades.Graphviz;

/**
 *
* @autor esvux
 */
public class Nodo {

    private static Integer ID = 0;
    private String id;
    private Integer rol;
    private Integer tipo;
    private String nombre;
    private String valor;
    private Integer visibilidad;
    private Boolean conservar;
    private ArrayList<Nodo> hijos;
    //Definición de ámbito
    private Integer padre;
    private Integer nivel;
    private Integer correlativo;
    //Ambito guarda la referencia a global o local 
    private Integer ambito;
    private int fila;
    private int columna;

    
    //<editor-fold defaultstate="collapsed" desc=" Constructores ">

    /**
     * Constructor vacio
     */
    public Nodo() {
        this.id = "nodo"+ID++;
        this.rol = Tipos.NULL;
        this.tipo = Tipos.NULL;
        this.nombre = "";
        this.valor = "";
        this.visibilidad = Tipos.NULL;
        this.conservar = false;
        this.padre = Tipos.NULL;
        this.nivel = Tipos.NULL;
        this.correlativo = Tipos.NULL;
        this.hijos = new ArrayList<>();
        this.ambito = Tipos.NULL;
        this.fila = 0;
        this.columna = 0;
    }

    private Nodo(Integer rol, String nombre, int fila, int columna) {
        this.id = "nodo"+ID++;
        this.rol = rol;
        this.tipo = Tipos.NULL;
        this.nombre = nombre;
        this.valor = "";
        this.visibilidad = Tipos.NULL;
        this.conservar = false;
        this.padre = Tipos.NULL;
        this.nivel = Tipos.NULL;
        this.correlativo = Tipos.NULL;
        this.hijos = new ArrayList<>();
        this.ambito = Tipos.NULL;
        this.fila = fila;
        this.columna = columna;
    }

    private Nodo(Integer rol, Integer tipo, String nombre, String valor, int fila, int columna) {
        this.id = "nodo"+ID++;
        this.rol = rol;
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
        this.visibilidad = Tipos.NULL;
        this.conservar = false;
        this.padre = Tipos.NULL;
        this.nivel = Tipos.NULL;
        this.correlativo = Tipos.NULL;
        this.hijos = new ArrayList<>();
        this.ambito = Tipos.NULL;
        this.fila = fila;
        this.columna = columna;
    }

    private Nodo(Nodo nodo){
        this.id = "nodo"+ID++;
        this.rol = nodo.getRol();
        this.tipo = nodo.getTipo();
        this.nombre = nodo.getNombre();
        this.valor = nodo.getValor();
        this.visibilidad = nodo.getVisibilidad();
        this.conservar = nodo.getConservar();
        this.hijos = nodo.getHijos();
        this.padre = nodo.getPadre();
        this.nivel = nodo.getNivel();
        this.correlativo = nodo.getCorrelativo();
        this.ambito = nodo.getAmbito();
        this.fila = nodo.getFila();
        this.columna = nodo.getColumna();
    }

    public Nodo(Integer rol, Integer tipo, String nombre, String valor) {
        this.id = "nodo"+ID++;
        this.rol = rol;
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
        this.visibilidad = -1;
        this.conservar = false;
        this.hijos = new ArrayList<>();
        this.padre = -1;
        this.nivel = -1;
        this.correlativo = -1;
        this.ambito = -1;
        this.fila = 0;
        this.columna = 0;
    }

    public Nodo(Integer rol, Integer tipo, String nombre, String valor, Integer visibilidad, Boolean conservar, Integer padre, Integer nivel, Integer correlativo, Integer ambito, int fila, int columna) {
        this.id = "nodo"+ID++;
        this.rol = rol;
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
        this.visibilidad = visibilidad;
        this.conservar = conservar;
        this.hijos = new ArrayList<>();
        this.padre = padre;
        this.nivel = nivel;
        this.correlativo = correlativo;
        this.ambito = ambito;
        this.fila = fila;
        this.columna = columna;
    }
    
    
    //----------------------------------------------------- CREAR NODO VARIABLE
    public static Nodo crearVariable(String nombre, Integer tipo, Integer visibilidad, Boolean conservar, Integer padre, Integer nivel, Integer correlativo, Integer ambito, int fila, int columna){
       Nodo variable = new Nodo(Tipos.ID, tipo, nombre, "", visibilidad, conservar, padre, nivel, correlativo, ambito, fila, columna);
       return variable;
    }

    //----------------------------------------------------- CREAR NODO ID
    public static Nodo crearID(String nombre, int fila, int columna){
       return new Nodo(Tipos.ID, nombre, fila, columna);
    }

    //----------------------------------------------------- CREAR NODO ARREGLO
    public static Nodo crearArreglo(String nombre, int fila, int columna, Nodo dimensiones){
        Nodo arreglo = new Nodo(Tipos.ARREGLO, nombre, fila, columna);
        arreglo.addHijo(dimensiones);
        return arreglo;
    }
    
    //----------------------------------------------------- CREAR NODO LLAMADA
    public static Nodo crearLlamada(String nombre, int fila, int columna, Nodo parametros){
       Nodo llamada =  new Nodo(Tipos.LLAMADA, nombre, fila, columna);
       llamada.addHijo(parametros);
       return llamada;
    }

    //----------------------------------------------------- CREAR NODO TERMINAL
    public static Nodo crearTerminal(Integer rol, Integer tipo, String nombre, String valor, int fila, int columna){
        return new Nodo(rol, tipo, nombre, valor, fila, columna);
    }
    
    //----------------------------------------------------- CREAR NODO PARA PAUSA
    public static Nodo crearPausa(String nombre, int fila, int columna){
        return new Nodo(Tipos.PAUSA, nombre, fila, columna);
    }

    //----------------------------------------------------- CREAR NODO DECLARACION
    public static Nodo crearDeclaracionArreglo(int fila, ArrayList<Nodo> ids, Nodo dimensiones, Nodo valor){
        Nodo declaracion = new Nodo(Tipos.DECLARACION, "declaracion", fila, 0);
        declaracion.setTipo(Tipos.ARREGLO);
        for(Nodo id: ids){
            declaracion.addHijo(id);         
        }
        declaracion.addHijo(dimensiones);
        if(valor == null){
            valor = new Nodo();
        }
        declaracion.addHijo(valor);
        return declaracion;
    }
    
    //----------------------------------------------------- CREAR NODO DECLARACION
    public static Nodo crearDeclaracion(int fila, ArrayList<Nodo> ids, Nodo valor){
        Nodo declaracion = new Nodo(Tipos.DECLARACION, "declaracion", fila, 0);
        declaracion.setTipo(Tipos.VARIABLE);
        for(Nodo id: ids){
            declaracion.addHijo(id);         
        }
        if(valor == null){
            valor = new Nodo();
        }
        declaracion.addHijo(valor);
        return declaracion;
    }

    //----------------------------------------------------- CREAR NODO DECLARACION
    public static Nodo crearDeclaracion(Integer tipo, int fila, Nodo variable, Nodo valor){
        Nodo declaracion = new Nodo(Tipos.DECLARACION, "declaracion", fila, 0);
        declaracion.setTipo(tipo);
        declaracion.addHijo(variable);
        if(valor == null){
            valor = new Nodo();
        }
        declaracion.addHijo(valor);
        return declaracion;
    }

    
    //----------------------------------------------------- CREAR NODO OPERACION BINARIA
    public static Nodo crearOperacionBinaria(Integer rol, String nombre, int fila, int columna, Nodo izq, Nodo der){
        Nodo operacion = new Nodo(rol, nombre, fila, columna);
        operacion.addHijo(izq);
        operacion.addHijo(der);
        return operacion;
    }

    //----------------------------------------------------- CREAR NODO OPERACION UNARIA
    public static Nodo crearOperacionUnaria(Integer rol, String nombre, int fila, int columna, Nodo izq){
        Nodo operacion = new Nodo(rol, nombre, fila, columna);
        operacion.addHijo(izq);
        return operacion;
    }


    //----------------------------------------------------- CREAR NODO ASIGNACION
    public static Nodo crearAsignacion(String nombre, int fila, int columna, Nodo izq, Nodo der){
        Nodo asignacion = new Nodo(Tipos.ASIGNACION, nombre, fila, columna);
        asignacion.addHijo(izq);
        asignacion.addHijo(der);
        return asignacion;
    }

    //----------------------------------------------------- CREAR NODO ASIGNACION PARA ARREGLOS {}
    public static Nodo crearAsignacionArreglo(String nombre, Integer tipo,  int fila){
        Nodo asignacion = new Nodo();
        asignacion.setRol(Tipos.ASIGNACION);
        asignacion.setNombre(nombre);
        asignacion.setTipo(tipo);
        asignacion.setFila(fila);
        return asignacion;
    }
    
    
    //----------------------------------------------------- CREAR NODO RETORNA
    public static Nodo crearRetorna(String nombre, int fila, int columna, Nodo expresion){
        Nodo retorna = new Nodo(Tipos.RETORNA, nombre, fila, columna);
        retorna.addHijo(expresion);
        return retorna;
    }

    //----------------------------------------------------- CREAR NODO PROCEDIMIENTO
    public static Nodo crearProcedimiento(Integer rol, String nombre, int fila, int columna, Nodo ... hijos){
        Nodo procedimiento = new Nodo(rol, nombre, fila, columna);
        for(Nodo hijo: hijos){
            procedimiento.addHijo(hijo);
        }
        return procedimiento;
    }

    //----------------------------------------------------- CREAR NODO FUNCION
    public static Nodo crearFuncion(Integer rol, String nombre, int fila, int columna, Nodo hijo){
        Nodo funcion = new Nodo(rol, nombre, fila, columna);
        funcion.addHijo(hijo);
        return funcion;
    }

    //----------------------------------------------------- CREAR NODO CASO
    public static Nodo crearCaso(String nombre, int fila, int columna, Nodo expresion, Nodo acciones){
        Nodo funcion = new Nodo(Tipos.CASO, nombre, fila, columna);
        funcion.addHijo(expresion);
        funcion.addHijo(acciones);
        return funcion;
    }

    //----------------------------------------------------- CREAR NODO DEFECTO
    public static Nodo crearDefecto(String nombre, int fila, int columna, Nodo acciones){
        Nodo funcion = new Nodo(Tipos.DEFECTO, nombre, fila, columna);
        if(acciones==null){
            acciones = crearTransicion(Tipos.ACCIONES, "acciones", 0); 
        }
        funcion.addHijo(acciones);
        return funcion;
    }
    
    
    //----------------------------------------------------- CREAR NODO ESCAPE
    public static Nodo crearEscape(Integer rol, String nombre, int fila, int columna){
        return new Nodo(rol, nombre, fila, columna);
    }

    //----------------------------------------------------- CREAR NODO BUCLE
    public static Nodo crearBucle(Integer rol, String nombre, int fila, int columna, Nodo ... hijos){
        Nodo bucle = new Nodo(rol, nombre, fila, columna);
        for (Nodo hijo: hijos){
            bucle.addHijo(hijo);
        }
        return bucle;
    }

    
    //----------------------------------------------------- CREAR NODO FLUJO_SI
    public static Nodo crearFlujoSi(String nombre, int fila, int columna, Nodo condicion, Nodo accionesSi, Nodo accionesSino){
        Nodo flujo = new Nodo(Tipos.FLUJO_SI, nombre, fila, columna);
        flujo.addHijo(condicion);
        flujo.addHijo(accionesSi);
        if(accionesSino==null){
            accionesSino = crearTransicion(Tipos.ACCIONES, "acciones", 0); 
        }
        flujo.addHijo(accionesSino);
        return flujo;
    }
    
    //----------------------------------------------------- CREAR NODO FLUJO
    public static Nodo crearFlujo(Integer rol, String nombre, int fila, int columna, Nodo ... hijos){
        Nodo flujo = new Nodo(rol, nombre, fila, columna);
        for (Nodo hijo: hijos){
            flujo.addHijo(hijo);
        }
        return flujo;
    }
    
    //----------------------------------------------------- CREAR NODO TRANSICION, NO CONTIENE UNA POSICION EXACTA, SOLO LA FILA
    public static Nodo crearTransicion(Integer rol, String nombre, int fila){
        Nodo nodo = new Nodo();
        nodo.setRol(rol);
        nodo.setNombre(nombre);
        nodo.setFila(fila);
        return nodo;
    }

    public static Nodo crearMetodo(String nombre, Integer tipo, String dimensiones, Integer visibilidad, 
            Boolean conservar,Integer nivel, Integer padre, Integer correlativo, Integer fila, Integer columna, 
            Nodo parametros, Nodo acciones){
        Nodo metodo = new Nodo(Tipos.METODO, nombre, fila, columna);
        metodo.setVisibilidad(visibilidad);
        metodo.setTipo(tipo);
        metodo.setValor(dimensiones);
        metodo.setConservar(conservar);
        metodo.setNivel(nivel);
        metodo.setPadre(padre);
        metodo.addHijo(parametros);
        metodo.addHijo(acciones);
        return metodo;
    }
    
    
    public static Nodo crearPrincipal(String nombre, Integer nivel, Integer padre, Integer fila, Integer columna,
            Nodo parametros, Nodo acciones){
        Nodo principal = new Nodo(Tipos.PRINCIPAL, nombre, fila, columna);
        principal.setVisibilidad(Tipos.PUBLICO);
        principal.setTipo(Tipos.T_VOID);
        principal.setConservar(true);
        principal.setNivel(nivel);
        principal.setPadre(padre);
        principal.addHijo(parametros);
        principal.addHijo(acciones);
        return principal;
    }
    
    //----------------------------------------------------- CREAR NODO COPIA
    public static Nodo duplicar(Nodo nodo){
       return new Nodo(nodo);
    }
    
//
    

    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc=" Getters, Setters y otros metodos ">

    /**
     * Agregar un hijo al array de hijos del nodo
     * @param hijo 
     */
    public void addHijo(Nodo hijo){
        this.hijos.add(hijo);
    }

    /**
     * Agregar un hijo en una posición
     * @param hijo
     * @param pos 
     */
    public void insertNodo(Nodo hijo, int pos){
        this.getHijos().add(pos, hijo);
    }

    /**
     * Agregando una lista de hijos
     * @param hijos 
     */
    public void addNodos(ArrayList<Nodo> hijos){
        this.getHijos().addAll(hijos);
    }

    /**
     * Obtener el número de hijos que posee el nodo
     * @return int
     */
    public int getSize(){
        return this.getHijos().size();
    }
    
    /**
     * @return the rol
     */
    public Integer getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Integer rol) {
        this.rol = rol;
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

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
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
     * @return the hijos
     */
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
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
     * @return the ambito
     */
    public Integer getAmbito() {
        return ambito;
    }

    /**
     * @param ambito the ambito to set
     */
    public void setAmbito(Integer ambito) {
        this.ambito = ambito;
    }
    
        /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }
    

    //</editor-fold>

    private void asignarConFila(Nodo hijo){
        if(this.fila==0){
            this.fila = hijo.getFila();
        }
        this.hijos.add(hijo);        
    }
    
    public void agregarAAcciones(Nodo accion, String lienzo){
        Nodo pausa = crearPausa("pausa", accion.getFila(), 0);
        pausa.setValor(lienzo+".lz");
        this.hijos.add(pausa);
        asignarConFila(accion);        
    }
    
    public void agregarADimension(Nodo dimension){
        asignarConFila(dimension);
    }

    public void agregarADeclaracion(Nodo declaracion){
        asignarConFila(declaracion);
    }

    public void agregarAAsignacion(Nodo asignacion){
        asignarConFila(asignacion);
    }
    
    public void agregarAParametros(Nodo param){
        asignarConFila(param);
    }

    public void agregarACasos(Nodo hijo){
        asignarConFila(hijo);
    }
    
    /**
     * @return la cadena que representa la visibilidad del nodo
     * si es un valor erróneo devuelve una cadena vacía.
     */
    public String visibilidadAsString(){
        return Tipos.getVisibilidadAsString(this.visibilidad);
    }    
    
    /**
     * @return la cadena que representa el tipo de dato asociado al nodo
     * si es un valor erróneo devuelve una cadena vacía.
     */
    public String tipoAsString(){
        return Tipos.getTipoAsString(this.tipo);
    }
    
    /**
     * @return la cadena que representa el rol del nodo dentro del AST
     * si es un valor erróneo devuelve una cadena vacía.
     */
    public String rolAsString(){
        return Tipos.getRolAsString(this.rol);
    }

    /**
     * Retorna una cadena que representa a los parametros de creacion
     * @return 
     */
    public String parametrosAsString(){
        String resultado="";
        String temp = resultado;
        for(Nodo nodo: this.getHijos()){
            if(nodo.getTipo()==Tipos.NULL){
                return resultado;
            }
            resultado= temp + Tipos.getTipoAsString(nodo.getTipo());
            temp = resultado +",";          
        }
        return resultado;
    }
    /**
     * Obtiene un hijo en la posicion especifica.
     * @param pos Posicion solicitada.
     * @return El hijo en la posicion deseada o null si no existe.
     */
    public Nodo getHijo(int pos){
        if(pos < 0 || pos >= this.hijos.size())
            return null;
        return this.hijos.get(pos);
    }

    //<editor-fold defaultstate="collapsed" desc=" Graficar AST ">
    
    public String getDOT() {
        String DOT = "digraph G{\n";
        //DOT += "\tgraph[rankdir=\"LR\"];\n";
        DOT += "\tnode[shape=\"record\"];\n";
        DOT += this.getNodo();
        DOT += this.recorrerNodos(this.id, this.hijos.iterator());
        DOT += "}";
        return DOT;
    }

    public void generarImagen() {
        Graphviz gv = new Graphviz();
        gv.add(this.getDOT());
        String type = "png";
        File out = new File(this.id + "." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    private String getNodo() {
        String nodo = this.id + "[label=\"{";
        
        if(0 == this.rol.compareTo(Tipos.TRUE) *
                this.rol.compareTo(Tipos.FALSE) *
                this.rol.compareTo(Tipos.ENTERO) *
                this.rol.compareTo(Tipos.DOBLE) *
                this.rol.compareTo(Tipos.CADENA) *
                this.rol.compareTo(Tipos.CARACTER))
        {
            nodo += this.rolAsString() + "|" + this.tipoAsString();
            nodo += "|" + esc(this.nombre);
        }
        else if(0 == this.rol.compareTo(Tipos.LOGICA) *
                     this.rol.compareTo(Tipos.RELACIONAL) *
                     this.rol.compareTo(Tipos.ARITMETICA))
        {
            nodo = this.id + "[shape=\"circle\" label=\"{" + this.nombre;
        }
        else if(0 == this.rol.compareTo(Tipos.FLUJO_SI) *
                this.rol.compareTo(Tipos.FLUJO_COMPROBAR) *
                this.rol.compareTo(Tipos.CASOS) *
                this.rol.compareTo(Tipos.CASO) *
                this.rol.compareTo(Tipos.DEFECTO) *
                this.rol.compareTo(Tipos.BUCLE_PARA) * 
                this.rol.compareTo(Tipos.BUCLE_MIENTRAS) *
                this.rol.compareTo(Tipos.BUCLE_HACER) *
                this.rol.compareTo(Tipos.SALIR) *
                this.rol.compareTo(Tipos.CONTINUAR) *
                this.rol.compareTo(Tipos.DECLARACIONES) *
                this.rol.compareTo(Tipos.DIMENSION) *
                this.rol.compareTo(Tipos.PARAM_CREACION) *
                this.rol.compareTo(Tipos.PARAM_LLAMADA) *
                this.rol.compareTo(Tipos.ACCIONES))
        {
            nodo = this.id + "[shape=\"box\" label=\"{" + this.nombre;
        }
        else if(0 == this.rol.compareTo(Tipos.PRINCIPAL) *
                     this.rol.compareTo(Tipos.METODO))
        {
            nodo += this.rolAsString() + "|" + this.nombre;
            nodo += "|" + this.tipoAsString() + "|" + this.visibilidadAsString();
            nodo += "|" + ((this.conservar) ? "" : "No ") + "Conservar";
        }
        else if(0 == this.rol.compareTo(Tipos.DECLARACION))
        {
            nodo += this.rolAsString() + "|" + Tipos.getRolAsString(this.tipo);
        }
        else if(0 == this.rol.compareTo(Tipos.ID))
        {
            nodo += this.rolAsString() + "|" + this.nombre + "|" + this.tipoAsString();
            nodo += "|" + this.visibilidadAsString();
            nodo += "|" + ((this.conservar) ? "" : "No ") + "Conservar";
        }
        else if(0 == this.rol.compareTo(Tipos.ASIGNACION))
        {
            nodo += this.rolAsString() + "|" + this.nombre;
        }
        else if(0 == this.rol.compareTo(Tipos.ORDENAR))
        {
            nodo += "Funcion primitiva|" + this.nombre + "|Ordenamiento: " + this.nombre + "|" + this.tipoAsString();
        }
        else if(0 == this.rol.compareTo(Tipos.SUMARIZAR))
        {
            nodo += "Funcion primitiva|" + this.nombre + "|" + this.tipoAsString();
        }
        else if(0 == this.rol.compareTo(Tipos.PINTAR_OR) *
                     this.rol.compareTo(Tipos.PINTAR_P) *
                     this.rol.compareTo(Tipos.PINTAR_S))
        {
            nodo += "Funcion primitiva|" + this.nombre;
        }
        else
        {
            nodo += this.rolAsString() + "|" + this.tipoAsString() + "|" + this.visibilidadAsString();
            nodo += "|" + esc(this.valor) + "|" + esc(this.valor);
            nodo += "|" + ((this.conservar) ? "" : "No ") + "Conservar";
        }
        return nodo + "}\"];\n";
    }

    private String recorrerNodos(String padre, Iterator<Nodo> nodos) {
        String DOT = "";
        while (nodos.hasNext()) {
            Nodo nodo = nodos.next();
            DOT += nodo.getNodo();
            DOT += "\t" + padre + "->" + nodo.id + ";\n";
            DOT += nodo.recorrerNodos(nodo.id, nodo.hijos.iterator());
        }
        return DOT;
    }

    private String esc(String escapado) {
        return escapado.replace("\"", "\\\"");
    }
    

    //</editor-fold>


}
