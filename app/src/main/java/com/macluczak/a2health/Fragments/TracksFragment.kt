package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.a2health.R
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.databinding.FragmentTracksBinding

class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private lateinit var binding: FragmentTracksBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTracksBinding.bind(view)
        val db = DBHelper(requireContext())
//        db.dropTable()
//        db.addTrack("Piotrowo 3", "5", "Poznań")
        val tracklist = db.getAllTracks()

//        var tracklist = mutableListOf(
//            Track("1","droga 32", 30, "poznan"),
//            Track("2","strzelecka", 24, "poznan"),
//            Track("3","PUT", 59, "poznan"),
//            Track("4","aleje", 21, "warszawa"),
//            Track("5","droga 80", 3, "katowice"),
//            Track("6","street", 64, "gdynia"),
//            Track("7","UAM", 2, "poznan"),
//            Track("8","leśne zbocze", 13, "swarzędz"),
//        )
        val adapter = TracksAdapter(tracklist)
        binding.rvTracks.adapter = adapter
        binding.rvTracks.layoutManager = LinearLayoutManager(context)

    }

}