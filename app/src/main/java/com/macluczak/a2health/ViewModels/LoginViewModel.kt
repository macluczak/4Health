package com.macluczak.a2health.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.macluczak.a2health.Models.UserModel
import com.macluczak.a2health.Repositories.LoginRepo

class LoginViewModel: ViewModel() {

    val repo = LoginRepo()
    val userAccount = repo.loggedUser
    val ifLogged = repo.ifLoggedin

    fun loginToAccount(username: String, password: String) {
        repo.loginToAccount(username, password)
    }
}