package com.macluczak.a2health.Fragments

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TrackStats
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.Adapters.TracksAdapterMode
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentGeneralStatsBinding
import com.macluczak.a2health.databinding.FragmentGenreBinding
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class GeneralStatsFragment : Fragment(R.layout.fragment_general_stats), TracksAdapter.TrackInterface  {
    lateinit var binding: FragmentGeneralStatsBinding
    lateinit var db: DBHelper
    lateinit var mainCallback: TracksFragment.MainCallback
    var mostUsedID by Delegates.notNull<Int>()
    lateinit var statsList: ArrayList<TrackStats>

    override fun onResume() {
        super.onResume()





        lifecycleScope.launchWhenStarted {
            try {
                db = DBHelper(requireContext())

                statsList = db.getAllStats()
                val recentOrderList = statsList.reversed()
                val fdList = ArrayList<Int>()
                val recentList = ArrayList<Track>()

                Log.d("LISTCHECK", "INIT| IS FDLIST EMPTY: ${fdList.isEmpty()}")
                Log.d("LISTCHECK", "INIT| IS recentList EMPTY: ${recentList.isEmpty()}")

                if(statsList.isNotEmpty()) {

                    val numbersByElement = statsList.groupingBy { it.trackID }.eachCount()
                    mostUsedID = numbersByElement.maxByOrNull { it.value }?.key!!
                    Log.d("MOST USED TRACK", "ID: $mostUsedID")

                    val mostUsedTrack = mostUsedID.let { db.getTrack(it) }

                    binding.txtMostViewed.text = mostUsedTrack.title
                    binding.bestDay.text = mostUsedTrack.bestDay
                    binding.bestTime.text = "Best Time:${mostUsedTrack.bestTime}"
                    binding.LastDay.text = mostUsedTrack.lastDay
                    binding.LastTime.text = "Last Time:${mostUsedTrack.lastTime}"
                    binding.EstTime.text = "Est. Time: ${mostUsedTrack.duration}"
                    binding.distance.text = mostUsedTrack.distance

                    Glide.with(requireContext())
                        .load("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg")
//            .load("https://maps.googleapis.com/maps/api/streetview?size=600x300&location=46.414382,10.013988&heading=151.78&pitch=-0.76&key=${key}")
                        .fallback(R.drawable.ic_baseline_image_24)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_hide_image_24)
                        .centerCrop()
                        .into(binding.imgMostViewed)

                    for (i in recentOrderList.indices) {

                        if (recentList.size < 10) {
                            if (!fdList.contains(recentOrderList[i].trackID)) {

                                recentList.add(db.getTrack(recentOrderList[i].trackID))
                                fdList.add(recentOrderList[i].trackID)

                            }
                        }

                    }

                    val adapter = TracksAdapter(recentList, this@GeneralStatsFragment)
                    binding.rvRecent.adapter = adapter
                    binding.rvRecent.layoutManager = LinearLayoutManager(context)

                    binding.gridLayoutDiff.visibility = View.VISIBLE
                }

            } finally {

                binding.progressbar.visibility = View.GONE
                if (statsList.isNotEmpty()) {


                    binding.imgMostViewed.setOnClickListener {
                        mainCallback.clickCallback(mostUsedID)
                    }

                }
            }
        }





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralStatsBinding.bind(view)
        mainCallback = requireActivity() as TracksFragment.MainCallback



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