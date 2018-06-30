package com.scape.sec.sec.data;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by frogu on 17/04/2018.
 */

public class GeoFireHelper {

    GeoFire geoFire;

    public GeoFireHelper(){

    }

    public GeoFireHelper(DatabaseReference ref){
        geoFire = new GeoFire(ref);
    }

    public GeoFire getLocation(String key){
        geoFire.getLocation(key, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                if (location != null) {
                    System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));
                } else {
                    System.out.println(String.format("There is no location for key %s in GeoFire", key));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("There was an error getting the GeoFire location: " + databaseError);
            }
        });

        return geoFire;
    }

    public void checkLocation() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 0.01);

                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {

                    }

                    @Override
                    public void onKeyExited(String key) {

                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

}
