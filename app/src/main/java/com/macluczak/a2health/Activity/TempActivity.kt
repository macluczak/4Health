package com.macluczak.a2health.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import com.bumptech.glide.Glide
import com.macluczak.a2health.BuildConfig
//import com.bumptech.glide.Glide
import com.macluczak.a2health.Fragments.TimerFragment
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.ActivityTempBinding

class TempActivity : AppCompatActivity() {
    lateinit var binding: ActivityTempBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        binding =  ActivityTempBinding.inflate(layoutInflater)




//        val key = BuildConfig.GoogleMap_ApiKey
//        val textView = TextView(this)
//        textView.text = "leciiii"
//        Glide.with(this)
//            .load("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg")
////            .load("https://maps.googleapis.com/maps/api/streetview?size=600x300&location=46.414382,10.013988&heading=151.78&pitch=-0.76&key=${key}")
//            .fallback(R.drawable.edit_ic)
//            .placeholder(R.drawable.checked_fav)
//            .error(R.drawable.ic_baseline_list_alt_24)
//            .into(imageView)
//
//        tempText.text = textView.text
//






    }
}