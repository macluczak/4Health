package com.macluczak.a2health.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentAddTrackBinding



class AddTrackFragment : Fragment(R.layout.fragment_add_track) {
    lateinit var binding: FragmentAddTrackBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTrackBinding.bind(view)

        val newMapsFragment = NewMapFragment()
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.flAddTrackMap, newMapsFragment)
            commit()
        }

        binding.addButton.setOnClickListener{
            if(newMapsFragment.markersList.size == 2 && binding.editTxt.text.isNotBlank()){
                Toast.makeText(requireContext(), "Track Created!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "invalid data", Toast.LENGTH_SHORT).show()
            }

        }


    }
}