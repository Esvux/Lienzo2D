package org.esvux.lienzo2D.compilador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Lienzo;
import org.esvux.lienzo2D.AST.Metodo;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.utilidades.Formateador;
import org.esvux.lienzo2D.utilidades.ManejadorArchivos;

/**
 *
* @autor esvux
 */
public class TablaSimbolos {

    private static final String PLANTILLA = "PlantillaTablaDeSimbolos.html";
    public static final String REPORTE = "TablaDeSimbolos.html";
    private String nombre;
    private ArrayList<Simbolo> ts;
    private Boolean permisoPrivado;

    /**
     * Constructor vacio
     */
    public TablaSimbolos() {
        this.ts = new ArrayList<>();
        this.nombre = "";
        this.permisoPrivado = true;
    }

    /**
     * Método para agregar un nuevo simbolo
     *
     * @param simbolo
     */
    public void addSimbolo(Simbolo simbolo) {
        this.ts.add(simbolo);
    }

    /**
     * Método que busca crear la tabla de Símbolos al finalizar el análisis.
     *
     * @param raiz Lienzo que fue analizado.
     */
    public void generarReporte(Lienzo raiz) {
        generarSimbolos(raiz);
        this.permisoPrivado = false;
        for(Lienzo lienzo : Lienzo.getCatalogoExtendes().values()){
           generarSimbolos(lienzo);
        }
        refresh();
    }

    private void generarSimbolos(Lienzo raiz) {
        for(Nodo nodo: raiz.getDeclaraciones()){
            agregarDeclaracion(raiz.getNombre(), nodo);
        }
        for(Metodo metodo : raiz.getCatalogoMetodos().values()){
            agregarMetodo(raiz.getNombre(), metodo, Tipos.METODO);
        }
        if(raiz.getPrincipal()!=null && this.permisoPrivado){
            agregarMetodo(raiz.getNombre(), raiz.getPrincipal(), Tipos.PRINCIPAL);
        }
    }

    private void agregarMetodo(String clase, Metodo metodo, Integer rol){
        if(Check.EsTipo(metodo.getVisibilidad(), Tipos.PRIVADO) && !this.permisoPrivado){
            return;
        }
        Simbolo simbolo = new Simbolo(metodo.getNombre(), rol, metodo.getTipo(), metodo.getVisibilidad(), metodo.getConservar(), metodo.getPadre(), 
                metodo.getNivel(), metodo.getCorrelativo(), clase, metodo.getDetalleParametros(), Tipos.GLOBAL);
        if(comprobarExistencia(simbolo)){
            ManejadorErrores.getInstance(clase).addErrorSemantico(metodo.getFila(), metodo.getColumna(), "Duplicación de símbolo, al declarar el método "+metodo.getNombre());
            return;
        }    
        simbolo.setDimensiones(metodo.getDimensiones());
        addSimbolo(simbolo);
        recorrerAST(clase, metodo.getAcciones());
        
    }

    private void agregarParametros(String clase, Nodo param){
        Simbolo simbolo;
        for(Nodo nodo: param.getHijos()){
            simbolo = new Simbolo(nodo.getNombre(), Tipos.VARIABLE, nodo.getTipo(), nodo.getVisibilidad(), nodo.getConservar(), nodo.getPadre(), nodo.getNivel(), nodo.getCorrelativo(), clase, "", nodo.getAmbito());
            if(comprobarExistencia(simbolo)){
                //ManejadorErrores.getInstance(clase).addErrorSemantico(nodo.getFila(), nodo.getColumna(), "Duplicación de símbolo, ya existe el parámetro "+nodo.getNombre());
                return;
            }    
            addSimbolo(simbolo);
        } 
    }
        
    private void recorrerAST(String clase, Nodo nodo){
        if(Check.EsTipo(nodo.getRol(), Tipos.DECLARACION)){
            agregarDeclaracion(clase, nodo);
            return;
        }
        if(Check.EsTipo(nodo.getRol(), Tipos.PARAM_CREACION)){
            agregarParametros(clase, nodo);
            return;
        }
        for(Nodo hijo: nodo.getHijos()){
            recorrerAST(clase, hijo);
        }
    }
    
