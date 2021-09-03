package com.example.memorybooster

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class Judger {
    fun j(t0: Date, nowRecords:String):String{     //这个类收入旧的record,判断到第几个复习点，是否超时，然后返回新的record
        var t9=Date(Date().time)   //现在
        var t1=Date(t0.time+300000)   //5 min。第一个复习点
        var t2=Date(t0.time+1800000)   //30 min。第一个复习点
        var t3=Date(t0.time+43200000)   //12h。第一个复习点
        var t4=Date(t0.time+86400000)   //1 day。第一个复习点
        var t5=Date(t0.time+172800000)   //2 day。第一个复习点
        var t6=Date(t0.time+345600000)   //4 day。第一个复习点
        var t7=Date(t0.time+604800000)   //7 day。第一个复习点
        var t8=Date(t0.time+1296000000)   //15 day。第一个复习点
        var nowAt=0
        if (t1.time<t9.time)nowAt++
        if (t2.time<t9.time)nowAt++
        if (t3.time<t9.time)nowAt++
        if (t4.time<t9.time)nowAt++
        if (t5.time<t9.time)nowAt++
        if (t6.time<t9.time)nowAt++
        if (t7.time<t9.time)nowAt++
        if (t8.time<t9.time)nowAt++        //判断在第几个复习点的后面


        if (nowAt==0){
            return "00000000"             //8个复习点里面，连第一个点都没到，直接返回空的record
        }
        var currentBit=nowRecords.get(nowAt-1)         //
        if (currentBit=='1'||currentBit=='2'){
            return nowRecords
        }
        var target="0"

        var diff:Long=0
        when(nowAt){
            1->{     diff=t9.time-t1.time      }
            2->{     diff=t9.time-t2.time      }
            3->{     diff=t9.time-t3.time      }
            4->{     diff=t9.time-t4.time      }
            5->{     diff=t9.time-t5.time      }
            6->{     diff=t9.time-t6.time      }
            7->{     diff=t9.time-t7.time      }
            8->{     diff=t9.time-t8.time      }
        }

        if (diff>3600000){
            target="2"
        }else{
            target="1"
        }
        var res=""

        if (nowAt==8){
            res+=nowRecords.substring(0,nowAt-1)
            res+=target
        }else{
            res+=nowRecords.substring(0,nowAt-1)
            res+=target
            res+=nowRecords.substring(nowAt,8)
        }

        return res
    }

}