package com.scape.sec.sec;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.View;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scape.sec.sec.Model.AudioManager;
import com.scape.sec.sec.Model.Work;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    GeoFire geoFire;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("works");
    DatabaseReference myGeoRef = database.getReference();

    private double lng, lat;

    LoaderManager locationManager;

    FloatingActionButton fab;

    String _key;
    String _keyAudio = "";
    Boolean _keyActive = false;
    private String _keyName;

    MediaPlayer mp;
    private String[] _keyNames = new String[10];
    ArrayList<String> namesList = new ArrayList<>();
    ArrayList<Work> work;
    ArrayList<LatLng> latlngList = new ArrayList<>();

    AudioManager audioManager;

    double latDouble;
    double lngDouble;

    int count;
    String infoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //firebase = new FetchExhibition.FirebaseProvider("works");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY).withAlpha(200));

        namesList = getIntent().getStringArrayListExtra("nameList");

        Log.i("_keyActive",_keyActive.toString());

        database = FirebaseDatabase.getInstance();
        DatabaseReference _myRef = database.getReference();

        for (String name:namesList) {
            Log.i("key name",name);
            infoTitle = name;
            LatLng latlng;
            //Get LatLng for active work and draw circle on the map
            _myRef.child(name + "/l").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String lat = snapshot.child("0").getValue().toString();
                    String lng = snapshot.child("1").getValue().toString();

                    Log.i("lat snapshop",lat);
                    Log.i("lng snapshop",lng);

                    latlngList.add(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng)));

                    latDouble = Double.parseDouble(lat);
                    lngDouble = Double.parseDouble(lng);

                    Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latDouble, lngDouble))
                        .radius(30)
                        .strokeColor(Color.RED)
                        .fillColor(Color.BLUE)
                        .clickable(true)
                    );

                    mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                        @Override
                        public void onCircleClick(Circle circle) {
                            Log.i("circle click", "circle click");
                            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                            circle.setStrokeColor(strokeColor);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

//        //Draw Circles from latlng list
        Log.e("latlng list: ",latlngList.toString());
        for(LatLng loc : latlngList) {
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(loc.latitude, loc.longitude))
                    .radius(30)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE)
                    .clickable(true)
            );

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {

                    //melbourne.setTitle(infoTitle);
                    //melbourne[count].showInfoWindow();
                    // Flip the r, g and b components of the circle's
                    // stroke color.
                    Log.i("circle click", "circle click");
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
                }
            });
        }

        geoFire = new GeoFire(myGeoRef);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16.0f));
                Log.i("Location Changed :", location.toString());
                GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 0.01);

                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        Log.i("Key Enter :", key);
                        _key = key;

                        checkKey(key);

                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                audioManager = new AudioManager(_keyAudio,fab);
                                audioManager.play();
                            }
                        });

//                        if(_keyActive) {
//                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
//                            fab.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                        try {
//                                            if(mp == null){
//                                                mp = new MediaPlayer();
//                                             mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                                                mp.setDataSource(_keyAudio);
//
//                                                 mp.prepareAsync();
//
//                                                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                                    @Override
//                                                    public void onPrepared(MediaPlayer mp) {
//                                                        mp.start();
//                                                        fab.setImageResource(R.drawable.ic_media_pause);
//                                                    }
//                                                });
//                                            }else {
//                                                mp.stop();
//                                                mp.release();
//                                                mp = null;
//                                                fab.setImageResource(R.drawable.ic_media_play);
//                                            }
//
//                                        } catch (IllegalStateException ise) {
//                                            Log.e("play", "prepare() failed" + ise.toString());
//                                        } catch (IOException e) {
//                                            Log.e("IOException", e.toString());
//                                        }catch (NullPointerException npe){
//                                            Log.e("NullPointerException", npe.toString());
//                                        }
//                                }
//                            });
                    //}
                        getStreamInfo(_key);
                    }

                    @Override
                    public void onKeyExited(String key) {
//                        Log.i("Key Exit :", key);
//                        String _null = null;
//                        try {
//                            mp.setDataSource(_null);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        //Stop and release the Media Player
//                        mp.stop();
//                        mp.release();
//
//                        //Change icon on FAB and set color to Grey
//                        fab.setImageResource(R.drawable.ic_media_play);
//                        fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {
                       if(latlngList !=null){
//                           createInfoWindow(namesList,latlngList);
                       }
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {
                        Log.i("Query Error :", error.toString());
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

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    private void checkKey(String key) {
//        work = firebase.getActiveWork();
//        for(Work piece : work){
//            if(piece.getName() == key){
//                _keyAudio = piece.getAudio();
//            }
//        }
    }

    public void createInfoWindow(ArrayList<String> nameL, ArrayList<LatLng> location){

        int count =0;
        for(String name : nameL){
            Marker melbourne = mMap.addMarker(new MarkerOptions()
                    .position(location.get(count))
                    .title(name)
                    .snippet("Population: 4,137,400")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background))
            );
            count++;
        }

    }

//    private boolean isActive(String key) {
//        final boolean[] _active = new boolean[1];
//        myRef.child(key).addValueEventListener(new ValueEventListener() {
//            Work audio;
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                audio = snapshot.getValue(Work.class);
//                _active[0] = audio.isActive();
//                Log.i("_Active", _active[0]+"");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//        Log.i("_Active", _active[0]+"");
//        return _active[0];
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng myLocation = new LatLng(lat, lng );
        mMap.setOnInfoWindowClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.getUiSettings().setZoomControlsEnabled(false);

    }


    private void getStreamInfo(String key) {

        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Work audio = snapshot.getValue(Work.class);
                Log.i("Audio1: ",snapshot.toString());
                Log.i("Audio2: ",audio.getAudio());
                _keyAudio = audio.getAudio();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            
        });

    }

    private void updateUI() {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
