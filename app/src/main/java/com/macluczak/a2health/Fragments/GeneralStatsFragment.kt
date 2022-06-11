package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentGeneralStatsBinding
import com.macluczak.a2health.databinding.FragmentGenreBinding


class GeneralStatsFragment : Fragment(R.layout.fragment_general_stats) {
    lateinit var binding: FragmentGeneralStatsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralStatsBinding.bind(view)
    }

}