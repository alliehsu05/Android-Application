package com.example.calorietrackerr;

public class Food {
    private Integer foodid;
    private String foodname;
    private String category;
    private Double calamount;
    private String servingunit;
    private Double servingamount;
    private Double fat;

    public Food(Integer foodid, String foodname, String category, Double calamount, String servingunit, Double servingamount, Double fat){
        this.foodid = foodid;
        this.foodname = foodname;
        this.category = category;
        this.calamount = calamount;
        this.servingunit = servingunit;
        this.servingamount = servingamount;
        this.fat = fat;

    }

    public Food() {
    }

    public Integer getFoodid() {
        return foodid;
    }
    public String getFoodname() {
        return foodname;
    }
    public String getCategory() {
        return category;
    }
    public Double getCalamount() {
        return calamount;
    }
    public String getServingunit() {
        return servingunit;
    }
    public Double getServingamount() {
        return servingamount;
    }
    public Double getFat() {
        return fat;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }
    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setCalamount(Double calamount) {
        this.calamount = calamount;
    }
    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }
    public void setServingamount(Double servingamount) {
        this.servingamount = servingamount;
    }
    public void setFat(Double fat) {
        this.fat = fat;
    }

}


