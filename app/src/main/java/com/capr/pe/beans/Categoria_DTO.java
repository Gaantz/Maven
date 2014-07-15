package com.capr.pe.beans;

import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class Categoria_DTO {
    private JSONObject jsonObject;

    public Categoria_DTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
