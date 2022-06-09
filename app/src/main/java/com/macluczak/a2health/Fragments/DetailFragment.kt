package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.TraceCompat.isEnabled
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.macluczak.a2health.*
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.ViewModels.AddViewModel
import com.macluczak.a2health.ViewModels.DetailViewModel
import com.macluczak.a2health.databinding.FragmentDetailBinding
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_NAME = "id"
class DetailFragment() : Fragment(R.layout.fragment_detail), TimerFragment.DetailCallback{

    private lateinit var binding: FragmentDetailBinding
    lateinit var trackDetail: Track

    companion object {
        lateinit var idMap: String
        @JvmStatic
        fun newTrack(id: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NAME, id)

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val id: Int? = arguments?.getInt(ARG_NAME)
        val db = DBHelper(requireContext())
        val viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)


        if(id != null){
            if(viewModel.page == 0) {
                idMap = id.toString()

//            if track select
                trackDetail = db.getTrack(id)

                Log.d("TIME_CALC", "DETAIL TRACK ID: ${trackDetail.id}" )


                binding.idTxt.text = trackDetail.id
                binding.titleTxt.text = trackDetail.title
                binding.distanceTxt.text = trackDetail.distance
                binding.durationtxt.text = trackDetail.duration
                binding.startAddr.text = trackDetail.startAdress
                binding.endAddr.text = trackDetail.stopAdress

                if (trackDetail.lastTime.isBlank()) {
                    binding.statsCV?.visibility = View.GONE
                } else {
                    binding.lastTime.text = trackDetail.lastTime
                    binding.lastDay.text = trackDetail.lastDay
                    binding.bestTime.text = trackDetail.bestTime
                    binding.bestDay.text = trackDetail.bestDay

                }




                binding.mapCV.visibility = View.VISIBLE
                val mapOfTrack = MapsFragment()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flFragmentDetailMap, mapOfTrack)
                    commit()
                }

                binding.timerCV?.visibility = View.VISIBLE
                val timerFragment = TimerFragment()
                childFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragmentDetailTimer, timerFragment)
                    commit()
                }

                binding.fab.setOnClickListener {

                    val runDetailFragment = RunDetailFragment.trackStats(id)
                    viewModel.page = 1
                    activity?.supportFragmentManager?.beginTransaction()?.apply {
                        replace(R.id.flFragmentDetail, runDetailFragment)
                        commit()
                    }

                }

            }
            else if(viewModel.page == 1){

                val runDetailFragment = RunDetailFragment.trackStats(id)
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flFragmentDetail, runDetailFragment)
                    commit()
                }

            }


        }
        else{
//            if track not selected

            binding.idTxt.text = " "
            binding.mapCV.visibility = View.GONE
            binding.statsCV.visibility = View.GONE
            binding.timerCV.visibility = View.GONE
            binding.titleTxt.text = "Choose Track"
            binding.fab.visibility = View.GONE

        }



    }

    override fun onTimeStop(time: String) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val formatedDate = formatter.format(date)
        val dbHelper = DBHelper(requireContext())






        binding.lastTime.text = time
        binding.lastDay.text = formatedDate


        dbHelper.addTrackLastTime(trackDetail.id.toInt(),time, formatedDate)
        dbHelper.addStats(trackDetail.id.toInt(), time, "Monday", formatedDate)

        val trackDetail = dbHelper.getTrack(trackDetail.id.toInt())

        Log.d("TIME_CALC", "TRACK STATS ID: ${trackDetail.id}" )

        if (trackDetail.bestTime.isBlank()){
            binding.bestTime.text = time
            binding.bestDay.text = formatedDate
            dbHelper.updateTrackBestTime(trackDetail.id.toInt(),time, formatedDate)


        }else{
            val bestTime = trackDetail.bestTime
            val bestStr = bestTime.split(":")

            val lastStr = time.split(":")

            val calcBest = ArrayList<String>()
            val calcLast = ArrayList<String>()

            for(e in 0 until lastStr.size){
                if(lastStr[e][0] == '0'){
                     calcLast.add(lastStr[e].drop(1))
                    calcBest.add(bestStr[e].drop(1))
                }
                else{
                    calcLast.add(lastStr[e])
                    calcBest.add(bestStr[e])
                }
            }
            Log.d("TIME_CALC", "LAST TIME: ${trackDetail.lastTime}" )
            Log.d("TIME_CALC", "BEST TIME: ${trackDetail.bestTime}" )

            val totalLast = (calcLast[0].toInt() * 3600 + calcLast[1].toInt() * 60 + calcLast[2].toInt())
            Log.d("TIME_CALC", "LAST summary: $totalLast" )
            val totalBest = (calcBest[0].toInt() * 3600 + calcBest[1].toInt() * 60 + calcBest[2].toInt())
            Log.d("TIME_CALC", "BEST summary: $totalBest" )


            if (totalBest >= totalLast ) {
                Log.d("TIME_CALC ", "TOTAL summary ${totalBest >= totalLast }")
                binding.bestTime.text = time
                binding.bestDay.text = formatedDate
                dbHelper.updateTrackBestTime(trackDetail.id.toInt(),time, formatedDate)
            }


        }

        if (binding.statsCV.isVisible == false){
            binding.statsCV.visibility = View.VISIBLE
        }

    }


}