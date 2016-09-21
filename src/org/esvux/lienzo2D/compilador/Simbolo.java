package org.esvux.lienzo2D.compilador;

/**
 *
* @autor esvux
 */
public class Simbolo {

    private String nombre;
    private Integer rol;
    private Integer tipo;
    private Integer visibilidad;
    private Boolean conservar;
    private Integer padre;
    private Integer nivel;
    private Integer correlativo;
    private String clase;
    private String parametros;
    private Integer ambito;
    private String dimensiones;
    
    /**
     *
     * @param nombre
     * @param rol
     * @param tipo
     * @param visibilidad
     * @param conservar
     * @param padre
     * @param nivel
     * @param correlativo
     * @param clase
     * @param parametros
     * @param ambito
     */
    public Simbolo(String nombre, Integer rol, Integer tipo,Integer visibilidad, Boolean conservar, Integer padre, Integer nivel, Integer correlativo, String clase, String parametros, Integer ambito) {
        this.nombre = nombre;
        this.rol = rol;
        this.tipo = tipo;
        this.visibilidad = visibilidad;
        this.conservar = conservar;
        this.padre = padre;
        this.nivel = nivel;
        this.correlativo = correlativo;
        this.clase = clase;
        this.parametros = parametros;
        this.ambito = ambito;
    }

    /**
     * Getters y Setters
     */
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rol
     */
    public Integer getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Integer rol) {
        this.rol = rol;
    }

    /**
     * @return the tipo
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the visibilidad
     */
    public Integer getVisibilidad() {
        return visibilidad;
    }

    /**
     * @param visibilidad the visibilidad to set
     */
    public void setVisibilidad(Integer visibilidad) {
        this.visibilidad = visibilidad;
    }

    /**
     * @return the conservar
     */
    public Boolean getConservar() {
        return conservar;
    }

    /**
     * @param conservar the conservar to set
     */
    public void setConservar(Boolean conservar) {
        this.conservar = conservar;
    }

    /**
     * @return the padre
     */
    public Integer getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Integer padre) {
        this.padre = padre;
    }

    /**
     * @return the nivel
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the correlativo
     */
    public Integer getCorrelativo() {
        return correlativo;
    }

    /**
     * @param correlativo the correlativo to set
     */
    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the parametros
     */
    public String getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the ambito
     */
    public Integer getAmbito() {
        return ambito;
    }

    /**
     * @param ambito the ambito to set
     */
    public void setAmbito(Integer ambito) {
        this.ambito = ambito;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    
    
    public boolean compare(Simbolo o) {
        if (this.nombre.equals(o.getNombre()) && this.padre == o.getPadre() && this.nivel == o.getNivel() 
                && this.getClase().equals(o.getClase()) && this.parametros.equals(o.getParametros())) {
            return true;
        }
        return false;
    }

    /**
     * Método que retorna el valor del símbolo en formato html
     *
     * @return
     */
    @Override
    public String toString() {
        String ok = "<span class='btn-floating green'><i class='material-icons'>done</i></span>";
        String fail = "<span class='btn-floating red'><i class='material-icons'>close</i></span>";
        String simbolo = "  <tr>\n"
                + "    <td>" + ((this.getRol()==Tipos.METODO) ? (this.getNombre() + "[ ]x" +this.getDimensiones()) : this.getNombre()) + "</td>\n"
                + "    <td>" + Tipos.getRolAsString(this.getRol()) + "</td>\n"
                + "    <td>" + Tipos.getTipoAsString(this.getTipo()) + "</td>\n"
                + "    <td>" + Tipos.getVisibilidadAsString(this.getVisibilidad()) + "</td>\n"
                + "    <td>" + Tipos.getVisibilidadAsString(this.getAmbito()) + "</td>\n"
                + "    <td>" + ((this.conservar) ? ok : fail) + "</td>\n"
                + "    <td>" + this.getParametros() + "</td>\n"
                + "    <td>" + this.getClase() + "</td>\n"
                + "    <td class=\"center\">" + this.getPadre() + "</td>\n"
                + "    <td class=\"center\">" + this.getNivel() + "</td>\n"
                + "    <td class=\"center\">" + this.getCorrelativo() + "</td>\n"
                + "  </tr>";
        return simbolo;
    }

}
