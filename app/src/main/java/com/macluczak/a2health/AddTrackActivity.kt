package com.macluczak.a2health

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.AddTrackFragment

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