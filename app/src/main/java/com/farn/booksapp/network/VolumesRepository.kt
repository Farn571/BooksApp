package com.farn.booksapp.network


class VolumesRepository{
    private val service: VolumesService = RetrofitService.create()
    fun getVolumes(searchKey : String) = service.getVolumes(searchKey)
}