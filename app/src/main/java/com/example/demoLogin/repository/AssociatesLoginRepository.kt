package com.example.demoLogin.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoLogin.api.AssociatesLoginApi
import com.example.demoLogin.data.model.LoginDataSource
import com.example.demoLogin.data.dao.AssociatesPersonDao
import com.example.demoLogin.data.model.PersonNew
import com.example.demoLogin.data.model.LoggedInUser
import com.example.demoLogin.data.model.LoginUser
import com.example.demoLogin.service.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class AssociatesLoginRepository private constructor(dataSource: LoginDataSource) {
    private val associatesLoginApi: AssociatesLoginApi
    val result = LoginUser()
    val loginResult: LiveData<LoginUser>
        get() = Companion.loginResult

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private var user: LoggedInUser? = null
    val isLoggedIn: Boolean
        get() = user != null

    fun logout() {
        user = null
    }

    private fun setLoggedInUser(user: LoggedInUser) {
        this.user = user
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun login(loginUser: LoginUser?) {
        // handle login

        //result.setJob(loginUser.getJob());
        // result.setName(loginUser.getName());
        associatesLoginApi.createPost(loginUser).enqueue(object : Callback<LoginUser> {
            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                // this method is called when we get response from our api.
                if (response.isSuccessful) {
                    Log.d("response", "onResponse: " + response.body()!!.name)
                    result.name = response.body()!!.job
                    result.job = response.body()!!.job
                    Log.d("responsehhhh", "onResponse: " + result.name)
                    Companion.loginResult.value = result
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {}
        })
    }

    companion object {
        @Volatile
        private var instance: AssociatesLoginRepository? = null
        private val loginResult = MutableLiveData<LoginUser>()
        public fun getInstance(dataSource: LoginDataSource): AssociatesLoginRepository? {
            if (instance == null) {
                instance = AssociatesLoginRepository(dataSource)
            }
            return instance
        }
    }

    // private constructor : singleton access
    init {
        associatesLoginApi = LoginService.createService(AssociatesLoginApi::class.java)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(associatesPersonDao: AssociatesPersonDao, personNew: PersonNew) {
        associatesPersonDao.insertPerson(personNew)
    }

}