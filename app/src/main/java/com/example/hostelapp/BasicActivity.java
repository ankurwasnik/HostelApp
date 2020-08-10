package com.example.hostelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends AppCompatActivity {
    Button btnRectorLogin  ,btnStudentLogin,btnServicemanLogin;
    private  final  static  String BASIC_PREFS_FILENAME="com.hostelapp.basicSharedPreference";
    SharedPreferences preferences=getSharedPreferences(BASIC_PREFS_FILENAME,MODE_PRIVATE);
    SharedPreferences.Editor editor = getSharedPreferences(BASIC_PREFS_FILENAME,MODE_PRIVATE).edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);


        btnRectorLogin=findViewById(R.id.btnRectorLogin);
        btnStudentLogin=findViewById(R.id.btnStudentLogin);
        btnServicemanLogin=findViewById(R.id.btnServicemanLogin);





    }

    public void setPrefRector(View view) {
        editor.putString("BASIC_PREFERENCE","RECTOR")
                .apply();
        startActivity(new Intent(BasicActivity.this , MainActivity.class));
        this.finish();

    }

    public void setPrefStudent(View view) {
        editor.putString("BASIC_PREFERENCE","STUDENT")
                .apply();
        startActivity(new Intent(BasicActivity.this , MainActivity.class));
        this.finish();
    }

    public void setPrefServiceman(View view) {
        editor.putString("BASIC_PREFERENCE","SERVICEMAN")
                .apply();
        startActivity(new Intent(BasicActivity.this , MainActivity.class));
        this.finish();
    }

    private  String getBasicPreference(){
        return preferences.getString("BASIC_PREFERENCE","");
    }


}
