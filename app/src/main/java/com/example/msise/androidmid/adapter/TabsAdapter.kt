package com.example.msise.androidmid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.msise.androidmid.fragment.DoneFragment
import com.example.msise.androidmid.fragment.TodoFragment

class TabsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            1 -> DoneFragment()
            else -> TodoFragment()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Todo"
            1 -> "Done"
            else -> "Todo"
        }
    }

}