package com.macluczak.a2health.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macluczak.a2health.Repositories.LoginRepo
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    val repo = LoginRepo()
    val usernameAvailable = repo.usernameAvailable
    val userCreated = repo.userCreated
    val createdUser = repo.createdUser



    fun createAccount(username: String, password: String) {
        repo.isUsernameAvailable(username, password)
    }
}