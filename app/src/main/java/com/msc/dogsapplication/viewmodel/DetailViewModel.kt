package com.msc.dogsapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msc.dogsapplication.model.DogBreed
import com.msc.dogsapplication.model.DogDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val dog = MutableLiveData<DogBreed>()

    fun fetchData(uuid: Int) {
        launch {
            val dogDao = DogDatabase(getApplication()).dogDao()
            dog.value = dogDao.getDog(uuid)

        }
    }

}