package com.example.demoLogin.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoLogin.repository.AssociatesLoginRepository
import com.example.demoLogin.data.model.LoginUser
import com.example.demoLogin.data.dao.AssociatesPersonDao
import com.example.demoLogin.data.model.PersonNew
import com.example.demoLogin.ui.view.AssociatesLoginFormState
import com.example.myapplicationlogintest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssociatesLoginViewModel internal constructor(  //private MutableLiveData<LoginUser> loginResult = new MutableLiveData<>();
    private val associatesLoginRepository: AssociatesLoginRepository
) : ViewModel() {
    private val loginFormState = MutableLiveData<AssociatesLoginFormState>()
    fun getLoginFormState(): LiveData<AssociatesLoginFormState> {
        return loginFormState
    }

    val loginResult: LiveData<LoginUser>
        get() = associatesLoginRepository.loginResult

    fun login(username: String?, password: String?) {
        associatesLoginRepository.login(
            LoginUser(
                username,
                password
            )
        )
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(AssociatesLoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(AssociatesLoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(AssociatesLoginFormState(true))
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

    fun insertIntoDB(associatesPersonDao: AssociatesPersonDao, personNew: PersonNew){
     viewModelScope.launch(Dispatchers.IO) {
            associatesLoginRepository.insert(associatesPersonDao,personNew)}
    }


}