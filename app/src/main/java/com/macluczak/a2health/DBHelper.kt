package com.macluczak.a2health
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.macluczak.a2health.Adapters.Track
import org.json.JSONObject
import java.time.Duration

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
        private val COL_DURATION = "duration"
        private val COL_START_LAT = "startLat"
        private val COL_START_LONG = "startLong"
        private val COL_STOP_LAT = "stopLat"
        private val COL_STOP_LONG = "stopLong"
        private val COL_START_ADRESS = "startAdress"
        private val COL_STOP_ADRESS = "stopAdress"

//        private val TABLE_WAYPOINTS = "WAYPOINTS"
//
        private val COL_WAYPOINTS = "waypoints"


    }



    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COL_TITLE TEXT, $COL_DISTANCE TEXT, $COL_DURATION TEXT," +
                " $COL_START_LAT TEXT, $COL_START_LONG TEXT, $COL_STOP_LAT TEXT, $COL_STOP_LONG TEXT," +
                "$COL_START_ADRESS TEXT, $COL_STOP_ADRESS TEXT, $COL_WAYPOINTS TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
//        val CREATE_WAIPOINTS_QUERY = ("CREATE TABLE $TABLE_WAYPOINTS ($COL_ID INTEGER PRIMARY KEY , $COL_WAYPOINTS TEXT)")
//        db!!.execSQL(CREATE_WAIPOINTS_QUERY)
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

    fun addTrack(title: String, distance: String, duration: String,
                 startAdr: String, stopAdr: String, startLat: String, startLong:String,
                    stopLat: String, stopLong: String, waypoints: String){
        val db= this.writableDatabase
        val values = ContentValues()

        values.put(COL_TITLE, title)
        values.put(COL_DISTANCE, distance)
        values.put(COL_DURATION, duration)
        values.put(COL_START_ADRESS, startAdr)
        values.put(COL_STOP_ADRESS, stopAdr)
        values.put(COL_START_LAT, startLat)
        values.put(COL_START_LONG, startLong)
        values.put(COL_STOP_LAT, stopLat)
        values.put(COL_STOP_LONG, stopLong)
        values.put(COL_WAYPOINTS, waypoints)




        db.insert(TABLE_NAME, null, values)
        db.close()

    }

//    fun addWaypoints(id: Int, waypoints: List<String>){
//        val db= this.writableDatabase
//        val values = ContentValues()
//
//        for (i in 0 until waypoints.size){
//            values.put(COL_ID, id)
//            values.put(COL_WAYPOINTS, waypoints[i])
//            db.insert(TABLE_WAYPOINTS, null, values)
//        }
//
//
//
//
//
//
//
//
//        db.close()
//    }

    @SuppressLint("Range")
    fun getTrack(id: Int): Track {
        val selectQuery =
            "SELECT $COL_TITLE, $COL_DISTANCE, $COL_DURATION, $COL_ID " +
                    ", $COL_START_ADRESS, $COL_STOP_ADRESS, $COL_START_LAT, $COL_START_LONG," +
                    " $COL_STOP_LAT, $COL_STOP_LONG, $COL_WAYPOINTS FROM $TABLE_NAME WHERE $COL_ID = ${id}"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst()

        var track = Track(
            cursor.getString(cursor.getColumnIndex(COL_ID)),
            cursor.getString(cursor.getColumnIndex(COL_TITLE)),

            cursor.getString(cursor.getColumnIndex(COL_DISTANCE)),
            cursor.getString(cursor.getColumnIndex(COL_DURATION)),

            cursor.getString(cursor.getColumnIndex(COL_START_ADRESS)),
            cursor.getString(cursor.getColumnIndex(COL_STOP_ADRESS)),

            cursor.getString(cursor.getColumnIndex(COL_START_LAT)),
            cursor.getString(cursor.getColumnIndex(COL_START_LONG)),

            cursor.getString(cursor.getColumnIndex(COL_STOP_LAT)),
            cursor.getString(cursor.getColumnIndex(COL_STOP_LONG)),

            cursor.getString(cursor.getColumnIndex(COL_WAYPOINTS))




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
        val selectQuery =
            "SELECT $COL_TITLE, $COL_DISTANCE, $COL_DURATION, $COL_ID " +
                    ", $COL_START_ADRESS, $COL_STOP_ADRESS, $COL_START_LAT, $COL_START_LONG," +
                    " $COL_STOP_LAT, $COL_STOP_LONG, $COL_WAYPOINTS FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val trackList = ArrayList<Track>()

        if(cursor.moveToFirst()){
            do {
                var track = Track(
                    cursor.getString(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),

                    cursor.getString(cursor.getColumnIndex(COL_DISTANCE)),
                    cursor.getString(cursor.getColumnIndex(COL_DURATION)),

                    cursor.getString(cursor.getColumnIndex(COL_START_ADRESS)),
                    cursor.getString(cursor.getColumnIndex(COL_STOP_ADRESS)),

                    cursor.getString(cursor.getColumnIndex(COL_START_LAT)),
                    cursor.getString(cursor.getColumnIndex(COL_START_LONG)),

                    cursor.getString(cursor.getColumnIndex(COL_STOP_LAT)),
                    cursor.getString(cursor.getColumnIndex(COL_STOP_LONG)),

                    cursor.getString(cursor.getColumnIndex(COL_WAYPOINTS))
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