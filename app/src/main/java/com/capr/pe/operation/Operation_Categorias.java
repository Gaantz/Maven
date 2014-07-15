package com.capr.pe.operation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capr.pe.beans.Categoria_DTO;
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
public class Operation_Categorias {

    private Context context;
    private Interface_Operation_Categorias interface_operation_categorias;

    public Operation_Categorias(Context context) {
        this.context = context;
    }

    public void getCategoryes() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_OBTENER_CATEGORIAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);
                            JSONArray responseJsonArray = responseJsonObject.getJSONArray("categories");

                            ArrayList<Categoria_DTO> categoria_dtos = new ArrayList<Categoria_DTO>();
                            boolean status = true;

                            for (int i = 0; i < responseJsonArray.length(); i++) {
                                Log.e("CATEGORIES", responseJsonArray.getJSONObject(i).toString());
                                Categoria_DTO categoria_dto = new Categoria_DTO(responseJsonArray.getJSONObject(i));
                                categoria_dtos.add(categoria_dto);
                            }

                            interface_operation_categorias.getCategoryes(status, categoria_dtos);

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
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void setInterface_operation_categorias(Interface_Operation_Categorias interface_operation_categorias) {
        this.interface_operation_categorias = interface_operation_categorias;
    }

    private interface Interface_Operation_Categorias {
        void getCategoryes(boolean status, ArrayList<Categoria_DTO> categoria_dtos);
    }
}
