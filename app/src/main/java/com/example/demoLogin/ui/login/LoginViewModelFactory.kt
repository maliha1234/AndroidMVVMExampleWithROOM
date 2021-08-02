package com.example.demoLogin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoLogin.data.LoginDataSource
import com.example.demoLogin.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(LoginRepository.getInstance(LoginDataSource())!!) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}