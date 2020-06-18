package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestore.Classes.AppUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private EditText email_editText, password_editText, name_editText,adress_editText;
    private Button  btn_Create;
    private TextView signIn_textView;
    private ProgressDialog loadingBar;
    private String name,email,password,address;
    private FirebaseAuth auth;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        signIn_textView = (TextView) findViewById(R.id.createAccount_signIn_TextView);
        btn_Create = (Button) findViewById(R.id.createAccount_create_button);
        email_editText = (EditText) findViewById(R.id.createAccount_email_editText);
        name_editText =(EditText)  findViewById(R.id.createAccount_name_editText);
        password_editText = (EditText) findViewById(R.id.createAccount_password_editText);
        adress_editText =(EditText)  findViewById(R.id.createAccount_address_editText);

        loadingBar = new ProgressDialog(this);
        signIn_textView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, SignInActivity.class));

            }

        });

        btn_Create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = email_editText.getText().toString().trim();
                name = name_editText.getText().toString();
                password = password_editText.getText().toString().trim();
                address = adress_editText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;

                }

                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("creating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                //create user

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingBar.dismiss();
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateAccountActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    AppUser user = new AppUser();
                                    user.setName(name);
                                    user.setEmail(email);
                                    user.setAddress(address);
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    final DatabaseReference databaseReference;
                                    databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(user).
                                            addOnCompleteListener(CreateAccountActivity.this,
                                                    new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful())
                                                            {
                                                                Toast.makeText(CreateAccountActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                                                                loadingBar.dismiss();
                                                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                            else
                                                            {
                                                                loadingBar.dismiss();
                                                                Toast.makeText(CreateAccountActivity.this, "Error, Please try again...", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                }
                            }

                        });
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingBar.dismiss();
    }

}