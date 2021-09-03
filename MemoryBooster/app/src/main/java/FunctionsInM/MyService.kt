package FunctionsInM

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.memorybooster.R

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var mediaPlayer= MediaPlayer.create(this, R.raw.tishiying)
        mediaPlayer.isLooping=true
        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)

    }
    override fun onDestroy() {
        super.onDestroy()
    }
}