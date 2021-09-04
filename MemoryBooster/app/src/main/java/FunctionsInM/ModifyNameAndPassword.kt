package FunctionsInM

import Login.Login2
import RetrofitInterfaces.VerificationService
import WordItems.WordActivity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.memorybooster.Me
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyNameAndPassword {      //一个用来修改用户名和密码的
    companion object{
        var nam=""
        var passwor=""
    }

    fun getRegister(jso:String,conte:Context){
        var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jso);
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).updateUserIformation(Login2.token,obj)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        conte,
                        "请求失败！！！原因：\n" + t.message,
                        Toast.LENGTH_LONG
                    ).show();
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var hea=response.headers()
                    var feedback = response.body()?.string()

                        if (feedback != null) {
                            if (feedback.contains("操作成功")){
                                val editor=conte.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                                editor.putString("name", nam)
                                editor.putString("password", passwor)
                                editor.apply()
                                Login2.p=passwor
                                Toast.makeText(
                                    conte,
                                    "更新成功",
                                    Toast.LENGTH_LONG
                                ).show();
                            }else{
                                Toast.makeText(
                                    conte,
                                    "更新失败",
                                    Toast.LENGTH_LONG
                                ).show();
                            }
                        }else{
                            Toast.makeText(
                                conte,
                                "请检查网络",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                }

            })


    }


    fun onlyHaveDigit(str:String):Boolean{
        var flag=true
        var i=0
        while (i<str.length){
            var c=str.get(i)
            if (c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9'){

            }else{
                return false
            }
            i++
        }



        return flag
    }

}