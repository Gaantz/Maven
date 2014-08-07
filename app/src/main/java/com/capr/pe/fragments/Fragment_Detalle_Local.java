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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Cabecera_Local;
import com.capr.pe.views.View_Cupon;
import com.capr.pe.views.View_Detalle_Local;
import com.capr.pe.views.View_Local;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Detalle_Local extends Fragment implements View.OnClickListener {

    private GoogleMap map;

    private JSONObject jsonObject;
    private Local_DTO local_dto;

    public static final Fragment_Detalle_Local newInstance() {
        return new Fragment_Detalle_Local();
    }

    public static final Fragment_Detalle_Local newInstance(Local_DTO local_dto) {
        Fragment_Detalle_Local fragment_detalleLocal = new Fragment_Detalle_Local();
        Bundle bundle = new Bundle();
        bundle.putParcelable("local_dto", local_dto);
        fragment_detalleLocal.setArguments(bundle);
        return fragment_detalleLocal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local_dto = (Local_DTO) getArguments().getParcelable("local_dto");
        jsonObject = local_dto.getJsonObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        try {
            FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
            SupportMapFragment mSupportMapFragment = (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
            map = mSupportMapFragment.getMap();

            map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(false);

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")), Double.parseDouble(jsonObject.getString("Longitud"))))
                    .title(jsonObject.getString("NombreLocal"))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            CameraPosition camPos = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")), Double.parseDouble(jsonObject.getString("Longitud"))))
                    .zoom(16)
                    .build();
            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            map.animateCamera(camUpd3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout linearLayoutInformacion = (LinearLayout) getView().findViewById(R.id.container_local);
        LinearLayout linearLayoutDetalleInformacion = (LinearLayout) getView().findViewById(R.id.container_detalle_local);

        View_Cabecera_Local view_cabecera_local = new View_Cabecera_Local(getActivity(), local_dto);
        view_cabecera_local.setTag(local_dto);

        View_Detalle_Local view_detalle_local = new View_Detalle_Local(getActivity(), local_dto);

        try {
            String nombre_local = local_dto.getJsonObject().getString("NombreLocal");
            LinearLayout linearLayoutCupones = (LinearLayout) getView().findViewById(R.id.container_cupones);

            JSONArray jsonArrayCupones = jsonObject.getJSONArray("ListaCuponesRetail");
            if (jsonArrayCupones.length() > 0) {
                linearLayoutCupones.setVisibility(View.VISIBLE);
                for (int i = 0; i < jsonArrayCupones.length(); i++) {
                    Cupon_DTO cupon_dto = new Cupon_DTO(jsonArrayCupones.getJSONObject(i));
                    View_Cupon view_cupon = new View_Cupon(getActivity(), cupon_dto);
                    view_cupon.setNombre_local(nombre_local);
                    linearLayoutCupones.addView(view_cupon);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        linearLayoutInformacion.addView(view_cabecera_local);
        linearLayoutDetalleInformacion.addView(view_detalle_local);

        ((TextView)getView().findViewById(R.id.acb_titulo_local)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));

        /*
        Back Button Fragment
         */
        Fragment_Detalle_Local.this.getView().setFocusableInTouchMode(true);
        Fragment_Detalle_Local.this.getView().requestFocus();
        Fragment_Detalle_Local.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Detalle_Local.this);
                    trans.commit();
                    manager.popBackStack();
                    return true;
                }
                return false;
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
                trans.remove(Fragment_Detalle_Local.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
}
