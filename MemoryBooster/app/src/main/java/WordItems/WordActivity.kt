package WordItems

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import ReviewCards.Card
import ReviewCards.TextCardAdapter
import ReviewItems.TryWordBack
import TimeAndService.TimeManager
import TimeAndService.TimeUnitt
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorybooster.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class WordActivity : AppCompatActivity() {
    companion object {
        lateinit var rr5: Context
        var bookId: String = "CET4_3"
        var wordCardLength = "20"
        var bookplanID = ""
        var timeclear0=0
        var timeclera1=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word2)
        supportActionBar?.hide()
        rr5 = this
        AllActivities.addActivity(this)
        getRegister()


        var mBottomNavigationView4: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.navw)
        mBottomNavigationView4.selectedItemId = R.id.myhome
        initBottomNavigation()


        //数据库
        val dbh = DbHelper(getApplicationContext(), "db1.db", 1)
        val db = dbh.writableDatabase
//        db.execSQL("delete from book")
//        db.execSQL("insert into book values(\'$book\',\'$wordnum\',\'$id\',\'0\')")
        var coso = db.rawQuery("select * from book", null)
        if (coso.moveToFirst()) {
            while (coso.isAfterLast == false) {
                bookId = coso.getString(coso.getColumnIndex("bookName"))
                wordCardLength = coso.getString(coso.getColumnIndex("wordNum"))
                bookplanID = coso.getString(coso.getColumnIndex("planId"))
                coso.moveToNext()
            }
        }
        coso.close()

//修改界面样子：
        if (bookId == "CET4_3") {
            findViewById<TextView>(R.id.bookname).text = "四级词汇"
            findViewById<ImageView>(R.id.kujhedfkajh).setImageResource(R.drawable.cet4)
        } else if (bookId == "CET6_3") {
            findViewById<TextView>(R.id.bookname).text = "六级词汇"
            findViewById<ImageView>(R.id.kujhedfkajh).setImageResource(R.drawable.cet6)
        }
        findViewById<TextView>(R.id.processshower).text = "每日" + wordCardLength + ",还剩151天"
        findViewById<TextView>(R.id.neednewlearnwordnum).text = "" + wordCardLength


        findViewById<Button>(R.id.fejkshjkfs).setOnClickListener {
            GetWordByInt().getDigitWhenCreating(this)
        }


        //修改计划
        findViewById<Button>(R.id.modifyPlan).setOnClickListener {
            startActivity(Intent(this, ModifyPlanActivity2::class.java))
        }

        //以下是recyclerView:
//        var fruitList = ArrayList<WordCard>()
//        var recyclerView=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rclrvneedtoreview)
////        ini(fruitList) // 初始化数据
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        val adapter = CardAdapter(fruitList)
//        recyclerView.adapter = adapter

//        //以下是recyclerView2:
//        var fruitList2 = ArrayList<WordCard>()
//        var recyclerView2=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rclrvhavereviewed)
//        ini(fruitList2) // 初始化数据
//        val layoutManager2 = LinearLayoutManager(this)
//        recyclerView2.layoutManager = layoutManager2
//        val adapter2 = CardAdapter(fruitList2)
//        recyclerView2.adapter = adapter2
    }

