package com.bangkit2023.c23ps453.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2023.c23ps453.data.MeasuringRepository

class MainViewModelFactory private constructor(private val measuringRepository: MeasuringRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}