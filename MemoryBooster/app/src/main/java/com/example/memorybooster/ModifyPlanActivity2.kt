package com.example.memorybooster

import FunctionsInM.AllActivities
import Login.Login2
import RetrofitInterfaces.VerificationService
import WordItems.DbHelper
import WordItems.WordActivity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyPlanActivity2 : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_plan2)

        supportActionBar?.hide()
        AllActivities.addActivity(this)
        findViewById<SeekBar>(R.id.sk1).setOnSeekBarChangeListener(this)

        var boo=""
        var wordNumOfBook=2607
        var rg=findViewById<RadioGroup>(R.id.sex_rg)  //选择是四级还是六级
        rg.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.male_rb){         //点击了四级，辞书ID就等于CET4_3,词数是2607
                WordActivity.bookId="CET4_3"
                boo="CET4_3"
                wordNumOfBook=2607
            }else{                       //点击了六级，辞书ID就等于CET4_6,词数是2345
                WordActivity.bookId="CET6_3"
                boo="CET6_3"
                wordNumOfBook=2345
            }
        }


findViewById<Button>(R.id.cra2sf2b).setOnClickListener {
    WordActivity.wordCardLength=findViewById<TextView>(R.id.khadsk).text.toString()
var wordnum=WordActivity.wordCardLength


    getRegister(boo,""+wordNumOfBook,wordnum)  //开启网络请求，向后端发起“修改背单词计划”，修改每个单词卡含有的词数量，以及辞书ID(四级书和六级书有不一样的ID)



}

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        findViewById<TextView>(R.id.khadsk).text= progress.toString()
                   //如果用户拖动SeekBar的拉条 ，监听函数将获取拉条的值，这代表用户希望一个单词单元有多少个单词
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


    fun getRegister(book:String,wordNumOfBook:String,wordnum:String){            //这个函数向后端发送用户更改后的背单词计划

        val retr = Login2.publicretrofit
        retr.create(VerificationService::class.java).createWordBookPlanMsg(Login2.token,book,wordNumOfBook)
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

                            var id= JSONObject(feedback).getJSONObject("data").getString("id")
                            //数据库操作，更新背单词计划
                            val dbh= DbHelper( getApplicationContext(),"db1.db",1)
                            val db=dbh.writableDatabase
                            db.execSQL("delete from book")
    db.execSQL("insert into book values(\'$book\',\'$wordnum\',\'$id\',\'0\')")
                            var i=Intent(getApplicationContext(),WordActivity::class.java)
                            startActivity(i)
                        }else{
                            Toast.makeText(
                                getApplicationContext(),
                                "上传失败"+feedback,
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }else{
                        Toast.makeText(
                            getApplicationContext(),
                            "上传失败",
                            Toast.LENGTH_LONG
                        ).show();
                    }

                }

            })


    }



}


