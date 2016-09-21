/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esvux.lienzo2D.interprete.expresion;

import org.esvux.lienzo2D.compilador.Tipos;
import org.esvux.lienzo2D.interprete.Resultado;

/**
 *
* @autor esvux
 */
public class Relacional {
    
    public static Resultado nulo(Resultado izq){      
        Resultado respuesta = new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        if(izq.getValor()==null){
            // La variable existe pero no se ha asignado ningun valor 
            respuesta.setValor("false");
            return respuesta;
        }
        // La variable existe y se ha asignado al menos una vez
        respuesta.setValor("true");
        return respuesta;            
    }

    public static Resultado igual(Resultado izq, Resultado der){        
       Resultado respuesta= new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // Si los tipos no son iguales 
        if(!(Check.EsTipo(izq.getTipo(), der.getTipo()))){
            respuesta.setValor("false");
            return respuesta;
        }
        // Tipos iguales, pero los valores no son iguales 
        if(!(izq.getValor().equals(der.getValor()))){
            respuesta.setValor("false");
            return respuesta;            
        }
        // Tipos y valores iguales 
        respuesta.setValor("true");
        return respuesta;
    }
    
    public static Resultado noIgual(Resultado izq, Resultado der){   
        Resultado respuesta= new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // Si los tipos no son iguales 
        if(!(Check.EsTipo(izq.getTipo(), der.getTipo()))){
            respuesta.setValor("true");
            return respuesta;
        }
        // Tipos iguales, pero los valores no son iguales 
        if(!(izq.getValor().equals(der.getValor()))){
            respuesta.setValor("true");
            return respuesta;            
        }
        // Tipos y valores iguales 
        respuesta.setValor("false");
        return respuesta;
    }

    public static Resultado mayor(Resultado izq, Resultado der){   
        Resultado respuesta = new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // izq es tipo ENTERO, DOBLE 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(izq.getTipo(),Tipos.T_DOBLE)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(izq.getValor()),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo BOOLEAN 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_BOOLEAN)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo CARACTER 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_CARACTER)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        return new Resultado();
    }

    public static Resultado menor(Resultado izq, Resultado der){        
        Resultado respuesta = new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // izq es tipo ENTERO, DOBLE 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(izq.getTipo(),Tipos.T_DOBLE)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(izq.getValor()),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo BOOLEAN 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_BOOLEAN)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo CARACTER 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_CARACTER)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenor(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        return new Resultado();
    }

    public static Resultado mayorIgual(Resultado izq, Resultado der){        
        Resultado respuesta = new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // izq es tipo ENTERO, DOBLE 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(izq.getTipo(),Tipos.T_DOBLE)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo BOOLEAN 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_BOOLEAN)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo CARACTER 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_CARACTER)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMayorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        return new Resultado();
    }

    public static Resultado menorIgual(Resultado izq, Resultado der){        
        Resultado respuesta = new Resultado();
        respuesta.setTipo(Tipos.T_BOOLEAN);
        // izq es tipo ENTERO, DOBLE 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(izq.getTipo(),Tipos.T_DOBLE)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(izq.getValor()),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo BOOLEAN 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_BOOLEAN)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.BooleanToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        // izq es tipo CARACTER 
        if (Check.EsTipo(izq.getTipo(),Tipos.T_CARACTER)){
            // der es tipo ENTERO, DOBLE
            if (Check.EsTipo(der.getTipo(),Tipos.T_ENTERO) || Check.EsTipo(der.getTipo(),Tipos.T_DOBLE)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(der.getValor())));
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if (Check.EsTipo(der.getTipo(),Tipos.T_BOOLEAN)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.BooleanToEntero(der.getValor()))));
                return respuesta;
            }
            // der es tipo CARACTER 
            if (Check.EsTipo(der.getTipo(),Tipos.T_CARACTER)){
                respuesta.setValor(Check.EsMenorIgual(Check.ToDoble(Check.CaracterToEntero(izq.getValor())),Check.ToDoble(Check.CaracterToEntero(der.getValor()))));
                return respuesta;
            }                    
        }
        return new Resultado();
    }    
}
