package Login

import RetrofitInterfaces.VerificationService
import WordItems.WordActivity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSuccessAndLogin {
    fun getRegister(jso:String,context: Context){
        var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jso);
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendLoginMsg(obj)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "请求失败！！！原因：\n" + t.message,
                        Toast.LENGTH_LONG
                    ).show();
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var hea=response.headers()
                    Login2.token =hea.get("Set-Token").toString()

                    var feedback = response.body()?.string()
                    if (Login2.token ==null){
                        Toast.makeText(
                            context,
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
                                val editor=context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                                editor.putString("email", Login2.usn)
                                editor.putString("password", Login2.p)
                                editor.apply()
                                var intent = Intent(context, WordActivity::class.java)
                                context.startActivity(intent)



//                            var intent = Intent(getApplicationContext(), TryWordBack::class.java)
//                            startActivity(intent)
                            }else{
                                Toast.makeText(
                                    context,
                                    "登陆失败",
                                    Toast.LENGTH_LONG
                                ).show();
                            }
                        }else{
                            Toast.makeText(
                                context,
                                "请检查网络",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }


                }

            })


    }
}