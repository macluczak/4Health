package com.macluczak.a2health

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.Fragments.TracksFragment
import com.macluczak.a2health.databinding.ActivityMainBinding
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(), TracksFragment.MainCallback {

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

    override fun clickCallback(position: Int) {

        if(binding.flFragmentDetail != null){
            val detailsFragment = DetailFragment.newTrack(position)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentDetail, detailsFragment)
                commit()
            }
        }
        else{
            val intent = Intent(this, TrackDetails::class.java)
            intent.putExtra("id", position)
            this.startActivity(intent)
        }

    }


}