package com.example.hostelapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

public class ApplicationClass extends Application {
    public  static FirebaseUser app_user ;
    public  static UserProfileClass userProfileClass;
}
