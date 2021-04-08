package com.msc.dogsapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.msc.dogsapplication.model.DogBreed
import com.msc.dogsapplication.model.DogDatabase
import com.msc.dogsapplication.model.DogsApiService
import com.msc.dogsapplication.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val sPrefHelper = SharedPreferencesHelper(getApplication())
    private val disposable = CompositeDisposable()
    private val dogsService = DogsApiService()
    private var refreshTime = 5 * 60 * 1000L

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val updateTime = sPrefHelper.getUpdateTime()
        val time = if (updateTime != null) System.currentTimeMillis() - updateTime else 0
        if (updateTime != null && updateTime != 0L && time < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshByPassCache() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogsService.getDogs().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribeWith(object : DisposableSingleObserver<List<DogBreed>>() {
                override fun onSuccess(dogList: List<DogBreed>) {
                    storeDogsLocally(dogList)
                    Toast.makeText(
                        getApplication(),
                        "Dogs retrieved from endpoint",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onError(e: Throwable) {
                    loading.value = false
                    dogsLoadError.value = true
                    e.printStackTrace()
                }

            })
        )
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            val dogs = dao.getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun dogsRetrieved(list: List<DogBreed>) {
        loading.value = false
        dogsLoadError.value = false
        dogs.value = list
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAll()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                i++
            }
            dogsRetrieved(list)
            sPrefHelper.saveUpdateTime(System.currentTimeMillis())
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}