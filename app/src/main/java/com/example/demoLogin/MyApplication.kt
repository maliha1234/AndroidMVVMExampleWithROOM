package com.example.demoLogin

import android.app.Application
import android.content.Context
import com.example.demoLogin.data.LoginRepository
import com.example.demoLogin.data.dao.AppDBNew

class MyApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    override fun onCreate() {
        super.onCreate()
        MyApplication.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
    val database by lazy { AppDBNew.getDatabase(this) }
}
