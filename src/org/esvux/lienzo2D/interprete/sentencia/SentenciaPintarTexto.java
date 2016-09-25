package org.esvux.lienzo2D.interprete.sentencia;

import java.awt.Color;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.editor.Publisher;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.expresion.Expresion;

/**
 *
 * @author esvux
 */
public class SentenciaPintarTexto extends Sentencia {

    public SentenciaPintarTexto(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        boolean hayError = false;
        Nodo hijoX = sentencia.getHijo(0);
        Resultado solX = new Expresion(hijoX, ctx).resolverExpresion();
        if (!(Check.EsTipo(solX.getTipo(), Tipos.T_ENTERO) || Check.EsTipo(solX.getTipo(), Tipos.T_DOBLE))) {
            manager.addErrorSemantico(hijoX.getFila(), hijoX.getColumna(), "Se esperaba un entero para el parámetro 'Posición X' del método Pintar_S.");
            hayError = true;
        }
        
        Nodo hijoY = sentencia.getHijo(1);
        Resultado solY = new Expresion(hijoY, ctx).resolverExpresion();
        if (!(Check.EsTipo(solY.getTipo(), Tipos.T_ENTERO) || Check.EsTipo(solY.getTipo(), Tipos.T_DOBLE))) {
            manager.addErrorSemantico(hijoY.getFila(), hijoY.getColumna(), "Se esperaba un entero para el parámetro 'Posición Y' del método Pintar_S.");
            hayError = true;
        }
        
        Nodo hijoColor = sentencia.getHijo(2);        
        Resultado solColor = new Expresion(hijoColor, ctx).resolverExpresion();
        if (!Check.EsTipo(solColor.getTipo(), Tipos.T_CADENA)) {
            manager.addErrorSemantico(hijoColor.getFila(), hijoColor.getColumna(), "Se esperaba una cadena para el parámetro 'Color' del método Pintar_S.");
            hayError = true;
        }
        
        Nodo hijoTexto = sentencia.getHijo(3);
        Resultado solTexto = new Expresion(hijoTexto, ctx).resolverExpresion();
        if (!Check.EsTipo(solTexto.getTipo(), Tipos.T_CADENA)) {
            manager.addErrorSemantico(hijoTexto.getFila(), hijoTexto.getColumna(), "Se esperaba una cadena para el parámetro 'Texto' del método Pintar_S.");
            hayError = true;
        }

        if(hayError){
            return Resultado.ejecucionErronea();
        }

        Color color;
        try{
            color = Color.decode(solColor.getValor());
        }catch(NumberFormatException ex){
            manager.addErrorSemantico(hijoColor.getFila(), hijoColor.getColumna(), "Existe un error en el formato de la cadena para el parámetro 'Color' del método Pintar_S.");
            return Resultado.ejecucionErronea();
        }
                
        int x = Check.ToEntero(solX.getValor());
        int y = Check.ToEntero(solY.getValor());
        String texto = solTexto.getValor();
        
        Publisher.getInstance().getCanvas().addTexto(x, y, texto, color);
        Publisher.getInstance().repaint();

        return Resultado.ejecucionCorrecta();
    }
    
}
