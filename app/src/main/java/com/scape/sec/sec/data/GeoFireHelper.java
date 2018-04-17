package com.scape.sec.sec.data;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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

}
