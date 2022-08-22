package com.macluczak.a2health.Activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.macluczak.a2health.Fragments.*
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewPagerAdapter
import com.macluczak.a2health.databinding.ActivityMainBinding
import java.util.*
import javax.xml.transform.Templates

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), TracksFragment.MainCallback, SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var fab: FloatingActionButton
    private lateinit var sensorManager: SensorManager


    fun hideSystemUI(window: Window) {

        val decorView: View = window.getDecorView()
        var uiVisibility: Int = decorView.getSystemUiVisibility()
        uiVisibility = uiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.setSystemUiVisibility(uiVisibility)
    }


    override fun onResume() {
        super.onResume()

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this,
                it,
                SensorManager.SENSOR_DELAY_UI,
                SensorManager.SENSOR_DELAY_UI)

            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                hideSystemUI(window)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    fun setUpPagerViewDefault(){

        val adapter = ViewPagerAdapter(supportFragmentManager).apply {
            addFragment(GenreFragment(), "Categories")
            addFragment(TracksFragment(), "Home")
            addFragment(GeneralStatsFragment(), "Recent")
            addFragment(StatsFragment(), "Statistics")   }
        binding.vpFragment.adapter = adapter
        binding.vpFragment.currentItem = 1
        binding.tab.setupWithViewPager(binding.vpFragment)

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_history_24)
        binding.tab.getTabAt(3)?.setIcon(R.drawable.ic_baseline_bar_chart_24)

//       binding.vpFragment.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
//           override fun onPageScrolled(
//               position: Int,
//               positionOffset: Float,
//               positionOffsetPixels: Int,
//           ) {
//           }
//
//           override fun onPageSelected(position: Int) {
//               if(binding.vpFragment.currentItem == 0){
//
//               }
//               if(binding.vpFragment.currentItem == 2){
//                   Toast.makeText(this@MainActivity, "2", Toast.LENGTH_SHORT).show()
//               }
//           }
//
//           override fun onPageScrollStateChanged(state: Int) {
//           }
//
//       })



    }


    fun setUpPagerViewTablet(){

//        fab.visibility = View.INVISIBLE

        val adapter = ViewPagerAdapter(supportFragmentManager).apply {
            addFragment(GenreFragment(), "Category")
            addFragment(TracksFragment(), "Home")
            addFragment(GeneralStatsFragment(), "Recent")
            addFragment(AddTrackFragment(), "Add")
        }
        binding.vpFragment.adapter = adapter
        binding.vpFragment.currentItem = 1
        binding.tab.setupWithViewPager(binding.vpFragment)

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_history_24)
        binding.tab.getTabAt(3)?.setIcon(R.drawable.ic_baseline_add_24)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager



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


        }


        binding.fab?.setOnClickListener {

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

    override fun onSensorChanged(event: SensorEvent?) {
                if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            binding.fab?.apply {

                rotationX = (-y+5) * 4f
                rotationY = -x * 4f

                rotation = -x

//                translationX = -x * -5
//                translationY = -y * 5
                Log.d("SENSOR", "($x, $y)")
            }

        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}