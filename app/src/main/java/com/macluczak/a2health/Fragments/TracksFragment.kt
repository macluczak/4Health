package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.a2health.R
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.Adapters.TracksAdapter.*
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.databinding.FragmentTracksBinding

class TracksFragment() : Fragment(R.layout.fragment_tracks), TracksAdapter.TrackInterface  {

    private lateinit var binding: FragmentTracksBinding
    lateinit var mainCallback:MainCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTracksBinding.bind(view)
        val db = DBHelper(requireContext())
        mainCallback = requireActivity() as MainCallback

//        db.dropTable()
//        db.addTrack("Park Wildecki", "7", "Poznań")
//        db.addTrack("Piotrowo 3", "5", "Poznań")
//        db.addTrack("Wartostrada", "8", "Poznań")
//        db.addTrack("Park Wilsona", "2", "Poznań")
//        db.addTrack("Kampus UAM", "4", "Morawsko")

        val tracklist = db.getAllTracks()


        val adapter = TracksAdapter(tracklist, this)
        binding.rvTracks.adapter = adapter
        binding.rvTracks.layoutManager = LinearLayoutManager(context)

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