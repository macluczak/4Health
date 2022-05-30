package com.macluczak.a2health.Fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.macluczak.a2health.DBHelper
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewModels.TimerViewModel
import com.macluczak.a2health.databinding.FragmentTimerBinding
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Logger.global
import kotlin.concurrent.timer
import kotlin.properties.Delegates


class TimerFragment() : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    lateinit var detailCallback: DetailCallback

    private lateinit var viewModel: TimerViewModel

    lateinit var handler: Handler

    fun updateTimer(h: Int, m: Int, s: Int): String {

        val hh: String
        val mm: String
        val ss: String
        when (h) {
            in 0..9 -> hh = "0${h}"
            else -> hh = h.toString()

        }
        when (m) {
            in 0..9 -> mm = "0${m}"
            else -> mm = m.toString()

        }
        when (s) {
            in 0..9 -> ss = "0${s}"
            else -> ss = s.toString()

        }
        return "${hh}:${mm}:${ss}"
    }

    private fun chronometerWork() {
        handler = Handler(Looper.getMainLooper())
        var seconds = viewModel.seconds
        var minutes = viewModel.minutes
        var hours = viewModel.hours

        binding.timerButton.text = "Stop"

        viewModel.threadStart()

        handler.postDelayed(object : Runnable {
            override fun run() {


                if (viewModel.isThreadRun) {

//                    viewModel.incrementSeconds()
                    seconds = seconds.inc()
                    viewModel.setData(hours, minutes, seconds)

                    binding.Timer.text =
                        updateTimer(viewModel.hours, viewModel.minutes, viewModel.seconds)
                    if (viewModel.seconds == 60) {
//                        viewModel.resetSeconds()
                        seconds = 0

//                        viewModel.incrementMinutes()
                        minutes = minutes.inc()
                        viewModel.setData(hours, minutes, seconds)
                        binding.Timer.text =
                            updateTimer(viewModel.hours, viewModel.minutes, viewModel.seconds)
                        if (viewModel.minutes == 60) {
//                            viewModel.resetMinutes()
                            minutes = 0
                            hours = hours.inc()
                            viewModel.setData(hours, minutes, seconds)


//                            viewModel.incrementHours()
                            binding.Timer.text =
                                updateTimer(viewModel.hours, viewModel.minutes, viewModel.seconds)

                        }
                    }
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this)
                }


            }
        }, 1000
        )


    }

    override fun onPause() {
        viewModel.threadStop()
        super.onPause()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        viewModel.threadStop()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailCallback = requireParentFragment() as DetailCallback




        viewModel = ViewModelProvider(requireActivity()).get(TimerViewModel::class.java)
        val dbHelper = DBHelper(requireContext())

        binding.Timer.text = viewModel.state

        if (viewModel.isTimerOn) {
            chronometerWork()

        }


        binding.timerButton.setOnClickListener {
            if (!viewModel.isTimerOn) {
                viewModel.state = "00:00:00"
                binding.Timer.text = viewModel.state
                viewModel.resetAll()
                viewModel.timerOn()

                binding.timerButton.text = "Stop"

                chronometerWork()
            } else {
                viewModel.timerOff()
                viewModel.threadStop()
                viewModel.state = binding.Timer.text.toString()
                detailCallback.onTimeStop(viewModel.state)
                binding.timerButton.text = "Start"


            }
        }

    }
    interface DetailCallback{
        fun onTimeStop(time: String)
    }
}