package org.esvux.lienzo2D.interprete;

import java.util.ArrayList;
import org.esvux.lienzo2D.AST.Nodo;
import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.expresion.Check;

/**
 *
 * @author esvux
 */
public class Resultado {
    
    private static final int OK = 20;
    private static final int FAIL = 30;
    private static final int BREAK = 40;
    private static final int CONTINUE = 50;
    private static final int RETURN = 60;
    
    private Integer tipo;
    private String valor;
    private Integer status;
    private ArrayList<Resultado> soluciones;
    private Integer dimensiones;
    private boolean esArreglo;    
    
    public Resultado() {
        this.tipo = Tipos.T_ERROR;
        this.valor = null;
        this.status = OK;
        this.soluciones = null;
        this.dimensiones = 0;
        this.esArreglo = false;
    }
    
    public Resultado(Integer tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.status = OK;
        this.soluciones = null;
        this.dimensiones = 0;
        this.esArreglo = false;
    }
    
    private Resultado(Integer tipo, String valor, Integer status) {
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
        this.soluciones = null;
        this.dimensiones = 0;
        this.esArreglo = false;
    }
    
    public static Resultado BREAK() {
        return new Resultado(Tipos.T_VOID, "", BREAK);
    }
    
    public static Resultado CONTINUE() {
        return new Resultado(Tipos.T_VOID, "", CONTINUE);
    }
    
    public static Resultado ejecucionCorrecta() {
        return new Resultado(Tipos.T_VOID, "", OK);
    }
    
    public static Resultado ejecucionErronea() {
        return new Resultado(Tipos.T_ERROR, "", FAIL);
    }
    
    public Integer getTipo() {
        return tipo;
    }
    
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public boolean esRetorno() {
        return this.status.compareTo(RETURN) == 0;
    }
    
    public boolean esContinuar() {
        return this.status.compareTo(CONTINUE) == 0;
    }
    
    public boolean esSalir() {
        return this.status.compareTo(BREAK) == 0;
    }
    
    public boolean esOk(){
        return this.status.compareTo(OK) == 0;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public ArrayList<Resultado> getSoluciones() {
        return soluciones;
    }
    
    public void setSoluciones(ArrayList<Resultado> valores) {
        this.soluciones = valores;
    }
    
    public Integer getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(Integer dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    public boolean esArreglo() {
        return esArreglo;
    }
    
    public void marcarComoArreglo() {
        esArreglo = true;
    }
    
    public void marcarComoRetorno() {
        status = RETURN;
    }
    
    public Nodo getAsNodo() {
        Nodo nodo = new Nodo();
        nodo.setTipo(this.tipo);
        nodo.setValor(this.valor);
        if (Check.EsTipo(this.tipo, Tipos.T_BOOLEAN)) {
            nodo.setRol((Check.ToBooleano(this.valor)) ? Tipos.TRUE : Tipos.FALSE);
        }else if (Check.EsTipo(this.tipo, Tipos.T_CADENA)) {
            nodo.setRol(Tipos.CADENA);
        }else if (Check.EsTipo(this.tipo, Tipos.T_CARACTER)) {
            nodo.setRol(Tipos.CARACTER);
        }else if (Check.EsTipo(this.tipo, Tipos.T_DOBLE)) {
            nodo.setRol(Tipos.DOBLE);
        }else if (Check.EsTipo(this.tipo, Tipos.T_ENTERO)) {
            nodo.setRol(Tipos.ENTERO);
        }
        return nodo;
    }
    
}
