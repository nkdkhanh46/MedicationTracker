package com.martin.medicationtracker.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.medicationtracker.models.User
import com.martin.medicationtracker.repositories.UserRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(userRepository: UserRepository): ViewModel() {

    val user: LiveData<User> = userRepository.getCurrentUser()

    val username = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val bloodType = MutableLiveData<String>()
    val avatar = MutableLiveData<String>()

    fun bindUserInformation(user: User) {
        username.value = user.name
        age.value = "${user.age}"
        bloodType.value = user.bloodType
        avatar.value = user.avatar
    }
}