package com.macluczak.a2health

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.macluczak.a2health.Fragments.*


class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val mFragmentList = ArrayList<Fragment>()
    val mFragmentTitleList = ArrayList<String>()
//    override fun getCount(): Int {
//        return mFragmentList.size
//
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return mFragmentList[position]
//
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }


}