package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val id = activity?.intent?.extras?.getString("id")
        if(id != null) {
            val db = DBHelper(requireContext())
            val trackDetail = db.getTrack(id?.toInt() ?: 1)

            binding.idTxt.text = trackDetail.id
            binding.titleTxt.text = trackDetail.title
        }

    }


}