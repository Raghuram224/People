package com.example.everybody.factoryMethods

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everybody.viewmodels.ShowPersonsViewModel


class ShowPersonsFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowPersonsViewModel::class.java)) {
            return ShowPersonsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Factory class")
    }

}
