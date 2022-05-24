package com.macluczak.a2health

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.Fragments.TracksFragment
import com.macluczak.a2health.databinding.ActivityTrackDetailsBinding


class TrackDetails : AppCompatActivity() {

    lateinit var binding: ActivityTrackDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent?.extras?.getInt("id")

        if(id!= null) {
            val detailFragment = DetailFragment.newTrack(id)
            val flFragment = binding.flFragment

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, detailFragment)
                commit()
            }
        }
    }

}