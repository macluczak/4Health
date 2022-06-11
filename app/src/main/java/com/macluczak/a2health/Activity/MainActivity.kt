package com.macluczak.a2health.Activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.macluczak.a2health.Fragments.*
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewPagerAdapter
import com.macluczak.a2health.databinding.ActivityMainBinding
import javax.xml.transform.Templates

class MainActivity : AppCompatActivity(), TracksFragment.MainCallback {

    private lateinit var binding: ActivityMainBinding
    lateinit var fab: FloatingActionButton

    fun setUpPagerViewDefault(){

        val adapter = ViewPagerAdapter(supportFragmentManager).apply {

            addFragment(GenreFragment(), "Genre")
            addFragment(TracksFragment(), "Home")
            addFragment(GeneralStatsFragment(), "Statistics")


        }
        binding.vpFragment.adapter = adapter
        binding.vpFragment.currentItem = 1
        binding.tab.setupWithViewPager(binding.vpFragment)

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_bar_chart_24)

    }


    fun setUpPagerViewTablet(){

        fab.visibility = View.INVISIBLE

        val adapter = ViewPagerAdapter(supportFragmentManager).apply {

            addFragment(GenreFragment(), "Genre")
            addFragment(TracksFragment(), "Home")
            addFragment(GeneralStatsFragment(), "Statistics")
            addFragment(AddTrackFragment(), "Add")


        }
        binding.vpFragment.adapter = adapter
        binding.vpFragment.currentItem = 1
        binding.tab.setupWithViewPager(binding.vpFragment)

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_bar_chart_24)
        binding.tab.getTabAt(3)?.setIcon(R.drawable.ic_baseline_add_24)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab = binding.fab
//
//        val intent = Intent(this, TempActivity::class.java)
//        this.startActivity(intent)



//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flFragment, tracksFragment)
//            commit()
//        }


        if(binding.flFragmentDetail != null){

            setUpPagerViewTablet()

            val detailsFragment = DetailFragment()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentDetail, detailsFragment)
                commit()
            }
        }
        else{

            setUpPagerViewDefault()
//            val animationDrawable = binding.mainactivityBackground.background as AnimationDrawable
//            animationDrawable.apply{
//
//                setExitFadeDuration(2000)
//
//            }.start()
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