package com.macluczak.a2health.Activity


import ZoomOutPageTransformer
import android.content.Intent
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import com.macluczak.a2health.Fragments.*
import com.macluczak.a2health.Interface.StatsInterface
import com.macluczak.a2health.Interface.UserLogInterface
import com.macluczak.a2health.R
import com.macluczak.a2health.ViewPagerAdapter
import com.macluczak.a2health.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), TracksFragment.MainCallback, SensorEventListener,
    UserLogInterface, StatsInterface{

    lateinit var binding: ActivityMainBinding
    lateinit var fab: FloatingActionButton
    private lateinit var sensorManager: SensorManager
    lateinit var userNick: String


    private fun logUserIn(user: String) {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared", 0)
        val edit = sharedScore.edit()
        edit.putString("user", user)
        edit.apply()


    }


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

        val adapter = ViewPagerAdapter(this).apply {
            addFragment(GenreFragment(), "Categories")
            addFragment(GeneralStatsFragment(), "Recent")
            addFragment(TracksFragment(), "Home")
            addFragment(StatsFragment(), "Statistics")
            addFragment(LoginFragment(), "Login")
        }

        binding.vpFragment.adapter = adapter
        binding.vpFragment.doOnAttach {
            binding.vpFragment.setCurrentItem(2, false)
        }
        binding.vpFragment.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator( binding.tab, binding.vpFragment ) { tab, position ->
            tab.text = adapter.mFragmentTitleList[position]
        }.attach()

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_history_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(3)?.setIcon(R.drawable.ic_baseline_bar_chart_24)
        binding.tab.getTabAt(4)?.setIcon(R.drawable.ic_baseline_account_circle_24)

    }


    fun setUpPagerViewTablet(){

        fab.visibility = View.INVISIBLE

        val adapter = ViewPagerAdapter(this).apply {
            addFragment(GenreFragment(), "Categories")
            addFragment(GeneralStatsFragment(), "Recent")
            addFragment(TracksFragment(), "Home")
            addFragment(StatsFragment(), "Statistics")
            addFragment(LoginFragment(), "Login")
            addFragment(AddTrackFragment(), "Add")
        }
        binding.vpFragment.adapter = adapter
        binding.vpFragment.doOnAttach {
            binding.vpFragment.setCurrentItem(2, false)
        }
        binding.vpFragment.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator( binding.tab, binding.vpFragment ) { tab, position ->
            tab.text = adapter.mFragmentTitleList[position]
        }.attach()

        binding.tab.getTabAt(0)?.setIcon(R.drawable.ic_baseline_view_stream_24)
        binding.tab.getTabAt(1)?.setIcon(R.drawable.ic_baseline_home_24)
        binding.tab.getTabAt(2)?.setIcon(R.drawable.ic_baseline_history_24)
        binding.tab.getTabAt(5)?.setIcon(R.drawable.ic_baseline_add_24)
        binding.tab.getTabAt(3)?.setIcon(R.drawable.ic_baseline_bar_chart_24)
        binding.tab.getTabAt(4)?.setIcon(R.drawable.ic_baseline_account_circle_24)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       getLoggedUser().let { if(it.isNotBlank())
           Toast.makeText(this, "Welcome, $it!", Toast.LENGTH_SHORT).show() }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userNick = ""


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

            binding.fab?.apply {

                rotationX = (-y+5) * 4f
                rotationY = -x * 4f

                rotation = -x

            }

        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun logIn(user: String) {
       this.userNick = user
        logUserIn(userNick)
        binding.vpFragment.currentItem = 2

        Toast.makeText(this, "Welcome, $user!", Toast.LENGTH_SHORT).show()
    }

    override fun logOut() {
       logUserIn(" ")
    }

    override fun getLoggedUser(): String {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getString("user", " ").toString()
    }

    override fun moveToLogin() {
        binding.vpFragment.currentItem = 4
    }


}