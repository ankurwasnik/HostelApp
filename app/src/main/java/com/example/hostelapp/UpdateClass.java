package com.example.hostelapp;

import com.google.firebase.Timestamp;
import com.google.type.Date;

public class UpdateClass{
    String title , message ;
    Timestamp timeStamp ;

    public UpdateClass(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public UpdateClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}