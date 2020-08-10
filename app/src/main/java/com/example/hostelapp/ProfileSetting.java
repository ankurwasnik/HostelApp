package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSetting extends AppCompatActivity {
    public final String SHAREDPREFERENCE_FILENAME ="profiles_state";
    public static boolean isProfileSet ,isduplicateid ;
    private  String stream  ;
    public  static  String userCollegeID ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CircleImageView ivProfilePicture;
    EditText etStudentDisplayName, etStudentId, etStudentEmail, etStudentTelno,etStudentParentTelnr,  etStudentRoom, etStudentBranch, etStudentPermanentAddress;
    EditText et_profileedit_telnr ,et_profileedit_branch , et_profileedit_room ;
    Button btnSubmit_profile;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ScrollView scrollView;
    LinearLayout linearLayoutProfile, editLayout;
    SwitchCompat editSwitch;
    ImageButton imgbtn_editRoom, imgbtn_editBranch, imgbtn_editPhoneNumber;
    RadioButton rbDegree ,rbDiploma ;
    @Override
    protected void onStart() {
        super.onStart();
        changeview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeview();

    }

    @Override
    protected void onPause() {
        super.onPause();
        changeview();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("Profile");

        imgbtn_editBranch = findViewById(R.id.imgbtn_editBranch);
        imgbtn_editPhoneNumber = findViewById(R.id.imgbtn_editPhoneNumber);
        imgbtn_editRoom = findViewById(R.id.imgbtn_editRoom);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        etStudentDisplayName = findViewById(R.id.etStudentDisplayName);
        etStudentId = findViewById(R.id.etStudentId);
        etStudentEmail = findViewById(R.id.email);
        etStudentTelno = findViewById(R.id.etStudentTelno);
        etStudentParentTelnr=findViewById(R.id.etStudentParentTelnr);
        etStudentRoom = findViewById(R.id.etStudentRoom);
        etStudentBranch = findViewById(R.id.etStudentBranch);
        etStudentPermanentAddress = findViewById(R.id.etStudentPermanentAddress);
        btnSubmit_profile = findViewById(R.id.btnSubmit_profile);
        scrollView = findViewById(R.id.svProfile);
        linearLayoutProfile = findViewById(R.id.llProfileSet);
        editLayout = findViewById(R.id.editLayout);
        editSwitch =findViewById(R.id.switch_editProfile);
        et_profileedit_telnr=findViewById(R.id.et_profileedit_telnr);
        et_profileedit_room=findViewById(R.id.et_profileedit_room);
        et_profileedit_branch=findViewById(R.id.et_profileedit_branch);
        rbDegree=findViewById(R.id.rbDegree);
        rbDiploma=findViewById(R.id.rbDiploma);


        changeview();

        btnSubmit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName, profileId;
                profileName = etStudentDisplayName.getText().toString().trim();
                profileId = etStudentId.getText().toString().trim();
                userCollegeID=profileId;


                 if(    ( 
                         profileName.isEmpty()
                         || profileId.length()!=9
                         || etStudentId.getText().toString().isEmpty()
                         || etStudentTelno.getText().toString().trim().isEmpty()
                         || etStudentRoom.getText().toString().trim().isEmpty()
                         || etStudentBranch.getText().toString().trim().isEmpty()
                         || etStudentPermanentAddress.getText().toString().trim().isEmpty()
                         || etStudentParentTelnr.getText().toString().trim().isEmpty()
                         || stream.isEmpty() )
                   )
                 {
                     Toast.makeText(ProfileSetting.this, "Please enter all correct  details ", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     addUserProfile();
                     changeview();
                 }

                
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected man
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_setting);
        //set item click listener man
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_bottom_nav_home:
                        startActivity(new Intent(ProfileSetting.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        ProfileSetting.this.finish();
                        return true;

                    case R.id.menu_bottom_nav_dashboard:
                        startActivity(new Intent(ProfileSetting.this, Dashboard.class));
                        overridePendingTransition(0, 0);
                        ProfileSetting.this.finish();
                        return true;

                    case R.id.menu_bottom_nav_update:
                        startActivity(new Intent(ProfileSetting.this, newUpdate.class));
                        overridePendingTransition(0, 0);
                        ProfileSetting.this.finish();
                        return true;

                    case R.id.menu_bottom_nav_setting:
                        return true;

                    default:
                        return false;
                }
            }
        });

        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                      changeEditLayout();
                 }
                  else {
                    editLayout.setVisibility(View.GONE);
                  }
         }
        });

        imgbtn_editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newRoom = et_profileedit_room.getText().toString().trim();
                if (newRoom.isEmpty()){
                    Toast.makeText(ProfileSetting.this, "Please provide new room number", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.collection("profiles").document(mAuth.getCurrentUser().getUid())
                            .update("room",newRoom)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileSetting.this, "Sorry , New room is not updates \n Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileSetting.this, "Your Room Number is updated.", Toast.LENGTH_SHORT).show();
                                    et_profileedit_room.setText("");
                                }
                            });
                }
            }
        });

        imgbtn_editBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBranch = et_profileedit_branch.getText().toString().trim();
                if (newBranch.isEmpty()){
                    Toast.makeText(ProfileSetting.this, "Please provide new branch number", Toast.LENGTH_SHORT).show();
                }
                else {

                    db.collection("profiles").document(mAuth.getCurrentUser().getUid())
                            .update("branch",newBranch)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileSetting.this, "Sorry , New Branch is not updates \n Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileSetting.this, "Your Branch is updated.", Toast.LENGTH_SHORT).show();
                                    et_profileedit_branch.setText("");
                                }
                            });


                }

            }
        });
        imgbtn_editPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTelnr = et_profileedit_telnr.getText().toString().trim() ;
                if (newTelnr.isEmpty()){
                    Toast.makeText(ProfileSetting.this, "Please enter new telephone or mobile number ", Toast.LENGTH_SHORT).show();
                }
                else {

                    db.collection("profiles").document(mAuth.getCurrentUser().getUid())
                            .update("telNumber",newTelnr)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileSetting.this, "Sorry , your mobile number  is not updates \n Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileSetting.this, "Your Mobile Number is updated.", Toast.LENGTH_SHORT).show();
                                    et_profileedit_telnr.setText("");
                                }
                            });

                }
            }
        });



    }

    public   void  onRadioButtonClicked (View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case  R.id.rbDegree :
                stream="Degree";
                break;
            case  R.id.rbDiploma :
                stream="Diploma";
                break;
        }
    }
    private void changeview() {
        if ((isProfileSetCheck() || getSharedPreference() ) ) {
            scrollView.setVisibility(View.GONE);
            linearLayoutProfile.setVisibility(View.VISIBLE);

        } else {
            scrollView.setVisibility(View.VISIBLE);
            linearLayoutProfile.setVisibility(View.GONE);
        }
    }
    private boolean isProfileSetCheck() {

        DocumentReference profileRef = db.collection("profiles").document(mAuth.getCurrentUser().getUid());
        profileRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserProfileClass profile = documentSnapshot.toObject(UserProfileClass.class);
                        try {
                            isProfileSet = profile.getProfileSet();

                        }
                        catch (NullPointerException e)
                        {
                            isProfileSet=false;
                            Toast.makeText(ProfileSetting.this, "Please create your profile ", Toast.LENGTH_SHORT).show();
                            
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileSetting.this, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return  isProfileSet ;

    }
    private  void setSharedPreference(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFERENCE_FILENAME ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSharedPref_profileSet",true);
        editor.apply();

    }
    private boolean getSharedPreference () {
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFERENCE_FILENAME ,MODE_PRIVATE);
         return  sharedPreferences.getBoolean("isSharedPref_profileSet" , false);
    }
    private void changeEditLayout(){
        if (editSwitch.isEnabled()){
            changeview();
            editLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            changeview();
            editLayout.setVisibility(View.GONE);
        }
    }

    //TODO function to check duplicate values  of  id

    private  void  addUserProfile(){
        String profileName, profileId;
        profileName = etStudentDisplayName.getText().toString().trim();
        profileId = etStudentId.getText().toString().trim();
        userCollegeID=profileId;
        final ProgressDialog dialog = new ProgressDialog(ProfileSetting.this);
        dialog.setTitle("Setting Profile");
        dialog.setMessage("Please wait");
        dialog.show();
        UserProfileClass userProfile = new UserProfileClass();
        userProfile.setName(profileName);
        userProfile.setCollegeId(profileId);
        userProfile.setEmail(mAuth.getCurrentUser().getEmail());
        userProfile.setTelNumber(etStudentTelno.getText().toString().trim());
        userProfile.setRoom(etStudentRoom.getText().toString().trim());
        userProfile.setBranch(etStudentBranch.getText().toString().trim());
        userProfile.setPermanentAddress(etStudentPermanentAddress.getText().toString().trim());
        userProfile.setProfileSet(true);
        userProfile.setStream(stream);
        userProfile.setParentContact(etStudentParentTelnr.getText().toString().trim());

        FirebaseUser user = mAuth.getCurrentUser();
        db.collection("profiles").document(user.getUid())
                .set(userProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        Toast.makeText(ProfileSetting.this, "Profile Updatd Successfully ", Toast.LENGTH_SHORT).show();
                        setSharedPreference(); // set prefs
                        changeview();
                        etStudentDisplayName.setText("");
                        etStudentId.setText("");
                        etStudentTelno.setText("");
                        etStudentBranch.setText("");
                        etStudentRoom.setText("");
                        etStudentPermanentAddress.setText("");
                        etStudentParentTelnr.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileSetting.this, "Error updating profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if (item.getItemId() == R.id.menu_profile_showMyProfile){
           startActivity(new Intent(ProfileSetting.this , MyProfile.class));
       }
        return true;
    }
}
