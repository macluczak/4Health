package com.macluczak.a2health

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TrackStats
import org.json.JSONObject
import java.time.Duration

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VER
) {

    companion object {

        private val DATABASE_VER = 3
        private val DATABASE_NAME = "2Health.db"

        private val TABLE_NAME = "TRACKS"
        private val TABLE_STATS = "STATS"

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

        private val COL_BEST_TIME = "bestTime"
        private val COL_BEST_DAY = "bestDay"

        private val COL_LAST_TIME = "lastTime"
        private val COL_LAST_DAY = "lastDay"

        private val COL_WAYPOINTS = "waypoints"

        private val COL_IMAGE = "image"

        //STATS TABLE

        private val COL_TRACKID = "trackID"
        private val COL_RUNTIME = "runTime"
        private val COL_DAY = "runDay"
        private val COL_DATE = "runDate"

        private val COL_USER = "logUser"


    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $COL_TITLE TEXT, $COL_DISTANCE TEXT, $COL_DURATION TEXT," +
                    " $COL_START_LAT TEXT, $COL_START_LONG TEXT, $COL_STOP_LAT TEXT, $COL_STOP_LONG TEXT," +
                    "$COL_START_ADRESS TEXT, $COL_STOP_ADRESS TEXT, $COL_WAYPOINTS TEXT," +
                    " $COL_BEST_TIME TEXT,  $COL_LAST_TIME TEXT, $COL_BEST_DAY TEXT,  $COL_LAST_DAY TEXT, $COL_IMAGE TEXT)")

        val CREATE_STATS_QUERY = ("CREATE TABLE $TABLE_STATS ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COL_RUNTIME TEXT,$COL_USER TEXT, $COL_TRACKID INTEGER,  $COL_DAY TEXT, $COL_DATE TEXT)")

        db!!.execSQL(CREATE_STATS_QUERY)
        db!!.execSQL(CREATE_TABLE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_STATS")
        onCreate(db!!)
    }

    fun dropTable() {
        val db = this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_STATS")
        onCreate(db!!)

    }

    //Table Track

    fun addTrack(
        title: String, distance: String, duration: String,
        startAdr: String, stopAdr: String, startLat: String, startLong: String,
        stopLat: String, stopLong: String, waypoints: String, image:String
    ) {
        val db = this.writableDatabase
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
        values.put(COL_IMAGE, image)

        values.put(COL_BEST_TIME, " ")
        values.put(COL_BEST_DAY, " ")

        values.put(COL_LAST_TIME, " ")
        values.put(COL_LAST_DAY, " ")





        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    //stats table

    fun addStats(id: Int, runTime: String, runDay: String, runDate: String, logUser: String) {
        val db = this.writableDatabase
        val values = ContentValues()

        Log.d("DBHELPER", "ID: $id, TIME: $runTime")

        values.put(COL_TRACKID, id)
        values.put(COL_RUNTIME, runTime)
        values.put(COL_DAY, runDay)
        values.put(COL_DATE, runDate)
        values.put(COL_USER, logUser)

        db.insert(TABLE_STATS, null, values)
        db.close()

    }

    @SuppressLint("Range")
    fun getTrackStats(id: Int): ArrayList<TrackStats> {
        val selectQuery =
            "SELECT * FROM $TABLE_STATS WHERE $COL_TRACKID = ${id}"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val statsList = ArrayList<TrackStats>()
        cursor.moveToFirst()
        if (cursor.moveToFirst()) {
            do {
                var stats = TrackStats(
                    cursor.getString(cursor.getColumnIndex(COL_TRACKID)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_RUNTIME)),
                    cursor.getString(cursor.getColumnIndex(COL_DAY)),
                    cursor.getString(cursor.getColumnIndex(COL_DATE)),
                    cursor.getString(cursor.getColumnIndex(COL_USER))
                )
                Log.d("DBHELPER", "TRACKID: $id,  DBID: ${cursor.getString(cursor.getColumnIndex(COL_ID))}")
                statsList.add(stats)
            } while (cursor.moveToNext())
        }

        db.close()
        return statsList
    }

    @SuppressLint("Range")
    fun getAllStats(): ArrayList<TrackStats> {
        val selectQuery =
            "SELECT * FROM $TABLE_STATS"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val statsList = ArrayList<TrackStats>()
        cursor.moveToFirst()
        if (cursor.moveToFirst()) {
            do {
                var stats = TrackStats(
                    cursor.getString(cursor.getColumnIndex(COL_TRACKID)).toInt(),
                    cursor.getString(cursor.getColumnIndex(COL_RUNTIME)),
                    cursor.getString(cursor.getColumnIndex(COL_DAY)),
                    cursor.getString(cursor.getColumnIndex(COL_DATE)),
                    cursor.getString(cursor.getColumnIndex(COL_USER))

                )
                statsList.add(stats)
            } while (cursor.moveToNext())
        }

        db.close()
        return statsList
    }


    @SuppressLint("Range")
    fun getTrack(id: Int): Track {
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $COL_ID = ${id}"
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

            cursor.getString(cursor.getColumnIndex(COL_WAYPOINTS)),

            cursor.getString(cursor.getColumnIndex(COL_BEST_TIME)),
            cursor.getString(cursor.getColumnIndex(COL_BEST_DAY)),

            cursor.getString(cursor.getColumnIndex(COL_LAST_TIME)),
            cursor.getString(cursor.getColumnIndex(COL_LAST_DAY)),

            cursor.getString(cursor.getColumnIndex(COL_IMAGE))

        )

        db.close()

        return track

    }

    @SuppressLint("Range")
    fun getAllTracks(): ArrayList<Track> {
        val selectQuery =
            "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val trackList = ArrayList<Track>()

        if (cursor.moveToFirst()) {
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

                    cursor.getString(cursor.getColumnIndex(COL_WAYPOINTS)),

                    cursor.getString(cursor.getColumnIndex(COL_BEST_TIME)),
                    cursor.getString(cursor.getColumnIndex(COL_BEST_DAY)),

                    cursor.getString(cursor.getColumnIndex(COL_LAST_TIME)),
                    cursor.getString(cursor.getColumnIndex(COL_LAST_DAY)),

                    cursor.getString(cursor.getColumnIndex(COL_IMAGE))

                )
                trackList.add(track)
            } while (cursor.moveToNext())
        }
        db.close()
        return trackList
    }

    fun addTrackLastTime(id: Int, time: String, day: String) {
        val db = this.writableDatabase
        db!!.execSQL("UPDATE $TABLE_NAME SET $COL_LAST_TIME = '${time}' WHERE $COL_ID = '${id}'")
        db!!.execSQL("UPDATE $TABLE_NAME SET $COL_LAST_DAY = '${day}' WHERE $COL_ID = '${id}'")
        db.close()
    }

    fun updateTrackBestTime(id: Int, time: String, day: String) {
        val db = this.writableDatabase
        db!!.execSQL("UPDATE $TABLE_NAME SET $COL_BEST_TIME = '${time}' WHERE $COL_ID = '${id}'")
        db!!.execSQL("UPDATE $TABLE_NAME SET $COL_BEST_DAY = '${day}' WHERE $COL_ID = '${id}'")
        db.close()
    }


}