package com.example.botlocation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {



    FirebaseAuth mAuth;
    EditText username;
    EditText Password;
    ProgressBar progressBar;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance ();

        if(mAuth.getCurrentUser () != null){

            Intent intent = new Intent (LogInActivity.this,MainActivity.class);
            startActivity (intent);
        }

    }


public void logIn(View view){


//        progressBar.setVisibility (View.VISIBLE);
//        logInMethod ();

Intent intent = new Intent(LogInActivity.this,BotControlActivity.class);
startActivity(intent);



    //Method to LogIn

//    public void logInMethod( ){
//
//        mAuth = FirebaseAuth.getInstance ();
//        mAuth.signInWithEmailAndPassword (username.getText ().toString (),Password.getText ().toString ()).addOnCompleteListener (new OnCompleteListener <AuthResult> ( ) {
//            @Override
//            public void onComplete( @NonNull Task<AuthResult> task )
//            {
//
//                if(task.isSuccessful ()){
//
//                    Toast.makeText (LogInActivity.this,"LogIn Succ",Toast.LENGTH_SHORT).show ();
//                    Intent intent = new Intent (LogInActivity.this,MainActivity.class);
//                    startActivity (intent);
//
//                    progressBar.setVisibility (View.INVISIBLE);
//
//
//                }else {
//
//                    Toast.makeText (LogInActivity.this,"LogIn Failed",Toast.LENGTH_SHORT).show ();
//                    progressBar.setVisibility (View.INVISIBLE);
//
//                }
//            }
//        });



    }


    public void signIn(View view){

        Intent in = new Intent (LogInActivity.this,SignIn.class);
        startActivity (in);
    }

    public void iot(View view){

        Intent intent = new Intent(LogInActivity.this,Main_Activity3.class);
        startActivity(intent);
    }

}
