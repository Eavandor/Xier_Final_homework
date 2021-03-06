package FunctionsInM

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.memorybooster.Me
import com.example.memorybooster.R

class ChoosingNoise : AppCompatActivity() {            //用来选择白噪音的Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosing_noise)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
findViewById<Button>(R.id.voice1).setOnClickListener {
    al(this,1)                                        //选中第一款白噪音，弹窗向用户确认
}
        findViewById<Button>(R.id.voice2).setOnClickListener {
            al(this,2)
        }
        findViewById<Button>(R.id.voice3).setOnClickListener {
            al(this,3)
        }
        findViewById<Button>(R.id.voice4).setOnClickListener {
            al(this,4)
        }
    }
    fun al(conte: Context, index: Int){
        AlertDialog.Builder(conte).apply {
            setTitle("是否确认选中当前音效？")
            setMessage("您当前选中的是第"+index+"张")
            setCancelable(false)
            setPositiveButton("好哒！") { dialog, which ->
                val editor=conte.getSharedPreferences("data",0).edit()
                editor.putInt("voice",index)
                editor.apply()
                conte.startActivity(Intent(conte, Me::class.java))
            }
            setNegativeButton("再看看") { dialog,
                                       which ->
            }
            show()

        }
    }
}