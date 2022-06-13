package com.macluczak.a2health.Activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.macluczak.a2health.Fragments.AddTrackFragment
import com.macluczak.a2health.R

@Suppress("DEPRECATION")
class AddTrackActivity : AppCompatActivity() {

    fun hideSystemUI(window: Window) {

        val decorView: View = window.getDecorView()
        var uiVisibility: Int = decorView.getSystemUiVisibility()
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.setSystemUiVisibility(uiVisibility)
    }

    override fun onResume() {
        super.onResume()
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            hideSystemUI(window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_track)

        val addTrackFragment = AddTrackFragment()
        window.navigationBarColor = resources.getColor(R.color.defaultMain)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAddTrack, addTrackFragment)
            commit()
        }
    }
}