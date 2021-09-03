package WordItems

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import ReviewCards.ShowTextCard
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.example.memorybooster.Judger
import com.example.memorybooster.MainActivity
import com.example.memorybooster.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class DisplarAWordCard : AppCompatActivity() {
    companion object{
        lateinit var mediaPlayer: MediaPlayer
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displar_aword_card)
        supportActionBar?.hide()
        AllActivities.addActivity(this)



        val edi=getSharedPreferences("data",0)
        var noise=edi.getInt("voice",1)
        var needNoise=edi.getBoolean("openNoise",false)
        if (needNoise){
            when(noise){
                1->{   mediaPlayer= MediaPlayer.create(this,R.raw.bnoise)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                2->{   mediaPlayer= MediaPlayer.create(this,R.raw.flotingwater)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                3->{  mediaPlayer= MediaPlayer.create(this,R.raw.fire)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
                4->{   mediaPlayer= MediaPlayer.create(this,R.raw.forestrain)
                    mediaPlayer.isLooping=true
                    mediaPlayer.start()}
            }
        }

        var page=GetWordByInt.communicationWordCard.wordList.size          //告诉你单词卡有几个词
        var previousRecord=GetWordByInt.communicationWordCard.points      //修改前的record(record是八个复习点对应的完成情况，1是完成，0是未完成，2是超时)
        var ttime=GetWordByInt.communicationWordCard.time         //单词卡片的创建时间
        var newRecord= Judger().j(strToDateLong(ttime),previousRecord)     //拿到一个工具类里面，得出新的record,一会把这个发到后端更新
        var theID=GetWordByInt.communicationWordCard.wordCardID           //单词卡ID
        getRegister(theID,newRecord)                                     //把单词卡ID，新的record发送到后端，更新进度（record就是进度）
        var index=0   //计数器
        var thisword=GetWordByInt.communicationWordCard.wordList[index]      //从词卡片的list里面拿出第1个包含单词数据的json
        conveyJsonMsg(thisword)                               //这个函数可以把这个json解析，并显示到UI上
//        showAllJson(thisword)
//        findViewById<TextView>(R.id.dkjhsa).text=GetWordByInt.communicationWordCard.wordList.get(index)
        findViewById<Button>(R.id.np).setOnClickListener {              //点击“下一个单词”，跳转到下一个
            index++
            if (index==page){                //单词卡片里面的最后一个单词结束了，跳转到主页面
                startActivity(Intent(this,WordActivity::class.java))
                Toast.makeText(
                    getApplicationContext(),
                    "已完成所有知识点",
                    Toast.LENGTH_SHORT
                )
                    .show();
            }else{
                conveyJsonMsg(GetWordByInt.communicationWordCard.wordList[index])     //开始拿json里面的信息拿去解析，放进UI
//                showAllJson(GetWordByInt.communicationWordCard.wordList[index])
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

fun showAllJson(jso:String){                    //测试函数，页面显示未解析的json
    findViewById<TextView>(R.id.meanings).text=jso
}


    fun conveyJsonMsg(jso:String){                 //这个函数把传进来的json解析完，显示在UI上

        var j=JSONObject(jso)

        var i=0
        var sy=j.getJSONObject("content").getJSONObject("word")
            .getJSONObject("content")



        var headWord=j.getString("headWord")   //单词
        findViewById<TextView>(R.id.headword).text=headWord




        var means=""   //意思
        if (jso.contains("\"syno\":")&&jso.contains("\"synos\":")){
            var synos=j.getJSONObject("content").getJSONObject("word")
                .getJSONObject("content").getJSONObject("syno")
                .getJSONArray("synos")
            while (i<synos.length()){
                var cur=synos.getJSONObject(i)
                var pos=cur.getString("pos")
                var tran=cur.getString("tran")
                means+="\n"+pos+".  "+tran
                i++
            }
        }else{
            "暂无，等待收录中..."
        }

        findViewById<TextView>(R.id.meanings).text=means



        var pronounce=""  //音标
        if (jso.contains("\"ukphone\":")){
            pronounce+="英式发音     /"+sy.getString("ukphone")+"/"
            }
        if (jso.contains("\"usphone\":")){
            pronounce+="\n美式发音     /"+sy.getString("usphone")+"/"
        }
//        pronounce+="英式发音     /"+sy.getString("ukphone")+"/\n美式发音     /"+sy.getString("usphone")+"/"
        findViewById<TextView>(R.id.yinbiao).text=pronounce




        var sentences=""    //例句
        if (jso.contains("\"sentence\":")&&jso.contains("\"sentences\":")){
            var sent=sy.getJSONObject("sentence").getJSONArray("sentences")
            i=0
            while (i<sent.length()){
                var singlesentence=sent.getJSONObject(i)
                var en=singlesentence.getString("sContent")
                var ch=singlesentence.getString("sCn")
                sentences+="\n"+en+"\n"+ch
                i++
            }
        }else{
            sentences="暂无例句，等待收录中..."
        }

        findViewById<TextView>(R.id.instance).text=sentences





        var phr=""          //词组
        if (jso.contains("\"phrase\":")&&jso.contains("\"phrases\":")){
            var phrases=sy.getJSONObject("phrase").getJSONArray("phrases")
            i=0
            while (i<phrases.length()){
                var singlephr=phrases.getJSONObject(i)
                var ph=singlephr.getString("pContent")
                var pcn=singlephr.getString("pCn")
                phr+="\n"+ph+"\n"+pcn
                i++
            }
        }else{
            phr="暂无词组，等待收录中..."
        }

        findViewById<TextView>(R.id.phrase).text=phr






        var tgci=""         //同根词

        if (jso.contains("\"relWord\":")&&jso.contains("\"rels\":")){
            var rels=sy.getJSONObject("relWord").getJSONArray("rels")
            i=0
            while (i<rels.length()){
                var poss=rels.getJSONObject(i).getString("pos")+"."
                var tgccontent=rels.getJSONObject(i).getJSONArray("words").getJSONObject(0)
                var en=tgccontent.getString("hwd")
                var cj=tgccontent.getString("tran")
                tgci+="\n"+en+"  "+poss+"  "+cj
                i++
            }
        }else{
            tgci="暂无同根词，等待收录中..."
        }

        findViewById<TextView>(R.id.sameprefix).text=tgci





        var enen=""         //英英释义
        if (jso.contains("\"trans\":")&&jso.contains("\"tranOther\":")){
            var trans=sy.getJSONArray("trans")
            i=0
            while (i<trans.length()){

                var tr=trans.getJSONObject(i)
                if (tr.toString().contains("\"tranOther\":")){
                    enen+=tr.getString("tranOther")+"."
                }

                i++
            }
        }else{
            enen="暂无，等待收录中..."
        }
        findViewById<TextView>(R.id.enmean).text=enen
        thread {
            Thread.sleep(100)
            findViewById<ScrollView>(R.id.kjhajsdh).fullScroll(ScrollView.FOCUS_UP)   //回到顶部
        }.run()


    }
    fun strToDateLong(str: String): Date {
        var s: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var pos: ParsePosition = ParsePosition(0)
        var date: Date = s.parse(str, pos)

        return date
    }
    fun getRegister(id:String,record:String){
        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java). updateTextCardMsg(Login2.token,id,record)
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
                    var hea=response.headers().toString()
                    var feedback = response.body()?.string()


                    if (feedback != null) {
                        if (feedback.contains("操作成功")){

                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "数据更新失败"+hea+feedback,
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "数据更新失败"+hea+feedback,
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                }

            })


    }
}