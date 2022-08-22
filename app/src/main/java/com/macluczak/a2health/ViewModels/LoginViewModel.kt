package com.macluczak.a2health.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macluczak.a2health.Repositories.LoginRepo
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var usernameAvailable = MutableLiveData<Boolean>()
    val repo = LoginRepo()

    fun createAccount(username: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching { repo.isUsernameAvailable(username) }
                .onSuccess {
                    Log.d("jvbisubyurbvjsbyvjyrv", "createAccount:fkjsdnfukrg ")
                    if (it) {
                        repo.createAccount(username, password)
                    }
                }
                .onFailure { Log.d("Failure", "createAccount: Failure") }
        }
    }
}