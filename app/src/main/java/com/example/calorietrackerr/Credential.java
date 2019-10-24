package com.example.calorietrackerr;

import java.util.Date;

public class Credential {
    private Integer userid;
    private String username;
    private String passwordhash;
    private String signupdate;

    public Credential(Integer userid, String username, String passwordhash, String signupdate){
        this.userid = userid;
        this.username = username;
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
    }

    public Credential(){

    }

    public String getUserName(){
        return username;
    }

    public Integer getUserid(){
        return userid;
    }

    public String getPasswordhash(){
        return passwordhash;
    }

    public String getSignupdate(){
        return signupdate;
    }


    public void setUserid(Integer userid){
        this.userid = userid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPasswordhash(String passwordhash){
        this.passwordhash = passwordhash;
    }

    public void setSignupdate(String signupdate){
        this.signupdate = signupdate;
    }

}
