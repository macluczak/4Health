package com.macluczak.a2health.Activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macluczak.a2health.Fragments.DetailFragment
import com.macluczak.a2health.Fragments.TracksFragment
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.ActivityMainBinding
import javax.xml.transform.Templates

class MainActivity : AppCompatActivity(), TracksFragment.MainCallback {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fab = binding.fab
        val tracksFragment = TracksFragment()

//
//        val intent = Intent(this, TempActivity::class.java)
//        this.startActivity(intent)



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


        fab.setOnClickListener {
//            if(binding.flFragmentDetail != null){
//                val addTrackFragment = AddTrackFragment()
//
//                supportFragmentManager.beginTransaction().apply {
//                    replace(R.id.flFragmentDetail, addTrackFragment)
//                    commit()
//                }
//            }
//            else{
//                val intent = Intent(this, AddTrackActivity::class.java)
//                this.startActivity(intent)
//            }
            val intent = Intent(this, AddTrackActivity::class.java)
            this.startActivity(intent)

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
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", position)
            this.startActivity(intent)
        }

    }


}