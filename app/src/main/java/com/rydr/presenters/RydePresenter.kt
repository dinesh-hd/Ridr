package com.rydr.presenters

import com.rydr.pojo.LocData

class RydePresenter {

    private val gpsDataDump = ArrayList<LocData>()

    fun getLocDump(): List<LocData> {
        return gpsDataDump;
    }


    fun addLatLong(lat: Double, lon: Double) {
        gpsDataDump.add(LocData(0,lat, lon))
    }

    fun getLatLonDump(): ArrayList<LocData> {
        return gpsDataDump
    }
}