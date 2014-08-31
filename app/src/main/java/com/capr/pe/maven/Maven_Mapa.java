package com.capr.pe.maven;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.util.Util_GPS;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 14/08/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Maven_Mapa extends Activity {

    private GoogleMap googleMap;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("local_dto"));
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.acb_img_ruta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Maven_Mapa.this);
                builder.setItems(R.array.opciones_mapa, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        findViewById(R.id.acb_img_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maven_Mapa.this.finish();
            }
        });
    }

    private void initilizeMap() throws Exception {
        if (googleMap == null) {

            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")), Double.parseDouble(jsonObject.getString("Longitud")))).title(jsonObject.getString("NombreLocal")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(jsonObject.getString("Latitud")), Double.parseDouble(jsonObject.getString("Longitud")))).zoom(16).build();
            CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
            googleMap.moveCamera(camUpd3);

            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    DialogInterface.OnClickListener mDialogListener = new Dialog.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            Util_GPS mGpsUtil = new Util_GPS(Maven_Mapa.this);
            Intent intent;

            switch (which) {
                case 0:
                    try {
                        intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + mGpsUtil.getLatitude() + "," + mGpsUtil.getLongitude() + "&daddr=" + Double.parseDouble(jsonObject.getString("Latitud")) + "," + Double.parseDouble(jsonObject.getString("Longitud")) + "&mode=driving"));
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
