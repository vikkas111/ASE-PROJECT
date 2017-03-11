package com.example.criminaldatabase.model;

public class UploadCriminal {

    int _id;
    String _criminalname;
    String _email_id;
    String _mobile;
    String _address;
    String _criminal_rec_no;
    String _crime_description;
    String _image;
    String _crime_location;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_criminalname() {
        return _criminalname;
    }

    public void set_criminalname(String _criminalname) {
        this._criminalname = _criminalname;
    }

    public String get_email_id() {
        return _email_id;
    }

    public void set_email_id(String _email_id) {
        this._email_id = _email_id;
    }

    public String get_mobile() {
        return _mobile;
    }

    public void set_mobile(String _mobile) {
        this._mobile = _mobile;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_criminal_rec_no() {
        return _criminal_rec_no;
    }

    public void set_criminal_rec_no(String _criminal_rec_no) {
        this._criminal_rec_no = _criminal_rec_no;
    }

    public String get_crime_description() {
        return _crime_description;
    }

    public void set_crime_description(String _crime_description) {
        this._crime_description = _crime_description;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_crime_location() {
        return _crime_location;
    }

    public void set_crime_location(String _crime_location) {
        this._crime_location = _crime_location;
    }
}
