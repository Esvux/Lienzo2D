package org.esvux.lienzo2D.interprete.sentencia;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Elemento;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.expresion.Aritmetica;
import org.esvux.lienzo2D.interprete.expresion.Check;
import org.esvux.lienzo2D.interprete.expresion.Expresion;

/**
 *
 * @author esvux
 */
public class SentenciaAsignacion extends Sentencia{

    public SentenciaAsignacion(Nodo sentencia) {
        super(sentencia, false);
    }

    @Override
    public Resultado ejecutar(Contexto ctx, int nivel) {
        if(sentencia.getHijo(0)==null){
            return Resultado.ejecucionCorrecta();
        }
        if (Check.EsTipo(sentencia.getHijo(0).getRol(), Tipos.ARREGLO)) {
           asignacionDeArreglos(ctx);
        } else {
           asignacionDeVariables(ctx);
        }
        return Resultado.ejecucionCorrecta();
    }

    public void asignacionDeVariables(Contexto ctx){       
        Nodo variable = sentencia.getHijo(0);
        Nodo expresion = sentencia.getHijo(1);
        Elemento elemento=Sentencia.buscarEnContexto(ctx, variable);
        // Comprobar que la variable existe 
        if(elemento==null){
          return;
        }
        // Verificar que es tipo arreglo 
        if(elemento.getEsArreglo()){
            ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No existe o no es accesible una variable dentro del contexto que se llame "+variable.getNombre());
            return;
        }
        // Comprobar que la expresión sea valida 
        Resultado valor = new Expresion(expresion, ctx).resolverExpresion();
        if(Check.EsTipo(valor.getTipo(), Tipos.T_ERROR)){
            ManejadorErrores.getInstance().addErrorSemantico(expresion.getFila(), expresion.getColumna(), "Error al resolver la expresión");
            return;
        }
        // Asignación directa: 
        if(sentencia.getNombre().equals("=")){
            if(!elemento.realizarAsignacion(valor)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con la variable "+variable.getNombre());
                return;
            }
        }   
        // Asignación de aumento 
        if(sentencia.getNombre().equals("+=")){
            Resultado respuesta = Aritmetica.suma(elemento.obtenerValor(), valor);
            if(!elemento.realizarAsignacion(respuesta)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con la variable "+variable.getNombre());
                return;
            }            
        }
        // Asignación de disminución 
        if(sentencia.getNombre().equals("-=")){
            Resultado respuesta = Aritmetica.resta(elemento.obtenerValor(), valor);
            if(!elemento.realizarAsignacion(respuesta)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con la variable "+variable.getNombre());
            }      
        }
    }
    
    public void asignacionDeArreglos(Contexto ctx){
        Nodo variable = sentencia.getHijo(0);
        Nodo expresion = sentencia.getHijo(1);
        Elemento elemento=Sentencia.buscarEnContexto(ctx, variable);
        // Comprobar que la variable existe 
        if(elemento==null){
          return;
        }
        // Verificar que es tipo arreglo 
        if(!elemento.getEsArreglo()){
            ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No existe o no es accesible una variable dentro del contexto que se llame "+variable.getNombre());
            return;
        }
        // Comprobar que la expresión sea valida 
        Resultado valor = new Expresion(expresion, ctx).resolverExpresion();
        if(Check.EsTipo(valor.getTipo(), Tipos.T_ERROR)){
            ManejadorErrores.getInstance().addErrorSemantico(expresion.getFila(), expresion.getColumna(), "Error al resolver la expresión");
            return;
        }
        // Obtener la(s) solucion(es) de las dimensiones 
        ArrayList<Resultado> dimensiones = Sentencia.resolverDimensiones(variable.getHijo(0), ctx);
        // Verificar que contengan el mismo tamaño 
        if(dimensiones.size()!=elemento.getDimensiones().size()){
            ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "Las dimensiones indicadas no concuerdan");
            return;
        }
        // Obtener la posición por medio de mapeo 
        Integer posicion = elemento.obtenerPosicion(dimensiones);
        if(posicion==-1){
            // Nota: El error ya se ha contemplado al resolverDimensiones 
            return;            
        }
        // Comprobar si la posición es valida 
        if(posicion < 0 || posicion >= elemento.getCapacidad()){
            ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "La posicion "+posicion+" que se ha solicitado una posición que no existe para el arreglo "+variable.getNombre());
            return;
        }
        
        // Asignación directa: 
        if(sentencia.getNombre().equals("=")){
            if(!elemento.realizarAsignacionArreglo(valor, posicion)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con el arreglo "+variable.getNombre());
                return;
            }
        }           
        // Asignación de aumento 
        if(sentencia.getNombre().equals("+=")){
            Resultado respuesta = Aritmetica.suma(elemento.obtenerValorArrelgo(posicion), valor);
            if(!elemento.realizarAsignacionArreglo(respuesta, posicion)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con el arreglo "+variable.getNombre());
                return;
            }
        }
        // Asignación de disminución 
        if(sentencia.getNombre().equals("-=")){
            Resultado respuesta = Aritmetica.resta(elemento.obtenerValorArrelgo(posicion), valor);
            if(!elemento.realizarAsignacionArreglo(respuesta, posicion)){
                ManejadorErrores.getInstance().addErrorSemantico(variable.getFila(), variable.getColumna(), "No se ha podido realizar la asignación, tipo no compatible con el arreglo "+variable.getNombre());
            }
        }
    }

}
