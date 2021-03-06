package com.macluczak.a2health.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.a2health.Adapters.TrackStatsAdapter
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewModels.DetailViewModel
import com.macluczak.a2health.databinding.FragmentDetailBinding
import com.macluczak.a2health.databinding.FragmentRunDetailBinding


private const val TRACKID = "TRACKID"

class RunDetailFragment : Fragment(R.layout.fragment_run_detail) {
    private lateinit var binding: FragmentRunDetailBinding

    override fun onResume() {
        super.onResume()
        val upfloat = AnimationUtils.loadAnimation(requireContext(), R.anim.up_float_quick)
        binding.rvStats.startAnimation(upfloat)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRunDetailBinding.bind(view)

        val id: Int? = arguments?.getInt(TRACKID)
        val db = DBHelper(requireContext())

        if(id != null) {

            val trackStats = db.getTrackStats(id)

            val adapter = TrackStatsAdapter(trackStats.reversed())
            binding.rvStats.adapter = adapter
            binding.rvStats.layoutManager = LinearLayoutManager(context)



        }



    }





    companion object {
        @JvmStatic
        fun trackStats(id: Int) =
            RunDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(TRACKID, id)

                }
            }
    }
}