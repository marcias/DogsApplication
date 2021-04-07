package com.msc.dogsapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msc.dogsapplication.model.DogBreed

class DetailViewModel() : ViewModel() {
    val dog = MutableLiveData<DogBreed>()

    fun fetch() {
        val dogBreed =
            DogBreed("1", "Labrador", "7", "breedGroup", "breedFor", "temperament", "imageUri")
        dog.value = dogBreed
    }

}