package com.bangkit2023.c23ps453.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.findNavController
import com.bangkit2023.c23ps453.data.retrofit.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MeasuringRepository private constructor(
    private val apiService: ApiService,
    private val auth: FirebaseAuth
){

    private val resultRegister = MediatorLiveData<Result<FirebaseUser>>()
    private val resultLogin = MediatorLiveData<Result<FirebaseUser>>()

    fun register(name: String, email: String, password: String): LiveData<Result<FirebaseUser>> {
        resultRegister.value = Result.Loading
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                resultRegister.value = Result.Success(Firebase.auth.currentUser!!)
            } else {
                resultRegister.value = Result.Error("Authentication failed" + task.exception)
            }
        }
        return resultRegister
    }

    fun login(email: String, password: String): LiveData<Result<FirebaseUser>> {
        resultLogin.value = Result.Loading
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                resultLogin.value = Result.Success(auth.currentUser!!)
            } else {
                resultLogin.value = Result.Error("Authentication failed" + task.exception)
            }
        }
        return resultLogin
    }

    companion object {
        @Volatile
        private var instance: MeasuringRepository? = null
        fun getInstance(
            apiService: ApiService,
            auth: FirebaseAuth
        ): MeasuringRepository =
            instance ?: synchronized(this) {
                instance ?: MeasuringRepository(apiService,auth)
            }.also { instance = it }
    }
}