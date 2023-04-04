package com.hellomystar.naimrahat.helper;

public class Appointment {

    private String ap_id;
    private String drname,expertise,name,id,email,date,status,drImg;

    public Appointment(String ap_id, String drname,
                       String expertise, String name, String id, String email, String date, String status, String drImg) {
        this.ap_id = ap_id;
        this.drname = drname;
        this.expertise = expertise;
        this.name = name;
        this.id = id;
        this.email = email;
        this.date = date;
        this.status = status;
        this.drImg = drImg;
    }

    public String getAp_id() {
        return ap_id;
    }

    public String getDrname() {
        return drname;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getDrImg() {
        return drImg;
    }
}
