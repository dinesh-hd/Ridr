package com.rydr.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rydr.pojo.LocData

@Dao
interface LocDao {

    @Insert
    fun insertLoc(loc: LocData)

    @Query("DELETE FROM loc_table")
    fun deleteAll()

    @Query("SELECT * FROM loc_table")
    fun getAllLocations(): LiveData<List<LocData>>

}