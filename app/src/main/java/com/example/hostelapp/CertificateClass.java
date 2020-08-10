package com.example.hostelapp;

/*
to create hostel certificate we need name , current_studying_year , branch ,stream , room no , current_year , semesters ;
our profile have name, branch ,room,stream



we need is current_studying year , current_year , semester
so calender can have current year ,
ask user for semesters ,  studying year ,
 */

public class CertificateClass {
    String name ,studying_year, branch ,  stream ,  id , room_, semester   ;
    int current_year ;

    public CertificateClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRoom_() {
        return room_;
    }

    public void setRoom_(String room_) {
        this.room_ = room_;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudying_year() {
        return studying_year;
    }

    public void setStudying_year(String studying_year) {
        this.studying_year = studying_year;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public int getCurrent_year() {
        return current_year;
    }

    public void setCurrent_year(int current_year) {
        this.current_year = current_year;
    }
}
