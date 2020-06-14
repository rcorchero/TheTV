package com.rcorchero.app.features

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rcorchero.app.features.TvShowsActivity.TabPosition.AIRING_TODAY
import com.rcorchero.app.features.TvShowsActivity.TabPosition.POPULAR
import com.rcorchero.app.features.TvShowsActivity.TabPosition.TOP_RATED
import com.rcorchero.app.features.airingtoday.AiringTodayFragment

class TvShowsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
        when (position) {
            AIRING_TODAY -> AiringTodayFragment()
            POPULAR -> AiringTodayFragment()
            TOP_RATED -> AiringTodayFragment()
            else -> AiringTodayFragment()
        }

    override fun getItemCount(): Int = 3
}