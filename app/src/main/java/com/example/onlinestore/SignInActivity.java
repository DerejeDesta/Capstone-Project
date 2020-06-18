package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private EditText email_editText, password_editText;
    private TextView createAccount;
    private FirebaseAuth auth;
    private ProgressDialog loadingBar;
    private Button sign_in_button;
    private TextView  forgotP_textView;
    private final String adminEmail = "admin@admin.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
       if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();

        }

        // set the view now
        setContentView(R.layout.activity_sign_in);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        email_editText = (EditText) findViewById(R.id.signIn_Email_editText);
        password_editText = (EditText) findViewById(R.id.signIn_password_editText);
        loadingBar = new ProgressDialog(this);
        sign_in_button = (Button) findViewById(R.id.signIn_sign_in_button);
        createAccount  = (TextView) findViewById(R.id.singIn_CreateAcc_TextView);
        forgotP_textView = (TextView) findViewById(R.id.singIn_forgetP_TextView);

        forgotP_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }

        });

        createAccount.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, CreateAccountActivity.class));

            }

        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                final String email = email_editText.getText().toString();
                final String password = password_editText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;

                }

                loadingBar.setTitle("Sign-In");
                loadingBar.setMessage("authenticating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if(email.equals(adminEmail)){
                                        Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        loadingBar.dismiss();
                                    }else{
                                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        loadingBar.dismiss();
                                    }



                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }



                        });


            }

        });


    }

    private void resetPassword() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.forgot_password, null);
        dialogBuilder.setView(dialogView);

        final EditText editEmail = (EditText) dialogView.findViewById(R.id.email);
        final Button btnReset = (Button) dialogView.findViewById(R.id.btn_reset_password);
        final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        final AlertDialog dialog = dialogBuilder.create();

        btnReset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;

                }

                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override

                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Password reset failed!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();

                            }

                        });

            }

        });
        dialog.show();
    }

}
