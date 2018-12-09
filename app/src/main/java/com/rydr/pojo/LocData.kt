package com.rydr.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "loc_table")
data class LocData(
        @PrimaryKey(autoGenerate = true)
        val locID : Int,
        val lat: Double, val lon: Double)