package com.example.calorietrackerr;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class Steps {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "step")
    public String step;

    public Steps(String step) {
        this.step=step;
    }

    public int getId() {
        return uid;
    }

    public String getStep() {
        return step;
    }
    public void setStep(String step) {
        this.step = step;
    }
}



