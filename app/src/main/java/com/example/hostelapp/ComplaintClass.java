package com.example.hostelapp;

import com.google.firebase.Timestamp;

public class ComplaintClass {
    String subject , details , userroom;
    Timestamp timestamp ;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserroom() {
        return userroom;
    }

    public void setUserroom(String userroom) {
        this.userroom = userroom;
    }
}
