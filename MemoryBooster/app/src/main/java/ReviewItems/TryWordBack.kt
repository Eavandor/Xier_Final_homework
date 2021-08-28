package ReviewItems

import Login.Login2
import RetrofitInterfaces.VerificationService
import ReviewCards.Card
import WordItems.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorybooster.MainActivity
import com.example.memorybooster.R
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class TryWordBack : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_try_word_back)
        getRegister()
    }


    fun getRegister(){

        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendTryWordMsg(Login2.token,"-1")
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

                    var feedback = response.body()?.string()

                    if (feedback != null) {
                        if (feedback.contains("操作成功")){
//                           findViewById<TextView>(R.id.kjdfhkjf).text=feedback
                            var jsoarray= JSONObject(feedback).getJSONArray("data")
                            var i=0
                            var numberOfWordCard=0
                            while (i<jsoarray.length()) {
                                var obj = jsoarray.get(i) as JSONObject
                                var type = obj.getString("type")
                                if (type == "0") {
                                    numberOfWordCard++
                                }
                                i++
                            }
                            i=0




                            while (i<jsoarray.length()){
                                var obj=jsoarray.get(i) as JSONObject
                                var type=obj.getString("type")
                                if (type=="1"){
                                    i++
                                    continue
                                }
                                i++
                                var id=obj.getString("id")
//                                var name=obj.getString("name")  //标题
                                var time=obj.getString("created")  //卡片创建时间
                                var record=obj.getString("record")  //记忆点完成情况
                                var conten=obj.getJSONObject("content")  //内容
                                var book=""
                                var arr="["
                                if (conten.toString().contains("\"CET4_3\"")){
                                    book="CET4_3"
                                    var jso=conten.getJSONArray(book)
                                    var z=0
                                    while (z<jso.length()){
                                        arr+=jso.getInt(z)
                                        if (z==jso.length()-1)break
                                        arr+=","


                                        z++
                                    }

                                }else if (conten.toString().contains("\"CET6_3\"")){
                                    book="CET6_3"
                                    var jso=conten.getJSONArray(book)
                                    var z=0
                                    while (z<jso.length()){
                                        arr+=jso.getInt(z)
                                        if (z==jso.length()-1)break
                                        arr+=","


                                        z++
                                    }
                                }
                                arr+="]"
                                GetWordByInt.communicationWordCardListt.clear()

                                GetWordByInt().getWordListByDigitList( WordActivity.rr5,arr,id,time,record,1,book)
//                                findViewById<TextView>(R.id.kjdfhkjf).text= findViewById<TextView>(R.id.kjdfhkjf).text.toString()+obj.toString()


                            }
                            thread {
                                while (GetWordByInt.listcount<numberOfWordCard){
                                    Thread.sleep(300)
                                }
                                MainActivity.flag=true
                                var fruitList = ArrayList<WordCard2>()
                                var recyclerView=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rclrvneedtoreview)
//        ini(fruitList) // 初始化数据
                                val layoutManager = LinearLayoutManager(WordActivity.rr5)
                                recyclerView.layoutManager = layoutManager
                                val adapter = CardAdapter(fruitList)
                                recyclerView.adapter = adapter

                            }

                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "登陆失败",
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