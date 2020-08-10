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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

public class newUpdate extends AppCompatActivity {

    EditText etUpdate_subject, etUpdate_details;
    Button btnSubmit_update;
    ProgressDialog progressDialog ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_update);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("Add Update");

        etUpdate_subject = findViewById(R.id.etUpdate_subject);
        etUpdate_details = findViewById(R.id.etUpdate_details);
        btnSubmit_update = findViewById(R.id.btnSubmit_update);

        btnSubmit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update_subject, update_details ;
                update_subject = etUpdate_subject.getText().toString().trim();
                update_details = etUpdate_details.getText().toString().trim();
                if (update_subject.isEmpty() || update_details.isEmpty()) {
                    Toast.makeText(newUpdate.this, "Enter all details please ", Toast.LENGTH_SHORT).show();
                } else {
                   UpdateClass update = new UpdateClass() ;
                    Timestamp timeStamp=Timestamp.now() ;
                   update.setTitle(update_subject);
                   update.setMessage(update_details);
                   update.setTimeStamp(timeStamp);
                    progressDialog = new ProgressDialog(newUpdate.this);
                    progressDialog.setTitle("Updating");
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    db.collection("updates")
                            .add(update)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressDialog.dismiss();
                                    Toast.makeText(newUpdate.this, "Your Update is registered", Toast.LENGTH_SHORT).show();
                                    etUpdate_details.setText("");
                                    etUpdate_subject.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(newUpdate.this, "Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected man
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_update);
        //set item click listener man
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_bottom_nav_home:
                        startActivity(new Intent(newUpdate.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        newUpdate.this.finish();
                        return true;

                    case R.id.menu_bottom_nav_dashboard:
                        startActivity(new Intent( newUpdate.this , Dashboard.class));
                        newUpdate.this.finish();
                        return  true ;



                    case R.id.menu_bottom_nav_setting:
                        startActivity(new Intent(newUpdate.this, ProfileSetting.class));
                        overridePendingTransition(0, 0);
                        newUpdate.this.finish();
                        return true;
                    case R.id.menu_bottom_nav_update:
                        return true;

                }
                return false;
            }
        });


    }

}
