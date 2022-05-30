package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.TraceCompat.isEnabled
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.macluczak.a2health.*
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_NAME = "id"
class DetailFragment() : Fragment(R.layout.fragment_detail), TimerFragment.DetailCallback{
    private lateinit var binding: FragmentDetailBinding


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


        if(id != null){
            idMap = id.toString()

//            if track select
            val trackDetail = db.getTrack(id)

            binding.idTxt.text = trackDetail.id
            binding.titleTxt.text = trackDetail.title
            binding.distanceTxt.text = trackDetail.distance
            binding.durationtxt.text = trackDetail.duration
            binding.startAddr.text = trackDetail.startAdress
            binding.endAddr.text = trackDetail.stopAdress

            if (trackDetail.lastTime.isBlank()){
                binding.statsCV?.visibility = View.GONE
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


        }
        else{
//            if track not selected


            binding.idTxt.text = " "
            binding.mapCV.visibility = View.GONE
            binding.statsCV.visibility = View.GONE
            binding.timerCV.visibility = View.GONE
            binding.titleTxt.text = "Choose Track"

        }



    }

    override fun onTimeStop(time: String) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val formatedDate = formatter.format(date)

        binding.lastTime.text = time
        binding.lastDay.text = formatedDate

        if (binding.statsCV.isVisible == false){
            binding.statsCV.visibility = View.VISIBLE
        }

    }


}