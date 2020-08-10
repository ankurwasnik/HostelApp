package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Dashboard extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference complaintRef = firebaseFirestore.collection("complaints");
    private ComplaintAdapter complaintAdapter;
    private FloatingActionButton fab_add_complaint;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_actionbar_backbutton);
        setTitle("Complaints");

        fab_add_complaint = findViewById(R.id.fab_add_complaint);
        fab_add_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, newComplaint.class));
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Complaints Loading");
        progressDialog.setMessage(" Please Wait");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
        new MyTask().execute();

        setRecyclerView();

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

    private void setRecyclerView() {
        Query query = complaintRef.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ComplaintClass> options = new FirestoreRecyclerOptions.Builder<ComplaintClass>()
                .setQuery(query, ComplaintClass.class)
                .build();
        complaintAdapter = new ComplaintAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.dashboard_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintAdapter);

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT ) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               if (direction==ItemTouchHelper.LEFT)
                     complaintAdapter.deleteItem(viewHolder.getAdapterPosition());

           }
       }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintAdapter.stopListening();
    }

   
}
