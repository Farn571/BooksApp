package com.farn.booksapp.network

import com.farn.booksapp.models.Volume
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService{
    companion object {
        fun create() : VolumesService{
            val gson = GsonBuilder()
                .registerTypeAdapter(Volume::class.java, CustomVolumeDeserializer())
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://www.googleapis.com/books/v1/")
                .build()
            return retrofit.create(VolumesService::class.java)
        }
    }
}