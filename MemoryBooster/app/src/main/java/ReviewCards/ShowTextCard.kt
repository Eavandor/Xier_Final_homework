package ReviewCards

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.memorybooster.Judger
import com.example.memorybooster.MainActivity
import com.example.memorybooster.R
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class ShowTextCard : AppCompatActivity() {        //这个类用来显示一个复习卡片的内容
    companion object{
        lateinit var mediaPlayer: MediaPlayer
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_text_card)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
        var intent9=intent
val name=intent9.getStringExtra("name")
        val time=intent9.getStringExtra("time")
        val id=intent9.getStringExtra("id")
        val content=intent9.getStringExtra("content")
        var points=intent9.getStringExtra("points")


        val edi=getSharedPreferences("data",0)
        var noise=edi.getInt("voice",1)
        var needNoise=edi.getBoolean("openNoise",false)
        if (needNoise){                        //从SharedPreference里面获取，是否开启白噪音，如果要开，就按照选定的白噪音索引号码来开启对应的白噪音
            when(noise){
                1->{  mediaPlayer= MediaPlayer.create(this,R.raw.bnoise)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                2->{   mediaPlayer= MediaPlayer.create(this,R.raw.flotingwater)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                3->{   mediaPlayer= MediaPlayer.create(this,R.raw.fire)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                4->{   mediaPlayer= MediaPlayer.create(this,R.raw.forestrain)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
            }
        }

val editor=getSharedPreferences("data",0)
        var imgindex=editor.getInt("pic",1)         //得到背景图片的索引
        val resources: Resources = applicationContext.resources
        var btnDrawable: Drawable = resources.getDrawable(R.drawable.paper)

        when(imgindex){
            1->{}
            2->{btnDrawable=resources.getDrawable(R.drawable.artifact)}
            3->{btnDrawable=resources.getDrawable(R.drawable.protecteyes)}
        }
        findViewById<LinearLayout>(R.id.bgtc).background = btnDrawable



findViewById<TextView>(R.id.titl35e1).text=name
findViewById<TextView>(R.id.con345tent1).text=content

        if (points!=null){
            if (id != null) {
                if (time!=null){     //反正这三个不可能是null
                    var newRecord=Judger().j(strToDateLong(time),points)
                    getRegister(id,newRecord)
                }

            }
        }




    }

    @Override
    override fun onPause() {
        super.onPause()
        val edi=getSharedPreferences("data",0)
        var needNoise=edi.getBoolean("openNoise",false)
        if (needNoise==true){
            mediaPlayer.stop()
            mediaPlayer.release()
        }

    }
    fun getRegister(id:String,record:String){
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java). updateTextCardMsg(Login2.token,id,record)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        getApplicationContext(),
                        "请求失败！！！原因：\n" + t.message,
                        Toast.LENGTH_SHORT
                    ).show();
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var hea=response.headers().toString()
                    var feedback = response.body()?.string()


                    if (feedback != null) {
                        if (feedback.contains("操作成功")){
                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "数据更新失败",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "请检查网络"+hea+feedback,
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                }

            })


    }

    fun strToDateLong(str: String): Date {
        var s: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var pos: ParsePosition = ParsePosition(0)
        var date: Date = s.parse(str, pos)

        return date
    }
}