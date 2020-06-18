package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {
        private Button signOut;
        private FirebaseAuth.AuthStateListener authListener;
        private FirebaseAuth auth;
        private Button btn_add_item, btn_check_order, btn_signout;


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);

            auth = FirebaseAuth.getInstance();



            //get current user

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



            authListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {

                        startActivity(new Intent(AdminActivity.this, SignInActivity.class));
                        finish();

                    }

                }

            };



            signOut = (Button) findViewById(R.id.sign_out);

            btn_add_item = (Button) findViewById(R.id.add_btn);
            btn_check_order = (Button) findViewById(R.id.check_orders_btn);
            signOut = (Button) findViewById(R.id.sign_out_btn);



            btn_add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(AdminActivity.this, AdminAddItemActivity.class);
                    startActivity(intent);
                }
            });


            btn_check_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(AdminActivity.this, AdminCheckOrdersActivity.class);
                    startActivity(intent);
                }
            });

            signOut.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    signOut();

                }

            });


        }

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
