package org.esvux.lienzo2D.AST;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.parser.ParseException;
import org.esvux.lienzo2D.parser.ParserL2D;
import org.esvux.lienzo2D.utilidades.ManejadorArchivos;

/**
 *
* @autor esvux
 */
public class Lienzo {

    public static String RAIZ = "/home/esvux/";

    private String nombre;
    private Integer visibilidad;
    private HashMap<String, Metodo> catalogoMetodos;
    private ArrayList<Nodo> declaraciones;
    private static HashMap<String, Lienzo> catalogoExtendes;
    private Metodo principal;

    public Lienzo() {
        this.nombre = "";
        this.visibilidad = -1;
        this.catalogoMetodos = new HashMap<>();
        this.declaraciones = new ArrayList<>();
    }

    public Lienzo(String nombre, Integer visibilidad) {
        this.nombre = nombre;
        this.visibilidad = visibilidad;
        this.catalogoMetodos = new HashMap<>();
        this.declaraciones = new ArrayList<>();
    }

    public static void resetCatalogoExtendes() {
        catalogoExtendes = new HashMap<>();
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
     * @return the catalogoMetodos
     */
    public HashMap<String, Metodo> getCatalogoMetodos() {
        return catalogoMetodos;
    }

    /**
     * @param catalogoMetodos the catalogoMetodos to set
     */
    public void setCatalogoMetodos(HashMap<String, Metodo> catalogoMetodos) {
        this.catalogoMetodos = catalogoMetodos;
    }

    /**
     * @return the declaraciones
     */
    public ArrayList<Nodo> getDeclaraciones() {
        return declaraciones;
    }

    /**
     * @param declaraciones the declaraciones to set
     */
    public void setDeclaraciones(ArrayList<Nodo> declaraciones) {
        this.declaraciones = declaraciones;
    }

    /**
     * @return the principal
     */
    public Metodo getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Metodo principal) {
        this.principal = principal;
    }

    /**
     * @return the catalogoExtendes
     */
    public static HashMap<String, Lienzo> getCatalogoExtendes() {
        return catalogoExtendes;
    }

    public void agregarMetodo(Nodo nodo) {
        Metodo metodo = Metodo.crearMetodo(nodo);
        this.catalogoMetodos.put(metodo.getLLave(), metodo);
    }

    public void agregarDeclaracion(Nodo nodo) {
        this.declaraciones.add(nodo);
    }

    public void agregarPrincipal(Nodo nodo) {
        Metodo metodo = Metodo.crearPrincipal(nodo);
        this.setPrincipal(metodo);
    }

    public void generarExtend(String name) {
        name += ".lz";
        File archivo = new File(Lienzo.RAIZ + name);
        try {
            String contenido = ManejadorArchivos.abrir(archivo);
            ParserL2D parser = new ParserL2D(new java.io.StringReader(contenido));
            Lienzo lienzo = parser.CLASE();
            if (lienzo == null) {
                ManejadorErrores.getInstance(name).addErrorGeneral("Imposible generar el lienzo, error en el archivo");
                return;
            }
            agregarExtend(lienzo);
        } catch (IOException ex) {
            ManejadorErrores.getInstance(name).addErrorGeneral("No existe el archivo asociado al lienzo del que se desea extender.<br><span class='light'>" + archivo.getAbsolutePath() + "</span>");
        } catch (ParseException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarExtend(Lienzo lienzo) {
        if (Check.EsTipo(lienzo.getVisibilidad(), Tipos.PRIVADO)) {
            return;
        }
        catalogoExtendes.put(lienzo.getNombre(), lienzo);
    }

    private ArrayList<Metodo> getMetodos(String llave) {
        ArrayList<Metodo> candidatos = new ArrayList<>();
        for (Lienzo temp : catalogoExtendes.values()) {
            Metodo metodo = temp.getMetodo(llave);
            if (metodo != null) {
                candidatos.add(metodo);
            }
        }
        return candidatos;
    }

    private Metodo getMetodo(String llave) {
        Metodo buscado = this.catalogoMetodos.get(llave);
        return buscado;
    }

    public Metodo getMetodo(String nombre, String parametros) {
        String llave = nombre + ":" + parametros;
        Metodo buscado = this.catalogoMetodos.get(llave);
        if (buscado != null && buscado.getConservar()) {
            return buscado;
        }
        for(Metodo temp : getMetodos(llave)){
            if(temp.getConservar())
                return temp;
            if(!temp.getConservar() && buscado == null)
                return temp;
        }
        return buscado;
    }

}
