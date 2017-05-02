package com.example.criminaldatabase.model;


public class Criminal {
    private int id;
    private String criminal_name;
    private String email_id;
    private String mobile;
    private String address;
    private String criminal_rec_no;
    private String description;
    private String imageurl;
    private String crime_location;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCriminal_name() {
        return criminal_name;
    }

    public void setCriminal_name(String criminal_name) {
        this.criminal_name = criminal_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
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

    public String getCriminal_rec_no() {
        return criminal_rec_no;
    }

    public void setCriminal_rec_no(String criminal_rec_no) {
        this.criminal_rec_no = criminal_rec_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCrime_location() {
        return crime_location;
    }

    public void setCrime_location(String crime_location) {
        this.crime_location = crime_location;
    }
}
