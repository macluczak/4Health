package com.macluczak.a2health
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VER
) {

    companion object {

        private val DATABASE_VER = 1
        private val DATABASE_NAME = "2Health.db"

        private val TABLE_NAME = "TRACKS"
        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_DISTANCE = "distance"
        private val COL_CITY = "city"
        private val COL_FAV = "fav"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TITLE TEXT, $COL_DISTANCE INTEGER, $COL_CITY TEXT, $COL_FAV INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

//    fun addTrack(username: String, password: String){
//        val db= this.writableDatabase
//        val values = ContentValues()
//        values.put(COL_USERNAME, username)
//        values.put(COL_PASSWORD, password)
//        values.put(COL_SCORE, 0)
//
//        db.insert(TABLE_NAME, null, values)
//        db.close()
//    }

//    fun isUserExists(username: String): Boolean{
//
//        val selectQuery = "SELECT * FROM $TABLE_NAME where $COL_USERNAME = '$username'"
//        val db = this.writableDatabase
//        val cursor: Cursor = db.rawQuery(selectQuery, null)
//        if (cursor.count > 0 ){
//                return true
//        }
//        return false
//    }

//    fun tryLogUser(username: String, password: String): Boolean{
//        val selectQuery = "SELECT * FROM $TABLE_NAME where $COL_USERNAME = '$username' AND $COL_PASSWORD = '$password'"
//        val db = this.writableDatabase
//        val cursor: Cursor = db.rawQuery(selectQuery, null)
//        if (cursor.count > 0 ){
//            return true
//        }
//        return false
//    }

//    @SuppressLint("Range")
//    fun getUserScores(): ArrayList<Player>{
//        val selectQuery = "SELECT $COL_USERNAME, $COL_SCORE FROM $TABLE_NAME"
//        val db = this.writableDatabase
//        val cursor: Cursor = db.rawQuery(selectQuery, null)
//        val playerStats = ArrayList<Player>()
//
//        if(cursor.moveToFirst()){
//            do {
//                var player = Player()
//                player.username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
//                player.score = cursor.getString(cursor.getColumnIndex(COL_SCORE))
//
//
//                playerStats.add(player)
//            } while (cursor.moveToNext())
//        }
//        db.close()
//        return playerStats
//    }
//
//    @SuppressLint("Range")
//    fun isScoreGreater(score:String, username: String):Boolean{
//        val db = this.writableDatabase
//        val selectQuery = "SELECT $COL_SCORE FROM $TABLE_NAME WHERE $COL_USERNAME = '$username'"
//        val cursor: Cursor = db.rawQuery(selectQuery, null)
//        cursor.moveToFirst()
//        val bestScore = cursor.getString(cursor.getColumnIndex(COL_SCORE))
//        return score.toInt() > bestScore.toInt()
//
//    }
//
//    fun updateUserScore(username: String, score: String){
//        if(isScoreGreater(score, username)){
//            val db = this.writableDatabase
//            db!!.execSQL("UPDATE $TABLE_NAME SET $COL_SCORE = $score WHERE $COL_USERNAME = '$username'")
//            db.close()}
//
//    }

}