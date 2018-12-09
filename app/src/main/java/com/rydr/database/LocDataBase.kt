package com.rydr.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rydr.pojo.LocData
import com.rydr.utils.SingletonHolder


@Database(entities = arrayOf(LocData::class), version = 1)
abstract class LocDatabase : RoomDatabase() {


    abstract fun locDao(): LocDao

    companion object : SingletonHolder<LocDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                LocDatabase::class.java, "loc_Data").build()

    })

}