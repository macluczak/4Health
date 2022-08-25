package com.macluczak.a2health.ViewModels

import android.util.Log
import android.view.Display
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.macluczak.a2health.Models.UserModel
import com.macluczak.a2health.Repositories.LoginRepo

class LoginViewModel: ViewModel() {

    val repo = LoginRepo()
    var userAccount: UserModel? = null

    fun loginToAccount(username: String, password: String, respond: (UserModel?) -> Unit) {
        repo.loginToAccount(username, password){
            respond(it)
            userAccount = it
        }


    }
}