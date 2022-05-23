package com.macluczak.a2health
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.macluczak.a2health.Adapters.Track

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
        private val COL_START_LAT = "startLat"
        private val COL_START_LONG = "startLong"
        private val COL_STOP_LAT = "stopLat"
        private val COL_STOP_LONG = "stopLong"


    }



    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COL_TITLE TEXT, $COL_DISTANCE INTEGER, $COL_CITY TEXT, $COL_FAV INTEGER," +
                " $COL_START_LAT TEXT, $COL_START_LONG TEXT, $COL_STOP_LAT TEXT, $COL_STOP_LONG TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun dropTable(){
        val db= this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)

    }

    fun addTrack(title: String, distance: String, city: String){
        val db= this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, title)
        values.put(COL_DISTANCE, distance)
        values.put(COL_CITY, city)
        values.put(COL_FAV, 0)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getTrack(id: Int): Track {
        val selectQuery =
            "SELECT $COL_TITLE, $COL_DISTANCE, $COL_CITY, $COL_ID , $COL_FAV FROM $TABLE_NAME WHERE $COL_ID = ${id}"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst()

        var track = Track(
            cursor.getString(cursor.getColumnIndex(COL_ID)),
            cursor.getString(cursor.getColumnIndex(COL_TITLE)),
            cursor.getString(cursor.getColumnIndex(COL_DISTANCE)).toInt(),
            cursor.getString(cursor.getColumnIndex(COL_CITY)),
            cursor.getString(cursor.getColumnIndex(COL_FAV)).toBoolean()
        )

        db.close()

        return track

    }

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

    @SuppressLint("Range")
    fun getAllTracks(): ArrayList<Track>{
        val selectQuery = "SELECT $COL_TITLE, $COL_DISTANCE, $COL_CITY, $COL_ID , $COL_FAV FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val trackList = ArrayList<Track>()

        if(cursor.moveToFirst()){
            do {
                var track = Track(
                cursor.getString(cursor.getColumnIndex(COL_ID)),
                 cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_DISTANCE)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_CITY)),
                    cursor.getString(cursor.getColumnIndex(COL_FAV)).toBoolean()
                )
                trackList.add(track)
            } while (cursor.moveToNext())
        }
        db.close()
        return trackList
    }
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