package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.views.View_Cupon;
import com.capr.pe.views.View_Detalle_Local;
import com.capr.pe.views.View_Local;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Information extends Fragment implements View.OnClickListener {

    private GoogleMap map;

    private JSONObject jsonObject;
    private Local_DTO local_dto;

    public static final Fragment_Information newInstance(Local_DTO local_dto) {
        Fragment_Information fragment_information = new Fragment_Information();
        Bundle bundle = new Bundle();
        bundle.putSerializable("local_dto",local_dto);
        fragment_information.setArguments(bundle);
        return fragment_information;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local_dto = (Local_DTO) getArguments().getSerializable("local_dto");
        jsonObject = local_dto.getJsonObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout linearLayoutInformacion = (LinearLayout) getView().findViewById(R.id.container_local);
        LinearLayout linearLayoutDetalleInformacion = (LinearLayout) getView().findViewById(R.id.container_detalle_local);

        View_Local view_local = new View_Local(getActivity(),local_dto);
        view_local.setTag(local_dto);

        View_Detalle_Local view_detalle_local = new View_Detalle_Local(getActivity(),local_dto);

        try {
            LinearLayout linearLayoutCupones = (LinearLayout) getView().findViewById(R.id.container_cupones);

            JSONArray jsonArrayCupones = jsonObject.getJSONArray("ListaCupones");
            if(jsonArrayCupones.length() > 0){
                linearLayoutCupones.setVisibility(View.VISIBLE);
                for (int i = 0; i < jsonArrayCupones.length(); i++) {
                    Cupon_DTO cupon_dto = new Cupon_DTO(jsonArrayCupones.getJSONObject(i));
                    View_Cupon view_cupon = new View_Cupon(getActivity(),cupon_dto);
                    linearLayoutCupones.addView(view_cupon);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        linearLayoutInformacion.addView(view_local);
        linearLayoutDetalleInformacion.addView(view_detalle_local);

        try {
            initMap();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            initMap();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void initMap() throws JSONException {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if(status == ConnectionResult.SUCCESS) {
            if (map == null) {
                map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                if (map != null) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")),Double.parseDouble(jsonObject.getString("Longitud"))))
                            .title(jsonObject.getString("NombreLocal"))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    CameraPosition camPos = new CameraPosition.Builder()
                            .target(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")),Double.parseDouble(jsonObject.getString("Longitud"))))
                            .zoom(16)
                            .build();
                    CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                    map.animateCamera(camUpd3);
                }
            }

            map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        map.clear();
    }
}
