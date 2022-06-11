package com.macluczak.a2health.Fragments

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
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.Adapters.TracksAdapterMode
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentGenreBinding
import com.macluczak.a2health.databinding.FragmentTracksBinding


class GenreFragment : Fragment(R.layout.fragment_genre), TracksAdapterMode.TrackInterface  {
    lateinit var binding: FragmentGenreBinding
    lateinit var db: DBHelper
    lateinit var mainCallback: TracksFragment.MainCallback

    override fun onResume() {
        super.onResume()


        lifecycleScope.launchWhenStarted {
            try {
                db = DBHelper(requireContext())

                val tracklist = db.getAllTracks()
                val trackEasy = ArrayList<Track>()
                val trackMedium = ArrayList<Track>()
                val trackHard = ArrayList<Track>()

                for (i in 0 until tracklist.size) {
                    when (tracklist[i].distance
                        .filter { it.isDigit() || it == '.' }.toFloat()) {
                        in 0f..8f -> trackEasy.add(tracklist[i])
                        in 8f..25f -> trackMedium.add(tracklist[i])
                        else -> trackHard.add(tracklist[i])

                    }
                }
                if(trackEasy.isNotEmpty()){
                    val adapterEasy = TracksAdapterMode(trackEasy, this@GenreFragment)
                    binding.rcEasy.adapter = adapterEasy
                    binding.rcEasy.layoutManager = GridLayoutManager(context, 2)
                }
                else{
                    binding.txtEasy.visibility = View.GONE
                    binding.rcEasy.visibility = View.GONE
                }

                if(trackMedium.isNotEmpty()){
                    val adapterMedium = TracksAdapterMode(trackMedium, this@GenreFragment)
                    binding.rcMedium.adapter = adapterMedium
                    binding.rcMedium.layoutManager = GridLayoutManager(context, 2)
                }

                else{
                    binding.txtMedium.visibility = View.GONE
                    binding.rcMedium.visibility = View.GONE
                }

                if(trackHard.isNotEmpty()){
                    val adapterHard = TracksAdapterMode(trackHard, this@GenreFragment)
                    binding.rcHard.adapter = adapterHard
                    binding.rcHard.layoutManager = GridLayoutManager(context, 2)
                }
                else{
                    binding.txtHard.visibility = View.GONE
                    binding.rcHard.visibility = View.GONE
                }

                binding.gridLayoutDiff.visibility = View.VISIBLE

            } finally {

                if (binding.gridLayoutDiff.isVisible) {
                    binding.progressbar.visibility = View.GONE

                }
            }
        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenreBinding.bind(view)
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