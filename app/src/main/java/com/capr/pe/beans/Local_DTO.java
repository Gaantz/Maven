package com.capr.pe.beans;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Gantz on 5/07/14.
 */
public class Local_DTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JSONObject jsonObject;

    public Local_DTO(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}