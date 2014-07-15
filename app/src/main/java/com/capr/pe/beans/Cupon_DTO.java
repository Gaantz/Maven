package com.capr.pe.beans;

import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class Cupon_DTO {
    private JSONObject jsonObject;

    public Cupon_DTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
