package com.example.demoLogin.ui.login

import android.util.Patterns
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoLogin.data.LoginRepository
import com.example.demoLogin.data.LoginUser
import com.example.demoLogin.data.dao.PersonDaoNew
import com.example.demoLogin.data.dao.PersonNew
import com.example.myapplicationlogintest.R
import kotlinx.coroutines.launch

class LoginViewModel internal constructor(  //private MutableLiveData<LoginUser> loginResult = new MutableLiveData<>();
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val loginFormState = MutableLiveData<LoginFormState>()
    fun getLoginFormState(): LiveData<LoginFormState> {
        return loginFormState
    }

    val loginResult: LiveData<LoginUser>
        get() = loginRepository.loginResult

    fun login(username: String?, password: String?) {
        loginRepository.login(LoginUser(username, password))
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        if (username == null) {
            return false
        }
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            !username.trim { it <= ' ' }.isEmpty()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim { it <= ' ' }.length > 5
    }

    fun insertIntoDB(personDaoNew: PersonDaoNew, personNew: PersonNew){
     viewModelScope.launch {
            loginRepository.insert(personDaoNew,personNew)}
    }


}