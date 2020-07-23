package com.example.themoviedb.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.themoviedb.ui.home.PopularMovieFragment
import com.example.themoviedb.ui.home.UpcomingMovieFragment

class MoviePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    companion object{
        const val ITEM_COUNT = 2
    }
    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PopularMovieFragment()
            else -> UpcomingMovieFragment()
        }
    }
}