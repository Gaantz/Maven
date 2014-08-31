package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.dialog.Dialog_Cupon;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 16/07/14.
 */
public class Fragment_Detalle_Cupon_Life extends Fragment implements Dialog_Cupon.Interface_Dialog_Cupon {

    private Cupon_DTO cupon_dto;
    private String nombre_local;

    public static final Fragment_Detalle_Cupon_Life newInstance(Cupon_DTO cupon_dto, String nombre_local) {
        Fragment_Detalle_Cupon_Life fragment_detalle_cupon = new Fragment_Detalle_Cupon_Life();
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
        View view = inflater.inflate(R.layout.fragment_detalle_cupon, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            TextView acbtitulo = (TextView) getView().findViewById(R.id.acb_titulo_local);
            TextView txtnombrecupon = (TextView) getView().findViewById(R.id.txt_nombre_cupon);
            TextView txtdescripcioncupon = (TextView) getView().findViewById(R.id.txt_detalle_cupon);
            TextView txtdetallecupon = (TextView) getView().findViewById(R.id.txt_descripcion_cupon);
            TextView txtcondiciones = (TextView) getView().findViewById(R.id.txt_terminos_y_condiciones);

            JSONObject jsonObject = cupon_dto.getJsonObject();

            String tipoBeneficio = jsonObject.getString("Beneficio");
            if (tipoBeneficio.equals("0")) {
                txtnombrecupon.setText("Oferta Especial");
                txtdetallecupon.setText(jsonObject.getString("Nombre"));

                ((LinearLayout) getView().findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_plomo_claro);
                getView().findViewById(R.id.btnusar).setVisibility(View.VISIBLE);

            } else if (tipoBeneficio.equals("1")) {
                txtnombrecupon.setText("Oferta en Tienda");
                txtdetallecupon.setText(jsonObject.getString("Nombre"));

                ((LinearLayout) getView().findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_plomo_claro);
                getView().findViewById(R.id.btnusar).setVisibility(View.GONE);
            }

            acbtitulo.setText(nombre_local);
            txtnombrecupon.setText(jsonObject.getString("Nombre"));
            txtdescripcioncupon.setText(jsonObject.getString("Descripcion"));

            txtnombrecupon.setTextColor(getResources().getColor(R.color.color_letra));
            txtdescripcioncupon.setTextColor(getResources().getColor(R.color.color_letra));
            //txtdetallecupon.setText(jsonObject.getString(""));

            JSONArray jsonArray = jsonObject.getJSONArray("Condiciones");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {
                stringBuffer.append(jsonArray.getString(i) + "\n \n");
            }
            txtcondiciones.setText(stringBuffer);

            /*
            SET FONTS
             */
            txtnombrecupon.setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
            txtdetallecupon.setTypeface(Util_Fonts.setPNALight(getActivity()));
            ((TextView) getView().findViewById(R.id.txt_terminos_y_condiciones_text)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
            ((Button) getView().findViewById(R.id.btnusar)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
            txtcondiciones.setTypeface(Util_Fonts.setPNALight(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView)getView().findViewById(R.id.acb_titulo_local)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));

        /*
        Back Button Fragment
         */
        Fragment_Detalle_Cupon_Life.this.getView().setFocusableInTouchMode(true);
        Fragment_Detalle_Cupon_Life.this.getView().requestFocus();
        Fragment_Detalle_Cupon_Life.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Detalle_Cupon_Life.this);
                    trans.commit();
                    manager.popBackStack();
                    return true;
                }
                return false;
            }
        });

        getView().findViewById(R.id.btnusar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView().findViewById(R.id.txt_terminos_y_condiciones).setVisibility(View.GONE);

            }
        });

        /*
        Button Cerrar
         */
        getView().findViewById(R.id.acb_img_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Detalle_Cupon_Life.this);
                trans.commit();
                manager.popBackStack();
            }
        });

        getView().findViewById(R.id.btnusar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Cupon dialog_cupon = new Dialog_Cupon(getActivity());
                dialog_cupon.setInterface_dialog_cupon(Fragment_Detalle_Cupon_Life.this);
                dialog_cupon.show();
            }
        });
    }


    @Override
    public void onAcepted(Dialog_Cupon dialog_cupon) {
        dialog_cupon.hide();

        getView().findViewById(R.id.txt_terminos_y_condiciones_text).setVisibility(View.GONE);
        getView().findViewById(R.id.txt_terminos_y_condiciones).setVisibility(View.GONE);

        ((TextView) getView().findViewById(R.id.txtcontador)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        getView().findViewById(R.id.txtcontador).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.imgqr).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.btnusar).setVisibility(View.GONE);
    }
}
