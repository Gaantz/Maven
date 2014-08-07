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
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.ws.WS_Maven;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gantz on 11/07/14.
 */
public class Operation_Busquedas {

    private Context context;
    private Interface_Operation_Busquedas interface_operation_busquedas;
    private int page;

    public Operation_Busquedas(Context context) {
        this.context = context;
    }

    public void buscarLocales(final String s, final String categoria, final String ciudad) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_BUSQUEDAS_LOCALES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);

                            int resultado = Integer.parseInt(responseJsonObject.getString("resultado"));
                            if (resultado == 1) {
                                JSONArray responseJsonArray = responseJsonObject.getJSONArray("locales");
                                ArrayList<Local_DTO> local_dtos = new ArrayList<Local_DTO>();

                                int totalpages = -1;

                                for (int i = 0; i < responseJsonArray.length(); i++) {
                                    Local_DTO local_dto = new Local_DTO(responseJsonArray.getJSONObject(i));
                                    local_dtos.add(local_dto);
                                }

                                interface_operation_busquedas.getLocalesCercanos(true, local_dtos, totalpages, "Correcto");
                            } else {
                                interface_operation_busquedas.getLocalesCercanos(false, null, -1, null);
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
                params.put("token", WS_Maven.TOKEN_MAVEN);
                params.put("s", s);
                params.put("category", categoria);
                params.put("city", ciudad);
                params.put("page", String.valueOf(page));

                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setInterface_operation_busquedas(Interface_Operation_Busquedas interface_operation_busquedas) {
        this.interface_operation_busquedas = interface_operation_busquedas;
    }

    public interface Interface_Operation_Busquedas {
        void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje);
    }
}
