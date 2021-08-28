package com.example.memorybooster

import Login.Login2
import RetrofitInterfaces.VerificationService
import WordItems.DbHelper
import WordItems.WordActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyPlanActivity2 : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_plan2)

        supportActionBar?.hide()

        findViewById<SeekBar>(R.id.sk1).setOnSeekBarChangeListener(this)

        var boo=""
        var wordNumOfBook=2607
        var rg=findViewById<RadioGroup>(R.id.sex_rg)
        rg.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.male_rb){
                WordActivity.bookId="CET4_3"
                boo="CET4_3"
                wordNumOfBook=2607
            }else{
                WordActivity.bookId="CET6_3"
                boo="CET6_3"
                wordNumOfBook=2345
            }
        }


findViewById<Button>(R.id.cra2sf2b).setOnClickListener {








    WordActivity.wordCardLength=findViewById<TextView>(R.id.khadsk).text.toString()
var wordnum=WordActivity.wordCardLength


    getRegister(boo,""+wordNumOfBook,wordnum)


// 获取控件id



}

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        findViewById<TextView>(R.id.khadsk).text= progress.toString()

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


    fun getRegister(book:String,wordNumOfBook:String,wordnum:String){

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
                            //数据库
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


