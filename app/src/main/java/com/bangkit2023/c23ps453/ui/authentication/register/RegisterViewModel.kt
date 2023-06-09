package com.bangkit2023.c23ps453.ui.authentication.register

import androidx.lifecycle.ViewModel
import com.bangkit2023.c23ps453.data.MeasuringRepository

class RegisterViewModel(private val measuringRepository: MeasuringRepository): ViewModel()  {
    fun register(name: String, email: String, password: String) = measuringRepository.register(name, email, password)
}