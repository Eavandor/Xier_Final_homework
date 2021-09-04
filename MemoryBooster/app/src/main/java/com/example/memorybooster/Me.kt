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
        mPopupKayout=findViewById(R.id.start_ctrl)           //这是一个被隐藏起来的LinearLayout，把它设置为可以显示，可以用动画做弹窗效果
        val editor=getSharedPreferences("data",Context.MODE_PRIVATE)         //使用SharedPreference获取键值对（因为这里再写Room数据库或者SQLite数据库太大材小用了，只是存一个字段而已）
        var username=editor.getString("name","用户名")   //从SharedPreference中获取用户名，无则暂时用“用户名”三个字代替
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p

        findViewById<TextView>(R.id.usernameinme).text=username

        findViewById<Button>(R.id.adsfsf).setOnClickListener {
            var ctrlAnimation:TranslateAnimation=TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF,
                1F                     //动画，移动的X坐标不变，Y坐标向下移动1（因为向上移的是在Adapter2里面，这里点击完是收回的，向下收回）
            )
            ctrlAnimation.duration=1000     //设置动画完成耗时1秒钟
            mPopupKayout.postDelayed(Runnable {
                mPopupKayout.visibility= View.GONE     //然后把这个LinearLayout的可视与否设置为"GONE"，隐藏这个LinearLayout，收回弹窗效果就出来了
                mPopupKayout.startAnimation(ctrlAnimation) //开始动画
            },500)



            var nsn=findViewById<EditText>(R.id.jkdhkjedfhkj).text.toString()

            if (ain=="name"){         //ain是目标（我一定不会告诉你，是我个大傻逼把aim打错了，所以才变成ain，总之，这个表示弹窗的目的，因为我不想写两个，在修改用户名和密码都用这个弹窗）
                                      //点击“修改密码”，ain=password ,点击修改名称，ain=neme
                findViewById<TextView>(R.id.usernameinme).text=nsn  //修改名称，把在上面那个弹窗效果的LinearLayout里面EditText的值取出来，它此时此刻是用户输入的名字
                ModifyNameAndPassword.nam=nsn
                ModifyNameAndPassword.passwor=Login2.p
            }else if (ain=="password"){
    if (nsn.length<6||nsn.length>12){       //输入密码的长度校验，必须在6——12位之间
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p
        Toast.makeText(this, "密码只能是6-12位长的数字哦", Toast.LENGTH_SHORT).show()
    }else if (ModifyNameAndPassword().onlyHaveDigit(nsn)==false){   //这个函数，是用来判断有个字符串是不是“只含有数字”，只有数字就返回true
        Toast.makeText(
            getApplicationContext(),
            "密码只能是数字哦",
            Toast.LENGTH_SHORT
        )
            .show();
        if (username != null) {
            ModifyNameAndPassword.nam=username
        }
        ModifyNameAndPassword.passwor=Login2.p
    }else{
        ModifyNameAndPassword.passwor=nsn
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
            ModifyNameAndPassword().getRegister(jso.toString(),this)    //后端一定要俺传Body,不喜欢表单，Body是json字符串
        }
        var  mBottomNavigationView4: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav3)
        mBottomNavigationView4.selectedItemId=R.id.me        //底部导航栏当前选中的项目是这个
        rr1=this
        initBottomNavigation()

        //以下是recyclerView:
        var fruitList = ArrayList<Fruit>()
        var recyclerView=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recinm)
        initFruits(fruitList) // 初始化数据
        val layoutManager = LinearLayoutManager(this)   //一维的排列
        recyclerView.layoutManager = layoutManager
        val adapter = Adapter2(fruitList)
        recyclerView.adapter = adapter
    }
    fun initFruits(fruitlist:ArrayList<Fruit>){

        val editor=rr1.getSharedPreferences("data",Context.MODE_PRIVATE)     //从SharedPreference里面拿到各个状态值
        var announcementornot=editor.getBoolean("openNotification",false)   //是否打开通知的状态字段
        var viborateOrnot=editor.getBoolean("openViborate",false)         //是否振动
        var ringornot=editor.getBoolean("openRing",false)      //通知时是否开启振动提示
        var opennoiseornot=editor.getBoolean("openNoise",false)       //是否开启白噪音
        var opentoastornot=editor.getBoolean("openToast",false)       //如果通知由于系统不给权限，还可以使用Toast来弹窗


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
            override fun onNavigationItemSelected(item: MenuItem): Boolean {   //底部导航栏，点击对应的图标跳转到其他界面
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