//    fun ini(fruitList: ArrayList<WordCard>) {
//        var fr = ArrayList<String>()
//        fr.add("{\"wordRank\":1,\"headWord\":\"cancel\",\"content\":{\"word\":{\"wordHead\":\"cancel\",\"wordId\":\"CET4_3_1\",\"content\":{\"exam\":[{\"question\":\"As we can no longer wait for the delivery of our order, we have to _______ it.\",\"answer\":{\"explain\":\" cancel order：  取消订单。 句意：  订购的货物尚未送到， 我们不能再等了， 不得不取消订单。 postpone：  推迟， 使延期； refuse：  拒绝， 谢绝； delay：  耽搁， 延迟， 延期。\",\"rightIndex\":4},\"examType\":1,\"choices\":[{\"choiceIndex\":1,\"choice\":\"postpone\"},{\"choiceIndex\":2,\"choice\":\"refuse\"},{\"choiceIndex\":3,\"choice\":\"delay\"},{\"choiceIndex\":4,\"choice\":\"cancel\"}]}],\"sentence\":{\"sentences\":[{\"sContent\":\"Our flight was cancelled.\",\"sCn\":\"我们的航班取消了。\"},{\"sContent\":\"I’m afraid I’ll have to cancel our meeting tomorrow.\",\"sCn\":\"恐怕我得取消我们明天的会议。\"},{\"sContent\":\"You’ll just have to ring John and cancel.\",\"sCn\":\"你只能打电话给约翰取消了。\"}],\"desc\":\"例句\"},\"usphone\":\"'kænsl\",\"syno\":{\"synos\":[{\"pos\":\"vt\",\"tran\":\"[计]取消；删去\",\"hwds\":[{\"w\":\"recall\"},{\"w\":\"call it off\"}]},{\"pos\":\"vi\",\"tran\":\"[计]取消；相互抵销\",\"hwds\":[{\"w\":\"call it off\"},{\"w\":\"declare off\"}]},{\"pos\":\"n\",\"tran\":\"[计]取消，撤销\",\"hwds\":[{\"w\":\"withdrawal\"},{\"w\":\"revocation\"}]}],\"desc\":\"同近\"},\"ukphone\":\"'kænsl\",\"ukspeech\":\"cancel&type=1\",\"phrase\":{\"phrases\":[{\"pContent\":\"cancel button\",\"pCn\":\"取消按钮\"},{\"pContent\":\"cancel out\",\"pCn\":\"取消；抵销\"},{\"pContent\":\"cancel after verification\",\"pCn\":\"核销\"}],\"desc\":\"短语\"},\"relWord\":{\"rels\":[{\"pos\":\"n\",\"words\":[{\"hwd\":\"cancellation\",\"tran\":\" 取消；删除\"}]}],\"desc\":\"同根\"},\"usspeech\":\"cancel&type=2\",\"trans\":[{\"tranCn\":\" 取消， 撤销； 删去\",\"descOther\":\"英释\",\"pos\":\"vt\",\"descCn\":\"中释\",\"tranOther\":\"to decide that something that was officially planned will not happen\"}]}}},\"bookId\":\"CET4_3\"}")
//        fruitList.add(WordCard( "007",fr,"2021-07-22 18:23:00","00000000"))
//        var f = ArrayList<String>()
//        f.add("{\"wordRank\":1,\"headWord\":\"cancel\",\"content\":{\"word\":{\"wordHead\":\"cancel\",\"wordId\":\"CET4_3_1\",\"content\":{\"exam\":[{\"question\":\"As we can no longer wait for the delivery of our order, we have to _______ it.\",\"answer\":{\"explain\":\" cancel order：  取消订单。 句意：  订购的货物尚未送到， 我们不能再等了， 不得不取消订单。 postpone：  推迟， 使延期； refuse：  拒绝， 谢绝； delay：  耽搁， 延迟， 延期。\",\"rightIndex\":4},\"examType\":1,\"choices\":[{\"choiceIndex\":1,\"choice\":\"postpone\"},{\"choiceIndex\":2,\"choice\":\"refuse\"},{\"choiceIndex\":3,\"choice\":\"delay\"},{\"choiceIndex\":4,\"choice\":\"cancel\"}]}],\"sentence\":{\"sentences\":[{\"sContent\":\"Our flight was cancelled.\",\"sCn\":\"我们的航班取消了。\"},{\"sContent\":\"I’m afraid I’ll have to cancel our meeting tomorrow.\",\"sCn\":\"恐怕我得取消我们明天的会议。\"},{\"sContent\":\"You’ll just have to ring John and cancel.\",\"sCn\":\"你只能打电话给约翰取消了。\"}],\"desc\":\"例句\"},\"usphone\":\"'kænsl\",\"syno\":{\"synos\":[{\"pos\":\"vt\",\"tran\":\"[计]取消；删去\",\"hwds\":[{\"w\":\"recall\"},{\"w\":\"call it off\"}]},{\"pos\":\"vi\",\"tran\":\"[计]取消；相互抵销\",\"hwds\":[{\"w\":\"call it off\"},{\"w\":\"declare off\"}]},{\"pos\":\"n\",\"tran\":\"[计]取消，撤销\",\"hwds\":[{\"w\":\"withdrawal\"},{\"w\":\"revocation\"}]}],\"desc\":\"同近\"},\"ukphone\":\"'kænsl\",\"ukspeech\":\"cancel&type=1\",\"phrase\":{\"phrases\":[{\"pContent\":\"cancel button\",\"pCn\":\"取消按钮\"},{\"pContent\":\"cancel out\",\"pCn\":\"取消；抵销\"},{\"pContent\":\"cancel after verification\",\"pCn\":\"核销\"}],\"desc\":\"短语\"},\"relWord\":{\"rels\":[{\"pos\":\"n\",\"words\":[{\"hwd\":\"cancellation\",\"tran\":\" 取消；删除\"}]}],\"desc\":\"同根\"},\"usspeech\":\"cancel&type=2\",\"trans\":[{\"tranCn\":\" 取消， 撤销； 删去\",\"descOther\":\"英释\",\"pos\":\"vt\",\"descCn\":\"中释\",\"tranOther\":\"to decide that something that was officially planned will not happen\"}]}}},\"bookId\":\"CET4_3\"}")
//        fruitList.add(WordCard( "008",fr,"2021-07-22 20:23:00","10000000"))
//
//    }

    fun initBottomNavigation() {
        var mBottomNavigationView: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.navw)
        // 添加监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.myhome -> {
                    }
                    R.id.tools -> {
                        var intent = Intent(rr5, MainActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.me -> {
                        var intent = Intent(rr5, Me::class.java)
                        startActivity(intent)
                    }
                    else -> {
                    }
                }
                // 这里注意返回true,否则点击失效
                return true
            }
        })
    }

    fun getRegister() {
        var coli = ArrayList<WordCard2>()

        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).sendTryWordMsg(Login2.token, "-1")
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

                    var hea = response.headers()
                    var feedback = response.body()?.string()







                    if (feedback != null) {
                        if (feedback.contains("操作成功")) {
                            var wordTotal=0
//                           findViewById<TextView>(R.id.kjdfhkjf).text=feedback
                            var jsoarray = JSONObject(feedback).getJSONArray("data")
                            var i = 0
                            var numberOfWordCard = 0

                            while (i < jsoarray.length()) {
                                var obj = jsoarray.get(i) as JSONObject
                                var type = obj.getString("type")
                                if (type == "0") {
                                    numberOfWordCard++
                                }
                                i++
                            }
                            i = 0
                            findViewById<TextView>(R.id.cardnum).text=""+numberOfWordCard

                            if (timeclear0==0){
                                TimeManager.originalWTime.clear()
                                timeclear0=1
                            }




                            while (i < jsoarray.length()) {
                                var lll=ArrayList<String>()     //放数字的
                                var obj = jsoarray.get(i) as JSONObject
                                var type = obj.getString("type")
                                if (type == "1") {
                                    i++
                                    continue
                                }
                                i++
                                var id = obj.getString("id")  //id
                                var time = obj.getString("created")  //卡片创建时间
                                var record = obj.getString("record")  //记忆点完成情况
                                var conten = obj.getJSONObject("content")  //内容
                                var book = ""
                                var arr = "["
                                var msg=""
                                if (conten.toString().contains("\"CET4_3\"")) {
                                    book = "CET4_3"
                                    msg="您的一张四级词汇卡，该复习了哦！创建时间："+time
                                    var jso = conten.getJSONArray(book)
                                    wordTotal+=jso.length()
                                    var z = 0
                                    while (z < jso.length()) {
                                        arr += jso.getInt(z)
                                        if (z == jso.length() - 1) break
                                        arr += ","
                                        z++
                                    }

                                } else if (conten.toString().contains("\"CET6_3\"")) {
                                    msg="您的一张六级词汇卡，该复习了哦！创建时间："+time
                                    book = "CET6_3"
                                    var jso = conten.getJSONArray(book)
                                    wordTotal+=jso.length()
                                    var z = 0
                                    while (z < jso.length()) {
                                        arr += jso.getInt(z)
                                        if (z == jso.length() - 1) break
                                        arr += ","

                                        z++
                                    }
                                }

                                arr += "]"



                                lll.add(arr)
//                                GetWordByInt.communicationWordCardListt.clear()
//                                Toast.makeText(
//                                    getApplicationContext(),
//                                    "id:"+id+"\narr:"+arr+"\n书:"+book,
//                                    Toast.LENGTH_LONG
//                                ).show();
//                                GetWordByInt().getWordListByDigitList(
//                                    rr5,
//                                    arr,
//                                    id,
//                                    time,
//                                    record,
//                                    1,
//                                    book
//                                )


                                if (timeclera1==0){
                                    TimeManager.originalWTime.add(TimeUnitt(time,msg,record))
                                }



coli.add(WordCard2(id,lll,time,record,book))

//                                GetWordByInt().getWordListByDigitList( WordActivity.rr5,arr,id,time,record,1,book)
//                                findViewById<TextView>(R.id.kjdfhkjf).text= findViewById<TextView>(R.id.kjdfhkjf).text.toString()+obj.toString()

                            }
                            findViewById<TextView>(R.id.needreviewwordnum).text=""+wordTotal

                            var skb=findViewById<SeekBar>(R.id.skbb)


                            if (bookId=="CET4_3"){
                                findViewById<TextView>(R.id.processdigit).text="已学单词：   "+wordTotal+"/2607"
                                skb.progress=100*(wordTotal/2607)

                            }else{
                                findViewById<TextView>(R.id.processdigit).text="已学单词：   "+wordTotal+"/2345"
                                skb.progress=100*(wordTotal/2345)


                            }

//                                while (GetWordByInt.listcount<numberOfWordCard){
//                                    Thread.sleep(300)
//                                }
//                            Thread.sleep(3000)
                            MainActivity.flag = true
//                            var fruitList = ArrayList<WordCard>()
                            var fruitList =coli
                            var recyclerView = findViewById<RecyclerView>(R.id.rclrvneedtoreview)
//                            ini(fruitList) // 初始化数据
//                            fruitList=GetWordByInt.communicationWordCardListt
                            val layoutManager = LinearLayoutManager(rr5)
                            recyclerView.layoutManager = layoutManager
                            val adapter = CardAdapter(fruitList)
                            recyclerView.adapter = adapter


                            if (timeclera1==0){
                                TimeManager().initializeW(getApplicationContext())
                                timeclera1=1
                            }



//                            Toast.makeText(
//                                getApplicationContext(),
//                                "coli"+coli.size+"\n"
//                                +coli.get(0).wordList[0],
//                                Toast.LENGTH_LONG
//                            ).show();

                        } else {
                            Toast.makeText(
                                getApplicationContext(),
                                "登陆失败",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    } else {
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