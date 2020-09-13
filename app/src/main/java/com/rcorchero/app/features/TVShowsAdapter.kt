package com.rcorchero.app.features

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rcorchero.app.features.TVShowsActivity.TabPosition.AIRING_TODAY
import com.rcorchero.app.features.TVShowsActivity.TabPosition.POPULAR
import com.rcorchero.app.features.TVShowsActivity.TabPosition.TOP_RATED
import com.rcorchero.app.features.tvshowslist.airingtoday.AiringTodayFragment
import com.rcorchero.app.features.tvshowslist.popular.PopularFragment
import com.rcorchero.app.features.tvshowslist.toprated.TopRatedFragment

class TVShowsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
        when (position) {
            AIRING_TODAY -> AiringTodayFragment()
            POPULAR -> PopularFragment()
            TOP_RATED -> TopRatedFragment()
            else -> AiringTodayFragment()
        }

    override fun getItemCount(): Int = 3
}