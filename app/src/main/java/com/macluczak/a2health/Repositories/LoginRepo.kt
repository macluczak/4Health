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

    fun isUsernameAvailable(username: String): Boolean {
        val query = database.collection("users").whereEqualTo("username", username).get()
        return query.result.isEmpty
    }
}