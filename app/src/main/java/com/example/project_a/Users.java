package com.example.project_a;

public class Users {

    String email, full_name, mobile_no, address, default_address;

    public Users(String email, String full_name, String mobile_no, String address, String default_address) {
        this.email = email;
        this.full_name = full_name;
        this.mobile_no = mobile_no;
        this.address = address;
        this.default_address = default_address;
    }

    // Empty constructor
    public Users() {

    }

    //Getter and setter methods

    public String getDefault_address() {
        return default_address;
    }

    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
