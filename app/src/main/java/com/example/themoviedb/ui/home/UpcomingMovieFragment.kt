package com.example.themoviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.databinding.MovieListLayoutBinding
import com.example.themoviedb.domain.MovieRepository
import com.example.themoviedb.ui.App
import javax.inject.Inject

class UpcomingMovieFragment: MovieListFragment<MovieListLayoutBinding>(false) {
    override lateinit var binding: MovieListLayoutBinding
    @Inject
    override lateinit var repository: MovieRepository

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListLayoutBinding.inflate(inflater, container, false)
        binding.movieModel = movieModel
        binding.lifecycleOwner = this
        setupView()
        return binding.root
    }

    override fun setupView() {
        binding.movieRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.movieRecycler.adapter = adapter
        super.setupView()
    }
}