package com.capr.pe.operation;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capr.pe.beans.Usuario_DTO;
import com.capr.pe.ws.WS_Maven;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gantz on 11/07/14.
 */
public class Operation_Logeo {

    private Context context;
    private Interface_Operation_Logeo interface_operation_logeo;

    public Operation_Logeo(Context context) {
        this.context = context;
    }

    public void logearUsuarioFacebook(final String facebook_id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_LOGEAR_USUARIO_FACEBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);

                            int resultado = responseJsonObject.getInt("resultado");
                            if (resultado == 1) {
                                Usuario_DTO usuario_dto = new Usuario_DTO(responseJsonObject.getJSONObject("datos_usuario"));
                                interface_operation_logeo.logearUsuario(true, usuario_dto);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Error o sin Internet.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("facebook_id", facebook_id);
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void logearUsuarioMaven(final String facebook_id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_LOGEAR_USUARIO_EMAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);

                            int resultado = responseJsonObject.getInt("resultado");
                            if (resultado == 1) {
                                Usuario_DTO usuario_dto = new Usuario_DTO(responseJsonObject.getJSONObject("datos_usuario"));
                                interface_operation_logeo.logearUsuario(true, usuario_dto);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Error o sin Internet.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("facebook_id", facebook_id);
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public interface Interface_Operation_Logeo {
        void logearUsuario(boolean state, Usuario_DTO usuario_dto);
    }
}
