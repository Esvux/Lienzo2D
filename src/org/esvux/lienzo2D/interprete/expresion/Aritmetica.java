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
public class Aritmetica {
    
    public static Resultado aumento(Resultado izq){
        Resultado respuesta = new Resultado();
        int resultado;
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            respuesta.setTipo(Tipos.T_ENTERO);
            resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()))+1;
            respuesta.setValor(""+resultado);
        }
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            Double valor = Check.ToDoble(izq.getValor());
            valor++;
            respuesta.setValor(""+valor);
            respuesta.setTipo(Tipos.T_DOBLE);
        }
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){
            int valor= Check.ToEntero(izq.getValor());
            valor++;
            respuesta.setValor(""+valor);
            respuesta.setTipo(Tipos.T_ENTERO);
        }
        return respuesta;
    }

    public static Resultado decremento(Resultado izq){
        Resultado respuesta = new Resultado();
        int resultado;
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            respuesta.setTipo(Tipos.T_ENTERO);
            resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()))-1;
            respuesta.setValor(""+resultado);
        }
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            Double valor = Check.ToDoble(izq.getValor());
            valor--;
            respuesta.setValor(""+valor);
            respuesta.setTipo(Tipos.T_DOBLE);
        }
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){
            int valor= Check.ToEntero(izq.getValor());
            valor--;
            respuesta.setValor(""+valor);
            respuesta.setTipo(Tipos.T_ENTERO);
        }
        return respuesta;
    }

    public static Resultado suma(Resultado izq, Resultado der){
        Resultado respuesta = new Resultado();        
        // CADENA || CADENA     
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CADENA)||Check.EsTipo(der.getTipo(), Tipos.T_CADENA)){
            respuesta.setTipo(Tipos.T_CADENA);
            respuesta.setValor(izq.getValor()+der.getValor());
            return respuesta;
        }      
        // izq es de tipo ENTERO 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){            
            // der es tipo DOBLE    
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(izq.getValor());
                resultado += Check.ToDoble(der.getValor());
                respuesta.setValor(resultado.toString());
                return respuesta;
            }
            respuesta.setTipo(Tipos.T_ENTERO);
            int resultado = Check.ToEntero(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado += Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado += Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
               resultado += Check.ToEntero(der.getValor());
            }          
            respuesta.setValor(""+resultado);
            return respuesta;
        }        
        // izq es de tipo DOBLE 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            respuesta.setTipo(Tipos.T_DOBLE);
            Double resultado = Check.ToDoble(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado += Check.ToDoble(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado += Check.ToDoble(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado += Check.ToDoble(der.getValor());
            }
            respuesta.setValor(resultado.toString());
            return respuesta;
        }
        // izq es de tipo BOOLEAN 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                respuesta.setTipo(Tipos.T_ENTERO);
                int resultado = Check.ToEntero(Check.BooleanToEntero(izq.getValor()));
                resultado += Check.ToEntero(Check.CaracterToEntero(der.getValor()));
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                respuesta.setTipo(Tipos.T_BOOLEAN);
                respuesta.setValor("false");
                if(Check.ToBooleano(izq.getValor()) || Check.ToBooleano(der.getValor())){
                    respuesta.setValor("true");
                }
                return respuesta;
            }
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado += Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_ENTERO);
                int resultado = Check.ToEntero(Check.BooleanToEntero(izq.getValor()));
                resultado += Check.ToEntero(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }            
        }        
        // izq es tipo CARACTER 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                respuesta.setTipo(Tipos.T_CADENA);
                respuesta.setValor(izq.getValor()+der.getValor());
                return respuesta;
            }            
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                respuesta.setTipo(Tipos.T_ENTERO);
                int resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()));
                resultado += Check.ToEntero(Check.BooleanToEntero(der.getValor()));
                respuesta.setValor(""+resultado);
                return respuesta;
            }                        
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
                resultado += Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_ENTERO);
                int resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()));
                resultado += Check.ToEntero(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }           
        }
        return new Resultado();
    }
    
    public static Resultado resta(Resultado izq, Resultado der){
        Resultado respuesta = new Resultado();        
        // izq es de tipo ENTERO 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){            
            // der es tipo DOBLE    
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(izq.getValor());
                resultado -= Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            int resultado = Check.ToEntero(izq.getValor());
            respuesta.setTipo(Tipos.T_ENTERO);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado -= Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado -= Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
               resultado -= Check.ToEntero(der.getValor());
            }          
            respuesta.setValor(""+resultado);
            return respuesta;
        }        
        // izq es de tipo DOBLE 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            respuesta.setTipo(Tipos.T_DOBLE);
            Double resultado = Check.ToDoble(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado -= Check.ToDoble(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado -= Check.ToDoble(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado -= Check.ToDoble(der.getValor());
            }
            respuesta.setValor(resultado.toString());
            return respuesta;
        }
        // izq es de tipo BOOLEAN 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado -= Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            int resultado = Check.ToEntero(Check.BooleanToEntero(izq.getValor()));
            respuesta.setTipo(Tipos.T_ENTERO);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado -= Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado -= Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                resultado -= Check.ToEntero(der.getValor());
            }            
            respuesta.setValor(""+resultado);
            return respuesta;
        }        
        // izq es tipo CARACTER 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
                resultado -= Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            int resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()));
            respuesta.setTipo(Tipos.T_ENTERO);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado -= Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }            
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado -= Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }                        
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                resultado -= Check.ToEntero(der.getValor());
            }           
            respuesta.setValor(""+resultado);
            return respuesta;
       }
        return new Resultado();
    }
    
    public static Resultado multiplicacion(Resultado izq, Resultado der){
        Resultado respuesta = new Resultado(); 
        // izq es de tipo ENTERO 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(izq.getValor());
                resultado = resultado * Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            respuesta.setTipo(Tipos.T_ENTERO);
            int resultado = Check.ToEntero(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado * Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado = resultado * Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO 
            if(Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado * Check.ToEntero(der.getValor());
            }          
            respuesta.setValor(""+resultado);
            return respuesta;
        }   
        // izq es de tipo DOBLE 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            respuesta.setTipo(Tipos.T_DOBLE);
            Double resultado = Check.ToDoble(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado * Check.ToDoble(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado = resultado * Check.ToDoble(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado * Check.ToDoble(der.getValor());
            }
            respuesta.setValor(""+resultado);
            return respuesta;
        }
        // izq es de tipo BOOLEAN 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado = resultado * Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                respuesta.setTipo(Tipos.T_BOOLEAN);
                respuesta.setValor("false");
                if(Check.ToBooleano(izq.getValor()) && Check.ToBooleano(der.getValor())){
                    respuesta.setValor("true");
                }
                return respuesta;
            }
            respuesta.setTipo(Tipos.T_ENTERO);
            int resultado = Check.ToEntero(Check.BooleanToEntero(izq.getValor()));
            // der es tipo ENTERO 
            if(Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado * Check.ToEntero(der.getValor());
            }            
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado * Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }
            respuesta.setValor(""+resultado);
            return respuesta;
        }        
        // izq es tipo CARACTER 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
               respuesta.setTipo(Tipos.T_DOBLE);
               Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
               resultado = resultado * Check.ToDoble(der.getValor());
               respuesta.setValor(""+resultado);
               return respuesta; 
            }
            respuesta.setTipo(Tipos.T_ENTERO);
            int resultado = Check.ToEntero(Check.CaracterToEntero(izq.getValor()));
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado * Check.ToEntero(Check.CaracterToEntero(der.getValor()));
            }            
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado = resultado * Check.ToEntero(Check.BooleanToEntero(der.getValor()));
            }                        
            // der es tipo ENTERO 
            if(Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado * Check.ToEntero(der.getValor());
            }           
            respuesta.setValor(""+resultado);
            return respuesta;
        }        
        return new Resultado();
    }

    public static Resultado division(Resultado izq, Resultado der){
        Resultado respuesta = new Resultado(); 
  
        // izq es de tipo ENTERO 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){
            Double resultado = Check.ToDoble(izq.getValor());
            respuesta.setTipo(Tipos.T_DOBLE);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado / Check.ToDoble(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado = resultado / Check.ToDoble(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado / Check.ToDoble(der.getValor());
            }          
            respuesta.setValor(""+resultado);
            return respuesta;
        }   
        // izq es de tipo DOBLE 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            Double resultado = Check.ToDoble(izq.getValor());
            respuesta.setTipo(Tipos.T_DOBLE);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                resultado = resultado / Check.ToDoble(Check.CaracterToEntero(der.getValor()));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                resultado = resultado / Check.ToDoble(Check.BooleanToEntero(der.getValor()));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                resultado = resultado / Check.ToDoble(der.getValor());
            }
            respuesta.setValor(""+resultado);
            return respuesta;
        }
        // izq es de tipo BOOLEAN 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                return respuesta;
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado = resultado / Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }            
        }        
        // izq es tipo CARACTER 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                return respuesta;
            }            
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                return respuesta;
            }                        
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
                resultado = resultado / Check.ToDoble(der.getValor());
                respuesta.setValor(""+resultado);
                return respuesta;
            }           
        }
        return new Resultado();
    }
    
    public static Resultado potencia(Resultado izq, Resultado der){
        Resultado respuesta = new Resultado();
        // izq es de tipo ENTERO 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_ENTERO)){
            Double resultado = Check.ToDoble(izq.getValor());
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
               respuesta.setTipo(Tipos.T_DOBLE);
               resultado = Math.pow(resultado, Check.ToDoble(der.getValor()));
               respuesta.setValor(""+resultado);
               return respuesta;
            }
            respuesta.setTipo(Tipos.T_ENTERO);
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
               resultado = Math.pow(resultado, Check.ToDoble(Check.CaracterToEntero(der.getValor())));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
               resultado = Math.pow(resultado, Check.ToDoble(Check.BooleanToEntero(der.getValor())));
            }
            // der es tipo ENTERO 
            if(Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
               resultado = Math.pow(resultado, Check.ToDoble(der.getValor()));
            }          
            respuesta.setValor(Check.DobleToEntero(resultado));
            return respuesta;
        }   
        // izq es de tipo DOBLE 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_DOBLE)){
            respuesta.setTipo(Tipos.T_DOBLE);
            Double resultado = Check.ToDoble(izq.getValor());
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
               resultado = Math.pow(resultado, Check.ToDoble(Check.CaracterToEntero(der.getValor())));
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
               resultado = Math.pow(resultado, Check.ToDoble(Check.BooleanToEntero(der.getValor())));
            }
            // der es tipo ENTERO, DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE) || Check.EsTipo (der.getTipo(), Tipos.T_ENTERO)){
               resultado = Math.pow(resultado, Check.ToDoble(der.getValor()));
            }
            respuesta.setValor(""+resultado);
            return respuesta;
        }
        // izq es de tipo BOOLEAN 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_BOOLEAN)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado = Math.pow(resultado,Check.ToDoble(der.getValor()));
                respuesta.setValor(""+resultado);
                return respuesta;
            }            
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                return respuesta;
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_ENTERO);
                Double resultado = Check.ToDoble(Check.BooleanToEntero(izq.getValor()));
                resultado = Math.pow(resultado,Check.ToDoble(der.getValor()));
                respuesta.setValor(Check.DobleToEntero(resultado));
                return respuesta;
            }            
        }        
        // izq es tipo CARACTER 
        if(Check.EsTipo(izq.getTipo(), Tipos.T_CARACTER)){
            // der es tipo DOBLE 
            if(Check.EsTipo(der.getTipo(), Tipos.T_DOBLE)){
                respuesta.setTipo(Tipos.T_DOBLE);
                Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
                resultado = Math.pow(resultado,Check.ToDoble(der.getValor()));
                respuesta.setValor(""+resultado);
                return respuesta;
            }            
            // der es tipo CARACTER 
            if(Check.EsTipo(der.getTipo(), Tipos.T_CARACTER)){
                return respuesta;
            }
            // der es tipo BOOLEAN 
            if(Check.EsTipo(der.getTipo(), Tipos.T_BOOLEAN)){
                return respuesta;
            }
            // der es tipo ENTERO 
            if(Check.EsTipo(der.getTipo(), Tipos.T_ENTERO)){
                respuesta.setTipo(Tipos.T_ENTERO);
                Double resultado = Check.ToDoble(Check.CaracterToEntero(izq.getValor()));
                resultado = Math.pow(resultado,Check.ToDoble(der.getValor()));
                respuesta.setValor(Check.DobleToEntero(resultado));
                return respuesta;
            }            
        }        
        return new Resultado();
    }
}
