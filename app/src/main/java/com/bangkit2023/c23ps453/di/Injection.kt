package com.bangkit2023.c23ps453.di

import android.content.Context
import com.bangkit2023.c23ps453.data.MeasuringRepository
import com.bangkit2023.c23ps453.data.retrofit.ApiConfig
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Injection {
    fun provideRepository(context: Context) : MeasuringRepository {
        val apiService = ApiConfig.getApiService()
        val auth = Firebase.auth
        return MeasuringRepository.getInstance(apiService,auth)
    }
}