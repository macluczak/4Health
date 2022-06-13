package com.macluczak.a2health.Fragments

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.TraceCompat.isEnabled
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.macluczak.a2health.*
import com.macluczak.a2health.Adapters.Track
import com.macluczak.a2health.Adapters.TracksAdapter
import com.macluczak.a2health.ViewModels.AddViewModel
import com.macluczak.a2health.ViewModels.DetailViewModel
import com.macluczak.a2health.databinding.FragmentDetailBinding
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_NAME = "id"

class DetailFragment() : Fragment(R.layout.fragment_detail), TimerFragment.DetailCallback {

    private lateinit var binding: FragmentDetailBinding
    lateinit var trackDetail: Track
    lateinit var fab: FloatingActionButton



    companion object {
        lateinit var idMap: String

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
        val viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
//        val viewModel by viewModels<DetailViewModel>()
        Log.d("FRAGMENT MANAGER", "DETAIL TRACK")

//        viewModel.page.observe(viewLifecycleOwner, Observer<Int> { hasFinished ->
//
//        })


        if (id != null) {
//            if(viewModel.page == 0) {

            binding.flStatsLayout?.visibility = View.GONE
            binding.cvbg?.visibility = View.VISIBLE
            binding.nsvCard?.visibility = View.VISIBLE
            binding.fab?.visibility = View.VISIBLE

            idMap = id.toString()

//            if track select
            trackDetail = db.getTrack(id)

            Log.d("TIME_CALC", "DETAIL TRACK ID: ${trackDetail.id}")



            binding.titleTxt?.text = trackDetail.title
            binding.distanceTxt?.text = trackDetail.distance
            binding.EstTime?.text = "Est. Time ${trackDetail.duration}"
            binding.startAddr?.text = "Start: ${trackDetail.startAdress}"
            binding.endAddr?.text ="Finish: ${trackDetail.stopAdress}"

            binding.trackImage?.let {
                Glide.with(requireContext())
                    .load(trackDetail.locationView)

                    .fallback(R.drawable.ic_baseline_image_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_hide_image_24)
                    .centerCrop()
                    .into(it)
            }

            if (trackDetail.lastTime.isBlank()) {

                binding.lastTime?.text = "Last Time: N/A"
                binding.lastDay?.text = ""
                binding.bestTime?.text = "Best Time: N/A"
                binding.bestDay?.text = ""

            } else {
                binding.lastTime?.text = "Last Time: ${trackDetail.lastTime}"
                binding.lastDay?.text = "${trackDetail.lastDay}"
                binding.bestTime?.text = "Best Time: ${trackDetail.bestTime}"
                binding.bestDay?.text = "${trackDetail.bestDay}"

            }




            binding.mapCV?.visibility = View.VISIBLE
            val mapOfTrack = MapsFragment()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.flFragmentDetailMap, mapOfTrack)
                commit()
            }

            binding.timerCV?.visibility = View.VISIBLE
            val timerFragment = TimerFragment()
            childFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentDetailTimer, timerFragment)
                commit()
            }

            if (viewModel.page == 1) {
                binding.flStatsLayout?.visibility = View.VISIBLE
                binding.cvbg?.visibility = View.GONE
                binding.nsvCard?.visibility = View.GONE
                binding.fab?.visibility = View.GONE
                val runDetailFragment = RunDetailFragment.trackStats(id)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flStatsLayout, runDetailFragment)
                    commit()
                }
            }

                binding.fab?.setOnClickListener {
                    binding.flStatsLayout?.visibility = View.VISIBLE
                    binding.cvbg?.visibility = View.GONE
                    binding.nsvCard?.visibility = View.GONE
                    binding.fab?.visibility = View.GONE
                    val runDetailFragment = RunDetailFragment.trackStats(id)
                    viewModel.page = 1
                    parentFragmentManager.beginTransaction().apply {

                        replace(R.id.flStatsLayout, runDetailFragment)
                        commit()
                    }

                }



            } else {
//            if track not selected


                binding.mapCV?.visibility = View.GONE
                binding.timerCV?.visibility = View.GONE
                binding.fab?.visibility = View.GONE
                binding.cvbg.visibility = View.GONE
                binding.emptyView?.visibility = View.VISIBLE
            binding.mapDetailcv?.visibility = View.GONE



            }

            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (viewModel.page == 1) {
                            Log.d("CALLBACK", "callback pressed 0")

                            viewModel.page = 0
                            binding.flStatsLayout?.visibility = View.GONE
                            binding.cvbg?.visibility = View.VISIBLE
                            binding.nsvCard?.visibility = View.VISIBLE
                            binding.fab?.visibility = View.VISIBLE

                            parentFragmentManager.popBackStack()


                        } else if(viewModel.page == 0){
                            this.isEnabled = false
                            activity?.onBackPressed()
                        }
                    }
                })


        }



        override fun onTimeStop(time: String) {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val formatedDate = formatter.format(date)
            val dbHelper = DBHelper(requireContext())






            binding.lastTime?.text = "Last Time: ${time}"
            binding.lastDay?.text = "${formatedDate}"


            var runDay = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                1 -> "Sunday"
                2 -> "Monday"
                3 -> "Tuesday"
                4 -> "Wednesday"
                5 -> "Thursday"
                6 -> "Friday"
                7 -> "Saturday"
                else -> "null"
            }


            dbHelper.addTrackLastTime(trackDetail.id.toInt(), time, formatedDate)
            dbHelper.addStats(trackDetail.id.toInt(), time, runDay, formatedDate)

            val trackDetail = dbHelper.getTrack(trackDetail.id.toInt())

            Log.d("TIME_CALC", "TRACK STATS ID: ${trackDetail.id}")

            if (trackDetail.bestDay.isBlank()) {
                binding.bestTime?.text = "Best Time: ${time}"
                binding.bestDay?.text = "${formatedDate}"
                dbHelper.updateTrackBestTime(trackDetail.id.toInt(), time, formatedDate)


            } else {
                val bestTime = trackDetail.bestTime
                val bestStr = bestTime.split(":")

                val lastStr = time.split(":")

                val calcBest = ArrayList<String>()
                val calcLast = ArrayList<String>()

                for (e in 0 until lastStr.size) {
                    if (lastStr[e][0] == '0') {
                        calcLast.add(lastStr[e].drop(1))
                        calcBest.add(bestStr[e].drop(1))
                    } else {
                        calcLast.add(lastStr[e])
                        calcBest.add(bestStr[e])
                    }
                }
                Log.d("TIME_CALC", "LAST TIME: ${trackDetail.lastTime}")
                Log.d("TIME_CALC", "BEST TIME: ${trackDetail.bestTime}")

                val totalLast =
                    (calcLast[0].toInt() * 3600 + calcLast[1].toInt() * 60 + calcLast[2].toInt())
                Log.d("TIME_CALC", "LAST summary: $totalLast")
                val totalBest =
                    (calcBest[0].toInt() * 3600 + calcBest[1].toInt() * 60 + calcBest[2].toInt())
                Log.d("TIME_CALC", "BEST summary: $totalBest")


                if (totalBest >= totalLast) {
                    Log.d("TIME_CALC ", "TOTAL summary ${totalBest >= totalLast}")
                    binding.bestTime?.text = "Best Time: ${time}"
                    binding.bestDay?.text = "${formatedDate}"
                    dbHelper.updateTrackBestTime(trackDetail.id.toInt(), time, formatedDate)
                }


            }

        }


}