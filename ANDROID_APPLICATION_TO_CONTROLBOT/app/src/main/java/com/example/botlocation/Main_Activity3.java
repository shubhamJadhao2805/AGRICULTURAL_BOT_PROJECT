package com.example.botlocation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main_Activity3 extends AppCompatActivity {


    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);

        reference = FirebaseDatabase.getInstance().getReference().child("user");
    }



    public void on(View view){

        reference.setValue("ON").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Main_Activity3.this,"ON",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Main_Activity3.this,"OFF",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void off(View view){


        reference.setValue("OFF").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Main_Activity3.this,"OFF",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Main_Activity3.this,"ON",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
