package com.example.themoviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.themoviedb.databinding.HomeLayoutBinding

class HomeFragment: Fragment() {
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

    private fun setupView(){

    }

    private fun setupToolbar(){
        (activity as MainActivity).apply {
            setSupportActionBar(binding.toolbar)
            setupToolbar()
        }
    }
}