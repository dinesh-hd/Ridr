package com.rydr.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.API
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.rydr.models.RydeRepository
import com.rydr.pojo.LocData


class LocationListenerService : Service(), GoogleApiClient.ConnectionCallbacks,
                                GoogleApiClient.OnConnectionFailedListener {

    private val mBinder = LocationBinder()

    private lateinit var mApiClient: GoogleApiClient
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mRydeRepo: RydeRepository

    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    inner class LocationBinder : Binder() {
        fun getService(): LocationListenerService = this@LocationListenerService
    }

    override fun onCreate() {
        super.onCreate()
        mRydeRepo = RydeRepository(this.application)
    }

    override fun onConnected(p0: Bundle?) {
        var client: FusedLocationProviderClient =
                getFusedLocationProviderClient(this)
        var locationRequest: LocationRequest = LocationRequest()
                .setInterval(0)
                .setPriority(100)

        client.requestLocationUpdates(locationRequest, mLocationCallback, null)
    }

    override fun onConnectionSuspended(p0: Int) {
        // Retry ?
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        // Retry ?
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mApiClient = GoogleApiClient.Builder(application, this, this)
                .addApi(API).build()

        mApiClient.connect()
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {

                locationResult?:return
                for (location in locationResult.locations) {
                    //  Store data and update UI
                    mRydeRepo.insertLocation(LocData(0,location.latitude, location.longitude))
                }
            }
        }
        return START_STICKY
    }
}