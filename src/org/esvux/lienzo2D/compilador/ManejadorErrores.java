package org.esvux.lienzo2D.compilador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import static org.esvux.lienzo2D.compilador.ErrorLienzo.ERROR_LEXICO;
import static org.esvux.lienzo2D.compilador.ErrorLienzo.ERROR_OTROS;
import static org.esvux.lienzo2D.compilador.ErrorLienzo.ERROR_SEMANTICO;
import static org.esvux.lienzo2D.compilador.ErrorLienzo.ERROR_SINTACTICO;
import org.esvux.lienzo2D.utilidades.Formateador;
import org.esvux.lienzo2D.utilidades.ManejadorArchivos;

/**
 * Para utilizar el manejador de errores es necesario colocar esta sentencia al
 * inicio de cada clase en donde querramos usar esta clase:<br>
 * ManejadorErrores errores = ManejadorErrores.getInstance("Lienzo1.lz");<br>
 * de esta manera podremos reportar los errores encontrados utilizando los
 * métodos según el tipo de error que se desea reportar, por ejemplo:<br>
 * errores.addErrorSintactico(3,20,"Se esperaba el token $.");
 *
 * @author esvux
 */
public class ManejadorErrores {

    //Contexto estático para el manejo del patrón de diseño SINGLETON    
    private static ManejadorErrores singleton;
    private static final String PLANTILLA = "PlantillaErrores.html";
    private static final String NO_LIENZO = "No aplica";
    public static final String REPORTE = "Errores.html";

    /**
     * Obtiene la instancia actual del manejador de errores para ser utilizada
     * fuera de cualquier lienzo.
     *
     * @return La única instancia del manejador de errores.
     */
    public static ManejadorErrores getInstance() {
        if (singleton == null) {
            singleton = new ManejadorErrores(NO_LIENZO);
        }
        return singleton;
    }

    /**
     * Reinicia la instancia actual del manejador de errores para ser utilizada
     * fuera de cualquier lienzo.
     *
     * @return La única instancia (reiniciada) del manejador de errores.
     */
    public static ManejadorErrores resetInstance() {
        singleton = new ManejadorErrores(NO_LIENZO);
        return singleton;
    }

    /**
     * Obtiene la instancia actual del manejador de errores ubicada en el lienzo
     * que recibe como parámetro.
     *
     * @param lienzo Ubicación del manejador de errores.
     * @return La única instancia del manejador de errores, pero con una nueva
     * ubicación.
     */
    public static ManejadorErrores getInstance(String lienzo) {
        if (singleton == null) {
            singleton = new ManejadorErrores(lienzo);
        } else {
            singleton.lienzo = lienzo;
        }
        return singleton;
    }

    /**
     * Reinicia la instancia actual del manejador de errores ubicada en el
     * lienzo que recibe como parámetro.
     *
     * @param lienzo Ubicación del manejador de errores.
     * @return La única instancia (reiniciada) del manejador de errores, pero en
     * una nueva ubicación.
     */
    public static ManejadorErrores resetInstance(String lienzo) {
        singleton = new ManejadorErrores(lienzo);
        return singleton;
    }

    //Definición del manejador de errores
    private final ArrayList<ErrorLienzo> errores;
    private static String plantilla;
    private String lienzo;

    /**
     * Constructor privado para garantizar la implementación del patrón de
     * diseño singleton, única instancia estática; también es el encargado de
     * cargar la plantilla para el reporte de errores en HTML.
     *
     * @param lienzo Lienzo sobre el que se registrarán los errores.
     */
    private ManejadorErrores(String lienzo) {
        this.errores = new ArrayList<>();
        this.lienzo = lienzo;
        try {
            plantilla = ManejadorArchivos.abrir(new File(PLANTILLA));
        } catch (IOException ex) {
            System.err.println("Cargando plantilla por defecto... " + ex.getMessage());
            addErrorGeneral("No se encontró la plantilla HTML de errores, se cargará la plantilla por defecto.");
            plantilla = PLANTILLA_DEFAULT;
        }
    }

    /**
     * Agrega un error cualquiera.
     *
     * @param err Error que será agregado.
     */
    public void addError(ErrorLienzo err) {
        errores.add(err);
    }

    /**
     * Agrega un nuevo error léxico.
     *
     * @param fila Fila del error
     * @param columna Columna del error
     * @param descripcion Descripción detallada del error
     */
    public void addErrorLexico(int fila, int columna, String descripcion) {
        ErrorLienzo err = new ErrorLienzo(fila, columna, ERROR_LEXICO, descripcion, lienzo);
        errores.add(err);
    }

    /**
     * Agrega un nuevo error sintáctico.
     *
     * @param fila Fila del error
     * @param columna Columna del error
     * @param descripcion Descripción detallada del error
     */
    public void addErrorSintactico(int fila, int columna, String descripcion) {
        ErrorLienzo err = new ErrorLienzo(fila, columna, ERROR_SINTACTICO, descripcion, lienzo);
        errores.add(err);
    }

