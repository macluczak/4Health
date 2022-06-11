package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentGenreBinding
import com.macluczak.a2health.databinding.FragmentTracksBinding


class GenreFragment : Fragment(R.layout.fragment_genre), TracksAdapter.TrackInterface  {
    lateinit var binding: FragmentGenreBinding
    lateinit var db: DBHelper
    lateinit var mainCallback: TracksFragment.MainCallback

    override fun onResume() {
        super.onResume()
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

        val adapterEasy = TracksAdapter(trackEasy, this)
        binding.rcEasy.adapter = adapterEasy
        binding.rcEasy.layoutManager = GridLayoutManager(context, 2)

        val adapterMedium = TracksAdapter(trackMedium, this)
        binding.rcMedium.adapter = adapterMedium
        binding.rcMedium.layoutManager = GridLayoutManager(context, 2)

        val adapterHard = TracksAdapter(trackHard, this)
        binding.rcHard.adapter = adapterHard
        binding.rcHard.layoutManager = GridLayoutManager(context, 2)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGenreBinding.bind(view)
        mainCallback = requireActivity() as TracksFragment.MainCallback



    }

    override fun onClick(position: Int) {
//        Toast.makeText(requireContext(), "Click ${position}", Toast.LENGTH_SHORT).show()
        mainCallback.clickCallback(position)


    }

    override fun onLongClick(position: Int) {
        Toast.makeText(requireContext(), "longClick ${position}", Toast.LENGTH_SHORT).show()

    }

    interface MainCallback{
        fun clickCallback(position: Int)

    }

}