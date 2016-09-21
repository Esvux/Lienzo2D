package org.esvux.lienzo2D.interprete.expresion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Elemento;
import org.esvux.lienzo2D.interprete.Resultado;
import org.esvux.lienzo2D.interprete.sentencia.Sentencia;
import org.esvux.lienzo2D.interprete.sentencia.SentenciaLlamada;

/**
 *
* @autor esvux
 */
public class Expresion {
    
    private Nodo raiz; 
    private Contexto ctx;
    private ManejadorErrores ma;
    
    /**
     * Constructores
     */
    public Expresion() {
        this.raiz=null;
        this.ctx = null;
        this.ma = ManejadorErrores.getInstance("Lienzo_prueba.lz");
    }

    public Expresion(Nodo expresion, Contexto ctx) {
        this.raiz = expresion;
        this.ctx = ctx;
        this.ma = ManejadorErrores.getInstance("Lienzo_prueba.lz");
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public Contexto getCtx() {
        return ctx;
    }

    public void setCtx(Contexto ctx) {
        this.ctx = ctx;
    }
    
    public Resultado resolverExpresion(){
        return resolverExpresion(this.raiz);
    }
    
    private Resultado resolverExpresion(Nodo nodo){
        Resultado respuesta=new Resultado();
        if(nodo.getRol().compareTo(Tipos.EXPRESION)==0){
            respuesta = resolverExpresion(nodo.getHijos().get(0));
            return respuesta;
        }
        if(nodo.getRol().compareTo(Tipos.LOGICA)==0){
            respuesta = resolverLogica(nodo);
            return respuesta;
        }
        if(nodo.getRol().compareTo(Tipos.RELACIONAL)==0){
            respuesta = resolverRelacional(nodo);
            return respuesta;
        }
        if(nodo.getRol().compareTo(Tipos.ARITMETICA)==0){
            respuesta = resolverAritmetica(nodo);
            return respuesta;
        }  
        if (nodo.getRol().compareTo(Tipos.ORDENAR)==0){
            respuesta = resolverOrdenar(nodo);
            return respuesta;
        }
        if (nodo.getRol().compareTo(Tipos.SUMARIZAR)==0){
            respuesta = resolverSumarizar(nodo);
            return respuesta;
        }
        if (nodo.getRol().compareTo(Tipos.LLAMADA)==0){
            SentenciaLlamada stmnt = new SentenciaLlamada(nodo, false);
            respuesta = stmnt.ejecutar(ctx, 1);
            return respuesta;
        }
        // Este rol aparece del lado derecho en una expresion, se asume que ya ha sido declarado 
        if (nodo.getRol().compareTo(Tipos.ARREGLO)==0){
            respuesta = resolverArreglo(nodo);
            return respuesta;
        }        

        respuesta = resolverHojas(nodo);
        return respuesta;
    }

    private Resultado resolverSumarizar(Nodo op){
        Resultado respuesta = new Resultado();
        // Necesito un arrayList<Resultado> para operar, pero existen dos caminos... 
        ArrayList<Resultado> resultados = new ArrayList<>();
        Nodo valor = op.getHijo(0);
        // 1. nombre de arreglo, debo obtener los valores de un arreglo y convertirlos a un arralist<Resultado>
        if(Check.EsTipo(valor.getRol(), Tipos.ID)){
            Elemento elemento=Sentencia.buscarEnContexto(this.ctx, valor);
            // Comprobar que la variable existe 
            if(elemento==null){
              return respuesta;
            }
            // Comprobar que es tipo arreglo 
            if(!elemento.getEsArreglo()){
                ManejadorErrores.getInstance().addErrorSemantico(valor.getFila(), valor.getColumna(), "No existe o no es accesible un arreglo dentro del contexto que se llame "+valor.getNombre());
                return respuesta;
            }
            resultados = obtenerResultados(elemento.getTipo(), elemento.getValores());
        }
        // 2. asignacion de tipo arreglo, necesito resolverAsignación, método dentro de la clase sentenciasDeclaraciones
        if(Check.EsTipo(valor.getRol(), Tipos.ASIGNACION)){
            resultados = Sentencia.resolverAsignacion(valor, this.ctx);
        }
        respuesta = FuncionPrimitiva.sumarizar(resultados);
        return respuesta;
    }
    
    private ArrayList<Resultado> obtenerResultados(Integer tipo, ArrayList<String> valores){
        ArrayList<Resultado> Resultadoes = new ArrayList<>();
        Resultado Resultado;
        for(String valor: valores){
            Resultado = new Resultado(tipo, valor);
            Resultadoes.add(Resultado);
        }
        return Resultadoes;
    }

    private Resultado resolverOrdenar(Nodo op){
        Resultado respuesta = new Resultado(Tipos.T_ENTERO, "0");
        ArrayList<Resultado> Resultadoes;
        // Obtener el arreglo en cuestion 
        Nodo valor = op.getHijo(0);
        if(!Check.EsTipo(valor.getRol(), Tipos.ID)){
            return respuesta;
        }
        Elemento elemento=Sentencia.buscarEnContexto(this.ctx, valor);
        // Comprobar que la variable existe 
        if(elemento==null){
          return respuesta;
        }
        // Comprobar que es tipo arreglo 
        if(!elemento.getEsArreglo()){
            ManejadorErrores.getInstance().addErrorSemantico(valor.getFila(), valor.getColumna(), "No existe o no es accesible un arreglo dentro del contexto que se llame "+valor.getNombre());
            return respuesta;
        }        
        // Ascendente 
        if(op.getNombre().equalsIgnoreCase("ascendente")){
            Collections.sort(elemento.getValores());
            respuesta.setValor("1");
        }
        // Descendente 
        if(op.getNombre().equalsIgnoreCase("descendente")){
            Comparator<String> comparador = Collections.reverseOrder();
            Collections.sort(elemento.getValores(), comparador);
            respuesta.setValor("1");
        }
        // Verificando que el arreglo sea de un tipo permitido 
        if(!(Check.EsTipo(elemento.getTipo(), Tipos.T_ENTERO) || Check.EsTipo(elemento.getTipo(), Tipos.T_DOBLE)
                || Check.EsTipo(elemento.getTipo(), Tipos.T_CARACTER))){
            ManejadorErrores.getInstance().addErrorSemantico(valor.getFila(), valor.getColumna(), "No se puede hacer un ordenamiento para un arreglo de tipo "+Tipos.getTipoAsString(elemento.getTipo()));
            return respuesta;            
        }
        // Pares 
        if(op.getNombre().equalsIgnoreCase("pares")){
            respuesta = FuncionPrimitiva.ordenarPares(elemento.getTipo(), elemento.getValores());
        }
        // Impares 
        if(op.getNombre().equalsIgnoreCase("pares")){
            respuesta = FuncionPrimitiva.ordenarImpares(elemento.getTipo(), elemento.getValores());
        }
        // Primos 
        if(op.getNombre().equalsIgnoreCase("pares")){
            respuesta = FuncionPrimitiva.ordenarPrimos(elemento.getTipo(), elemento.getValores());
        }
        return respuesta;
    }
    
    private Resultado resolverArreglo(Nodo nodo){
        Resultado respuesta = new Resultado();
        Elemento elemento=Sentencia.buscarEnContexto(this.ctx, nodo);
        // Comprobar que la variable existe 
        if(elemento==null){
          return respuesta;
        }
        // Comprobar que es tipo arreglo 
        if(!elemento.getEsArreglo()){
            ManejadorErrores.getInstance().addErrorSemantico(nodo.getFila(), nodo.getColumna(), "No existe o no es accesible un arreglo dentro del contexto que se llame "+nodo.getNombre());
            return respuesta;
        }
        // Obtener la(s) Resultado(es) de las dimensiones 
        ArrayList<Resultado> dimensiones = Sentencia.resolverDimensiones(nodo.getHijo(0), ctx);
        // Verificar que contengan el mismo tamaño 
        if(dimensiones.size()!=elemento.getDimensiones().size()){
            ManejadorErrores.getInstance().addErrorSemantico(nodo.getFila(), nodo.getColumna(), "Las dimensiones indicadas no concuerdan");
            return respuesta;
        }
        // Obtener la posición por medio de mapeo 
        Integer posicion = elemento.obtenerPosicion(dimensiones);
        if(posicion==-1){
            // Nota: El error ya se ha contemplado al resolverDimensiones 
            return respuesta;            
        }
        // Comprobar si la posición es valida 
        if(posicion < 0 || posicion >= elemento.getCapacidad()){
            ManejadorErrores.getInstance().addErrorSemantico(nodo.getFila(), nodo.getColumna(), "La posicion "+posicion+" que se ha solicitado una posición que no existe para el arreglo "+nodo.getNombre());
        return respuesta;
        }
        respuesta = elemento.obtenerValorArrelgo(posicion);
        return respuesta;
    }
    
    private Resultado resolverHojas(Nodo op){
        Resultado respuesta = new Resultado();
        if (op.getRol().compareTo(Tipos.TRUE)==0 || op.getRol().compareTo(Tipos.FALSE)==0
                || op.getRol().compareTo(Tipos.ENTERO)==0
                || op.getRol().compareTo(Tipos.DOBLE)==0
                || op.getRol().compareTo(Tipos.CADENA)==0
                || op.getRol().compareTo(Tipos.CARACTER)==0 ){
            respuesta.setTipo(op.getTipo());
            respuesta.setValor(op.getValor());
            return respuesta;
        }
        if (op.getRol().compareTo(Tipos.ID)==0){
            Elemento id=Sentencia.buscarEnContexto(this.ctx, op);
            // Comprobar que la variable existe 
            if(id==null){
              return respuesta;
            }
            // Verificar si es un arreglo 
            if(id.getEsArreglo()){
                ArrayList<Resultado> valores = new ArrayList<Resultado>();
                valores = obtenerResultados(id.getTipo(), id.getValores());
                respuesta.setTipo(Tipos.T_ARREGLO);
                respuesta.setSoluciones(valores);
                respuesta.setDimensiones(id.getDimensiones().size());
                respuesta.marcarComoArreglo();
                return respuesta;
            }
            respuesta.setTipo(id.getTipo());
            respuesta.setValor(id.getValor());
        }
        return respuesta;
    }
        
    private Resultado resolverAritmetica(Nodo op){
        Resultado respuesta = new Resultado();
        Resultado izq, der;
        // Evaluar y comprobar tipo del primer hijo         
        izq=resolverExpresion(op.getHijos().get(0));
        if(Check.EsError(izq.getTipo()) || izq.getValor()==null){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Ha presentado un error o no posee valor en su operador izquierdo");
            return respuesta;
        }
        
        // AUMENTO: función 
        if(op.getNombre().equals("++")){
            // Verificar si existe tipo cadena y marcar como error         
            if(Check.EsTipo(izq.getTipo(),Tipos.T_CADENA)||Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
                ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " No acepta tipo Cadena ni Booleano");
                return respuesta;
            }
          return Aritmetica.aumento(izq);
        }
        // DECREMENTO: función 
        if(op.getNombre().equals("--")){
            // Verificar si existe tipo cadena y marcar como error         
            if(Check.EsTipo(izq.getTipo(),Tipos.T_CADENA)||Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
                ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " No acepta tipo Cadena ni Booleano");
                return respuesta;
            }
          return Aritmetica.decremento(izq);
        }

