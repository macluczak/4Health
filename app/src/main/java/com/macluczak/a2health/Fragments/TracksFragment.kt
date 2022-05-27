package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.macluczak.a2health.R
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.Adapters.TracksAdapter.*
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.databinding.FragmentTracksBinding

class TracksFragment() : Fragment(R.layout.fragment_tracks), TracksAdapter.TrackInterface  {

    private lateinit var binding: FragmentTracksBinding
    lateinit var mainCallback:MainCallback
    lateinit var db: DBHelper




    override fun onResume() {
        super.onResume()
        db = DBHelper(requireContext())
        val tracklist = db.getAllTracks()

        val adapter = TracksAdapter(tracklist, this)
        binding.rvTracks.adapter = adapter
        binding.rvTracks.layoutManager = LinearLayoutManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTracksBinding.bind(view)
        mainCallback = requireActivity() as MainCallback

//        db = DBHelper(requireContext())
//        db.dropTable()
//        db.addTrack("Park Wildecki", "7", "Poznań")
//        db.addTrack("Piotrowo 3", "5", "Poznań")
//        db.addTrack("Wartostrada", "8", "Poznań")
//        db.addTrack("Park Wilsona", "2", "Poznań")
//        db.addTrack("Kampus UAM", "4", "Morawsko")




        val labels = ArrayList<BarEntry>()
        labels.add(BarEntry(0f, 11f))
        labels.add(BarEntry(1f, 10f))
        labels.add(BarEntry(2f, 12f))
        labels.add(BarEntry(3f, 6f))
        labels.add(BarEntry(4f, 14f))
        labels.add(BarEntry(5f, 8f))

        val barLabelSet = BarDataSet(labels, "Labels")

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
            xAxis.setDrawLabels(false)
            description.isEnabled = false

            binding.chartDetail!!.data = data
        }


        binding.chartDetail?.animateY(1800)

    }

    override fun onClick(position: Int) {
        Toast.makeText(requireContext(), "Click ${position}", Toast.LENGTH_SHORT).show()
        mainCallback.clickCallback(position)


    }

    override fun onLongClick(position: Int) {
        Toast.makeText(requireContext(), "longClick ${position}", Toast.LENGTH_SHORT).show()

    }

    interface MainCallback{
        fun clickCallback(position: Int)

    }


}