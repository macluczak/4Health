package com.macluczak.a2health.Fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapterMode
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.Interface.StatsInterface
import com.macluczak.a2health.Interface.UserLogInterface
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentStatsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class StatsFragment : Fragment(R.layout.fragment_stats) {
    lateinit var binding: FragmentStatsBinding
    lateinit var user: String
    lateinit var db: DBHelper
    lateinit var weekStats: ArrayList<Int>
    private val statsInterface: StatsInterface
        get() = requireActivity() as StatsInterface

    private fun getLoggedUser(): String {
        val sharedScore = requireActivity().getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getString("user", " ").toString()
    }


    fun countDayStats(index: Int, value: String): Int{
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
        return totalTime
    }

    fun String.zeroPrefix():String=
        when(this.length){
            0 -> "00"
            1 -> "0$this"
            else -> this
        }

    fun Int.toHoursMinuteSeconds():String {
        val hours = this / 3600f
        val fullHours = hours.toInt()

        val minutes = (hours - fullHours) * 60f
        val fullMinutes = minutes.toInt()

        val seconds = (minutes - fullMinutes) * 60f
        val fullSeconds = seconds.toInt()



        return "${fullHours.toString().zeroPrefix()}:" +
                "${fullMinutes.toString().zeroPrefix()}:" +
                fullSeconds.toString().zeroPrefix()
    }


    @SuppressLint("SimpleDateFormat")
    fun setHomeStats(){
        Log.d("DAY CALC", "IDZIE")
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val todayDate = formatter.format(date)
        var runCounter = 0
        var timeCounter = 0
        var timer = 0
        var distance = 0f
        var dailyTimer = 0

        val statslist = db.getAllStats().filter { it.user == user }

        binding.dailyprogress.max = 15 * 60



        for(element in 0 until statslist.size){

            val days = TimeUnit.DAYS.convert(
                kotlin.math.abs(formatter.parse(todayDate)!!.time -
                        formatter.parse(statslist[element].runDate)!!.time),
                TimeUnit.MILLISECONDS)

            Log.d("TIME_CALC", "TIME PASSED: ${days}")

            if(days < 7) {
                runCounter ++

                var track = db.getTrack(statslist[element].trackID).distance.split(" ")

                distance += when(track[1]) {
                    "km" -> track[0].toFloat()
                    else -> (track[0].toFloat() *  0.001f)

                }


                timer += when (statslist[element].runDay) {
                    "Monday" -> countDayStats(0, statslist[element].runTime)
                    "Tuesday" -> countDayStats(1, statslist[element].runTime)
                    "Wednesday" -> countDayStats(2, statslist[element].runTime)
                    "Thursday" -> countDayStats(3, statslist[element].runTime)
                    "Friday" -> countDayStats(4, statslist[element].runTime)
                    "Saturday" -> countDayStats(5, statslist[element].runTime)
                    else -> countDayStats(6, statslist[element].runTime)
                }
                if(days < 1 ) dailyTimer += timer
                timeCounter += timer


            }
        }
        binding.runCountTxt.text = runCounter.toString()
        binding.timeCountTxt.text = timeCounter.toHoursMinuteSeconds()
        binding.distanceCountTxt.text = "$distance km"
        ObjectAnimator.ofInt(binding.dailyprogress, "progress", dailyTimer)
            .setDuration(1500)
            .start()
        binding.progresstxt.text = "${(dailyTimer/60)}/15 min"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        binding.blancView.visibility = View.GONE
        binding.userView.visibility = View.GONE
    }




    override fun onResume() {
        super.onResume()


        lifecycleScope.launchWhenStarted {
            try {
                binding.loadstats.visibility = View.VISIBLE
                user = getLoggedUser()
                if(user.isNotBlank()) {

                    binding.blancView.visibility = View.GONE
                    binding.userView.visibility = View.VISIBLE

                    binding.usernameTV.text = "Welcome, $user!"

                    db = DBHelper(requireContext())
                    val tracklist = db.getAllTracks()
                    weekStats = arrayListOf(0, 0, 0, 0, 0, 0, 0)

                    if (tracklist.isNotEmpty()) {


                        setHomeStats()


                        val labels = ArrayList<Entry>()
                        labels.add(Entry(0f, weekStats[0].toFloat()))
                        labels.add(Entry(1f, weekStats[1].toFloat()))
                        labels.add(Entry(2f, weekStats[2].toFloat()))
                        labels.add(Entry(3f, weekStats[3].toFloat()))
                        labels.add(Entry(4f, weekStats[4].toFloat()))
                        labels.add(Entry(5f, weekStats[5].toFloat()))
                        labels.add(Entry(6f, weekStats[6].toFloat()))


                        val barLabelSet = LineDataSet(labels, "Labels")
                        val xlabel = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun")

                        val data = LineData(barLabelSet)
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


                        binding.chartDetail?.setTouchEnabled(false)

                    } else {
                        binding.chartDetail?.visibility = View.GONE
                        binding.chartDetail?.setNoDataText("Add Tracks!")
                    }
                }
                else{
                    binding.userView.visibility = View.GONE
                    binding.blancView.visibility = View.VISIBLE
                }

            } finally {
                binding.loadstats.visibility = View.GONE
                val fade_in = AnimationUtils.loadAnimation(requireContext(), R.anim.up_float_quick)

                if ( binding.userView.isVisible) {

                    binding.userView.startAnimation(fade_in)

                }
                else if(binding.blancView.isVisible){
                    binding.blancView.startAnimation(fade_in)
                }
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatsBinding.bind(view)
        binding.chartDetail?.animateY(1800)
        binding.blancView.setOnClickListener{
            statsInterface.moveToLogin()
        }
    }


}