package com.example.memorybooster

import FunctionsInM.AllActivities
import Login.Login2
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
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