        // Comprobando si existe más de un hijo...
        if (op.getSize()<=1){
            return respuesta;
        }
        // Evaluar y comprobar tipo del segundo hijo         
        der=resolverExpresion(op.getHijos().get(1));
        if(Check.EsError(der.getTipo()) || der.getValor()==null){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Ha presentado un error o no posee valor en su operador derecho");
            return respuesta;
        }
        // SUMA: función 
        if(op.getNombre().equals("+")){
          return Aritmetica.suma(izq, der);
        }
        // Verificar si existe tipo cadena y marcar como error         
        if(Check.EsTipo(izq.getTipo(),Tipos.T_CADENA) || Check.EsTipo(der.getTipo(),Tipos.T_CADENA)){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " No admite operadores tipo Cadena");
            return respuesta;
        }
        // RESTA: función 
        if(op.getNombre().equals("-")){
          return Aritmetica.resta(izq, der);        
        }
        // DIVISION: función 
        if(op.getNombre().equals("/")){
            // El divisor es cero? 
            if(Check.EsCero(der.getTipo(), der.getValor())){
                ma.addErrorSemantico(op.getFila(), op.getColumna(), "La division dentro de cero no se encuentra definida");
                return respuesta;
            }  
          return Aritmetica.division(izq, der);
        }
        // MULTIPLICACION: función 
        if(op.getNombre().equals("*")){
          return Aritmetica.multiplicacion(izq, der);
        }


        // POTENCIA: función 
        if(op.getNombre().equals("^")){
            // izq es cero? 
            if (Check.EsCero(izq.getTipo(), izq.getValor())){
                if(Check.EsCero(der.getTipo(), der.getValor())){
                    ma.addErrorSemantico(op.getFila(), op.getColumna(), "La potencia de cero a cero no se encuentra definida");
                    return respuesta;      
                }
                if(Check.EsNegativo(der.getTipo(), der.getValor())){
                    ma.addErrorSemantico(op.getFila(), op.getColumna(),"La potencia de cero a un negativo no se encuentra definida");
                    return respuesta;            
                }
            }
            respuesta = Aritmetica.potencia(izq, der);
            if (Check.EsTipo(respuesta.getTipo(), Tipos.T_ENTERO) && (
                    Check.ToEntero(respuesta.getValor()) >= 2147483647 || 
                    Check.ToEntero(respuesta.getValor()) <= -2147483647)){
                    ma.addErrorSemantico(op.getFila(), op.getColumna(), "Se ha excedido la capacidad de un entero");
                return new Resultado();
            }     
        }
        return respuesta;
    }
    
    private Resultado resolverRelacional(Nodo op){
        Resultado respuesta = new Resultado();
        Resultado izq, der;
        // Evaluar y comprobar tipo del primer hijo         
        izq=resolverExpresion(op.getHijos().get(0));
        if(Check.EsError(izq.getTipo()) || izq.getValor()==null){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Ha presentado un error o no posee valor en su operador izquierdo");
            return respuesta;
        }
        // NULO: función 
        if(op.getNombre().equals("!&¡")){
          return Relacional.nulo(izq);
        }
        // Comprobando si existe más de un hijo...
        if (op.getSize()<=1){
            return respuesta;
        }
        // Evaluar y comprobar tipo del segundo hijo         
        der=resolverExpresion(op.getHijos().get(1));
        if(Check.EsError(der.getTipo()) || der.getValor()==null){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Ha presentado un error o no posee valor en su operador derecho");
            return respuesta;
        }
        // IGUALACION: función 
        if(op.getNombre().equals("==")){
          return Relacional.igual(izq, der);
        }
        // DIFERENCIACION: función 
        if(op.getNombre().equals("!=")){
          return Relacional.noIgual(izq, der);//        
        }
        // Verificar si existe tipo cadena y marcar como error         
        if(Check.EsTipo(izq.getTipo(),Tipos.T_CADENA) || Check.EsTipo(der.getTipo(),Tipos.T_CADENA)){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " No acepta tipo Cadena como operador");
            return respuesta;
        }
        // MENOR QUE: función 
        if(op.getNombre().equals("<")){
          return Relacional.menor(izq, der);
        }
        // MENOR O IGUAL QUE: función 
        if(op.getNombre().equals("<=")){
          return Relacional.menorIgual(izq, der);
        }
        // MAYOR: función 
        if(op.getNombre().equals(">")){
          return Relacional.mayor(izq, der);
        }
        // MAYOR O IGUAL QUE: función 
        if(op.getNombre().equals(">=")){
          return Relacional.mayorIgual(izq, der);
        }
        return respuesta;
    }
    
    private Resultado resolverLogica(Nodo op){
        Resultado respuesta = new Resultado();
        Resultado izq, der;        
        // Evaluar y comprobar tipo del primer hijo         
        izq=resolverExpresion(op.getHijos().get(0));
        if(!(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN))){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Requiere un operador izquierdo booleano");
            return respuesta;
        }
        // NOT: Caso único de un solo hijo 
        if (op.getNombre().equals("!")){
           return Logica.not(izq);
        }
        // Comprobando si existe más de un hijo...
        if (op.getSize()<=1){
            return respuesta;
        }
        // Evaluar y comprobar tipo del segundo hijo 
        der= resolverExpresion(op.getHijos().get(1));
        if(!(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN))){
            ma.addErrorSemantico(op.getFila(), op.getColumna(), "La operacion "+ op.getNombre() + " Requiere un operador derecho booleano");
            return respuesta;
        }
        // OR: Función 
        if (op.getNombre().equals("||")){
           return Logica.or(izq, der);
        }
        // AND: Función 
        if (op.getNombre().equals("&&")){
           return Logica.and(izq, der);
        }
        // NOR: Función 
        if (op.getNombre().equals("!||")){
           return Logica.nor(izq, der);
        }
        // NAND: Función 
        if (op.getNombre().equals("!&&")){
           return Logica.nand(izq, der);
        }
        // XOR: Función 
        if (op.getNombre().equals("&|")){
           return Logica.xor(izq, der);
        }
        return respuesta;
    }

 }
