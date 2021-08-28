package FunctionsInM

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.memorybooster.Me
import com.example.memorybooster.R

class ChooseImg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_img)
        findViewById<ImageView>(R.id.img1).setOnClickListener {

            al(this,1)
        }
        findViewById<ImageView>(R.id.img2).setOnClickListener {

            al(this,2)
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
val editor=conte.getSharedPreferences("data",0).edit()
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