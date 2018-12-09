package com.rydr.models

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.rydr.database.LocDao
import com.rydr.database.LocDatabase
import com.rydr.pojo.LocData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Repository
class RydeRepository(app: Application) {

    private var locDao: LocDao = LocDatabase.getInstance(app).locDao()
    private var allLocs: LiveData<List<LocData>> = locDao.getAllLocations()

    fun insertLocation(loc: LocData) {
        GlobalScope.launch {
            locDao.insertLoc(loc)
        }
    }

    fun insertLocations(locList: List<LocData>) {
        GlobalScope.launch {
            for (loc in locList) {
                locDao.insertLoc(loc)
            }
        }
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: LocDao) :
            AsyncTask<LocData, Void, Void>() {

        override fun doInBackground(vararg params: LocData): Void? {
            mAsyncTaskDao.insertLoc(params[0])
            return null
        }
    }

    private class InsertMultipleAsyncTask internal constructor(private val mAsyncTaskDao: LocDao) :
            AsyncTask<List<LocData>, Void, Void>() {

        override fun doInBackground(vararg params: List<LocData>): Void? {
            for (locData in params[0]) {
                mAsyncTaskDao.insertLoc(locData)
            }

            return null
        }
    }
}