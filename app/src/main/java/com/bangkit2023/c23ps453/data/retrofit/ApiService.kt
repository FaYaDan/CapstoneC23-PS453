package com.bangkit2023.c23ps453.data.retrofit

import com.bangkit2023.c23ps453.data.response.PredictResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("predict")
    fun predict(@Body data: String): Call<PredictResponse>
}