package com.example.criminaldatabase.model;

public class Report {
    private int id;
    private String mobile;
    private String address;
    private int user_id;
    private int criminal_id;
    private String username;
    private String criminalname;
    private String crime_location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCriminal_id() {
        return criminal_id;
    }

    public void setCriminal_id(int criminal_id) {
        this.criminal_id = criminal_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCriminalname() {
        return criminalname;
    }

    public void setCriminalname(String criminalname) {
        this.criminalname = criminalname;
    }

    public String getCrime_location() {
        return crime_location;
    }

    public void setCrime_location(String crime_location) {
        this.crime_location = crime_location;
    }
}
