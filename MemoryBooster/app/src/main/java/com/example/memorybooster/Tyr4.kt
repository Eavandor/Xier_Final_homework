package com.example.memorybooster

import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.*
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Tyr4 : Service() {
    companion object {
        var servi = 0
        var wordStarted=false
        var textCardStarted=false
        var expiredPop=0
    }
   
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var m = msg.data.getString("content")
            if (m != null) {
                val editor=getSharedPreferences("data",0)
                var need=editor.getBoolean("openNotification",false)
                var needToast=editor.getBoolean("openToast",false)
                if (need){
                    Notificationnnn().send(getApplicationContext(), "1", "233", "您有到时的复习卡，请查收~", m, "")
                }
                if (needToast){
                            Toast.makeText(
            getApplicationContext(),
            m,
            Toast.LENGTH_LONG
        )
            .show();
                }

            }


        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        servi = 1


        //前台service代码：
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("2", "Notification 2", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val p = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "2")
            .setContentTitle("Memory Booster后台进程")
            .setStyle(NotificationCompat.BigTextStyle().bigText("少侠刀下留人！请勿结束本后台进程，让卑职继续守护你的复习计划吧~"))
////                .setStyle(
////                    NotificationCompat.BigPictureStyle().bigPicture(
////                        BitmapFactory.decodeResource(
////                            resources, R.drawable.nl
////                        )
////                    )
////                )
            .setAutoCancel(true)
            .setContentIntent(p)
            .setSmallIcon(R.drawable.head)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.head))
            .build()
        Thread.sleep(1000)
//            playRingTone()
//            playVibrate()
//            manager.notify(1, notification)
        startForeground(2, notification)


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Toast.makeText(
//            getApplicationContext(),
//            "onStartCommand发的信息",
//            Toast.LENGTH_LONG
//        )
//            .show();

        addTimer()


        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(

            getApplicationContext(),
            "Memory Booster后台进程已中断，若想继续运行复习提醒服务，请重启Memory Booster",
            Toast.LENGTH_LONG
        )
            .show();
    }

    fun addTimer() {
        Notificationnnn.manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Notificationnnn.resources = resources
        var timer = Timer(true)

//    var l=ArrayList<Reminder>()
//    l.add(Reminder("2021-08-23 19:17:30","操作系统"))
//    l.add(Reminder("2021-08-23 19:17:50","计算机网络"))
        var l = ArrayList<TimeUnitt>()

        if (TimeManager.verifyDigit == 1) {
            var t = TimeManager.added.time
            var str = TimeManager.added.msg
            eightPoints(t,str,timer)
            TimeManager.verifyDigit = 0
        } else {


            if (TimeManager.originalTTime.isEmpty() == false&&(textCardStarted==false)) {
                textCardStarted=true
                l = TimeManager.originalTTime
                for (a in l) {
                    var t = a.time
                    var str=a.msg
                        eightPoints(t,str,timer)

                }
                val msg = Message()
                msg.what = 1
                var bun = Bundle()
                bun.putString("content", "叮~~您有"+ expiredPop+"份复习卡片正眼巴巴等你来哦")
                msg.data = bun
                handler.sendMessage(msg)
                expiredPop=0
            }
            if (TimeManager.originalWTime.isEmpty() == false&&(wordStarted==false)) {
                wordStarted=true
                l = TimeManager.originalWTime
                for (a in l) {
                    var t = a.time
                    var str=a.msg
                    eightPoints(t,str,timer)
                }
                val msg = Message()
                msg.what = 1
                var bun = Bundle()
                bun.putString("content", "叮~~您有"+ expiredPop+"份复习卡片正眼巴巴你来哦")
                msg.data = bun
                handler.sendMessage(msg)
                expiredPop=0
            }


        }


    }



    fun judge(time: String, content: String,timer:Timer) {
        var c = Date().time
        var t = strToDateLong(time).time
        if (c - t > 3600000) {

        } else {
            var tm: TimerTask = object : TimerTask() {
                override fun run() {
                    val msg = Message()
                    msg.what = 1
                    var bun = Bundle()
                    bun.putString("content", content)
                    msg.data = bun
                    handler.sendMessage(msg)
                }

            }
            timer.schedule(tm, Date(strToDateLong(time).time))
        }
    }
    fun judge2(time: String):Boolean {
        var c = Date().time
        var t = strToDateLong(time).time
        if (c - t > 3600000) {
            return false

        } else if((c-t)<0){
            return false
        }
        return true
    }




    fun strToDateLong(str: String): Date {
        var s: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var pos: ParsePosition = ParsePosition(0)
        var date: Date = s.parse(str, pos)
        return date
    }


    fun eightPoints(time: String, content: String,timer:Timer) {
        var t0=strToDateLong(time)
        var t1=Date(t0.time+300000)   //5 min。第一个复习点
        var t2=Date(t0.time+1800000)   //30 min。第一个复习点
        var t3=Date(t0.time+43200000)   //12h。第一个复习点
        var t4=Date(t0.time+86400000)   //1 day。第一个复习点
        var t5=Date(t0.time+172800000)   //2 day。第一个复习点
        var t6=Date(t0.time+345600000)   //4 day。第一个复习点
        var t7=Date(t0.time+604800000)   //7 day。第一个复习点
        var t8=Date(t0.time+1296000000)   //15 day。第一个复习点
        var str1=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t1)
        var str2=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t2)
        var str3=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t3)
        var str4=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t4)
        var str5=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t5)
        var str6=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t6)
        var str7=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t7)
        var str8=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t8)
        if (judge2(str1)){
            expiredPop++}else{
            judge(str1, content+"（第一个复习点）",timer)
        }
        if (judge2(str2)){
            expiredPop++}else{
            judge(str2, content+"（第二个复习点）",timer)
        }
        if (judge2(str3)){
            expiredPop++}else{
            judge(str3, content+"（第三个复习点）",timer)
        }
        if (judge2(str4)){
            expiredPop++}else{
            judge(str4, content+"（第四个复习点）",timer)
        }
        if (judge2(str5)){
            expiredPop++}else{
            judge(str5, content+"（第五个复习点）",timer)
        }
        if (judge2(str6)){
            expiredPop++}else{
            judge(str6, content+"（第六个复习点）",timer)
        }
        if (judge2(str7)){
            expiredPop++}else{
            judge(str7, content+"（第七个复习点）",timer)
        }
        if (judge2(str8)){
            expiredPop++}else{
            judge(str8, content+"（第八个复习点）",timer)
        }
    }


}