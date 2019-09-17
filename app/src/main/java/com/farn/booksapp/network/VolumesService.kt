package com.farn.booksapp.network

import com.farn.booksapp.models.VolumeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VolumesService{
    @GET("volumes?{}&maxResults=40")
    fun getVolumes(@Query("q")searchKey : String) : Observable<VolumeResponse>
}