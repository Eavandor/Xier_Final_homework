package Login

import FunctionsInM.AllActivities
import FunctionsInM.ModifyNameAndPassword
import RetrofitInterfaces.VerificationService
import ReviewItems.TryWordBack
import WordItems.WordActivity
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import com.example.memorybooster.MainActivity
import com.example.memorybooster.Notificationnnn
import com.example.memorybooster.R
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class Login2 : AppCompatActivity() {

    companion object{

       lateinit var token:String
        lateinit var usn:String
        lateinit var p:String
        var missCount=0
        val client: OkHttpClient = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
            .build()   //设置各种超时都是10秒钟

        var publicretrofit =
            Retrofit.Builder().baseUrl("http://planeter.icu:9000/").client(
                client
            )
                .addConverterFactory(GsonConverterFactory.create()).build()
      lateinit  var oiop:Context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
oiop=this
        supportActionBar?.hide()

        setContentView(R.layout.activity_login2)
        Notificationnnn.manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Notificationnnn.resources=resources







//登陆
        findViewById<Button>(R.id.cra22b).setOnClickListener {
            var email=findViewById<EditText>(R.id.ed2).text.toString()
            var pwd=findViewById<EditText>(R.id.ed5).text.toString()
//            var cbox = findViewById<CheckBox>(R.id.cb132)
//            var keepLogin = false
//            if (cbox.isChecked) {
//                keepLogin = true
//            } else {
//                keepLogin = false
//            }
            if (email==""||pwd==""){
                Toast.makeText(
                    getApplicationContext(),
                    "别急，每一行都要填哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else if(pwd.length<6||pwd.length>12){
                Toast.makeText(
                    getApplicationContext(),
                    "密码长度需在6-12之间",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else if (ModifyNameAndPassword().onlyHaveDigit(pwd)==false){
                Toast.makeText(
                    getApplicationContext(),
                    "密码只能是数字哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else{

                var jso: JSONObject = JSONObject()
                jso.put("username",email)
                jso.put("password",pwd.toInt())
                getRegister(jso.toString())
                Toast.makeText(
                    getApplicationContext(),
                    "登陆中，请稍后......",
                    Toast.LENGTH_SHORT
                ).show();
                p=pwd
                usn=email
            }
        }
        //前往注册
        findViewById<TextView>(R.id.gotoca).setOnClickListener {
            var intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }
    }


    fun getRegister(jso:String){
        var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jso);
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendLoginMsg(obj)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        getApplicationContext(),
                        "请求失败！！！原因：\n" + t.message,
                        Toast.LENGTH_LONG
                    ).show();
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var hea=response.headers()
                    token=hea.get("Set-Token").toString()

                    var feedback = response.body()?.string()
                    if (token==null){
                        Toast.makeText(
                            getApplicationContext(),
                            "未获取到token:"+hea.toString()+feedback,
                            Toast.LENGTH_LONG
                        ).show();
                    }else{
//                        Toast.makeText(
//                            getApplicationContext(),
//                            "token:"+ token+"\n"+hea.toString(),
//                            Toast.LENGTH_LONG
//                        ).show();
                        if (feedback != null) {
                            if (feedback.contains("操作成功")){
                                val editor=getSharedPreferences("data",Context.MODE_PRIVATE).edit()
                                editor.putString("email",usn)
                                editor.putString("password", p)
                                editor.apply()
                                var intent = Intent(getApplicationContext(), WordActivity::class.java)
                                startActivity(intent)



//                            var intent = Intent(getApplicationContext(), TryWordBack::class.java)
//                            startActivity(intent)
                            }else{
                                Toast.makeText(
                                    getApplicationContext(),
                                    "登陆失败",
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


                }

            })


    }



}