package com.rcorchero.app.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rcorchero.app.R
import kotlinx.android.synthetic.main.activity_tv_shows.*

class TVShowsActivity : AppCompatActivity(R.layout.activity_tv_shows) {

    companion object TabPosition {
        const val AIRING_TODAY = 0
        const val POPULAR = 1
        const val TOP_RATED = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAdapter()
        setTabLayout()
    }

    private fun setAdapter() {
        viewPager.adapter = TVShowsAdapter(this)
    }

    private fun setTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                AIRING_TODAY -> getString(R.string.tab_airing_today)
                POPULAR -> getString(R.string.tab_popular)
                TOP_RATED -> getString(R.string.tab_top_rated)
                else -> null
            }
        }.attach()
    }
}