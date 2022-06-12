package com.macluczak.a2health.Fragments

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentTracksBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TracksFragment() : Fragment(R.layout.fragment_tracks), TracksAdapter.TrackInterface  {

    private lateinit var binding: FragmentTracksBinding
    lateinit var mainCallback:MainCallback
    lateinit var db: DBHelper
    lateinit var weekStats: ArrayList<Int>


    fun countDayStats(index: Int, value: String){
        var current = weekStats[index]

        val runTime = value.split(":")



        val calcTime = ArrayList<String>()


        for (e in 0 until runTime.size) {
            if (runTime[e][0] == '0') {
                calcTime.add(runTime[e].drop(1))
            } else {
                calcTime.add(runTime[e])
            }
        }

        val totalTime =
            (runTime[0].toInt() * 3600 + runTime[1].toInt() * 60 + runTime[2].toInt())
        Log.d("TIME_CALC", "LAST summary: $totalTime")

        weekStats[index] = totalTime + current
        Log.d("DAY CALC", "DAY: $index,  TIME:$totalTime, SUMMARY: ${weekStats[index]}")

    }

    @SuppressLint("SimpleDateFormat")
    fun setHomeStats(){
        Log.d("DAY CALC", "IDZIE")
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val todayDate = formatter.format(date)

        val statslist = db.getAllStats()


        for(element in 0 until statslist.size){

            val days = TimeUnit.DAYS.convert(
                formatter.parse(statslist[element].runDate)!!.getTime() -
                        formatter.parse(todayDate)!!.getTime(),
                TimeUnit.MILLISECONDS)

            Log.d("TIME_CALC", "TIME PASSED: ${days}")

            if(days < 7) {
                when (statslist[element].runDay) {
                    "Monday" -> countDayStats(0, statslist[element].runTime)
                    "Tuesday" -> countDayStats(1, statslist[element].runTime)
                    "Wednesday" -> countDayStats(2, statslist[element].runTime)
                    "Thursday" -> countDayStats(3, statslist[element].runTime)
                    "Friday" -> countDayStats(4, statslist[element].runTime)
                    "Saturday" -> countDayStats(5, statslist[element].runTime)
                    "Sunday" -> countDayStats(6, statslist[element].runTime)
                    else -> null
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        db = DBHelper(requireContext())
//        db.dropTable()
        val tracklist = db.getAllTracks()
        weekStats = arrayListOf(0,0,0,0,0,0,0)

        if(tracklist.isNotEmpty()) {


            setHomeStats()


            val labels = ArrayList<BarEntry>()
            labels.add(BarEntry(0f, weekStats[0].toFloat()))
            labels.add(BarEntry(1f, weekStats[1].toFloat()))
            labels.add(BarEntry(2f, weekStats[2].toFloat()))
            labels.add(BarEntry(3f, weekStats[3].toFloat()))
            labels.add(BarEntry(4f, weekStats[4].toFloat()))
            labels.add(BarEntry(5f, weekStats[5].toFloat()))
            labels.add(BarEntry(6f, weekStats[6].toFloat()))


            val barLabelSet = BarDataSet(labels, "Labels")
            val xlabel = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun")

            val data = BarData(barLabelSet)
            binding.chartDetail?.apply {
                axisRight.setDrawGridLines(false)
                axisLeft.setDrawGridLines(false)
                xAxis.setDrawGridLines(false)
                axisRight.setDrawAxisLine(false)
                axisLeft.setDrawAxisLine(false)
                xAxis.setDrawAxisLine(false)
                isClickable = false
                isDoubleTapToZoomEnabled = false
                isDoubleTapToZoomEnabled = false
                legend.isEnabled = false
                axisLeft.setDrawLabels(false)
                axisRight.setDrawLabels(false)
//            xAxis.setDrawLabels(false)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(xlabel)
                description.isEnabled = false
                xAxis.valueFormatter

                binding.chartDetail!!.data = data
            }


            binding.chartDetail?.animateY(1800)
            binding.chartDetail?.setTouchEnabled(false)


            val adapter = TracksAdapter(tracklist, this)
            binding.rvTracks.adapter = adapter
            binding.rvTracks.layoutManager = LinearLayoutManager(context)
        }
        else{
            binding.chartDetail?.visibility = View.GONE
            binding.chartDetail?.setNoDataText("Add Tracks!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTracksBinding.bind(view)
        mainCallback = requireActivity() as MainCallback


    }

    override fun onClick(position: Int) {

        mainCallback.clickCallback(position)


    }

    override fun onLongClick(position: Int) {
        val track = db.getTrack(position)
        Toast.makeText(requireContext(), "longClick ${track.title}", Toast.LENGTH_SHORT).show()

    }

    interface MainCallback{
        fun clickCallback(position: Int)

    }


}