    private void agregarDeclaracion(String clase, Nodo declaracion){
        Integer rol = declaracion.getTipo();
        Simbolo simbolo;
        for (Nodo nodo: declaracion.getHijos()){
            if(Check.EsTipo(nodo.getRol(), Tipos.ID)){
                if(Check.EsTipo(nodo.getVisibilidad(), Tipos.PRIVADO) && !this.permisoPrivado){
                    continue;
                }
                simbolo = new Simbolo(nodo.getNombre(), rol, nodo.getTipo(), nodo.getVisibilidad(), nodo.getConservar(), nodo.getPadre(), nodo.getNivel(), nodo.getCorrelativo(), clase, "", nodo.getAmbito());
                if(comprobarExistencia(simbolo)){
                    //if(Check.EsTipo(rol, Tipos.VARIABLE))
                        //ManejadorErrores.getInstance(clase).addErrorSemantico(nodo.getFila(), nodo.getColumna(), "Duplicación de símbolo, ya existe la variable "+nodo.getNombre());
                    //else
                        //ManejadorErrores.getInstance(clase).addErrorSemantico(nodo.getFila(), nodo.getColumna(), "Duplicación de símbolo, ya existe el arreglo "+nodo.getNombre());                        
                    return;
                }    
                addSimbolo(simbolo);
            }
        }
    }
    
    
    /**
     * Método que comprueba que un símbolo exista dentro de la tabla de
     * símbolos.
     *
     * @param simbolo
     * @return
     */
    private boolean comprobarExistencia(Simbolo simbolo) {
        for (Simbolo temp : this.ts) {
            if (simbolo.compare(temp)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Método que retorna la tabla de simbolo en formato html
     *
     * @return El reporte de la tabla de símbolos en HTML.
     */
    @Override
    public String toString() {
        ManejadorErrores manager = ManejadorErrores.getInstance();
        String contenido;
        try {
            contenido = ManejadorArchivos.abrir(new File(PLANTILLA));
        } catch (IOException ex) {
            manager.addErrorGeneral("No se encontró la plantilla para la tabla de símbolos, " + ex.getMessage() + ".");
            contenido = PLANTILLA_DEFAULT;
        }

        // Recorriendo los simbolos  
        String body = "";
        for (Simbolo simbolo : this.ts) {
            body += simbolo.toString() + "\n";
        }

        // Reemplazando contenido 
        contenido = contenido.replace("__DATE__", Formateador.formatearFecha(null));
        contenido = contenido.replace("__STATE__", ManejadorErrores.getInstance().getEstadoHTML());
        contenido = contenido.replace("__FILE__", this.nombre);
        contenido = contenido.replace("__BODY__", body);
        return contenido;
    }

    public void refresh() {
        try {
            String contenido = this.toString();
            ManejadorArchivos.guardar(new File(REPORTE), contenido);
        } catch (IOException ex) {
            ManejadorErrores manager = ManejadorErrores.getInstance();
            manager.addErrorGeneral("Imposible crear el reporte de la tabla de símbolos, " + ex.getMessage() + ".");
        }
    }

    private static final String PLANTILLA_DEFAULT
            = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "	<meta charset=\"UTF-8\">\n"
            + "	<title>Tabla de símbolos - Lienzos2D</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "	<h3>TABLA DE SÍMBOLOS</h3>\n"
            + "	<h5><b>Archivo compilado:</b> __FILE__ </h5>\n"
            + "	<h5><b>Fecha:</b> __DATE__ </h5>\n"
            + "	<h5><b>Estado:</b> __STATE__ </h5>\n"
            + "	<table>\n"
            + "	<thead>\n"
            + "		<tr>\n"
            + "			<th rowspan=\"2\">NOMBRE</th>\n"
            + "			<th rowspan=\"2\">ROL</th>\n"
            + "			<th rowspan=\"2\">TIPO</th>\n"
            + "			<th rowspan=\"2\">VISIBILIDAD</th>\n"
            + "			<th rowspan=\"2\">¿ES GLOBAL?</th>\n"
            + "			<th rowspan=\"2\">CONSERVAR</th>\n"
            + "			<th rowspan=\"2\">PARAMETROS</th>\n"
            + "			<th rowspan=\"2\">CLASE</th>\n"
            + "			<th colspan=\"3\">ÁMBITO</th>\n"
            + "		</tr>\n"
            + "		<tr>\n"
            + "			<th>PADRE</th>\n"
            + "			<th>NIVEL</th>\n"
            + "			<th>CORRELATIVO</th>\n"
            + "		</tr>\n"
            + "	</thead>\n"
            + "	<tbody>\n"
            + "		__BODY__\n"
            + "	</tbody>\n"
            + "</body>\n"
            + "</html>";

}
