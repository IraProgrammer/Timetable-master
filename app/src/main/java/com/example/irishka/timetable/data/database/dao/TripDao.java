package com.example.irishka.timetable.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.timetable.data.database.entity.TripDb;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TripDao {

    //вставка поездки в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TripDb trip);

    //получение списка всех поездок из БД
    @Query("SELECT * FROM TripDb")
    Single<List<TripDb>> getTrips();

}
