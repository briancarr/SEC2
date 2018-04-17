package com.scape.sec.sec.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scape.sec.sec.Location;
import com.scape.sec.sec.Model.ExhibitionEntry;
import com.scape.sec.sec.Model.Work;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Me on 28/10/2016.
 */

public class ManageExhibition {

    SQLiteDatabase db;
    FirebaseProvider firebase;
    ArrayList<Work> work;

    public ManageExhibition(Context context, String directory){
        setupDatabase(context);
        firebase = new FirebaseProvider(directory);
        firebase.getWorks();
        work = new ArrayList<>();

    }

    private void setupDatabase(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long create(ExhibitionEntry exhibition){
//        String Title = params[0];
//        String Content = params[0];

        ContentValues Values = new ContentValues();

//        Values.put ( ExhibitionEntry.COLUMN_NAME , Title );
//        Values.put ( ExhibitionEntry.COLUMN_IMAGE_URL , Content );
        // Insert The New Row, Returning The Primary Key Value Of The New Row Long NewRowId ;
        long NewRowId = db.insert( ExhibitionEntry.TABLE_NAME ,null ,Values );

        return  NewRowId;
    }

    public void read(){
        firebase.getLocations();
    }

    public void update(ExhibitionEntry exhibition){

    }

    public void delete(ExhibitionEntry exhibition){

    }


     public void createWork(String name,String image, String audio, String location, boolean active){



//        int Id = 0 ;
//        String Title = params[0];
//        String Content = params[1];
//        String Name = params[2];
//        String Audio_Url = params[3];
//        String Image_Url = params[4];
//        String lat = params[5];
//        String lng = params[6];
//
//        ContentValues Values = new ContentValues();
//
//        Values.put ( WorkEntry.COLUMN_LOCATION_NAME, Title );
//        Values.put ( WorkEntry.COLUMN_WORK_NAME, Name );
//        Values.put ( WorkEntry.COLUMN_IMAGE_URL, Image_Url );
//        Values.put ( WorkEntry.COLUMN_AUDIO_URL, Audio_Url );
//        Values.put ( WorkEntry.COLUMN_COORD_LAT, lat );
//        Values.put ( WorkEntry.COLUMN_COORD_LONG, lng );
//        // Insert The New Row, Returning The Primary Key Value Of The New Row Long NewRowId ;
//        long NewRowId = db.insert( WorkEntry.TABLE_NAME ,null ,Values );
         firebase.createWork( name, image,  audio,  location,  active);

    }

    public ArrayList getWorks() {
        return firebase.getWorks();
    }


    public class FirebaseProvider {

        ArrayList<String> namesList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        Location[] locations;
        //ArrayList<LatLng> latlngList = new ArrayList<>();
        boolean finished = false;
        public final CountDownLatch latch = new CountDownLatch(1);


        public FirebaseProvider(String... params){
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(params[0]);
            getWorks();
        }

        public void createWork(String name,String image, String audio, String location, boolean active){
            String key =  myRef.push().getKey();
            myRef.child(key).setValue(new Work(key,name,image,audio,location,active));
        }

        public ArrayList getWorks() {
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    int count = 0;

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Work post = postSnapshot.getValue(Work.class);
                        System.out.println(post.getName() + " - " + post.getLocation());

                        if (post.isActive() == true) {
                            work.add(post);
                            namesList.add(post.getName());
                            Log.d("snaphot", "Value is: " + work.toString());
                            count++;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return namesList;
        }

        public void getLocations(){
            GeoFireHelper geoFireHelper = new GeoFireHelper(myRef);
            geoFireHelper.getLocation("213");
        }


//        public ArrayList<LatLng> GetLocations(){
//
//            Log.i("namelist: ", namesList.toString());
//
//            if(namesList != null) {
//                for (String name : namesList) {
//                    LatLng latlng;
//                    //Get LatLng for active work and draw circle on the map
//                    myRef.child(name + "/l").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot snapshot) {
//                            try {
//                                String lat = snapshot.child("0").getValue().toString();
//                                String lng = snapshot.child("1").getValue().toString();
//
//                                Log.i("lat snapshop", lat);
//                                Log.i("lng snapshop", lng);
//
//                                latlngList.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
//                            }catch (NullPointerException npe){
//                                Log.i("npe: ",npe.toString());
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                //call method here
//                Log.e("latlng list: ",latlngList.toString());
//                return latlngList;
//            }
//            else{
//                return null;
//            }
//
//        }

        public ArrayList<String> getNamesList() {
            return namesList;
        }

        public ArrayList<Work>  getActiveWork() {
            return work;
        }

//        public ArrayList<> getLatlngList() {
//            return latlngList;
//        }
    }
}
