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
import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.session.Session_Manager;
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
public class Operation_Empresas {

    private Context context;
    private Interface_Operation_Empresas interface_operation_empresas;

    public Operation_Empresas(Context context) {
        this.context = context;
    }

    public void getEmpresasLife() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_OBTENER_EMPRESAS_LIFE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);
                            int resultado = Integer.parseInt(responseJsonObject.getString("resultado"));
                            if (resultado == 1) {
                                ArrayList<Empresa_DTO> empresa_dtos = new ArrayList<Empresa_DTO>();
                                JSONArray responseJsonArray = responseJsonObject.getJSONArray("empresas");

                                for (int i = 0; i < responseJsonArray.length(); i++) {
                                    JSONObject jsonObject = responseJsonArray.getJSONObject(i);
                                    Empresa_DTO empresa_dto = new Empresa_DTO(responseJsonArray.getJSONObject(i));
                                    empresa_dtos.add(empresa_dto);
                                }
                                interface_operation_empresas.getEmpresasLife(true, empresa_dtos, "Mensaje");
                            } else {
                                interface_operation_empresas.getEmpresasLife(false, null, "Ocurrio un error.");
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
                Session_Manager session_manager = new Session_Manager(context);
                try {
                    JSONObject jsonObject = session_manager.getSession().getJsonObject();
                    params.put("token", WS_Maven.TOKEN_MAVEN);
                    params.put("idUsuario","1");
                    //params.put("idUsuario", jsonObject.getString("idUsuario"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void setInterface_operation_empresas(Interface_Operation_Empresas interface_operation_empresas) {
        this.interface_operation_empresas = interface_operation_empresas;
    }

    public interface Interface_Operation_Empresas {
        void getEmpresasLife(boolean status, ArrayList<Empresa_DTO> empresa_dtos, String mensaje);
    }
}
