package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    Button btnSignIn , btnExit ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btn_Sign_In);
        btnExit=findViewById(R.id.btnExit);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserSignedIn()) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));

                }
                else {
                    createSignInIntent();
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                finishAndRemoveTask();
                finish();

            }
        });
        intstantiateUser();

    }



    private void intstantiateUser() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    public void createSignInIntent() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
                startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.profile_icon)
                        .build(),
                RC_SIGN_IN);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isUserSignedIn()) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            MainActivity.this.finish();
        }
    }

    private boolean isUserSignedIn() {
        if (mFirebaseUser == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            //user is signed in
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Signing In ");
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            progressDialog.dismiss();
            MainActivity.this.finish();

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...


        }

    }

    public void signOut() {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


}
