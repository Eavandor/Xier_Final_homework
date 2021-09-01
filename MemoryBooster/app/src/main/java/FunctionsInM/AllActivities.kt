package FunctionsInM

import android.app.Activity

object AllActivities {
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