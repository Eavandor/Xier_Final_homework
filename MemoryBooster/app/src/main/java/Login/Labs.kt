package Login

import FunctionsInM.MyService
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.memorybooster.R
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


class Labs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labs)
var i=0
        findViewById<Button>(R.id.jdskf).setOnClickListener {
            if (i==0){
                startService(Intent(this,MyService::class.java))
                i++
            }else{
                stopService(Intent(this,MyService::class.java))
            }



        }
    }

}