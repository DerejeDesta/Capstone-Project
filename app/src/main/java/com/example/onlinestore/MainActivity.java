package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView email;
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getString(R.string.app_name));
        //setSupportActionBar(toolbar);

        //get firebase auth instance

        auth = FirebaseAuth.getInstance();



        //get current user

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        authListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {

                   startActivity(new Intent(MainActivity.this, SignInActivity.class));
                   finish();

                }

            }

        };



        signOut = (Button) findViewById(R.id.sign_out);

        email = (TextView) findViewById(R.id.email);



        email.setText(user.getEmail());



        signOut.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                signOut();

            }

        });



    }



    //sign out method

    public void signOut() {

        auth.signOut();

    }



    @Override

    public void onStart() {

        super.onStart();

        auth.addAuthStateListener(authListener);

    }



    @Override

    public void onStop() {

        super.onStop();

        if (authListener != null) {

            auth.removeAuthStateListener(authListener);

        }

    }

}