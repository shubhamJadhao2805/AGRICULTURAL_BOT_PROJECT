package com.example.botlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager manager;
    LocationListener locationListener;
    static Double lati = 0.0;
    static Double lang = 0.0;
    Marker mainMarker;
    private GoogleMap mMap;
    MarkerOptions markerOptions;
    MarkerOptions markerOptions2;
    Location loc;
    FirebaseDatabase database;
    LatLng mLastLocation;
    DatabaseReference ref;
    Map<String,String> cor;
    ArrayList<Double> latitudes;
    ArrayList<Double> longitude;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById (R.id.map1);
        mapFragment.getMapAsync (this);
        DatabaseReference ref1 = FirebaseDatabase.getInstance ().getReference ().child ("userLocation");
        cor = new HashMap<String,String>();
        ref = ref1;
        latitudes = new ArrayList <Double> ();
        longitude = new ArrayList <Double> ();



        manager = (LocationManager)this.getSystemService (LOCATION_SERVICE);
        locationListener = new LocationListener ( ) {
            @Override
            public void onLocationChanged( Location location ) {

                System.out.println (location.toString ());
                lati = location.getLatitude ();
                lang =  location.getLongitude ();
                locationChanged ();


            }

            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {

            }

            @Override
            public void onProviderEnabled( String provider ) {

            }

            @Override
            public void onProviderDisabled( String provider ) {

            }
        };

        //Method to check Permissions

        if(ContextCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        }else
        {
            try {
                manager.requestLocationUpdates ( LocationManager.GPS_PROVIDER , 900 , 0 , locationListener );
                loc = manager.getLastKnownLocation ( LocationManager.GPS_PROVIDER );
                Log.i ( "Location" , loc.toString ( ) );
                lati = loc.getLatitude ( );
                lang = loc.getLatitude ( );
            }catch (Exception e){

            }


        }




    }


    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults ) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


            if(ContextCompat.checkSelfPermission (this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                manager.requestLocationUpdates (LocationManager.GPS_PROVIDER,900,0,locationListener);
                loc = manager.getLastKnownLocation (LocationManager.GPS_PROVIDER);
                lati = loc.getLatitude ();
                lang = loc.getLatitude ();


            }






    }

};


    @Override
    public void onMapReady( GoogleMap googleMap ) {
        mMap = googleMap;
//         //Add a marker in Sydney and move the camera
        LatLng co = new LatLng (lati,lang);
         markerOptions = new MarkerOptions()
                .position(co).title("Bot Location").icon (BitmapDescriptorFactory.fromResource (R.drawable.robotlocation));
         mainMarker = mMap.addMarker(markerOptions);

        mMap.setOnMapClickListener (new GoogleMap.OnMapClickListener ( ) {
            @Override
            public void onMapClick( LatLng latLng ) {

                 markerOptions2 = new MarkerOptions ().position (latLng);
                 mMap.addMarker (markerOptions2);
                 mMap.moveCamera (CameraUpdateFactory.newLatLng (latLng));
                 Toast.makeText (MapsActivity.this,"LatiTude : "  +  latLng.latitude,Toast.LENGTH_SHORT).show ();
//                 Log.i ("Latitude", String.valueOf (latLng.latitude));
//                 Log.i ("Longitude",String.valueOf (latLng.longitude));
//                 latitudes.add (String.valueOf (latLng.latitude));
//                 longitude.add (String.valueOf (latLng.longitude));
                DatabaseReference ref3 = FirebaseDatabase.getInstance ().getReference ().child ("pointSelect");
                latitudes.add (Double.valueOf (String.valueOf (latLng.latitude)));
                longitude.add (Double.valueOf (Double.toString (latLng.longitude)));







            }
        });


    }

    //Method to clere Map in Firebase

    public void clere( View view){
        if(mMap != null) {
            mMap.clear ( );
        }
        locationChanged ();
        DatabaseReference ref3 = FirebaseDatabase.getInstance ().getReference ().child ("pointSelect");
        ref3.removeValue ();


    }




    //Method to get location s in on location change
    public  void locationChanged(){
        System.out.println (lati);
        System.out.println (lang);
        LatLng co = new LatLng (lati,lang);
        if(mMap != null) {
            mainMarker.setPosition (co);
            mMap.moveCamera (CameraUpdateFactory.newLatLng (co));
//            cor.put ("latitude", lati.toString ( ));
//            cor.put ("longitude", lang.toString ( ));
//            ref.setValue (cor);
        }

    }

    //Button to send Points Co-Ordinates to firebase;

    public void done(View view){


if(latitudes != null && longitude != null) {
    DatabaseReference ref3 = FirebaseDatabase.getInstance ( ).getReference ( ).child ("pointSelect");
    Map <String, ArrayList <Double>> dict = new HashMap <String, ArrayList <Double>> ( );
    dict.put ("latitudes", latitudes);
    dict.put ("longitudes", longitude);
    ref3.setValue (dict).addOnCompleteListener (new OnCompleteListener <Void> ( ) {
        @Override
        public void onComplete( @NonNull Task <Void> task ) {

            if (task.isSuccessful ( )) {
                Toast.makeText (MapsActivity.this, "Done", Toast.LENGTH_SHORT).show ( );
            } else {
                Toast.makeText (MapsActivity.this, "Failed", Toast.LENGTH_SHORT).show ( );

            }

        }
    });
}else{

    Toast.makeText (MapsActivity.this,"Please select Points",Toast.LENGTH_SHORT).show ();

}


    }




    public void manualControl(View view){

        Intent intent = new Intent ( MapsActivity.this,BotControlActivity.class );
        startActivity ( intent );
    }





}
