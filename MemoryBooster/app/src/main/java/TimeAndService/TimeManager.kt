package TimeAndService

import WordItems.WordCard2
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.memorybooster.Tyr4

class TimeManager {
    companion object{
        var originalWTime = ArrayList<TimeUnitt>()
        var originalTTime = ArrayList<TimeUnitt>()
       lateinit var added:TimeUnitt
       var verifyDigit=0
    }
fun initializeW(cont:Context){
     val it= Intent(cont, Tyr4::class.java)
        cont.startService(it)
}
    fun initializeT(cont:Context){
        val it= Intent(cont, Tyr4::class.java)
        cont.startService(it)
    }
}