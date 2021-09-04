package com.example.memorybooster


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Vibrator

import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class Try2 : AppCompatActivity() {            //这个类没有用，测试完发送通知以后，我个笨比交作业前要记得删掉！
    companion object{
        lateinit var rr2: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try2)
        rr2=this
        Notificationnnn.manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Notificationnnn.resources=resources
        findViewById<Button>(R.id.adsjkhadsjk).setOnClickListener {
            var title = findViewById<EditText>(R.id.askjk).text.toString()
            var content = findViewById<EditText>(R.id.askjk132).text.toString()

            Notificationnnn().send(this,"1","233",title,content,"")
//Thread.sleep(5000)
//            Notificationnnn().send(this,"1","233",title,content,"")
//            Thread.sleep(5000)
//            Notificationnnn().send(this,"1","233",title,content,"")



//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel =
//                    NotificationChannel("1", "Notification 1", NotificationManager.IMPORTANCE_HIGH)
//                manager.createNotificationChannel(channel)
//            }
//            val intent = Intent(this, Try2::class.java)
//            val p = PendingIntent.getActivity(this, 0, intent, 0)
//            val notification = NotificationCompat.Builder(this, "1")
//                .setContentTitle(title)
//                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
////                .setStyle(
////                    NotificationCompat.BigPictureStyle().bigPicture(
////                        BitmapFactory.decodeResource(
////                            resources, R.drawable.nl
////                        )
////                    )
////                )
//                .setAutoCancel(true)
//                .setContentIntent(p)
//                .setSmallIcon(R.drawable.head)
//                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.head))
//                .build()
//            Thread.sleep(1000)
//            playRingTone()
//            playVibrate()
//            manager.notify(1, notification)


        }
    }

    /**
     * 播放通知声音
     */
    fun playRingTone() {
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val rt = RingtoneManager.getRingtone(rr2, uri)
        rt.play()

    }

    /**
     * 手机震动一下
     */
    fun playVibrate() {
        val vibrator = rr2.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        val vibrationPattern = longArrayOf(0, 180, 80, 120)
        // 第一个参数为开关开关的时间，第二个参数是重复次数，振动需要添加权限
        vibrator.vibrate(vibrationPattern, -1)
    }
}