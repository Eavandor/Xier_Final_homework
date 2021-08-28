package com.example.memorybooster


import FunctionsInM.ChooseImg
import FunctionsInM.ChoosingNoise
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AlertDialog
class Adapter2(val fruitList: List<Fruit>) : RecyclerView.Adapter<Adapter2.ViewHolder>() {
companion object{lateinit var msgshow:TextView}
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        var fruitName: TextView = view.findViewById(R.id.fruitName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.iteminme, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            alertWindow(fruit.name,parent.context,viewHolder)
//            Toast.makeText(parent.context, "you clicked view ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            alertWindow(fruit.name,parent.context,viewHolder)
//            Toast.makeText(parent.context, "you clicked image ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }
    override fun getItemCount() = fruitList.size


    fun alertWindow(name:String,conte:Context,viewHolder: ViewHolder){
        if(name.equals("更改昵称")){
            Me.fram.hint="请输入新用户名"
Me.ain="name"
            var ctrlAnimation: TranslateAnimation = TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF,
                1F, TranslateAnimation.RELATIVE_TO_SELF,
                0F
            )
            ctrlAnimation.duration=1000
            Me.mPopupKayout.postDelayed(Runnable {
                Me.mPopupKayout.visibility= View.VISIBLE
                Me.mPopupKayout.startAnimation(ctrlAnimation)
            },500)
        }else if(name.contains("更改密码")){
            Me.fram.hint="请输入6-12位纯数字密码"
            Me.fram.inputType = InputType.TYPE_CLASS_NUMBER
            Me.ain="password"

            var ctrlAnimation: TranslateAnimation = TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,
                0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF,
                1F, TranslateAnimation.RELATIVE_TO_SELF,
                0F
            )
            ctrlAnimation.duration=1000
            Me.mPopupKayout.postDelayed(Runnable {
                Me.mPopupKayout.visibility= View.VISIBLE
                Me.mPopupKayout.startAnimation(ctrlAnimation)
            },500)

        }else if(name.contains("弹窗提醒")){
            val editor=Me.rr1.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
            if (name.equals("弹窗提醒：开启")){
                editor.putBoolean("openNotification",false)
         viewHolder.fruitName.text="弹窗提醒：关闭"

            }else if(name.equals("弹窗提醒：关闭")){
                editor.putBoolean("openNotification",true)
                viewHolder.fruitName.text="弹窗提醒：开启"
            }
            editor.apply()
//            Me.rr1.startActivity(Intent(Me.rr1,Me::class.java))

        }else if(name.contains("选择白噪音")){
            Me.rr1.startActivity(Intent(Me.rr1,ChoosingNoise::class.java))
        }else if(name.contains("振动提醒")){
            val editor=Me.rr1.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
            if (name.equals("振动提醒：开启")){
                editor.putBoolean("openViborate",false)
                viewHolder.fruitName.text="振动提醒：关闭"
            }else if(name.equals("振动提醒：关闭")){
                editor.putBoolean("openViborate",true)
                viewHolder.fruitName.text="振动提醒：开启"
            }
            editor.apply()
//            Me.rr1.startActivity(Intent(Me.rr1,Me::class.java))
        }else if(name.contains("弹窗通知")){
            val editor=Me.rr1.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
            if (name.equals("弹窗通知：开启")){
                editor.putBoolean("openToast",false)
                viewHolder.fruitName.text="弹窗通知：关闭"
            }else if(name.equals("弹窗通知：关闭")){
                editor.putBoolean("openToast",true)
                viewHolder.fruitName.text="弹窗通知：开启"
            }
            editor.apply()
//            Me.rr1.startActivity(Intent(Me.rr1,Me::class.java))
        }else if(name.contains("白噪音状态")){
            val editor=Me.rr1.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
            if (name.equals("白噪音状态：开启")){
                editor.putBoolean("openNoise",false)
                viewHolder.fruitName.text="白噪音状态：关闭"
            }else if(name.equals("白噪音状态：关闭")){
                editor.putBoolean("openNoise",true)
                viewHolder.fruitName.text="白噪音状态：开启"
            }
            editor.apply()
//            Me.rr1.startActivity(Intent(Me.rr1,Me::class.java))
        }else if(name.contains("提示音提醒")){
            val editor=Me.rr1.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
            if (name.equals("提示音提醒：开启")){
                editor.putBoolean("openRing",false)
                viewHolder.fruitName.text="提示音提醒：关闭"
            }else if(name.equals("提示音提醒：关闭")){
                editor.putBoolean("openRing",true)
                viewHolder.fruitName.text="提示音提醒：开启"
            }
            editor.apply()
//            Me.rr1.startActivity(Intent(Me.rr1,Me::class.java))

        }else if(name.contains("文字卡背景")){

            Me.rr1.startActivity(Intent(Me.rr1,ChooseImg::class.java))

        }else if(name.equals("帮助与反馈")){
            val intent = Intent()
            intent.data =
                Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3Dje63ll5g8f6sZCq5uo1DMfFtqJHUfPnR")
            // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
             try {
                conte.startActivity(intent)

                true
            } catch (e: java.lang.Exception) {
                // 未安装手Q或安装的版本不支持

                false
            }
        }else if(name.equals("关于我们")){
            al("关于我们","    Memory Booster是一款能够帮助同学们更加科学，有效地记忆知识点的助记利器，健忘的克星，app根据艾宾浩斯遗忘曲线，设定了8个记忆巩固点，若同学们能够完完全全按照本app的记忆点进行复习，那么理论上，将会牢牢地记住知识点，在极大程度上提高学习效率。开发团队：rm -rf；后端：朱丹清；美工：苏垚；Android:林宇涵",conte)
        }
    }
    fun al(name:String,contents:String,conte:Context){
        AlertDialog.Builder(conte).apply {
            setTitle(name)
            setMessage(contents)
            setCancelable(false)
            setPositiveButton("好哒！") { dialog, which ->

            }
            setNegativeButton("返回") { dialog,
                                      which ->
            }
            show()

        }
    }
}