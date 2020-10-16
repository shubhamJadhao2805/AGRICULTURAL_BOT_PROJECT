package com.example.botlocation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    ProgressBar progressBar;
    EditText username;
    EditText password;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_in);

        username = findViewById (R.id.username);
        password = findViewById (R.id.password);
        progressBar = findViewById (R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();


    }


    //Method to SignIn

    public void signInMethod(){
        mAuth.createUserWithEmailAndPassword (username.getText ().toString (),password.getText ().toString ()).addOnCompleteListener (new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {
                if(task.isSuccessful ()){
                    Toast.makeText (SignIn.this,"New User Created",Toast.LENGTH_SHORT).show ();
                     progressBar.setVisibility (View.INVISIBLE);

                }else{

                    Toast.makeText (SignIn.this,"Failed to Create User",Toast.LENGTH_SHORT).show ();
                    progressBar.setVisibility (View.INVISIBLE);

                }
            }
        });



    }





    public void signIn( View view){
        progressBar.setVisibility (View.VISIBLE);
        signInMethod ();

    }



    //Button to go back

    public void logInPage(View view){

        Intent in = new Intent (SignIn.this,LogInActivity.class);
        startActivity (in);
    }


}
