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
public class SentenciaPintarOvalRect extends Sentencia {

    public SentenciaPintarOvalRect(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        boolean hayError = false;
        Nodo hijoX = sentencia.getHijo(0);
        Resultado solX = new Expresion(hijoX, ctx).resolverExpresion();
        if (!Check.EsTipo(solX.getTipo(), Tipos.T_ENTERO)) {
            manager.addErrorSemantico(hijoX.getFila(), hijoX.getColumna(), "Se esperaba un entero para el parámetro 'Posición X' del método Pintar_OR.");
            hayError = true;
        }
        
        Nodo hijoY = sentencia.getHijo(1);
        Resultado solY = new Expresion(hijoY, ctx).resolverExpresion();
        if (!Check.EsTipo(solY.getTipo(), Tipos.T_ENTERO)) {
            manager.addErrorSemantico(hijoY.getFila(), hijoY.getColumna(), "Se esperaba un entero para el parámetro 'Posición Y' del método Pintar_OR.");
            hayError = true;
        }

        Nodo hijoAncho = sentencia.getHijo(3);
        Resultado solAncho = new Expresion(hijoAncho, ctx).resolverExpresion();
        if (!Check.EsTipo(solAncho.getTipo(), Tipos.T_ENTERO)) {
            manager.addErrorSemantico(hijoAncho.getFila(), hijoAncho.getColumna(), "Se esperaba un entero para el parámetro 'Ancho' del método Pintar_OR.");
            hayError = true;
        }

        Nodo hijoAlto = sentencia.getHijo(4);
        Resultado solAlto = new Expresion(hijoAlto, ctx).resolverExpresion();
        if (!Check.EsTipo(solAlto.getTipo(), Tipos.T_ENTERO)) {
            manager.addErrorSemantico(hijoAlto.getFila(), hijoAlto.getColumna(), "Se esperaba un entero para el parámetro 'Alto' del método Pintar_OR.");
            hayError = true;
        }
                
        Nodo hijoColor = sentencia.getHijo(2);        
        Resultado solColor = new Expresion(hijoColor, ctx).resolverExpresion();
        if (!Check.EsTipo(solColor.getTipo(), Tipos.T_CADENA)) {
            manager.addErrorSemantico(hijoColor.getFila(), hijoColor.getColumna(), "Se esperaba una cadena para el parámetro 'Color' del método Pintar_OR.");
            hayError = true;
        }
        
        Nodo hijoFigura = sentencia.getHijo(5);
        Resultado solFigura = new Expresion(hijoFigura, ctx).resolverExpresion();
        if (!Check.EsTipo(solFigura.getTipo(), Tipos.T_CARACTER)) {
            manager.addErrorSemantico(hijoFigura.getFila(), hijoFigura.getColumna(), "Se esperaba un caracter para el parámetro 'Figura' del método Pintar_OR.");
            hayError = true;
        }

        if(hayError){
            return Resultado.ejecucionErronea();
        }

        Color color;
        try{
            color = Color.decode(solColor.getValor());
        }catch(NumberFormatException ex){
            manager.addErrorSemantico(hijoColor.getFila(), hijoColor.getColumna(), "Existe un error en el formato de la cadena para el parámetro 'Color' del método Pintar_OR.");
            return Resultado.ejecucionErronea();
        }
                
        int x = Check.ToEntero(solX.getValor());
        int y = Check.ToEntero(solY.getValor());        
        int alto = Check.ToEntero(solAlto.getValor());
        int ancho = Check.ToEntero(solAncho.getValor());
        
        if(solFigura.getValor().compareToIgnoreCase("O")==0){
            
            Publisher.getInstance().getCanvas().addOvalo(x, y, ancho, alto, color);
            Publisher.getInstance().repaint();
            return Resultado.ejecucionCorrecta();
        }
        
        if(solFigura.getValor().compareToIgnoreCase("R")==0){
            Publisher.getInstance().getCanvas().addRectangulo(x, y, ancho, alto, color);
            Publisher.getInstance().repaint();
            return Resultado.ejecucionCorrecta();
        }
        
        manager.addErrorSemantico(hijoFigura.getFila(), hijoFigura.getColumna(), "Se esperaba 'o'|'O'|'r'|'R' para el parámetro 'Figura' del método Pintar_OR.");
        return Resultado.ejecucionErronea();
    }
    
}
