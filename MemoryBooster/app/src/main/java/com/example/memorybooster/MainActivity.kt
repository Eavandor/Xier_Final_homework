package com.example.memorybooster

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import ReviewCards.Card
import ReviewCards.CreateReviewCard
import ReviewCards.TextCardAdapter
import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import WordItems.CardAdapter
import WordItems.WordCard
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.DayOfWeek
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var rr4:Context
        var flag=false
        var timecleared0=0
        var timecleared1=0
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }                                             //沉浸式UI，去掉顶部状态栏
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()                     //去掉顶栏
        rr4=this
        AllActivities.addActivity(this)
        //底部导航栏部分：
        var  mBottomNavigationView4: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav2)
        mBottomNavigationView4.selectedItemId=R.id.tools
        initBottomNavigation()                                //实例化底部导航栏


        //顶部小日历实现部分：
       var todayOfWeek= LocalDate.now().dayOfWeek.value    //得到现在的时间，用毫秒表示
        var minu1=todayOfWeek-1
        var mi=minu1.toLong()                           //今天的时间减去一天
        var day1_1=LocalDate.now().minusDays(mi)       //这周一的时间（mi是今天的时间减去一天的毫秒），比如说，求周一（Long类型的），
        var day1=day1_1.dayOfMonth                      //周五日期（毫秒）减去周四日期（毫秒）=周一日期（毫秒），周三日期（毫秒）减去周二日期（毫秒）=周一
        var day2=day1_1.plusDays(1).dayOfMonth   //day1是周一的日期，周二周三以此类推，依次加1天
        var day3=day1_1.plusDays(2).dayOfMonth     //得到周一到周天每一天的日期。
        var day4=day1_1.plusDays(3).dayOfMonth
        var day5=day1_1.plusDays(4).dayOfMonth
        var day6=day1_1.plusDays(5).dayOfMonth
        var day7=day1_1.plusDays(6).dayOfMonth

        findViewById<TextView>(R.id.mon2).text=""+day1        //把得到的周一到周天每一天的日期，都放进对应的控件中显示
        findViewById<TextView>(R.id.tue2).text=""+day2        //（按照本周）日历制作完成
        findViewById<TextView>(R.id.wen2).text=""+day3
        findViewById<TextView>(R.id.thu2).text=""+day4
        findViewById<TextView>(R.id.fri2).text=""+day5
        findViewById<TextView>(R.id.sat2).text=""+day6
        findViewById<TextView>(R.id.sun2).text=""+day7

        when(todayOfWeek){                                                  //得出今天是星期几
            1->{ findViewById<TextView>(R.id.mon2).setTextColor(Color.BLACK)     //如果是星期一，把星期一所在的TextView控件字体变成黑色
                findViewById<TextView>(R.id.mon2).getPaint().setFakeBoldText(true)  //并且把当天字体加粗
                findViewById<TextView>(R.id.mon1).setTextColor(Color.BLACK)        //要改两个控件，一个显示月份中的日期数字，另一个显示星期几
                findViewById<TextView>(R.id.mon1).getPaint().setFakeBoldText(true)}

            2->{ findViewById<TextView>(R.id.tue2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tue1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tue2).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.tue1).getPaint().setFakeBoldText(true)}
            3->{ findViewById<TextView>(R.id.wen2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.wen1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.wen1).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.wen2).getPaint().setFakeBoldText(true)}
            4->{ findViewById<TextView>(R.id.thu2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.thu1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.thu1).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.thu2).getPaint().setFakeBoldText(true)}
            5->{ findViewById<TextView>(R.id.fri2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.fri1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.fri1).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.fri2).getPaint().setFakeBoldText(true)}
            6->{ findViewById<TextView>(R.id.sat2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.sat1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.sat1).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.sat2).getPaint().setFakeBoldText(true)}
            7->{ findViewById<TextView>(R.id.sun2).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.sun1).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.sun1).getPaint().setFakeBoldText(true)
                findViewById<TextView>(R.id.sun2).getPaint().setFakeBoldText(true)}

        }
findViewById<ImageView>(R.id.createcard).setOnClickListener {
    startActivity(Intent(this,CreateReviewCard::class.java))  //用户点击创建复习卡片，则跳转至创建界面
}

        getRegister("1")         //1表示正在进行的卡片（还没走完8个复习点的）
        findViewById<TextView>(R.id.needRCard).setTextColor(Color.BLACK)
        findViewById<TextView>(R.id.needRCard).getPaint().setFakeBoldText(true)
