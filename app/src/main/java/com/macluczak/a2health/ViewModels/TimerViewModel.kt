package com.macluczak.a2health.ViewModels

import androidx.lifecycle.ViewModel

class TimerViewModel: ViewModel() {
    var seconds = 0
    var minutes = 0
    var hours = 0
    var state = "00:00:00"
    var isTimerOn = false
    var isThreadRun = false

    fun incrementSeconds(){
        seconds++
    }

    fun incrementMinutes(){
        minutes++
    }

    fun incrementHours(){
        hours++
    }

    fun setData(h: Int, m: Int, s: Int){
         seconds = s
         minutes = m
         hours = h
    }

    fun resetAll(){
         seconds = 0
         minutes = 0
         hours = 0
    }

    fun resetSeconds(){
        seconds = 0
    }

    fun resetMinutes(){
        minutes = 0
    }

    fun resetHours(){
        hours = 0
    }

    fun timerOn(){
        isTimerOn = true
    }

    fun timerOff(){
        isTimerOn = false
    }

    fun threadStart(){
        isThreadRun = true
    }

    fun threadStop(){
        isThreadRun = false
    }

}