package com.macluczak.a2health.Repositories
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.gson.Gson
import com.macluczak.a2health.Models.UserModel

class LoginRepo {

    val database = Firebase.firestore

    val userCreated = MutableLiveData<Boolean>()
    val usernameAvailable = MutableLiveData<Boolean>()
    val createdUser = MutableLiveData<UserModel>()
    val ifLoggedin = MutableLiveData<Boolean>()
    val loggedUser = MutableLiveData<UserModel>()

    fun createAccount(username: String, password: String) {
        database.collection("users").add(UserModel(username, password))
            .addOnSuccessListener{
                userCreated.postValue(true)
                createdUser.postValue(UserModel(username, password))
            }
            .addOnFailureListener{
                userCreated.postValue(false)
            }
    }


    fun isUsernameAvailable(username: String, password: String){
        database.collection("users").whereEqualTo("username", username).get().addOnSuccessListener{
            if (it.isEmpty){
                usernameAvailable.postValue(true)
                createAccount(username, password)
            } else {
                usernameAvailable.postValue(false)
            }
        } .addOnFailureListener{
            Log.d("FAILURE", "isUsernameAvailable: ")
        }
    }

    fun loginToAccount(username: String, password: String) {
        database.collection("users").whereEqualTo("username", username).get().addOnSuccessListener{
            if (!it.isEmpty && it.documents[0].exists()) {
                val model = if (!it.isEmpty) {
                    Gson().fromJson(Gson().toJson(it.documents[0].data), UserModel::class.java)
                } else null
                if (model!!.password!! == password) {
                    ifLoggedin.postValue(true)
                    loggedUser.postValue(model!!)
                } else {
                    ifLoggedin.postValue(false)
                }

            } else {
                ifLoggedin.postValue(false)
            }
        }
    }

}