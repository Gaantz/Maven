package com.capr.pe.beans;

import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class Menu_DTO {

    private JSONObject jsonObjectMenu;

    public Menu_DTO(JSONObject jsonObjectMenu) {
        this.jsonObjectMenu = jsonObjectMenu;
    }

    public JSONObject getJsonObjectMenu() {
        return jsonObjectMenu;
    }

    public void setJsonObjectMenu(JSONObject jsonObjectMenu) {
        this.jsonObjectMenu = jsonObjectMenu;
    }
}
