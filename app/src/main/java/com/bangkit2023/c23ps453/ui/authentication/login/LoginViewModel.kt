package com.bangkit2023.c23ps453.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit2023.c23ps453.data.MeasuringRepository
import com.bangkit2023.c23ps453.data.Result
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val measuringRepository: MeasuringRepository): ViewModel() {
    fun login(email: String, password: String): LiveData<Result<FirebaseUser>> = measuringRepository.login(email, password)
}