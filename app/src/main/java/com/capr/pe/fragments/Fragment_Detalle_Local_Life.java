package com.capr.pe.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.Maven_Mapa;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.util.Util_GPS;
import com.capr.pe.views.View_Cabecera_Local;
import com.capr.pe.views.View_Cupon;
import com.capr.pe.views.View_Cupon_Life;
import com.capr.pe.views.View_Detalle_Local;
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
public class Fragment_Detalle_Local_Life extends Fragment implements View.OnClickListener {

    private GoogleMap map;

    private JSONObject jsonObject;
    private Local_DTO local_dto;

    public static final Fragment_Detalle_Local_Life newInstance() {
        return new Fragment_Detalle_Local_Life();
    }

    public static final Fragment_Detalle_Local_Life newInstance(Local_DTO local_dto) {
        Fragment_Detalle_Local_Life fragment_detalleLocal = new Fragment_Detalle_Local_Life();
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

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(getActivity(), Maven_Mapa.class);
                    intent.putExtra("local_dto", local_dto.getJsonObject().toString());
                    startActivity(intent);
                }
            });

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

        getView().findViewById(R.id.container_cabecera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Maven_Mapa.class);
                intent.putExtra("local_dto", local_dto.getJsonObject().toString());
                startActivity(intent);
            }
        });

        View_Detalle_Local view_detalle_local = new View_Detalle_Local(getActivity(), local_dto);

        try {
            String nombre_local = local_dto.getJsonObject().getString("NombreLocal");
            LinearLayout linearLayoutCupones = (LinearLayout) getView().findViewById(R.id.container_cupones);

            JSONArray jsonArrayCuponesLife = jsonObject.getJSONArray("ListaCuponesLife");

            if (jsonArrayCuponesLife.length() > 0) {
                linearLayoutCupones.setVisibility(View.VISIBLE);
                for (int i = 0; i < jsonArrayCuponesLife.length(); i++) {
                    Cupon_DTO cupon_dto = new Cupon_DTO(jsonArrayCuponesLife.getJSONObject(i));
                    View_Cupon_Life view_cupon_life = new View_Cupon_Life(getActivity(), cupon_dto);
                    view_cupon_life.setNombre_local(nombre_local);
                    linearLayoutCupones.addView(view_cupon_life);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        linearLayoutInformacion.addView(view_cabecera_local);
        linearLayoutDetalleInformacion.addView(view_detalle_local);

        ((TextView)getView().findViewById(R.id.acb_titulo_local)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));

        /*
        Button Cerrar
         */
        getView().findViewById(R.id.acb_img_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Detalle_Local_Life.this);
                trans.commit();
                manager.popBackStack();
            }
        });

        getView().findViewById(R.id.acb_img_mapa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.opciones_mapa, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
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

    DialogInterface.OnClickListener mDialogListener = new Dialog.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            Util_GPS mGpsUtil = new Util_GPS(getActivity());
            Intent intent;

            switch (which) {
                case 0:
                    try {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + mGpsUtil.getLatitude() + "," + mGpsUtil.getLongitude() + "&daddr=" + Double.parseDouble(jsonObject.getString("Latitud")) + "," + Double.parseDouble(jsonObject.getString("Longitud")) + "&mode=driving"));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try{
                        String url = "waze://?ll="+Double.parseDouble(jsonObject.getString("Latitud"))+","+Double.parseDouble(jsonObject.getString("Longitud"))+"&navigate=yes";
                        intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                        startActivity( intent );
                    }catch (Exception ex){
                        intent = new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                        startActivity(intent);
                    }break;
            }
        }
    };
}
