package com.macluczak.a2health.Interface

interface UserLogInterface {
    fun logIn(user: String)
    fun logOut()
    fun getLoggedUser(): String
}