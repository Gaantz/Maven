package com.capr.pe.beans;

import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class Categoria_DTO {

    private String nombrecategoria;
    private int imagencategoria;
    private String cantidadcategoria;

    public Categoria_DTO(String nombrecategoria, String cantidadcategoria, int imagencategoria) {
        this.nombrecategoria = nombrecategoria;
        this.cantidadcategoria = cantidadcategoria;
        this.imagencategoria = imagencategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public int getImagencategoria() {
        return imagencategoria;
    }

    public void setImagencategoria(int imagencategoria) {
        this.imagencategoria = imagencategoria;
    }

    public String getCantidadcategoria() {
        return cantidadcategoria;
    }

    public void setCantidadcategoria(String cantidadcategoria) {
        this.cantidadcategoria = cantidadcategoria;
    }
}
