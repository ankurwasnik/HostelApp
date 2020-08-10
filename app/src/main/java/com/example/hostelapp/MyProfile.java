package com.example.hostelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfile extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView tvProfileName ,tvProfileEmail ,tvProfileRoom ,tvProfileID,tvProfileBranch,tvProfileStream,
            tvProfileTelNr,tvProfileParentContact,tvProfilePermanentAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("My Profile");


        tvProfileName=findViewById(R.id.tvProfileName);
        tvProfileEmail=findViewById(R.id.tvProfileEmail);
        tvProfileRoom=findViewById(R.id.tvProfileRoom);
        tvProfileID=findViewById(R.id.tvProfileID);
        tvProfileBranch=findViewById(R.id.tvProfileBranch);
        tvProfileStream=findViewById(R.id.tvProfileStream);
        tvProfileTelNr=findViewById(R.id.tvProfileTelNr);
        tvProfileParentContact=findViewById(R.id.tvProfileParentContact);
        tvProfilePermanentAddress=findViewById(R.id.tvProfilePermanentAddress);


        db.collection("profiles").document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                       UserProfileClass userProfileClass = documentSnapshot.toObject(UserProfileClass.class);
                        //TODO setup all views to attributes.
                        tvProfileName.setText(userProfileClass.getName());
                        tvProfileEmail.setText(userProfileClass.getEmail());
                        tvProfileRoom.setText(userProfileClass.getRoom());
                        tvProfileID.setText(userProfileClass.getCollegeId());
                        tvProfileBranch.setText(userProfileClass.getBranch());
                        tvProfileStream.setText(userProfileClass.getStream());
                        tvProfileTelNr.setText(userProfileClass.getTelNumber());
                        tvProfileParentContact.setText(userProfileClass.getParentContact());
                        tvProfilePermanentAddress.setText(userProfileClass.getPermanentAddress());

                    }
                });



    }
}
