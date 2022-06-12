@file:Suppress("DEPRECATION")

package com.macluczak.a2health.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.macluczak.a2health.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    fun hideSystemUI(window: Window) { //pass getWindow();

        val decorView: View = window.getDecorView()
        var uiVisibility: Int = decorView.getSystemUiVisibility()
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.setSystemUiVisibility(uiVisibility)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI(window)







        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels


        Log.d("SCREEN WIDTH", "WIDTH: $width")




        val runner = binding.runner


       ObjectAnimator.ofFloat(runner, "translationX", width.toFloat()).apply {
           duration = 1500
           start()
       }



        Handler(Looper.getMainLooper())
            .postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1500)
    }


}
