package com.capr.pe.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.capr.pe.beans.Usuario_DTO;
import com.capr.pe.fragments.Fragment_Busqueda;
import com.capr.pe.fragments.Fragment_Local;
import com.capr.pe.fragments.Fragment_Perfil;
import com.capr.pe.fragments.Fragment_Splash;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 23/06/14.
 */
public class Session_Manager {

    private static final String PREFERENCE_NAME = "roypi_preferences";
    private int PRIVATE_MODE = 0;

    /*
    USUARIO DATA SESSION - JSON
     */
    public static final String USER_DATA = "userData";
    public static final String USER_LOGIN = "userLogin";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public Session_Manager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public boolean isLogin() {
        return preferences.getBoolean(USER_LOGIN, false);
    }

    public void crearSession(Usuario_DTO usuario_dto) throws JSONException {
        editor.putBoolean(USER_LOGIN, true);
        editor.putString(USER_DATA,usuario_dto.getJsonObject().toString());
        editor.commit();
        ((Maven)context).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Splash.newInstance()).commit();
    }

    public void cerrarSession() {
        editor.putBoolean(USER_LOGIN, false);
        editor.putString(USER_DATA, null);
        editor.commit();
        ((Maven)context).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Splash.newInstance()).commit();
        Toast.makeText(context, "Cerrando sessión", Toast.LENGTH_SHORT).show();
    }

    public Usuario_DTO getSession() throws JSONException {
        if (isLogin()) {
            String userData = preferences.getString(USER_DATA, null);
            Usuario_DTO usuario_dto = new Usuario_DTO();
            usuario_dto.setJsonObject(new JSONObject(userData));
            return usuario_dto;
        } else {
            return null;
        }
    }
}