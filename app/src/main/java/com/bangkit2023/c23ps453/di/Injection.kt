package com.bangkit2023.c23ps453.di

import android.content.Context
import com.bangkit2023.c23ps453.data.MeasuringRepository
import com.bangkit2023.c23ps453.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context) : MeasuringRepository {
        val apiService = ApiConfig.getApiService()
        return MeasuringRepository.getInstance(apiService)
    }
}