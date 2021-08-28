package WordItems

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version){
    private val tb1col="create table book(bookName text ,wordNum text,planId text,num text)"
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(tb1col)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("drop table if exists book")
        }
        onCreate(db)
    }

}