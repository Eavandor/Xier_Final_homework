package ReviewCards

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import WordItems.WordActivity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.memorybooster.MainActivity
import com.example.memorybooster.R
import com.example.memorybooster.Tyr4
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CreateReviewCard : AppCompatActivity() {
    companion object{
        var name0=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review_card)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
        findViewById<Button>(R.id.submitTextContent).setOnClickListener {
            Toast.makeText(
                getApplicationContext(),
                "上传中，请稍后......",
                Toast.LENGTH_SHORT
            ).show();

            var title=findViewById<EditText>(R.id.cardName).text.toString()
            var text=findViewById<EditText>(R.id.cardContent).text.toString()
            name0=title
            var jso = JSONObject()
            jso.put("name",title)
            jso.put("content",text)
            getRegister(jso.toString())         //把名字（实际上是email）放进josn，用Body的形式传到后端验证
        }


    }



    fun getRegister(jso:String){
        var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jso);
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendCardMsg(Login2.token,obj)
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
                    var feedback = response.body()?.string()
                    if (feedback != null) {
                        if (feedback.contains("操作成功")){
                            var t=Date(Date().time)

                            var str= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t)



                            var intent = Intent(getApplicationContext(), MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                getApplicationContext(),
                                "上传成功" ,
                                Toast.LENGTH_SHORT
                            ).show();


                                TimeManager.verifyDigit=1
                                TimeManager.added= TimeUnitt(str,name0,"00000000")  //刚创建的初始record是00000000
                                   //record的每个单数字表示一个复习点的完成状况，0表示未完成，1表示已经完成，2表示这个复习点超时了
                            val it= Intent( getApplicationContext(), Tyr4::class.java)
                            getApplicationContext().startService(it)      //验证成功，开启发送通知的前台Service
                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "上传失败",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "请检查网络",
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                }

            })


    }



}