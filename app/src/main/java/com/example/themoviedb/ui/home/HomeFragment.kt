package com.example.themoviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.themoviedb.R
import com.example.themoviedb.databinding.HomeLayoutBinding
import com.example.themoviedb.ui.adapter.MoviePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    lateinit var binding: HomeLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeLayoutBinding.inflate(inflater, container, false)
        setupToolbar()
        setupView()
        return binding.root
    }

    private fun setupView() {
        setupViewpager()
    }

    private fun setupViewpager() {
        binding.viewpager.adapter =
            MoviePagerAdapter(activity as AppCompatActivity)
        TabLayoutMediator(binding.movieTabs, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> requireContext().getString(R.string.tab_popular)
                1 -> requireContext().getString(R.string.tab_upcoming)
                else -> throw NoSuchElementException()
            }
        }.attach()
    }

    private fun setupToolbar() {
        (activity as MainActivity).apply {
            setSupportActionBar(binding.toolbar)
            setupToolbar()
        }
    }
}