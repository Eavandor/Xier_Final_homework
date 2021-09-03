package com.example.memorybooster

import FunctionsInM.AllActivities
import FunctionsInM.ModifyNameAndPassword
import Login.Login2
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

class Me : AppCompatActivity() {
    companion object{
        lateinit var rr1:Context
        lateinit var mPopupKayout:LinearLayout
        lateinit var fram:EditText
        var ain="name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)
        supportActionBar?.hide()
        AllActivities.addActivity(this)
        fram=findViewById<EditText>(R.id.jkdhkjedfhkj)
        rr1=this
        mPopupKayout=findViewById(R.id.start_ctrl)
        val editor=getSharedPreferences("data",Context.MODE_PRIVATE)
        var username=editor.getString("name","用户名")
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p
        //z这的注释记得删
        findViewById<TextView>(R.id.usernameinme).text=username
//        findViewById<TextView>(R.id.usernameinme).text="Timothy"

        findViewById<Button>(R.id.adsfsf).setOnClickListener {
            var ctrlAnimation:TranslateAnimation=TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF,
                1F
            )
            ctrlAnimation.duration=1000
            mPopupKayout.postDelayed(Runnable {
                mPopupKayout.visibility= View.GONE
                mPopupKayout.startAnimation(ctrlAnimation)
            },500)



            var nsn=findViewById<EditText>(R.id.jkdhkjedfhkj).text.toString()

            if (ain=="name"){
                findViewById<TextView>(R.id.usernameinme).text=nsn
//                val editor=getSharedPreferences("data",Context.MODE_PRIVATE).edit()
//                editor.putString("name",nsn)
//                editor.apply()
                ModifyNameAndPassword.nam=nsn
                ModifyNameAndPassword.passwor=Login2.p
            }else if (ain=="password"){
//                val editor=getSharedPreferences("data",Context.MODE_PRIVATE).edit()
//                editor.putString("password",nsn)
//                editor.apply()
//                Login2.p=nsn
    if (nsn.length<6||nsn.length>12){
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p
        Toast.makeText(this, "密码只能是6-12位长的数字哦", Toast.LENGTH_SHORT).show()
    }else if (ModifyNameAndPassword().onlyHaveDigit(nsn)==false){
        Toast.makeText(
            getApplicationContext(),
            "密码只能是数字哦",
            Toast.LENGTH_LONG
        )
            .show();
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p
    }else{
        ModifyNameAndPassword.passwor=nsn
        //z这的注释记得删
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }

    }

            }
            //开启更新函数
            var jso: JSONObject = JSONObject()
            jso.put("username",Login2.usn)
            jso.put("password",ModifyNameAndPassword.passwor.toInt())
            jso.put("nickname",ModifyNameAndPassword.nam)
            ModifyNameAndPassword().getRegister(jso.toString(),this)
        }
        var  mBottomNavigationView4: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav3)
        mBottomNavigationView4.selectedItemId=R.id.me
        rr1=this
        initBottomNavigation()

        //以下是recyclerView:
        var fruitList = ArrayList<Fruit>()
        var recyclerView=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recinm)
        initFruits(fruitList) // 初始化数据
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = Adapter2(fruitList)
        recyclerView.adapter = adapter
    }
    fun initFruits(fruitlist:ArrayList<Fruit>){

        val editor=rr1.getSharedPreferences("data",Context.MODE_PRIVATE)
        var announcementornot=editor.getBoolean("openNotification",false)
        var viborateOrnot=editor.getBoolean("openViborate",false)
        var ringornot=editor.getBoolean("openRing",false)
        var opennoiseornot=editor.getBoolean("openNoise",false)
        var opentoastornot=editor.getBoolean("openToast",false)


        fruitlist.add(0, Fruit("更改昵称",R.drawable.mei1))
        if (announcementornot){
            fruitlist.add(1, Fruit("弹窗提醒：开启",R.drawable.pop))
        }else{
            fruitlist.add(1, Fruit("弹窗提醒：关闭",R.drawable.pop))
        }
        if (viborateOrnot){
            fruitlist.add(2, Fruit("振动提醒：开启",R.drawable.vibrate))
        }else{
            fruitlist.add(2, Fruit("振动提醒：关闭",R.drawable.vibrate))
        }
        if (ringornot){
            fruitlist.add(3, Fruit("提示音提醒：开启",R.drawable.ring))
        }else{
            fruitlist.add(3, Fruit("提示音提醒：关闭",R.drawable.ring))
        }
        fruitlist.add(4, Fruit("帮助与反馈",R.drawable.feedback))
        fruitlist.add(5, Fruit("关于我们",R.drawable.aboutus))
        if (opennoiseornot){
            fruitlist.add(6, Fruit("白噪音状态：开启",R.drawable.whitnoise))
        }else{
            fruitlist.add(6, Fruit("白噪音状态：关闭",R.drawable.whitnoise))
        }

        fruitlist.add(7, Fruit("选择白噪音",R.drawable.choosenoise))
        fruitlist.add(8, Fruit("文字卡背景",R.drawable.cardbg))
        fruitlist.add(9, Fruit("退出登陆",R.drawable.changeico))
        fruitlist.add(10, Fruit("更改密码",R.drawable.changepwd))

        if (opentoastornot){
            fruitlist.add(11, Fruit("弹窗通知：开启",R.drawable.pop))
        }else{
            fruitlist.add(11, Fruit("弹窗通知：关闭",R.drawable.pop))
        }

    }

    fun initBottomNavigation() {
        var  mBottomNavigationView:BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav3)
        // 添加监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.myhome -> {var intent= Intent(rr1,WordItems.WordActivity::class.java)
                        startActivity(intent)}
                    R.id.tools ->{var intent= Intent(rr1,MainActivity::class.java)
                        startActivity(intent)}
                    R.id.me ->{}

                    else -> {
                    }
                }
                // 这里注意返回true,否则点击失效
                return true
            }
        })
    }




}