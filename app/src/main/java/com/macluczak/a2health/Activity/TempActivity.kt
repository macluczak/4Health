package com.macluczak.a2health.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.TimerFragment
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.ActivityTempBinding

class TempActivity : AppCompatActivity() {
    lateinit var binding: ActivityTempBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        binding =  ActivityTempBinding.inflate(layoutInflater)

        val timerFragment = TimerFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flTempleActivity, timerFragment)
            commit()
        }



    }
}