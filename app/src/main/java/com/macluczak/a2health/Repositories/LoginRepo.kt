package com.macluczak.a2health.Repositories
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.macluczak.a2health.Models.UserModel

class LoginRepo {

    val database = Firebase.firestore

    val userCreated = MutableLiveData<Boolean>()
    val usernameAvailable = MutableLiveData<Boolean>()

    fun createAccount(username: String, password: String) {
        database.collection("users").add(UserModel(username, password))
            .addOnSuccessListener{
                userCreated.postValue(true)
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
}