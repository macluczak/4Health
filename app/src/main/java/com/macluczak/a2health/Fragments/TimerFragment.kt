package com.macluczak.a2health.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.View
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.FragmentTimerBinding
import kotlinx.coroutines.*
import java.lang.String.format
import java.util.*
import kotlin.properties.Delegates
import android.os.CountDownTimer as CountDownTimer1


class TimerFragment : Fragment(R.layout.fragment_timer) {
    lateinit var binding: FragmentTimerBinding
    var isTimerOn by Delegates.notNull<Boolean>()
    lateinit var value: String
    var seconds by Delegates.notNull<Int>()
    var minutes by Delegates.notNull<Int>()
    var hours by Delegates.notNull<Int>()
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)
        isTimerOn = false
        handler = Handler(Looper.getMainLooper())


        binding.timerButton.setOnClickListener {
            if (!isTimerOn) {

                seconds = 0
                minutes = 0
                hours = 0
                binding.Timer.text = updateTimer(hours, minutes, seconds)
                isTimerOn = true

                binding.timerButton.text = "Stop"

                handler.postDelayed(object : Runnable {
                    override fun run() {
                        if (isTimerOn) {
                            seconds = (seconds.inc())
                            binding.Timer.text = updateTimer(hours, minutes, seconds)
                            if (seconds == 60) {
                                seconds = 0
                                minutes = (minutes.inc())
                                binding.Timer.text = updateTimer(hours, minutes, seconds)
                                if (minutes == 60) {
                                    minutes = 0
                                    hours = (hours.inc())
                                    binding.Timer.text =
                                        updateTimer(hours, minutes, seconds)
                                }
                            }
                            handler.postDelayed(this, 1000);
                        }


                    }
                }, 1000
                )


            } else {
                isTimerOn = false
                binding.timerButton.text = "Start"


            }
        }

    }
}