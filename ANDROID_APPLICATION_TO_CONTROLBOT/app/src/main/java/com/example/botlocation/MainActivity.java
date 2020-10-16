package com.example.botlocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.telephony.SmsManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView backgroundImage;
    Button stateChangeButton;
    ProgressBar progressBar;
    Button dashBoard;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        backgroundImage = findViewById (R.id.backgrounImage);
        stateChangeButton = findViewById (R.id.button9);
        progressBar = findViewById (R.id.progressBarStatus);
        dashBoard = findViewById (R.id.dashboard);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance ( ).getReference ( ).child ("Status Of Bot");
        Map <String, String> map = new HashMap <String, String> ( );
        map.put ("State","DEACTIVATE");
        databaseReference.setValue (map);
        stateChangeButton.setText ("ACTIVATE");

    }






    //Method to send On Signal To Bot

    public void action( View view) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance ( ).getReference ( ).child ("Status Of Bot");
        Map <String, String> map = new HashMap <String, String> ( );

        if (stateChangeButton.getText ( ) == "ACTIVATE") {
            progressBar.setVisibility (View.VISIBLE);
            databaseReference = FirebaseDatabase.getInstance ( ).getReference ( ).child ("Status Of Bot");
            map.put ("State" , "ACTIVATE");
            databaseReference.setValue (map).addOnCompleteListener (new OnCompleteListener <Void> ( ) {
                @Override
                public void onComplete( @NonNull Task <Void> task ) {
                    if (task.isSuccessful ( )) {
                        dashBoard.setVisibility (View.VISIBLE);
                        progressBar.setVisibility (View.INVISIBLE);
                        Toast.makeText (MainActivity.this , "ACTIVATED" , Toast.LENGTH_SHORT).show ( );
                        stateChangeButton.setText ("DEACTIVATE");
                        backgroundImage.setImageResource (R.drawable.greencircle);


                    } else {
                        progressBar.setVisibility (View.INVISIBLE);
                        Toast.makeText (MainActivity.this , "Failed" , Toast.LENGTH_SHORT).show ( );

                    }
                }
            });

        } else {
            progressBar.setVisibility (View.VISIBLE);
            map.put ("State" , "DEACTIVATE");
            databaseReference.setValue (map).addOnCompleteListener (new OnCompleteListener <Void> ( ) {
                @Override
                public void onComplete( @NonNull Task <Void> task ) {
                    if (task.isSuccessful ( )) {

                        dashBoard.setVisibility (View.INVISIBLE);
                        progressBar.setVisibility (View.INVISIBLE);
                        Toast.makeText (MainActivity.this , "DEACTIVATED" , Toast.LENGTH_SHORT).show ( );
                        stateChangeButton.setText ("ACTIVATE");
                        backgroundImage.setImageResource (R.drawable.circle2);


                    } else {

                        progressBar.setVisibility (View.INVISIBLE);
                        Toast.makeText (MainActivity.this , "Failed" , Toast.LENGTH_SHORT).show ( );
                    }
                }
            });


        }
    }


    //Method to go to DashBoard
    public void dash(View view){

        Intent intent = new Intent ( MainActivity.this,BotControlActivity.class );
        startActivity (intent);

    }


}
