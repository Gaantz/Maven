package com.capr.pe.util;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.capr.pe.maven.Maven;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

/**
 * Created by Gantz on 11/07/14.
 */
public class Util_GPS implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    private final String TAG = Util_GPS.class.getSimpleName();

    private Context context;
    private LocationClient locationClient;

    private Location location;

    public Util_GPS(Context context) {
        this.context = context;
    }

    @Override
    public void onConnected(Bundle bundle) {}

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected()    {}

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            return false;
        }
    }

    public Location getLocation() {

        locationClient = new LocationClient(context, this, this);
        locationClient.connect();

        return locationClient.getLastLocation();
    }
}
