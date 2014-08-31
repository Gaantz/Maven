package com.capr.pe.operation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capr.pe.beans.Pregunta_DTO;
import com.capr.pe.beans.Usuario_DTO;
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
public class Operation_Preguntas {

    private Context context;
    private Interface_Obtener_Preguntas interface_obtener_preguntas;

    public Operation_Preguntas(Context context) {
        this.context = context;
    }

    public void getPreguntas() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_OBTENER_PREGUNTAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject response = new JSONObject(s);
                            String success = response.getString("resultado");
                            if(success.equals("1")){

                                JSONArray jsonArray = response.getJSONArray("preguntas");
                                ArrayList<Pregunta_DTO> pregunta_dtos = new ArrayList<Pregunta_DTO>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Pregunta_DTO pregunta_dto = new Pregunta_DTO();
                                    pregunta_dto.setJsonObject(jsonArray.getJSONObject(i));
                                    pregunta_dtos.add(pregunta_dto);
                                }
                                interface_obtener_preguntas.getPreguntas(true,pregunta_dtos,"Correcto...!");
                            }else{
                                interface_obtener_preguntas.getPreguntas(false,null,response.getString("mensaje") + "-");
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
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void setInterface_obtener_preguntas(Interface_Obtener_Preguntas interface_obtener_preguntas) {
        this.interface_obtener_preguntas = interface_obtener_preguntas;
    }

    public interface Interface_Obtener_Preguntas{
        void getPreguntas(boolean status,ArrayList<Pregunta_DTO> pregunta_dtos,String mensaje);
    }
}
