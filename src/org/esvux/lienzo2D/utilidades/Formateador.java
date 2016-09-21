package org.esvux.lienzo2D.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author esvux
 */
public class Formateador {

    private static final SimpleDateFormat formateador = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

    public static String formatearFecha(Date fecha) {
        if (fecha == null) {
            fecha = new Date();
        }
        return formateador.format(fecha);
    }

}
