package com.macluczak.a2health.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewModels.AddViewModel
import com.macluczak.a2health.ViewModels.TimerViewModel
import com.macluczak.a2health.databinding.FragmentAddTrackBinding


class AddTrackFragment : Fragment(R.layout.fragment_add_track) {
    lateinit var binding: FragmentAddTrackBinding
    lateinit var viewModel: AddViewModel

    @SuppressLint("MissingPermission")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        viewModel.setTitleName(binding.editTxt.text.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTrackBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(AddViewModel::class.java)

        val newMapsFragment = NewMapFragment()
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.flAddTrackMap, newMapsFragment)
            commit()
        }

        if(viewModel.title != "null" && viewModel.title.isNotBlank()){
            binding.editTxt.setText(viewModel.title)
        }





        binding.addButton.setOnClickListener {
            if (isOnline(requireContext())) {
                if (newMapsFragment.markersList.size == 2 && binding.editTxt.text.isNotBlank()) {
                    val dbHelper = DBHelper(requireContext())
                    dbHelper.addTrack(
                        binding.editTxt.text.toString(),

                        newMapsFragment.distance,
                        newMapsFragment.duration,

                        newMapsFragment.startAdress,
                        newMapsFragment.endAdress,

                        newMapsFragment.origin.position.latitude.toString(),
                        newMapsFragment.origin.position.longitude.toString(),

                        newMapsFragment.destination.position.latitude.toString(),
                        newMapsFragment.destination.position.longitude.toString(),
                        newMapsFragment.waypoints.toString()

                    )

                    Toast.makeText(requireContext(), "Track Created!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "invalid data", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "require internet!", Toast.LENGTH_SHORT).show()
            }

        }


    }
}