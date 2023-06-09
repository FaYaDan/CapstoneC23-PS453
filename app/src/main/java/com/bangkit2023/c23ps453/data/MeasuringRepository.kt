package com.bangkit2023.c23ps453.data

import com.bangkit2023.c23ps453.data.retrofit.ApiService

class MeasuringRepository private constructor(
    private val apiService: ApiService
){


    companion object {
        @Volatile
        private var instance: MeasuringRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MeasuringRepository =
            instance ?: synchronized(this) {
                instance ?: MeasuringRepository(apiService)
            }.also { instance = it }
    }
}