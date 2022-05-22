package com.macluczak.a2health

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.TracksFragment
import com.macluczak.a2health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationDrawable = binding.mainactivityBackground.background as AnimationDrawable
        animationDrawable.apply{

            setExitFadeDuration(2000)

        }.start()



        val tracksFragment = TracksFragment()
        val flFragment = binding.flFragment


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, tracksFragment)
            commit()
        }

    }
}