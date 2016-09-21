package org.esvux.lienzo2D.interprete;

import org.esvux.lienzo2D.AST.Lienzo;
import org.esvux.lienzo2D.AST.Metodo;
import org.esvux.lienzo2D.interprete.sentencia.Sentencia;
import org.esvux.lienzo2D.interprete.sentencia.SentenciaCuerpo;


/**
 *
 * @author esvux
 */
public class Interprete {

    Lienzo principal;
    
    public Interprete(Lienzo lienzo, Contexto ctxInicial) {
        this.principal = lienzo;
        Sentencia.setContextoGlobal(ctxInicial);
        Sentencia.setPrincipal(lienzo);
        LostCanvas.resetInstance();
    }
    
    public void ejecutarMain(){
        System.out.println("Ejecución iniciada...");
        Metodo metodo = principal.getPrincipal();
        SentenciaCuerpo main = new SentenciaCuerpo(metodo.getAcciones(), false);
        main.ejecutar(new Contexto(), 1);//Uno (1) es el nivel de contexto dentro del método...
        System.out.println("Finalizó la ejecución...");
    }
    
}
