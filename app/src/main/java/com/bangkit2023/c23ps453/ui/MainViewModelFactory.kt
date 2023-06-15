package com.bangkit2023.c23ps453.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2023.c23ps453.data.MeasuringRepository
import com.bangkit2023.c23ps453.di.Injection
import com.bangkit2023.c23ps453.ui.authentication.login.LoginViewModel
import com.bangkit2023.c23ps453.ui.authentication.register.RegisterViewModel
import com.bangkit2023.c23ps453.ui.resultsMeasuring.ResultMeasuringViewModel

class MainViewModelFactory private constructor(private val measuringRepository: MeasuringRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(measuringRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(measuringRepository) as T
        }
        if (modelClass.isAssignableFrom(ResultMeasuringViewModel::class.java)){
            return ResultMeasuringViewModel(measuringRepository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel Class : " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance : MainViewModelFactory? = null
        fun getInstance(context: Context) : MainViewModelFactory =
            instance ?: synchronized(this){
                instance ?: MainViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}