    /**
     * Agrega un nuevo error semántico.
     *
     * @param fila Fila del error
     * @param columna Columna del error
     * @param descripcion Descripción detallada del error
     */
    public void addErrorSemantico(int fila, int columna, String descripcion) {
        ErrorLienzo err = new ErrorLienzo(fila, columna, ERROR_SEMANTICO, descripcion, lienzo);
        errores.add(err);
    }

    /**
     * Agrega un nuevo error general.
     *
     * @param descripcion Descripción detallada del error
     */
    public final void addErrorGeneral(String descripcion) {
        ErrorLienzo err = new ErrorLienzo(-1, -1, ERROR_OTROS, descripcion, lienzo);
        errores.add(err);
    }

    /**
     * Informa de la presencia (o ausencia) de errores en formato HTML.
     *
     * @return El estado
     */
    public String getEstadoHTML() {
        String detalle;
        if (this.errores.isEmpty()) {
            detalle = "<span>No se han registrado errores.</span>";
        } else {
            int errs = this.errores.size();
            detalle = "<span>" + ((errs > 1) ? "Se detectaron " + this.errores.size() + " errores" : "Se detectó un error")
                    + ", para más información pincha "
                    + "<a href='" + REPORTE + "' target='_blank'>aquí</a></span>";
        }
        return detalle;
    }

    /**
     * Informa de la presencia (o ausencia) de errores en formato HTML.
     *
     * @return El estado
     */
    public String getEstado() {
        String detalle;
        if (this.errores.isEmpty()) {
            detalle = "No se han registrado errores.";
        } else {
            int errs = this.errores.size();
            detalle = (errs > 1)? "Se detectaron " + this.errores.size() + " errores" : "Se detectó un error"
                    + ", para más información consulta el reporte de errores.";
        }
        return detalle;
    }

    /**
     * Genera el reporte en html de todos los errores que hasta el momento
     * contenga el manejador de errores.
     *
     * @param lienzoPrincipal Lienzo sobre el que fue realizado el análisis.
     * @return La dirección física (path) del reporte de errores que fue
     * generado, o null si no es posible generar el reporte.
     */
    public String generarReporte(String lienzoPrincipal) {
        try {
            return ManejadorArchivos.guardar(new File(REPORTE), getHTML(lienzoPrincipal));
        } catch (IOException ex) {
            System.err.println("Imposible generar el reporte de errores, " + ex.getMessage());
            return null;
        }
    }

    /**
     * Estructura el contenido del reporte basado en alguna plantilla definida
     * por el usuario (archivo PlantillaErrores.html) reemplazando los valores
     * __L2D__ por el nombre del lienzo analizado, __DATE__ por la fecha en que
     * se realizó el reporte y __CONTENT__ por el contenido del manejador de
     * errores.
     *
     * @param lienzoPrincipal Nombre del lienzo que fue analizado.
     * @return El cuerpo en HTML del manejador de errores.
     */
    public String getHTML(String lienzoPrincipal) {
        Iterator<ErrorLienzo> it = errores.iterator();
        String contenido = (!errores.isEmpty()) ? ""
                : "<tr><td colspan='5' class='center'>No existen errores en el programa.</td></tr>";
        while (it.hasNext()) {
            ErrorLienzo err = it.next();
            contenido += err.getHTML();
        }
        String html = plantilla.replace("__L2D__", lienzoPrincipal);
        html = html.replace("__DATE__", Formateador.formatearFecha(null));
        html = html.replace("__CONTENT__", contenido);
        return html;
    }

    /**
     * Genera el informe de errores sencillo en texto plano.
     *
     * @return Informe de errores.
     */
    @Override
    public String toString() {
        if (errores.isEmpty()) {
            return "No existen errores en el programa.";
        }
        String contenido = "Errores encontrados:\n";
        Iterator<ErrorLienzo> it = errores.iterator();
        while (it.hasNext()) {
            ErrorLienzo next = it.next();
            contenido += next.toString();
        }
        return contenido;
    }

    /**
     * Plantilla por defecto si no existe una plantilla definida por el usuario.
     */
    private static final String PLANTILLA_DEFAULT
            = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>__L2D__</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <h1>Reporte de errores</h1>\n"
            + "    <h3>Generado: __DATE__</h3>\n"
            + "    <table>\n"
            + "        <thead>\n"
            + "            <tr>\n"
            + "                <th>Tipo</th>\n"
            + "                <th>Descripción</th>\n"
            + "                <th>Lienzo</th>\n"
            + "                <th>Fila</th>\n"
            + "                <th>Columna</th>\n"
            + "            </tr>\n"
            + "        </thead>\n"
            + "        <tbody>__CONTENT__</tbody>\n"
            + "    </table>\n"
            + "</body>\n"
            + "</html>";

}
