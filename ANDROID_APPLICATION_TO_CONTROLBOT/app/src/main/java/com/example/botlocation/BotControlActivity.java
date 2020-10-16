package com.example.botlocation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.HashMap;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class BotControlActivity extends AppCompatActivity implements OnMapReadyCallback{

    DatabaseReference reference;
    EditText text;
    GoogleMap myMap;
    Marker mainMarker;
    MarkerOptions markerOptions;
    Location location;
    Double lati = 0.0;
    Double lang = 0.0;
    SupportMapFragment mapFragment;
    Button automationButton;
    ImageView cameraFeed;
    int feedStatus;


    int isAutomated;

    StorageReference reference2;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_bot_control );
         reference = FirebaseDatabase.getInstance ().getReference ().child ( "botOperation" );
         text = findViewById ( R.id.editText );
         DatabaseReference reference4 = reference.child ( "isDetect" );
         reference4.setValue ( 0 );
         automationButton = findViewById(R.id.automationButton);
         isAutomated = 0;
         cameraFeed = findViewById(R.id.cameraFeed);
         feedStatus = 0;
//         reference2 = FirebaseStorage.getInstance ().getReference ().child ( "camera.frame" );
//        final long ONE_MEGABYTE = 1024 * 1024;
//        reference2.getBytes ( ONE_MEGABYTE ).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                ImageView image =  findViewById(R.id.cameraFeed);
//                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
//                        image.getHeight(), false));
//            }
//        });
//



        final JoystickView joystickView = findViewById(R.id.joystickView);

        joystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
if(isAutomated == 0) {
    if (angle > 40 && angle < 130) {
        setData(1);
    } else if (angle < 200 && angle > 150) {
        setData(4);
    } else if (angle > 340) {

        setData(3);
    } else if (angle > 230 && angle < 320) {
        setData(2);
    } else if (angle > 0 && angle < 30) {
        setData(3);
    }

    if (strength == 0) {
        setData(5);
    }

//    DatabaseReference reference3 = reference.child("sp");
//    reference3.setValue(strength*5);

}








            }
        });



try {
    DatabaseReference reference5 = FirebaseDatabase.getInstance().getReference().child("ImageData");
    reference5.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
                String imageData = dataSnapshot.getValue().toString();
                byte[] data = Base64.decode(imageData, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                ImageView imageView = findViewById(R.id.cameraFeed);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}catch (Exception e){

}

//Coding For Maps

        mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById (R.id.locationShow);
        mapFragment.getMapAsync ((OnMapReadyCallback) this);
        mapFragment.setMenuVisibility(false);









    }


    //VariousButton

    public void forword( View view){

setData ( 1 );
    }

    public void back(View view){
setData ( 2 );

    }

    public void right(View view){

setData ( 3 );
    }

    public void left(View view){
setData ( 4 );

    }

    public void stop(View view){
setData ( 5 );
        DatabaseReference reference2 = reference.child ( "st" );
        reference2.setValue ( 0);
    }

    public void spray(View view){
        setData(6);

    }


    public void start(View view){
        DatabaseReference reference2 = reference.child ( "st" );
        reference2.setValue ( 1 );
        DatabaseReference reference3 = reference.child ( "sp" );
        try {
            reference3.setValue ( Integer.parseInt ( text.getText ( ).toString ( ) ) );
        }catch (Exception e){

            Toast.makeText ( BotControlActivity.this,"Please Enter Speed",Toast.LENGTH_LONG).show ();
        }

    }


    public void toggle(View view){


if(feedStatus == 0){

    cameraFeed.setVisibility(View.INVISIBLE);
    feedStatus = 1;

}else{
    feedStatus = 0;
    cameraFeed.setVisibility(View.VISIBLE);

}



    }




    void setData(int command){

DatabaseReference reference2 = reference.child ( "c" );
reference2.setValue (command);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;
        LatLng latLng = new LatLng(lati,lang);
        markerOptions = new MarkerOptions().position(latLng).title("Bot Location 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.robotlocation));
        mainMarker = myMap.addMarker(markerOptions);
        DatabaseReference coordinateRef = FirebaseDatabase.getInstance().getReference().child("Lcation");
        coordinateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    HashMap<String, Double> map = (HashMap<String, Double>) dataSnapshot.getValue();
                    LatLng latLng = new LatLng(map.get("Lati"),map.get("Longi"));
                    mainMarker.setPosition(latLng);

                }catch (Exception e){
                    System.out.println("Exception");
                    Toast.makeText(BotControlActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });





    }



    @SuppressLint("SetTextI18n")
    public void auto(View view){


if(isAutomated == 0) {
    final EditText Length = new EditText(BotControlActivity.this);
    final EditText breath = new EditText(BotControlActivity.this);
    final  EditText parts = new EditText(BotControlActivity.this);
    Length.setHint("Enter Length");
    parts.setHint("Enter repetition");

    LinearLayout linearLayout = new LinearLayout(BotControlActivity.this);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.addView(Length);
    linearLayout.addView(parts);



    AlertDialog.Builder builder = new AlertDialog.Builder(BotControlActivity.this);
    builder.setTitle("Enter Parameters");
    builder.setView(linearLayout);


    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (!Length.getText().toString().equals("")  && !parts.getText().toString().equals("")) {
                try {

                    DatabaseReference reference2 = reference.child("A");
                    reference2.setValue(1);
                    DatabaseReference reference3 = reference.child("c");
                    reference3.setValue(Integer.parseInt(Length.getText().toString()));
                    DatabaseReference reference4 = reference.child("sp");
                    reference4.setValue(Integer.parseInt(parts.getText().toString()));
                    automationButton.setText("manual");
                    isAutomated = 1;



                } catch (Exception e) {


                }

            }else{

                Toast.makeText(BotControlActivity.this,"Please Select all Parameters",Toast.LENGTH_SHORT).show();
            }



        }




    });


    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {


            dialog.cancel();


        }
    });

    AlertDialog alertDialog = builder.create ( );
    alertDialog.show ( );




}else {
try {
    DatabaseReference reference2 = reference.child("A");
    reference2.setValue(0);
    DatabaseReference reference3 = reference.child("c");
    reference3.setValue(5);
    DatabaseReference reference4 = reference.child("sp");
    reference4.setValue(Integer.parseInt(text.getText().toString()));
    isAutomated = 0;
    automationButton.setText("Auto");
}catch (Exception e){

}




}






    }







}
