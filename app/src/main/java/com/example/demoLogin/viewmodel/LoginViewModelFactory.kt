package com.example.demoLogin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoLogin.data.model.LoginDataSource
import com.example.demoLogin.repository.AssociatesLoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AssociatesLoginViewModel::class.java)) {
            AssociatesLoginViewModel(AssociatesLoginRepository.getInstance(LoginDataSource())!!) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}