var status="1"   //1是表示正在进行中的，0是表示完成的卡片，-1表示所有卡片，后端规定

        findViewById<TextView>(R.id.needRCard).setOnClickListener {
            //显示进行中的(state=1的)
            findViewById<TextView>(R.id.needRCard).setTextColor(Color.BLACK)
            findViewById<TextView>(R.id.needRCard).getPaint().setFakeBoldText(true)
            findViewById<TextView>(R.id.allCard).setTextColor(Color.GRAY)
            findViewById<TextView>(R.id.allCard).getPaint().setFakeBoldText(false)
            status="1"
            getRegister("1")
        }
        findViewById<TextView>(R.id.allCard).setOnClickListener {
            //显示完成的(state=0的)
            findViewById<TextView>(R.id.allCard).setTextColor(Color.BLACK)
            findViewById<TextView>(R.id.allCard).getPaint().setFakeBoldText(true)
            findViewById<TextView>(R.id.needRCard).setTextColor(Color.GRAY)
            findViewById<TextView>(R.id.needRCard).getPaint().setFakeBoldText(false)
            status="0"
            getRegister("0")
        }




    }
    fun initBottomNavigation() {
        var  mBottomNavigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav2)
        // 添加监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.myhome -> {var intent= Intent(rr4,WordItems.WordActivity::class.java)
                        startActivity(intent)}         //底部导航栏点击第一个，前往背单词界面
                    R.id.tools ->{}
                    R.id.me ->{var intent= Intent(rr4,Me::class.java)
                        startActivity(intent)}

                    else -> {
                    }
                }
                // 这里注意返回true,否则点击失效
                return true
            }
        })
    }



    fun getRegister(state:String){

        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).getCardMsg(Login2.token,state)
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
                    var feedback = response.body()?.string()
                    if (feedback != null) {
                        if (feedback.contains("操作成功")){


                            if (timecleared0==0){         //修改这个标记，表示复习卡已经加载过一次了其他类的一些行为要用这个标记
                                TimeManager.originalTTime.clear()
                                timecleared0=1
                            }


                            //recyclerView
                            var fruitList2 = ArrayList<Card>()
                            var recyclerView2=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recmain)
                            //        ini(fruitList2) // 初始化数据
                            val layoutManager2 = LinearLayoutManager(getApplicationContext())
                            recyclerView2.layoutManager = layoutManager2

                            //往list里面塞东西：
                            var jsoarray=JSONObject(feedback).getJSONArray("data")
                            var i=0

                           while (i<jsoarray.length()){
                               var obj=jsoarray.get(i) as JSONObject
                               var type=obj.getString("type")
                               if (type=="0"){
                                   i++
                                   continue          //type=0表示这个复习卡片是单词卡片，跳过，type=1，表示复习卡片是文字卡片，加进list来，这一页是专门显示文字复习卡的（type没有第三个取值）
                               }
                               i++
                               var id=obj.getString("id")     //得到复习卡ID
                               var name=obj.getString("name")  //标题
                               var time=obj.getString("created")  //卡片创建时间
                               var record=obj.getString("record")  //记忆点完成情况
                               var conten=obj.getJSONObject("content").getString("content")   //内容
                               var card=Card(name,conten,record,id,time)
                               fruitList2.add(card)                         //把这些值放进一个Card对象，再放进list来，最后这个list是要被放进recyclerView里面，显示所有复习中的卡片的

                               if (timecleared1==0){
                                   TimeManager.originalTTime.add(TimeUnitt(time,"您创建的复习卡："+name+"已到复习时间，请及时复习哦！",record))
                               }   //TimeManager.originalTTime ——在里面放着准备放进service的所有文字复习卡片，将后续被添加到Timer()里面用Handler来异步发送通知，这是一个前台Service做的事情


                           }
                            //继续初始化recyclerView
                            val adapter2 = TextCardAdapter(fruitList2)
                            recyclerView2.adapter = adapter2


                            if (timecleared1==0){
                                TimeManager().initializeT(getApplicationContext())
                                timecleared1=1     //这个函数，可以把放在TimeManager.originalTTime里面的所有复习卡片，加入service来发送通知

                            }







                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "请求失败",
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