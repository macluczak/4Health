package com.macluczak.a2health

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.Fragments.TracksFragment
import com.macluczak.a2health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val tracksFragment = TracksFragment()
        val flFragment = binding.flFragment


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, tracksFragment)
            commit()
        }

        if(binding.flFragmentDetail != null){
            val detailsFragment = DetailFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentDetail, detailsFragment)
                commit()
            }
        }else{
            val animationDrawable = binding.mainactivityBackground.background as AnimationDrawable
            animationDrawable.apply{

                setExitFadeDuration(2000)

            }.start()
        }

    }
}