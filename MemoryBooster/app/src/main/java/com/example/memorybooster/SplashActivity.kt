package com.example.memorybooster

import Login.Login2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }
    override fun onStart() {
        super.onStart()
        Thread(Runnable {
            val start = System.currentTimeMillis()
            val costTime = System.currentTimeMillis() - start
            //等待sleeptime时长
            if (2000 - costTime > 0) {
                try {
                    Thread.sleep(2000 - costTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            //进入主页面

                startActivity(Intent(this@SplashActivity, Login2::class.java))  //前往登陆界面

            finish()
        }).start()
    }
}