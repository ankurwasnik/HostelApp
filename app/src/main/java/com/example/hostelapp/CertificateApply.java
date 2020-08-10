/*
to create hostel certificate we need name , current_studying_year , branch ,stream , room no , current_year , semesters ;
our profile have name, branch ,room,stream



we need is current_studying year , current_year , semester
so calender can have current year ,
ask user for semesters ,  studying year ,
 */
package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CertificateApply extends AppCompatActivity {

    EditText etCertificate_semester ,etCertificate_studying_year ;
    Button btnCertificate_applysubmit ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_apply);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("Certificate");

        btnCertificate_applysubmit=findViewById(R.id.btnCertificate_applysubmit);
        etCertificate_semester=findViewById(R.id.etCertificate_semester);
        etCertificate_studying_year=findViewById(R.id.etCertificate_studying_year);

        btnCertificate_applysubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                certificate_submit();
            }
        });
    }


    public  void  certificate_submit(){
        final String studyingyear , semester ;
        studyingyear=etCertificate_studying_year.getText().toString().trim() ;
        semester=etCertificate_semester.getText().toString().trim();

        if (semester.isEmpty() || studyingyear.isEmpty()){
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            db.collection("profiles").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserProfileClass userProfile = documentSnapshot.toObject(UserProfileClass.class);
                            CertificateClass certificate = new CertificateClass() ;
                            certificate.setName(userProfile.getName());
                            certificate.setId(userProfile.getCollegeId());
                            certificate.setBranch(userProfile.getBranch());
                            certificate.setRoom_(userProfile.room);
                            certificate.setSemester(semester);
                            certificate.setStream(userProfile.getStream());
                            certificate.setStudying_year(studyingyear);
                            Calendar calendar = Calendar.getInstance();
                            certificate.setCurrent_year(calendar.get(Calendar.YEAR));

                            final ProgressDialog dialog = new ProgressDialog(CertificateApply.this);
                            dialog.setTitle("Applying  "); dialog.setMessage("Please wait");
                            dialog.show();

                            db.collection("Certificate_request").document(mAuth.getCurrentUser().getUid())
                                    .set(certificate)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CertificateApply.this, "Sorry "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            etCertificate_semester.setText("");
                                            etCertificate_studying_year.setText("");
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                           dialog.dismiss();
                                            Toast.makeText(CertificateApply.this, "You have applied for certificate successfully", Toast.LENGTH_SHORT).show();
                                            etCertificate_semester.setText("");
                                            etCertificate_studying_year.setText("");
                                        }
                                    });



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

}
