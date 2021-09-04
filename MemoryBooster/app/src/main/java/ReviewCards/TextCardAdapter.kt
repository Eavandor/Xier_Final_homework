package ReviewCards

import WordItems.WordCard
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.memorybooster.R
import org.json.JSONObject


class TextCardAdapter(val fruitList: List<Card>) : RecyclerView.Adapter<TextCardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wordcardtime: TextView = view.findViewById(R.id.wordcardtime)
        val firstword: TextView = view.findViewById(R.id.word1232)
        val point: TextView = view.findViewById(R.id.needpoint)
        val afds: TextView = view.findViewById(R.id.afds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardlist, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val intent9=Intent(parent.context,ShowTextCard::class.java)    //点击就跳转到显示卡片的Activity去
            intent9.putExtra("name",fruit.name)
            intent9.putExtra("id",fruit.id)
            intent9.putExtra("content",fruit.content)
            intent9.putExtra("points",fruit.points)
            intent9.putExtra("time",fruit.time)
            parent.context.startActivity(intent9)
        }

        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.wordcardtime.text = fruit.name
        holder.firstword.text ="点我开始复习 ♪(^∇^*)"
holder.afds.text=""
        var i:Int=fruit.points.indexOfFirst { it=='0' }+1
        if (i==0){
            holder.point.text = "全部复习点已完成"
        }else{
            holder.point.text = "需要复习第"+i+"个记忆点"
        }

    }
    override fun getItemCount() = fruitList.size

}