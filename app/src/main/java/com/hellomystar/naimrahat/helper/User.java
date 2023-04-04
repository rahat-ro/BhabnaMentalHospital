package com.hellomystar.naimrahat.helper;

public class User {

    private int id;
    private String name,age ,username, email, gender;

    public User(int id, String name, String age, String username, String email, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
