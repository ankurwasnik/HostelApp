package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class newComplaint extends AppCompatActivity {
    EditText etComplaint_subject, etComplaint_details ;
    Button btnSubmit_complaint ;
    ProgressDialog progressDialog ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("Add Complaint");


        etComplaint_subject=findViewById(R.id.etComplaint_subject);
        etComplaint_details=findViewById(R.id.etComplaint_details);

        btnSubmit_complaint=findViewById(R.id.btnSubmit_complaint);
        btnSubmit_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String complaintSubject=etComplaint_subject.getText().toString().trim() ,
                        complaintDetails=etComplaint_details.getText().toString().trim();
                if(complaintDetails.isEmpty() || complaintSubject.isEmpty()){
                    Toast.makeText(newComplaint.this, "Please enter all details ", Toast.LENGTH_SHORT).show();


                }
                else {

                    ComplaintClass complaint = new ComplaintClass() ;
                    Timestamp timestamp=Timestamp.now();
                    complaint.setTimestamp(timestamp);
                    complaint.setDetails(complaintDetails);
                    complaint.setSubject(complaintSubject);
                    complaint.setUserroom(FirebaseAuth.getInstance().getUid());
                    progressDialog = new ProgressDialog(newComplaint.this);
                    progressDialog.setTitle("");
                    progressDialog.setMessage("Registering your Complaint");
                    progressDialog.show();

                    db.collection("complaints")
                            .add(complaint)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                   progressDialog.dismiss();
                                    Toast.makeText(newComplaint.this, "Your complaint is registered", Toast.LENGTH_SHORT).show();
                                    etComplaint_subject.setText("");
                                    etComplaint_details.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(newComplaint.this, "Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }

            }
        });





    }
}
