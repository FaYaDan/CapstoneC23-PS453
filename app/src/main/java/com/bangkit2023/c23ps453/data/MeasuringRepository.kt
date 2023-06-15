package com.bangkit2023.c23ps453.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bangkit2023.c23ps453.data.response.PredictResponse
import com.bangkit2023.c23ps453.data.retrofit.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeasuringRepository private constructor(
    private val apiService: ApiService,
    private val auth: FirebaseAuth
){

    private val resultRegister = MediatorLiveData<Result<FirebaseUser>>()
    private val resultLogin = MediatorLiveData<Result<FirebaseUser>>()
    private val resultPredict = MediatorLiveData<Result<String>>()

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

    fun predict(age: String, height: String, weight: String): LiveData<Result<String>> {
        resultPredict.value = Result.Loading
        val user = JsonObject()
        user.addProperty("age", age)
        user.addProperty("height", height)
        user.addProperty("weight", weight)
        val client = apiService.predict(user.toString())
        client.enqueue(object  : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                if (response.isSuccessful){
                    resultPredict.value = Result.Success(response.body()?.prediction!!)
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                resultPredict.value = Result.Error(t.message.toString())
            }

        })
        return resultPredict
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