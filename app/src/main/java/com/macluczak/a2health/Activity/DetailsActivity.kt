package com.macluczak.a2health.Activity

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.ActivityTrackDetailsBinding
import java.security.AccessController.getContext


@Suppress("DEPRECATION")
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackDetailsBinding

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

}