package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.macluczak.a2health.*
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.databinding.FragmentDetailBinding
private const val ARG_NAME = "id"
class DetailFragment() : Fragment(R.layout.fragment_detail){
    private lateinit var binding: FragmentDetailBinding

    companion object {
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
            val trackDetail = db.getTrack(id)
            binding.idTxt.text = trackDetail.id
            binding.titleTxt.text = trackDetail.title
        }
        else{

            binding.idTxt.text = " "
            binding.titleTxt.text = "Choose Track"


        }



    }


}