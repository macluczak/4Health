package com.macluczak.a2health.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.AddTrackFragment
import com.macluczak.a2health.R

class AddTrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_track)

        val addTrackFragment = AddTrackFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAddTrack, addTrackFragment)
            commit()
        }
    }
}