package com.martin.medicationtracker.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martin.medicationtracker.models.User
import com.martin.medicationtracker.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getCurrentUser(): LiveData<User> {
        val user = User(
            "https://secure.img1-fg.wfcdn.com/im/02238154/compr-r85/8470/84707680/pokemon-pikachu-wall-decal.jpg",
            "Martin",
            30,
            "AB"
        )

        val result = MutableLiveData<User>()
        result.value = user
        return result
    }
}