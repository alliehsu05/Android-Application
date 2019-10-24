package com.example.calorietrackerr;

import java.util.Date;

public class Users {
    private Integer userid;
    private String username;
    private String usersname;
    private String email;
    private String dob;
    private Double height;
    private Double weight;
    private String gender;
    private String address;
    private Integer postcode;
    private Integer levelofact;
    private Integer stepsmile;


    public Users(Integer userid, String username, String usersname, String email, String dob, Double height, Double weight, String gender,
                 String address, Integer postcode, Integer levelofact,Integer stepsmile){
        this.userid = userid;
        this.username = username;
        this.usersname = usersname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelofact = levelofact;
        this.stepsmile = stepsmile;
    }

    public Users() {
    }

    public Integer getUserid() {
        return userid;
    }

    public String getUsersname() {
        return username;
    }

    public String getSurname() {
        return usersname;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public Integer getLevelofact() {
        return levelofact;
    }
    public Integer getStepsmile() {
        return stepsmile;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public void setLevelofact(Integer levelofact) {
        this.levelofact = levelofact;
    }

    public void setStepsmile(Integer stepsmile) {
        this.stepsmile = stepsmile;
    }

}
