package com.capr.pe.operation;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.util.Util_GPS;
import com.capr.pe.ws.WS_Maven;
import com.google.android.gms.location.LocationClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gantz on 11/07/14.
 */
public class Operation_Locales_Cercanos {

    private Context context;
    private Interface_Operation_Locales_Cercanos interface_operation_locales_cercanos;
    private int page;

    public Operation_Locales_Cercanos(Context context) {
        this.context = context;
    }

    public void getLocalesCercanos() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(
                Request.Method.POST,
                WS_Maven.WS_OBTENER_LOCALES_CERCANOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJsonObject = new JSONObject(response);
                            JSONArray responseJsonArray = responseJsonObject.getJSONArray("locales");

                            int totalpages = responseJsonObject.getInt("totalPaginas");
                            ArrayList<Local_DTO> local_dtos = new ArrayList<Local_DTO>();
                            boolean status = true;

                            for (int i = 0; i < responseJsonArray.length(); i++) {
                                Local_DTO local_dto = new Local_DTO(responseJsonArray.getJSONObject(i));
                                local_dtos.add(local_dto);
                            }

                            interface_operation_locales_cercanos.getLocalesCercanos(true, local_dtos, totalpages);

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

                //Util_GPS util_gps = new Util_GPS(context);
                //Location location = util_gps.getLocation();

                Map<String, String> params = new HashMap<String, String>();
                //params.put("latitud", String.valueOf(location.getLatitude()));
                //params.put("longitud", String.valueOf(location.getLongitude()));
                params.put("latitud", "-12.008");
                params.put("longitud", "-77.049");
                params.put("token", WS_Maven.TOKEN_MAVEN);
                params.put("pagina", String.valueOf(page));

                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setInterface_operation_locales_cercanos(Interface_Operation_Locales_Cercanos interface_operation_locales_cercanos) {
        this.interface_operation_locales_cercanos = interface_operation_locales_cercanos;
    }

    public interface Interface_Operation_Locales_Cercanos {
        void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages);
    }
}
