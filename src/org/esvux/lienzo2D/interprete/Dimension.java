/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.esvux.lienzo2D.interprete;

import java.util.ArrayList;

/**
 *
* @autor esvux
 */
public class Dimension {

    private Integer capacidad; 
    private ArrayList<Integer> detalle;

    public Dimension() {
        this.capacidad = 0;
        this.detalle = new ArrayList<>();
    }

    public Dimension(Integer capacidad, ArrayList<Integer> detalle) {
        this.capacidad = capacidad;
        this.detalle = detalle;
    }
    
    public void addDetalle(Integer detalle){
        this.detalle.add(detalle);
    }

    /**
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the detalle
     */
    public ArrayList<Integer> getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ArrayList<Integer> detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        String reporte="Detalle: ";
        String tempDetalle="Detalle: ";
        for(Integer i: this.detalle){
            reporte = tempDetalle + i;
            tempDetalle = reporte + ",";
        }
        reporte = reporte + "    Capacidad:" + this.capacidad;
        return reporte;
    }
    
    

}
