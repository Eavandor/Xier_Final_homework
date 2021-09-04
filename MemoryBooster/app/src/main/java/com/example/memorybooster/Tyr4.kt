package com.example.memorybooster

import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import android.annotation.SuppressLint
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

class Tyr4 : Service() {         //全app最有用最复杂的类，以一个前台Service的形式，在后台不断发送通知，即便用户退出，也还可以以前台Service的状态运行，不被操作系统回收
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
                val editor=getSharedPreferences("data",0)    //从SharedPreference得到，用户是否希望开启通知，抑或是Toast
                var need=editor.getBoolean("openNotification",false)
                var needToast=editor.getBoolean("openToast",false)
                if (need){
                    Notificationnnn().send(getApplicationContext(), "1", "233", "您有到时的复习卡，请查收~", m, "")
                }
                if (needToast){
                            Toast.makeText(
            getApplicationContext(),
            m,
            Toast.LENGTH_SHORT
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
        addTimer()             //把通知加入到Timer里面，定时发送的函数，如果加过了，它不会把通知重复加入的
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

        var l = ArrayList<TimeUnitt>()

        if (TimeManager.verifyDigit == 1) {     //三个地方可以调用这个addTimer()函数，所以运行前要判断一下，是三个调用放中的谁
            var t = TimeManager.added.time       //这三个调用放需要执行三个不同的功能
            var str = TimeManager.added.msg      //TimeManager.verifyDigit == 1，说明调用者是创建单词卡或创建文字复习卡片，
            eightPoints(t,str,timer)              //这时候这个值是1，本灵感来自操作系统的PV操作，但是因为多个卡片不可能同时创建，
            TimeManager.verifyDigit = 0          //所以不需要“忙等”，不需要循环检查verifyDigit这个“信号量”，并发可能性是0，一定是先后顺序
        } else {                                  //所以结束以后把这个V操作进行，信号量变成0就好了


            if (TimeManager.originalTTime.isEmpty() == false&&(textCardStarted==false)) {
                textCardStarted=true              //这是说明调用addTimer()的是将所有文字复习卡加入信息提示时间表序列的
                l = TimeManager.originalTTime      //所有要添加进消息提示弹窗消息队列的文字复习卡片，以及卡片内容（标题，文字内容，创建时间，完成度record) ，全都在里面了
                for (a in l) {                    //对TimeManager.originalTTime里面的所有卡片遍历，给 eightPoints()函数处理
                    var t = a.time
                    var str=a.msg
                        eightPoints(t,str,timer)

                }
                if(expiredPop!=0){
                    val msg = Message()
                    msg.what = 1
                    var bun = Bundle()
                    bun.putString("content", "叮~~您有"+ expiredPop+"份复习卡片正眼巴巴等你来哦")
                    msg.data = bun
                    handler.sendMessage(msg)
                }
                expiredPop=0      //循环完归零
            }
            if (TimeManager.originalWTime.isEmpty() == false&&(wordStarted==false)) {
                wordStarted=true
                l = TimeManager.originalWTime     //下面这个是单词卡的，跟上面添加逻辑差不多，都是8个复习时间点
                for (a in l) {
                    var t = a.time
                    var str=a.msg
                    eightPoints(t,str,timer)
                }
                if(expiredPop!=0){
                    val msg = Message()
                    msg.what = 1
                    var bun = Bundle()
                    bun.putString("content", "叮~~您有"+ expiredPop+"份复习卡片正眼巴巴你来哦")
                    msg.data = bun
                    handler.sendMessage(msg)
                }

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
    fun judge2(time: String):Boolean {      //用来判断一个时间，是否需要加入发送通知的队列
        var c = Date().time      //现在的时间
        var t = strToDateLong(time).time    //复习卡时间点（其中的一个）
        if (c - t > 3600000) {         //如果现在距离复习卡中的这个时间点已经超时了1小时了,则，返回false，超时过久，不用弹窗，这个时间已经不遵守艾宾浩斯复习曲线了
            return false

        } else if((c-t)<0){         //如果这个复习点的时间还没到时 ，也返回false
            return false
        }
        return true      //上面两种复习不需要加入通知弹窗队列的情况都排除了，剩下的就是需要加入弹窗队列的情况了
    }




    fun strToDateLong(str: String): Date {          //一个把时间字符串（yyyy-MM-dd HH:mm:ss格式），转化为Date()对象的函数
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
        var str1=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t1)   //把Date对象转换成字符串
        var str2=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t2)
        var str3=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t3)
        var str4=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t4)
        var str5=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t5)
        var str6=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t6)
        var str7=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t7)
        var str8=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t8)
        if (judge2(str1)){                                                //judge2(str)：判断一个时间是否需要加入通知等待队列
            expiredPop++}else{
            judge(str1, content+"（第一个复习点）",timer)              //这个函数，可以把复习卡的提示信息，按照时间加入消息通知队列
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