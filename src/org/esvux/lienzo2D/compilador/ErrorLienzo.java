package org.esvux.lienzo2D.compilador;

/**
 *
* @autor esvux
 */
public class ErrorLienzo {

    private final int fila;
    private final int columna;
    private final int tipo;
    private final String descripcion;
    private final String lienzo;
    public static final String GENERAL = "General";
    public static final String TIPO_ERROR[] = {"Léxico","Sintáctico","Semántico","Otros"};
    public static final int ERROR_LEXICO = 0;
    public static final int ERROR_SINTACTICO = 1;
    public static final int ERROR_SEMANTICO = 2;
    public static final int ERROR_OTROS = 3;

    /**
     * Constructor general para cualquier error en los lienzos analizados.
     * @param fila La línea del error.
     * @param columna La columna del error.
     * @param tipo El tipo del error (Léxico, Sintáctico, Semántico o General).
     * @param descripcion Detalle del error.
     * @param lienzo Lienzo en donde fue detectado el error.
     */
    public ErrorLienzo(int fila, int columna, int tipo, String descripcion, String lienzo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.lienzo = lienzo;
    }
    
    /**
     * Obtiene la representación en HTML del error, como una fila de una tabla.
     * @return Fila de tabla en HTML con el detalle del error.
     */
    public String getHTML(){
        String html = "<tr>\n";
        html += "\t<td>"+TIPO_ERROR[tipo]+"</td>\n";
        html += "\t<td>"+descripcion+"</td>\n";
        html += "\t<td class='center'>"+lienzo+"</td>\n";
        html += "\t<td class='center'>"+fila+"</td>\n";
        html += "\t<td class='center'>"+columna+"</td>\n";
        html += "</tr>\n";
        return html;
    }

    /**
     * Representación en texto plano del error.
     * @return Detalle del error.
     */
    @Override
    public String toString() {
        String err = "[" + TIPO_ERROR[tipo] + "]";
        err += "\t" + descripcion;
        err += ", linea: " + fila;
        err += " columna: " + columna;
        err += "\n";
        return err;
    }
    
}
