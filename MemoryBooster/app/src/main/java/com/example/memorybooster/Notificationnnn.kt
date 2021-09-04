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

//这个函数是发送通知的，如果用户给了权限，将支持锁屏显示，弹窗通知，任务栏通知，响铃通知以及振动通知。
        //title是通知的标题，content表示通知的内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, notificationName, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(conte, MainActivity::class.java)
        val p = PendingIntent.getActivity(conte, 0, intent, 0)
        val notification = NotificationCompat.Builder(conte, channelId)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))    //是的通知（在任务栏显示的通知）可以显示长长一段文字
//                .setStyle(
//                    NotificationCompat.BigPictureStyle().bigPicture(
//                        BitmapFactory.decodeResource(
//                            resources, R.drawable.nl
//                        )
//                    )           //设置通知图的，但是不太需要了，只能在状态栏栏目显示，锁屏显示和通知弹窗都不支持，鸡肋的很，但是在很早的Android系统曾经一度支持视频显示加图片，但是Android 10好像不行
//                )

            .setAutoCancel(true)
            .setContentIntent(p)        //设置点击通知，可以打开复习卡显示的那个Activity
            .setSmallIcon(R.drawable.head)       //设置小图标
//            .setLargeIcon(BitmapFactory.decodeResource(Context.resources, R.drawable.head))
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.head))
            .build()

        val editor=conte.getSharedPreferences("data",0)       //从SharedPreference里面拿出，用户是否想要提示时候振动，以及响铃声
        var wantring=editor.getBoolean("openRing",true)
        var wantvibrate=editor.getBoolean("openViborate",true)
        if (wantring){                          //如果用户想要响铃，就响
            playRingTone(conte)
            Thread.sleep(900)
            var mediaPlayer= MediaPlayer.create(conte,R.raw.tishiying)
            mediaPlayer.start()
        }
        if (wantvibrate){            //用户想要振动，就振动
            playVibrate(conte)
        }

        manager.notify(1, notification)       //发通知

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