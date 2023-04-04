package com.hellomystar.naimrahat.helper;

public class DoctorModel {

     private int doctor_id;
     private String doctor_name;
     private String doctor_img;
     private String doctor_qualification;
     private String doctor_designation;
     private String doctor_expertise;
     private String doctor_chamber;

    public DoctorModel(int doctor_id, String doctor_name, String doctor_img,
                       String doctor_qualification, String doctor_designation, String doctor_expertise, String doctor_chamber) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_img = doctor_img;
        this.doctor_qualification = doctor_qualification;
        this.doctor_designation = doctor_designation;
        this.doctor_expertise = doctor_expertise;
        this.doctor_chamber = doctor_chamber;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getDoctor_img() {
        return doctor_img;
    }

    public String getDoctor_qualification() {
        return doctor_qualification;
    }

    public String getDoctor_designation() {
        return doctor_designation;
    }

    public String getDoctor_expertise() {
        return doctor_expertise;
    }

    public String getDoctor_chamber() {
        return doctor_chamber;
    }
}
