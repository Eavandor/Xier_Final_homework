package com.example.memorybooster

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class Try3 : AppCompatActivity() {

    val handler=object: Handler(){
        override fun handleMessage(msg: Message) {
//            Toast.makeText(
//                getApplicationContext(),
//                "handler发的信息",
//                Toast.LENGTH_LONG
//            )
//                .show();
var m=msg.data.getString("content")
            if (m != null) {
                Notificationnnn().send(getApplicationContext(),"1","233","复习去哦",m,"")
            }



        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try3)
 var i=1
        Notificationnnn.manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Notificationnnn.resources=resources
        var timer=Timer(true)

        var l=ArrayList<Reminder>()
        l.add(Reminder("2021-08-23 19:17:30","操作系统"))
        l.add(Reminder("2021-08-23 19:17:50","计算机网络"))

        for (a in l){
            var tm:TimerTask=object :TimerTask(){
                override fun run() {

                    val msg=Message()
                    msg.what=1
                    var bun=Bundle()
                    bun.putString("content",a.content)
                    msg.data=bun
                    handler.sendMessage(msg)

                }

            }
//            timer.schedule(tm,Date(strToDateLong("2021-08-23 18:35:30").time))
            timer.schedule(tm,Date(strToDateLong(a.time).time))
        }



        //part2
//        val it=Intent(this,Tyr4::class.java)
//        startService(it)

    }
    fun strToDateLong(str:String):Date{
        var s:SimpleDateFormat= SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var pos:ParsePosition= ParsePosition(0)
        var date:Date=s.parse(str,pos)

        return date
    }
}