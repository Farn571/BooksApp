package com.farn.booksapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.farn.booksapp.models.ImageLinks
import com.farn.booksapp.models.Volume
import com.farn.booksapp.models.VolumeResponse
import com.farn.booksapp.network.VolumesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VolumesViewModel : ViewModel() {

    private val volumesRepository: VolumesRepository = VolumesRepository()
    private val disposable = CompositeDisposable()
    val volumesLiveData = MutableLiveData<VolumeResponse>()
    var searchViewCondition : String = "" //для сохранения последней записи в SearchView
    var lastSearchQuery : String = "" //для сохранения последнего запроса

    fun getVolumes(searchKey : String) {
        if (searchKey.isEmpty())
        {
            lastSearchQuery = "kotlin"
        }
        else
        {
            lastSearchQuery = searchKey
        }

        disposable.addAll(
            volumesRepository.getVolumes(lastSearchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                         var volumes : List<Volume> = fixNullImages(it.volumes)
                         if(it?.volumes == null){

                             postUIState(error = (Throwable("\"Книги по такому запросу не найдены\"")))
                         }else{
                             postUIState(volumes = volumes)
                         }
                    },
                    {
                         postUIState(error = it)
                    })
        )
    }

    private fun postUIState(volumes: List<Volume> = emptyList(), error: Throwable? = null) {
        volumesLiveData.postValue(VolumeResponse(volumes, error))
    }

    private fun fixNullImages(volumes: List<Volume>) : List<Volume>{
        for (volume in volumes){
            if (volume.imageLinks == null){
                volume.imageLinks = ImageLinks("", "")
            }
            if(volume.imageLinks != null)
            {
                if(volume.imageLinks.thumbnail == null){
                    volume.imageLinks.thumbnail = ""
                }
            }
        }
        return volumes
    }

    override fun onCleared() {
        disposable.clear()
    }

}