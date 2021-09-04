package FunctionsInM

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import com.example.memorybooster.Me
import com.example.memorybooster.R

class ChooseImg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_img)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
        findViewById<ImageView>(R.id.img1).setOnClickListener {

            al(this,1)                   //选择图片，选中第一张，就弹窗向用户确认
        }
        findViewById<ImageView>(R.id.img2).setOnClickListener {

            al(this,2)               //以此类推
        }
        findViewById<ImageView>(R.id.img3).setOnClickListener {

            al(this,3)
        }
    }

fun al(conte:Context,index: Int){
    AlertDialog.Builder(conte).apply {
        setTitle("是否确认选中当前图片？")
        setMessage("您当前选中的是第"+index+"张")
        setCancelable(false)
        setPositiveButton("好哒！") { dialog, which ->
val editor=conte.getSharedPreferences("data",0).edit()            //用户确认，就用SharedPreference来存储当前图片是第几张
            editor.putInt("pic",index)
            editor.apply()
            conte.startActivity(Intent(conte,Me::class.java))
        }
        setNegativeButton("再看看") { dialog,
                                  which ->
        }
        show()

    }
}
}