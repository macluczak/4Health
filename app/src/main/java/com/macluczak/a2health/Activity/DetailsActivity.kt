package com.macluczak.a2health.Activity

import android.app.PendingIntent.getActivity
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.Interface.UserLogInterface
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.ActivityTrackDetailsBinding
import java.security.AccessController.getContext


@Suppress("DEPRECATION")
class DetailsActivity : AppCompatActivity(), UserLogInterface{

    lateinit var binding: ActivityTrackDetailsBinding

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
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window?.statusBarColor = Color.TRANSPARENT



        window.navigationBarColor = resources.getColor(R.color.defaultMain)

        binding = ActivityTrackDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent?.extras?.getInt("id")

        if(id!= null) {


            val detailFragment = DetailFragment.newTrack(id)

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentDetail, detailFragment)
                commit()
            }
        }
    }

    override fun getLoggedUser(): String {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getString("user", " ").toString()
    }

}