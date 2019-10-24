package com.example.calorietrackerr;

public class Report {
    private Integer reportid;
    private String userid;
    private String reportdate;
    private Double totalcalconsumed;
    private Double totalcalburned;
    private Integer totalstepstaken;
    private Double calgoal;

    public Report(Integer reportid, String userid, String reportdate, Double totalcalconsumed, Double totalcalburned, Integer totalstepstaken, Double calgoal){
        this.reportid = reportid;
        this.userid = userid;
        this.reportdate = reportdate;
        this.totalcalconsumed = totalcalconsumed;
        this.totalcalburned = totalcalburned;
        this.totalstepstaken = totalstepstaken;
        this.calgoal = calgoal;

    }

    public Report() {
    }


    public Integer getReportid() {
        return reportid;
    }
    public String getUserid() {
        return userid;
    }
    public String getReportdate() {
        return reportdate;
    }
    public Double getTotalcalconsumed() {
        return totalcalconsumed;
    }
    public Double getTotalcalburned() {
        return totalcalburned;
    }
    public Integer getTotalstepstaken() {
        return totalstepstaken;
    }
    public Double getCalgoal() {
        return calgoal;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }
    public void setTotalcalconsumed(Double totalcalconsumed) {
        this.totalcalconsumed = totalcalconsumed;
    }
    public void setTotalcalburned(Double totalcalconsumed) {
        this.totalcalconsumed = totalcalconsumed;
    }
    public void setTotalstepstaken(Integer totalstepstaken) {
        this.totalstepstaken = totalstepstaken;
    }
    public void setCalgoal(Double calgoal) {
        this.calgoal = calgoal;
    }


}
