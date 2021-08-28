package WordItems


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


class CardAdapter(val fruitList: List<WordCard2>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wordcardtime: TextView = view.findViewById(R.id.wordcardtime)
        val firstword: TextView = view.findViewById(R.id.word1232)
        val point: TextView = view.findViewById(R.id.needpoint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardlist, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
//            alertWindow(fruit.name,parent.context)
            GetWordByInt().getWordListByDigitList(WordActivity.rr5,fruit.wordList[0],fruit.wordCardID,fruit.time,fruit.points,0,fruit.book)
//            Toast.makeText(parent.context, "you clicked view", Toast.LENGTH_SHORT).show()
        }

        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.wordcardtime.text = "创建时间："+fruit.time
//
//            var headWord= JSONObject(fruit.wordList.get(0)).getString("headWord")
//        var pos1=JSONObject(fruit.wordList.get(0)).getJSONObject("content").getJSONObject("word")
//            .getJSONObject("content").getJSONArray("trans").getJSONObject(0).getString("pos")
//            var mean=JSONObject(fruit.wordList.get(0)).getJSONObject("content").getJSONObject("word")
//                .getJSONObject("content").getJSONArray("trans").getJSONObject(0).getString("tranCn")
//var disp=headWord+" "+pos1+" "+mean
        var str=fruit.wordList[0]
        var count=0
        var j=0
        while (j<str.length){
            if (str.get(j)==','){
                count++
            }
            j++
        }
        count++
                holder.firstword.text ="本卡片共有"+count+"词"

        var i:Int=fruit.points.indexOfFirst { it=='0' }+1
        if (i==0){
            holder.point.text = "全部复习点已完成"
        }else{
            holder.point.text = "需要复习第"+i+"个记忆点"
        }
    }
    override fun getItemCount() = fruitList.size

}