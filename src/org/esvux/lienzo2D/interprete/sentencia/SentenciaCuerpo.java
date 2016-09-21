package org.esvux.lienzo2D.interprete.sentencia;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.editor.Editor;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Check;

/**
 *
 * @author esvux
 */
public class SentenciaCuerpo extends Sentencia {

    public SentenciaCuerpo(Nodo sentencia, boolean permiteInterrupciones) {
        super(sentencia, permiteInterrupciones);
    }
    
    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        if(! Check.EsTipo(sentencia.getRol(), Tipos.ACCIONES)){
            manager.addErrorGeneral("Error inesperado en la ejecucion del programa.");
            return Resultado.ejecucionErronea();
        }
        Object[] cuerpo = sentencia.getHijos().toArray();
        for (int i = 0; i < cuerpo.length; i++) {
            Nodo accion = (Nodo)cuerpo[i];
            int rol = accion.getRol();
            Resultado resultado = null;
            if (rol == Tipos.ASIGNACION) 
            {//Asignación directa o sobreasignación
                SentenciaAsignacion stmnt = new SentenciaAsignacion(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.ARITMETICA) 
            {//Incremento o Decremento
                SentenciaIncDec stmnt = new SentenciaIncDec(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.LLAMADA) 
            {//Llamada a un método
                SentenciaLlamada stmnt = new SentenciaLlamada(accion, false);
                resultado = stmnt.ejecutar(ctx, 1);
            }
            else if (rol == Tipos.DECLARACIONES) 
            {//Declaración de variables locales
                SentenciaDeclaraciones stmnt = new SentenciaDeclaraciones(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.FLUJO_SI) 
            {//If [ condición · acciones_SI · acciones_SINO ]
                SentenciaSi stmnt = new SentenciaSi(accion, permiteInterrupciones);
                resultado = stmnt.ejecutar(ctx, nivel + 1);
            }
            else if (rol == Tipos.FLUJO_COMPROBAR) 
            {//Switch [ casos -> [ caso+ · defecto ] ]
                SentenciaComprobar stmnt = new SentenciaComprobar(accion, permiteInterrupciones);
                resultado = stmnt.ejecutar(ctx, nivel + 1);
            }
            else if (rol == Tipos.BUCLE_PARA) 
            {//For [ [ declaración | asignacion ] · condición · finalización · acciones ]
                SentenciaPara stmnt = new SentenciaPara(accion, permiteInterrupciones);
                resultado = stmnt.ejecutar(ctx, nivel + 1);
            }
            else if (rol == Tipos.BUCLE_MIENTRAS) 
            {//While [ condición · acciones ]
                SentenciaMientras stmnt = new SentenciaMientras(accion, permiteInterrupciones);
                resultado = stmnt.ejecutar(ctx, nivel + 1);
            }
            else if (rol == Tipos.BUCLE_HACER) 
            {//Do While [ condición · acciones ]
                SentenciaHacer stmnt = new SentenciaHacer(accion, permiteInterrupciones);
                resultado = stmnt.ejecutar(ctx, nivel + 1);
            }
            else if (rol == Tipos.SALIR) 
            {//Break
                if(this.permiteInterrupciones){
                    return Resultado.BREAK();
                } else {
                    resultado = Resultado.ejecucionErronea();
                    manager.addErrorSemantico(accion.getFila(), accion.getColumna(), "Sentencia 'Salir$' fuera de lugar.");
                }
            }
            else if (rol == Tipos.CONTINUAR) 
            {//Continue
                if(this.permiteInterrupciones){
                    return Resultado.CONTINUE();
                } else {
                    resultado = Resultado.ejecucionErronea();
                    manager.addErrorSemantico(accion.getFila(), accion.getColumna(), "Sentencia 'Continuar$' fuera de lugar.");
                }
            }
            else if (rol == Tipos.PINTAR_P) 
            {//PintarPunto [ x · y · color · diámetro]
                SentenciaPintarCirculo stmnt = new SentenciaPintarCirculo(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.PINTAR_OR) 
            {//PintarÓvaloRectángulo [ x · y · color · ancho · alto · figura ]
                SentenciaPintarOvalRect stmnt = new SentenciaPintarOvalRect(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.PINTAR_S) 
            {//PintarTexto [ x · y · color · texto ]
                SentenciaPintarTexto stmnt = new SentenciaPintarTexto(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.ORDENAR) 
            {//Ordenar [ id ]
                SentenciaOrdenar stmnt = new SentenciaOrdenar(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.SUMARIZAR) 
            {//Sumarizar [ id | asignación ]
                SentenciaSumarizar stmnt = new SentenciaSumarizar(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }
            else if (rol == Tipos.RETORNA) 
            {//Return [ expresión ]
                SentenciaRetorno stmnt = new SentenciaRetorno(accion);
                resultado = stmnt.ejecutar(ctx, nivel);
            }else if(rol == Tipos.PAUSA){
                resultado = Resultado.ejecucionCorrecta();
                if(debug){
                    try {
                        Editor.getInstance().setPosicion(accion.getValor(), accion.getFila(), accion.getColumna());
                        Thread.sleep(tiempoPausa);
                        while(pausa){
                            Thread.sleep(10);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SentenciaCuerpo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if(resultado==null){
                manager.addErrorGeneral("Instrucción fuera de control(" + accion.getNombre()+").");
                continue;
            }
            if(resultado.esRetorno() || 
                    (permiteInterrupciones && 
                        (resultado.esContinuar() || resultado.esSalir()))){
                return resultado;
            }
        }
        //Termina la ejecución del cuerpo
        return Resultado.ejecucionCorrecta();
    }

}
