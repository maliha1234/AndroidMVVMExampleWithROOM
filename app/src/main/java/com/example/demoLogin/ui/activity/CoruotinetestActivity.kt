package com.example.demoLogin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.myapplicationlogintest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoruotinetestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coruotinetest)

       val mHandler = object : Handler(Looper.myLooper()!!){
           override fun handleMessage(msg: Message) {
               super.handleMessage(msg)

           }
       }

        mHandler.obtainMessage()

        GlobalScope.launch(Dispatchers.IO) { // not confined -- will work with main thread
            Log.d("before", "Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            Log.d("after", "Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }

    }
}