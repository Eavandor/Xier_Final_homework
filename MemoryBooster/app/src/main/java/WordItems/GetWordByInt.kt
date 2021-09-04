package WordItems

import Login.Login2
import RetrofitInterfaces.VerificationService
import ReviewCards.Card
import ReviewCards.ShowTextCard
import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.memorybooster.Me
import com.example.memorybooster.Tyr4
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetWordByInt {
companion object{
    lateinit var rrr:Context
    lateinit var communicationWordCard:WordCard
    var communicationWordCardListt = ArrayList<WordCard>()
    var listcount=0
}




    fun getDigitWhenCreating(con:Context){
rrr=con
            val retr = Login2.publicretrofit
            retr.create(VerificationService::class.java). getDigitMsg(Login2.token,WordActivity.bookplanID,WordActivity.wordCardLength)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                           con,
                            "请求失败！！！原因：\n" + t.message,
                            Toast.LENGTH_LONG
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

                                var jso=JSONObject(feedback).getJSONObject("data")
                                    .getJSONObject("content").getJSONArray(WordActivity.bookId)
var j=JSONObject(feedback).getJSONObject("data")
                                var id1=j.getString("id")
                                var time1=j.getString("created")
                                var record1=j.getString("record")


                                var i=0
                                var st="["
                                while (i<jso.length()){
                                    st+=jso.getInt(i)
                                    if (i==jso.length()-1)break
                                    st+=","


                                    i++
                                }
                                st+="]"

                                if (WordActivity.bookId=="CET4_3"){
                                    TimeManager.verifyDigit=1
                                    TimeManager.added= TimeUnitt(time1,"您有一个四级单词卡到复习时间了哦！请及时复习",record1)
                                }else{
                                    TimeManager.verifyDigit=1
                                    TimeManager.added= TimeUnitt(time1,"您有一个六级单词卡到复习时间了哦！请及时复习",record1)
                                }
                                val it= Intent(con, Tyr4::class.java)
                                con.startService(it)
                                getWordListByDigitList(rrr,st,id1,time1,record1,0,WordActivity.bookId)


                            }else{
                                Toast.makeText(
                                    con,
                                    "数据更新失败",
                                    Toast.LENGTH_LONG
                                ).show();
                            }
                        }else{
                            Toast.makeText(
                                con,
                                "数据更新失败",
                                Toast.LENGTH_LONG
                            ).show();
                        }

                    }

                })


        }


    fun getWordListByDigitList(con:Context,arr:String,wordCardID: String,time:String,points:String,who:Int,bkid:String){

        var st="{\n" +
                "    \"wordRanks\":"+arr+",\n" +
                "    \"bookId\":\""+bkid+"\"\n" +
                "}"
        var obj= RequestBody.create(MediaType.parse("application/json; charset=utf-8"), st);
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java). getWByDMsg(Login2.token,obj)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        con,
                        "请求失败！！！原因：\n" + t.message,
                        Toast.LENGTH_LONG
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

                            var jso=JSONObject(feedback).getJSONArray("data")

                            var fr = ArrayList<String>()
                            var i=0
                            while (i<jso.length()){
                                fr.add(jso.getString(i))
                                i++
                            }


                            if (who==0){
                                communicationWordCard=WordCard(wordCardID,fr,time,points)
                                var intent= Intent(con, DisplarAWordCard::class.java)
                                con.startActivity(intent)
                            }else if (who==1){
                                communicationWordCardListt.add(WordCard(wordCardID,fr,time,points))
                                listcount++

                            }
                        }else{
                            Toast.makeText(
                                con,
                                "数据更新失败"+hea+feedback+"\n使用的token:"+Login2.token,
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            con,
                            "数据更新失败"+hea+feedback+"\n使用的token:"+Login2.token,
                            Toast.LENGTH_LONG
                        ).show();
                    }

                }

            })


    }


}