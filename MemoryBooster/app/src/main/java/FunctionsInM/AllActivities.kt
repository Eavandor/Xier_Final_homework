package FunctionsInM

import android.app.Activity

object AllActivities {           //一个盛放所有Activity的容器，方便一下子结束所有Activity
    private val activities=ArrayList<Activity>()

    fun addActivity(a:Activity){
        activities.add(a)
    }
    fun removeActivity(a:Activity){
        activities.remove(a)
    }
    fun clearActivities(){
        for (a in activities){
            if (!a.isFinishing){
                a.finish()
            }
        }
        activities.clear()
    }
}