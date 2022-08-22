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
        val docData = hashMapOf(
            username to "username",
            password to "password"
        )
//        if (isUsernameAvailable(username)) {
//            database.collection("users").document(username).set(UserModel(username, password))
//                .addOnSuccessListener { Log.d("%%%%$$$$####", "createAccount: OK")}
//                .addOnFailureListener{ Log.d("%%%%$$$$####", "createAccount: NON")}
//            userCreated.postValue(true)
//        } else {
//            userCreated.postValue(false)
//        }
        database.collection("users").get()           .addOnSuccessListener { Log.d("%%%%$$$$####", "createAccount: OK")}
            .addOnFailureListener{ Log.d("%%%%$$$$####", "createAccount: NON")}
        userCreated.postValue(true)
    }

    fun isUsernameAvailable(username: String){
        val query = database.collection("users").whereEqualTo("username", username).get().addOnSuccessListener{
            if (it.isEmpty){
                usernameAvailable.postValue(true)
            } else {
                usernameAvailable.postValue(false)
            }
        } .addOnFailureListener{
            Log.d("FAILURE", "isUsernameAvailable: ")
        }
    }
}