package com.msc.dogsapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msc.dogsapplication.model.DogBreed

class ListViewModel : ViewModel() {
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val dog1 = DogBreed("1", "Labrador", "2 years", "breadGroup", "breedFor", "temperament", "")
        val dog2 = DogBreed("2", "Golden", "6 years", "breadGroup", "breedFor", "temperament", "")
        val dog3 = DogBreed("3", "Shih tzu", "1 year", "breadGroup", "breedFor", "temperament", "")
        val dog4 = DogBreed("4", "Rotweiler", "4 years", "breadGroup", "breedFor", "temperament", "")
        val dog5 = DogBreed("5", "Corgi", "15 years", "breadGroup", "breedFor", "temperament", "")

        val dogList = arrayListOf(dog1, dog2, dog3, dog4, dog5)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}