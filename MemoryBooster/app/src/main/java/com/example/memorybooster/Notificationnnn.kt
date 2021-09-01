package com.example.memorybooster

import WordItems.WordActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.appcompat.app.AppCompatActivity
class Notificationnnn {
companion object{
    lateinit var manager: NotificationManager
    lateinit var resources:Resources
}
    fun send(conte:Context,channelId:String,notificationName:String,title:String,content:String,intentTarget:String){



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, notificationName, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(conte, MainActivity::class.java)
        val p = PendingIntent.getActivity(conte, 0, intent, 0)
        val notification = NotificationCompat.Builder(conte, channelId)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
//                .setStyle(
//                    NotificationCompat.BigPictureStyle().bigPicture(
//                        BitmapFactory.decodeResource(
//                            resources, R.drawable.nl
//                        )
//                    )
//                )

            .setAutoCancel(true)
            .setContentIntent(p)
            .setSmallIcon(R.drawable.head)
//            .setLargeIcon(BitmapFactory.decodeResource(Context.resources, R.drawable.head))
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.head))
            .build()





        Thread.sleep(1000)
        val editor=conte.getSharedPreferences("data",0)
        var wantring=editor.getBoolean("openRing",true)
        var wantvibrate=editor.getBoolean("openViborate",true)
        if (wantring){
            playRingTone(conte)
            Thread.sleep(900)
            var mediaPlayer= MediaPlayer.create(conte,R.raw.tishiying)
            mediaPlayer.start()
        }
        if (wantvibrate){
            playVibrate(conte)
        }

        manager.notify(1, notification)

    }


    /**
     * 播放通知声音
     */
    fun playRingTone(cont:Context) {
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val rt = RingtoneManager.getRingtone(cont, uri)
        rt.play()

    }

    /**
     * 手机震动一下
     */
    fun playVibrate(cont:Context) {
        val vibrator = cont.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        val vibrationPattern = longArrayOf(0, 180, 80, 120)
        // 第一个参数为开关开关的时间，第二个参数是重复次数，振动需要添加权限
        vibrator.vibrate(vibrationPattern, -1)
    }
}