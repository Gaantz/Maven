package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 16/07/14.
 */
public class Fragment_Detalle_Cupon extends Fragment {

    private Cupon_DTO cupon_dto;
    private String nombre_local;

    public static final Fragment_Detalle_Cupon newInstance(Cupon_DTO cupon_dto,String nombre_local) {
        Fragment_Detalle_Cupon fragment_detalle_cupon = new Fragment_Detalle_Cupon();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cupon_dto", cupon_dto);
        bundle.putSerializable("nombre_local", nombre_local);
        fragment_detalle_cupon.setArguments(bundle);
        return fragment_detalle_cupon;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupon_dto = (Cupon_DTO) getArguments().getSerializable("cupon_dto");
        nombre_local = getArguments().getString("nombre_local");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_cupon, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {

            JSONObject jsonObject = cupon_dto.getJsonObject();

            String tipoBeneficio = jsonObject.getString("Beneficio");
            if (tipoBeneficio.equals("0")) {
                ((LinearLayout) getView().findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_azul_claro);
            } else if (tipoBeneficio.equals("1")) {
                ((LinearLayout) getView().findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_verde_claro);
            }
            TextView acbtitulo = (TextView) getView().findViewById(R.id.acb_titulo_local);
            TextView txtnombrecupon = (TextView) getView().findViewById(R.id.txt_nombre_cupon);
            TextView txtdescripcioncupon = (TextView) getView().findViewById(R.id.txt_detalle_cupon);
            TextView txtdetallecupon = (TextView) getView().findViewById(R.id.txt_descripcion_cupon);
            TextView txtcondiciones = (TextView) getView().findViewById(R.id.txt_terminos_y_condiciones);

            acbtitulo.setText(nombre_local);
            txtnombrecupon.setText(jsonObject.getString("Nombre"));
            txtdescripcioncupon.setText(jsonObject.getString("Descripcion"));

            //txtdetallecupon.setText(jsonObject.getString(""));

            JSONArray jsonArray = jsonObject.getJSONArray("Condiciones");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {
                stringBuffer.append(jsonArray.getString(i) + "\n \n");
            }
            txtcondiciones.setText(stringBuffer);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
