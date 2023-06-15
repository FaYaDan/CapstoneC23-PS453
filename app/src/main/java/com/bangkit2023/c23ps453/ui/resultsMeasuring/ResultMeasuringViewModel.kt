package com.bangkit2023.c23ps453.ui.resultsMeasuring

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit2023.c23ps453.data.MeasuringRepository
import com.bangkit2023.c23ps453.data.Result

class ResultMeasuringViewModel (private val measuringRepository: MeasuringRepository): ViewModel()  {
    fun predict(age: String, height: String, weight: String): LiveData<Result<String>> = measuringRepository.predict(age, height, weight)
}