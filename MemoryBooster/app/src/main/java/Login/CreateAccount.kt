package Login

import FunctionsInM.ModifyNameAndPassword
import RetrofitInterfaces.VerificationService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorybooster.R
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class CreateAccount : AppCompatActivity() {
    var emailAddress=""
    var pwdfinal=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        supportActionBar?.hide()
        //点击获取验证码：
        findViewById<Button>(R.id.vinlogin4).setOnClickListener {
            getVerification(findViewById<EditText>(R.id.ed235).text.toString())
            Toast.makeText(
                getApplicationContext(),
                "验证码发送中，请稍后，请勿频繁发送",
                Toast.LENGTH_LONG
            ).show();
        }
        //点击注册：
        findViewById<Button>(R.id.cra221b).setOnClickListener {
var email=findViewById<EditText>(R.id.ed235).text.toString()
            var pwd1=findViewById<EditText>(R.id.ed236).text.toString()
            var pwd2=findViewById<EditText>(R.id.ed237).text.toString()
            var verification=findViewById<EditText>(R.id.ed556).text.toString()
            var cbox = findViewById<CheckBox>(R.id.cb111132)
            var keepLogin = false
            if (cbox.isChecked) {
                keepLogin = true
            } else {
                keepLogin = false
            }
            if (email==""||pwd1==""||pwd2==""||verification==""){
                Toast.makeText(
                    getApplicationContext(),
                    "别急，每一行都要填哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else if(pwd1!=pwd2){
                Toast.makeText(
                    getApplicationContext(),
                    "两次输入的密码不一样，粗心了哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else if (pwd1.length<6||pwd1.length>12){
                Toast.makeText(
                    getApplicationContext(),
                    "密码长度只能在6-12位之间哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else if (ModifyNameAndPassword().onlyHaveDigit(pwd1)==false){
                Toast.makeText(
                    getApplicationContext(),
                    "密码只能是数字哦",
                    Toast.LENGTH_LONG
                )
                    .show();
            }else{//开始验证验证码，若成功就跳转
                Login2.p=pwd1
                Login2.usn=email



                var jso:JSONObject= JSONObject()
                jso.put("username",email)
                jso.put("password",pwd1.toInt())
                jso.put("verifyCode",verification.toInt())
                getRegister(jso.toString())
//                var jsob=jso.toString()
//                Toast.makeText(
//                    getApplicationContext(),
//                    ""+jsob,
//                    Toast.LENGTH_SHORT
//                ).show();
//                var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsob);
//                var body=obj as Body
            }


        }
        //点击前往登陆
        findViewById<TextView>(R.id.gotoca11).setOnClickListener {
            var intent = Intent(this, Login2::class.java)
            startActivity(intent)
        }
    }


fun getRegister(jso:String){
    var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jso);
    val retr = Login2.publicretrofit
    retr.create(VerificationService::class.java).sendRegisterMsg(obj)
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
                    Login2.token=hea.get("Set-Token").toString()
                    var feedback = response.body()?.string()

                    if (feedback != null) {
                        if (feedback.contains("操作成功")){
                            if (feedback.contains("注册成功")){
                                Toast.makeText(
                                    getApplicationContext(),
                                    "注册成功",
                                    Toast.LENGTH_SHORT
                                ).show();
                                var jso: JSONObject = JSONObject()
                                jso.put("username",Login2.usn)
                                jso.put("password",Login2.p.toInt())
                                CreateSuccessAndLogin().getRegister(jso.toString(),applicationContext)
                            }else{
                                Toast.makeText(
                                    getApplicationContext(),
                                    "注册失败，改邮箱地址可能已经被注册，或邮箱格式错误",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }


                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "请检查网络",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "请检查网络",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                    Toast.makeText(
                        getApplicationContext(),
                        ""+feedback,
                        Toast.LENGTH_LONG
                    ).show();

                }

            })


    }


    fun getVerification(email:String){
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendVerificationMsg("" + email)
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
                    var token=hea.get("Set-Token")
                    var feedback = response.body()?.string()

                    if (feedback != null) {
                        if (feedback.contains("验证码发送成功")){
                            Toast.makeText(
                                getApplicationContext(),
                                "验证码发送成功",
                                Toast.LENGTH_SHORT
                            ).show();

                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "请检查网络",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "请检查网络",
                            Toast.LENGTH_LONG
                        ).show();
                    }


                }

            })
    }
}