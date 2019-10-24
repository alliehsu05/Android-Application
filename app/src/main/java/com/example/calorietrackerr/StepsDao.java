package com.example.calorietrackerr;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
@Dao
public interface StepsDao {

    @Query("SELECT * FROM steps")
    List<Steps> getAll();

    @Query("SELECT * FROM steps WHERE uid = :stepsid LIMIT 1")
    Steps findByID(int stepsid);


    @Insert
    void insertAll(Steps... Steps);
    @Insert
    long insert(Steps Steps);
    @Delete
    void delete(Steps Steps);
    @Update(onConflict = REPLACE)
    public void updateInfo(Steps... Steps);
    @Query("DELETE FROM steps")
    void deleteAll();
}
