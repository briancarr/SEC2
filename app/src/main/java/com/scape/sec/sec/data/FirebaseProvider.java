package com.example.me.soundscape.data;

/**
 * Created by Me on 25/10/2016.
 */

//public class FirebaseProvider {
//
//    ArrayList<Work> activeWork = new ArrayList<>();
//    static ArrayList<String> namesList = new ArrayList<>();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    static DatabaseReference myRef;
//
//    Location[] locations;
//    static ArrayList<LatLng> latlngList = new ArrayList<>();
//    boolean finished = false;
//    public final CountDownLatch latch = new CountDownLatch(1);
//
//
//    public FirebaseProvider(String... params){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef = database.getReference(params[0]);
//        myRef.notify();
//    }
//
//    public void GetExibition(){
//
//    }
//
//    public void getWorks(){
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                int count = 0;
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                        Work post = postSnapshot.getValue(Work.class);
//                        System.out.println(post.getName() + " - " + post.getLocation());
//
//                        if (post.isActive() == true) {
//                            activeWork.add(post);
//                            namesList.add(post.getName());
//                            //Log.d("snaphot", "Value is: " + activeWork[count].getName() + " Image: " + activeWork[count].getImage());
//                            count++;
//                        }
//                    }
//                if (count == dataSnapshot.getChildrenCount()) {
//                    finished = true;
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//    }
//
//    public static ArrayList<LatLng> GetLocations(){
//
//        Log.i("namelist: ", namesList.toString());
//
//        if(namesList != null) {
//            for (String name : namesList) {
//                LatLng latlng;
//                //Get LatLng for active work and draw circle on the map
//                myRef.child(name + "/l").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        try {
//                            String lat = snapshot.child("0").getValue().toString();
//                            String lng = snapshot.child("1").getValue().toString();
//
//                            Log.i("lat snapshop", lat);
//                            Log.i("lng snapshop", lng);
//
//                            latlngList.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
//                        }catch (NullPointerException npe){
//                            Log.i("npe: ",npe.toString());
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//            //call method here
//            Log.e("latlng list: ",latlngList.toString());
//            return latlngList;
//        }
//        else{
//            return null;
//        }
//
//    }
//
//    public ArrayList<String> getNamesList() {
//        return namesList;
//    }
//
//    public ArrayList<Work>  getActiveWork() {
//        return activeWork;
//    }
//
//    public ArrayList<LatLng> getLatlngList() {
//        return latlngList;
//    }
//}
