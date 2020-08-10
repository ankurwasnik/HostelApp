package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference updateRef = firebaseFirestore.collection("updates");
    private UpdateAdapter updateAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updates Loading");
        progressDialog.setMessage(" Please Wait");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
        new MyTask().execute();
        setRecyclerView();

        //initiate the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected man
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_home);
        //set item click listener man
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_bottom_nav_home:
                        return true;

                    case R.id.menu_bottom_nav_dashboard:
                        startActivity(new Intent(HomeActivity.this, Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_bottom_nav_setting:
                        startActivity(new Intent(HomeActivity.this, ProfileSetting.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_bottom_nav_update:
                        startActivity(new Intent(HomeActivity.this, newUpdate.class));
                        overridePendingTransition(0, 0);
                }

                return false;
            }
        });


    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {
            progressDialog.show();
        }

        public Void doInBackground(Void... unused) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //initiate the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected man
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_home);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //initiate the bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected man
        bottomNavigationView.setSelectedItemId(R.id.menu_bottom_nav_home);
    }

    private void setRecyclerView() {
        Query query = updateRef.orderBy("timeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<UpdateClass> options = new FirestoreRecyclerOptions.Builder<UpdateClass>()
                .setQuery(query, UpdateClass.class)
                .build();
        updateAdapter = new UpdateAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.home_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(updateAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        updateAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_app_bar_certificate:
                startActivity(new Intent(HomeActivity.this , CertificateApply.class));
                return  true ;


            case R.id.menu_app_bar_logout:
                AuthUI.getInstance().signOut(this);
                Intent intent_logout = new Intent();
                intent_logout.addFlags(intent_logout.FLAG_ACTIVITY_CLEAR_TOP);
                intent_logout.addFlags(intent_logout.FLAG_ACTIVITY_CLEAR_TASK);
                finishAndRemoveTask();
                finishAffinity();
                return  true ;

            case R.id.menu_app_bar_contact_us :
                String number = "022-24198286";
                Intent intent_contact = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                intent_contact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent_contact.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent_contact);
                return true ;

            case  R.id.menu_app_bar_help :
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Send an email to ankurwasnik001@gmail.com ") ;
                alertDialog.setTitle("Help");
                alertDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent_share = new Intent(Intent.ACTION_SEND);
                        intent_share.setType("text/plain");
                        intent_share.putExtra(Intent.EXTRA_SUBJECT, "Send email ");
                        intent_share.addFlags(intent_share.FLAG_ACTIVITY_CLEAR_TASK);
                        intent_share.addFlags(intent_share.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent_share, "Share app via"));
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.create().dismiss();
                            }
                        });

                alertDialog.create().show();


                return true ;

            case  R.id.menu_app_bar_share :
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                intent_share.setType("text/plain");
                intent_share.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                intent_share.putExtra(Intent.EXTRA_TEXT, " Hi \n Download VJTI HostelApp  from Playstore ");
                intent_share.addFlags(intent_share.FLAG_ACTIVITY_CLEAR_TASK);
                intent_share.addFlags(intent_share.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent_share, "Share app via"));
                return  true ;

            case  R.id.menu_app_bar_hostel_map :
                Intent intent_map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=VJTI"));
                intent_map.setPackage("com.google.android.apps.maps");
                intent_map.addFlags(intent_map.FLAG_ACTIVITY_CLEAR_TASK);
                intent_map.addFlags(intent_map.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_map);
                return  true ;

            case  R.id.menu_app_bar_hostel_website :
                Intent intent_web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vjti.ac.in/"));
                intent_web.addFlags(intent_web.FLAG_ACTIVITY_CLEAR_TASK);
                intent_web.addFlags(intent_web.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_web);
                return  true ;


            default:
                return false;
        }

    }
}
